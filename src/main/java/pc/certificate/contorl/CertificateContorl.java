package pc.certificate.contorl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pc.certificate.domain.Certificate;
import pc.certificate.domain.enums.ErrorCode;
import pc.certificate.service.CertificateService;

import java.util.List;

/**
 * Created by wu on 17-8-18.
 */
@RestController
@CrossOrigin(origins = {}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class CertificateContorl {
    @Autowired
    private CertificateService certificateService;

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
}
