package pc.certificate.contorl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pc.certificate.domain.Certificate;
import pc.certificate.domain.enums.ErrorCode;
import pc.certificate.service.CertificateService;
import pc.certificate.service.DesService;
import pc.certificate.service.EnquiriesService;
import pc.certificate.service.UploadexlService;
import pc.certificate.utils.SessionUtil;

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
        if (StringUtils.hasText(certificate.getGetcardid())) {
            certificate.setGetcardid(this.desService.decrypt(certificate.getGetcardid()));
        }

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
            if (StringUtils.hasText(certificate.getGetcardid())) {
                certificate.setGetcardid(this.desService.decrypt(certificate.getGetcardid()));
            }
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
    public Object uploadexl(@RequestParam("filename") MultipartFile exl, HttpSession session) {
        if (SessionUtil.islogin(session) == false) {
            return ErrorCode.NOLOGIN;
        } else {
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
    }

    @RequestMapping("certificate/addcertificate")
    public ErrorCode addcertificate(String cardid) {
        Certificate certificate = new Certificate();
        certificate.setCardid(cardid);
        this.certificateService.addcertificate(certificate);
        return ErrorCode.SUCCESS;
    }

    @RequestMapping("/certificate/upcertificate")
    public Object upcertificate(String id, String cardid,String certificatenumber,String otherscard,String birthdate,String certificatename, String name,String gender,String approvalofdate,
                                String issuanceoftime,String issuanceagencies,String reviewcommittee,String objectone,String valueone,String objecttwo,String valuetwo,String objectthree,String valuethree,
                                String objectfour,String valuefour,String objectfive,String valuefive,String objectsix,String valuesix,String referencenumber,String bindingtype,
                                String bindingimage,String bindingphoto,String binding,String trueorfalse,String getcertificate,String getcardid,String gettype,String gettime,HttpSession session) {
        if (SessionUtil.islogin(session) == false) {
            return ErrorCode.NOLOGIN;
        } else {
            Certificate c=this.certificateService.findbyid(id);
            c.setCardid(cardid);
            c.setCertificatenumber(certificatenumber);
            c.setOtherscard(otherscard);
            c.setBirthdate(birthdate);
            c.setCertificatename(certificatename);
            c.setName(name);
            c.setGender(gender);
            c.setApprovalofdate(approvalofdate);
            c.setIssuanceoftime(issuanceoftime);
            c.setIssuanceagencies(issuanceagencies);
            c.setReviewcommittee(reviewcommittee);
            c.setObjectone(objectone);
            c.setValueone(valueone);
            c.setObjecttwo(objecttwo);
            c.setValuetwo(valuetwo);
            c.setObjectthree(objectthree);
            c.setValuethree(valuethree);
            c.setObjectfour(objectfour);
            c.setValuefour(valuefour);
            c.setObjectfive(objectfive);
            c.setValuefive(valuefive);
            c.setObjectsix(objectsix);
            c.setValuesix(valuesix);
            c.setReferencenumber(referencenumber);
            c.setBindingtype(bindingtype);
            c.setBindingimage(bindingimage);
            c.setBindingphoto(bindingphoto);
            c.setBinding(binding);
            c.setTrueorfalse(trueorfalse);
            c.setGetcertificate(getcertificate);
            c.setGetcardid(getcardid);
            c.setGettype(gettype);
            c.setGettime(gettime);
            return this.certificateService.upcertificate(c);
        }

    }

    @RequestMapping("/certificate/fuzzycertificate")//模糊查找证书名
    public Object fuzzycertificate(String certificatename, HttpSession session) {
        if (SessionUtil.islogin(session) == false) {
            return ErrorCode.NOLOGIN;
        } else {
            return this.certificateService.certificatename(certificatename);
        }
    }

    @RequestMapping("/certificate/blur")
    public Object blur(int page, int row, String fuzzy, HttpSession session) {
        if (SessionUtil.islogin(session) == false) {
            return ErrorCode.NOLOGIN;
        } else {
            return this.certificateService.fuzzy(page, row, fuzzy);
        }
    }

    @RequestMapping("/certificate/pickup")
    public ErrorCode pickup(String certificateid, String getcertificate, String getcardid, HttpSession session) {
        if (SessionUtil.islogin(session) == false) {
            return ErrorCode.NOLOGIN;
        } else {
            return this.certificateService.getcertificate(certificateid, getcertificate, getcardid);
        }
    }
}
