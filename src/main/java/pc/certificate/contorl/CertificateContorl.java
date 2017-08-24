package pc.certificate.contorl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pc.certificate.domain.Certificate;
import pc.certificate.domain.enums.ErrorCode;
import pc.certificate.service.CertificateService;
import pc.certificate.service.UploadexlService;

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

    @RequestMapping("/certificate/fuzzy")
    public List<Certificate> fuzzy(String usercardid,String name){
        return this.certificateService.findbynameandbirthdate(usercardid,name);
    }

    @RequestMapping("/certificate/erweima")
    public Object findbyid(String id){
        Certificate certificate=new Certificate();
        certificate=this.certificateService.findbyid(id);

        if (certificate!=null) {
            return certificate;
        }else
            return ErrorCode.NOCERTIFICATEID;
    }

    @RequestMapping("/certificate/certificatename")
    public List findbycertificatename(String certificatename){
        return this.certificateService.findbycertificatename(certificatename);
    }

    @RequestMapping("/certificate/findbycertificate")
    public Object findbycertificate(String name,String certificatenumber,String certificatename){
        return this.certificateService.findbycertificate(name,certificatenumber,certificatename);
    }

    @RequestMapping("/certificate/pageall")
    public Object pageall(int page,int row){
        return this.certificateService.pageall(page,row);
    }

    @RequestMapping("certificate/uploadexl")
    public Object uploadexl(MultipartFile exl){
        if (exl==null) return ErrorCode.NULL;
        String name=exl.getOriginalFilename();
        long size=exl.getSize();
        if(name==null || ("").equals(name) && size==0) return ErrorCode.NULL;//以上4行皆是判断文件是否为空
        try {
        this.uploadexlService.getexl(exl,name);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ErrorCode.SUCCESS;
    }

    @RequestMapping("certificate/addcertificate")
    public ErrorCode addcertificate(String cardid){
        Certificate certificate=new Certificate();
        certificate.setCardid(cardid);
        this.certificateService.addcertificate(certificate);
        return ErrorCode.SUCCESS;
    }
}
