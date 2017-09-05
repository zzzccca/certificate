package pc.certificate.contorl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pc.certificate.domain.Certificate;
import pc.certificate.domain.enums.ErrorCode;
import pc.certificate.service.CertificateService;
import pc.certificate.service.DesService;
import pc.certificate.service.EnquiriesService;
import pc.certificate.service.UploadexlService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by wu on 17-8-18.
 */
@RestController
@CrossOrigin(origins = {}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class CertificateContorl {
    @Autowired
    private CertificateService certificateService;

    @Autowired
    private UploadexlService uploadexlService;

    @Autowired
    private EnquiriesService enquiriesService;

    @Autowired
    private DesService desService;

    @RequestMapping("/certificate/fuzzy")
    public List<Certificate> fuzzy(String usercardid, String name) {
        return this.certificateService.findbynameandbirthdate(usercardid, name);
    }

    @RequestMapping("/certificate/mycertificate")
    public Object mycertificate(HttpSession session) {
        String userid = session.getAttribute("userid").toString();
        return this.certificateService.findbybinding(userid);
    }

    @RequestMapping("/certificate/erweima")
    public Object erweima(HttpServletRequest request, String id) {
        Certificate certificate = new Certificate();
        certificate = this.certificateService.findbyid(id);
        certificate.setCardid(this.desService.decrypt(certificate.getCardid()));

        if (certificate != null) {
            this.enquiriesService.addenquiries(request, certificate.getCertificatenumber(), certificate.getName(), certificate.getCertificatename());
            return certificate;
        } else
            return ErrorCode.NOCERTIFICATEID;
    }

    @RequestMapping("/certificate/findbyid")
    public Object findbyid(String id) {
        Certificate certificate = this.certificateService.findbyid(id);
        if (certificate != null) {
            certificate.setCardid(this.desService.decrypt(certificate.getCardid()));
            return certificate;
        } else
            return ErrorCode.NOCERTIFICATEID;
    }

    @RequestMapping("/certificate/certificatename")
    public List findbycertificatename(String certificatename) {
        return this.certificateService.findbycertificatename(certificatename);
    }

    @RequestMapping("/certificate/findbycertificate")
    public Object findbycertificate(HttpServletRequest request, String name, String certificatenumber, String certificatename) {
        Certificate certificate = this.certificateService.findbycertificate(name, certificatenumber, certificatename);
        if (certificate != null) {
            this.enquiriesService.addenquiries(request, certificate.getCertificatenumber(), certificate.getName(), certificate.getCertificatename());
        }
        return certificate;
    }

    @RequestMapping("certificate/uploadexl")
    public Object uploadexl(@RequestParam("filename") MultipartFile exl) {
        String name = exl.getOriginalFilename();
        long size = exl.getSize();
        if (exl == null) {
            return ErrorCode.NULL;
        } else if (name == null || ("").equals(name) || size == 0) {
            return ErrorCode.NULL;//以上4行皆是判断文件是否为空
        }
        boolean a = name.matches("^.+\\.(?i)(xls|xlsx)$");//正则匹配文件后缀
        if (a == false) {
            return ErrorCode.ERRORFILE;
        } else {
            try {
                this.uploadexlService.getexl(exl, name);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ErrorCode.SUCCESS;
        }
    }

    @RequestMapping("certificate/addcertificate")
    public ErrorCode addcertificate(String cardid) {
        Certificate certificate = new Certificate();
        certificate.setCardid(cardid);
        this.certificateService.addcertificate(certificate);
        return ErrorCode.SUCCESS;
    }

    @RequestMapping("/certificate/upcertificate")
    public Object upcertificate(String id, String certificatename, String name, String cardid, String birthdate, String certificatenumber, String issuanceagencies, String approvalofdate, String issuanceoftime) {
        return this.certificateService.upcertificate(id, certificatename, name, cardid, birthdate, certificatenumber, issuanceagencies, approvalofdate, issuanceoftime);

    }

    @RequestMapping("/certificate/fuzzycertificate")//模糊查找证书名
    public List<Certificate> fuzzycertificate(String certificatename) {
        return this.certificateService.certificatename(certificatename);
    }

    @RequestMapping("/certificate/blur")
    public Object blur(int page, int row, String fuzzy) {
        return this.certificateService.fuzzy(page, row, fuzzy);

    }
}
