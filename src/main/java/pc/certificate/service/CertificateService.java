package pc.certificate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pc.certificate.domain.Certificate;
import pc.certificate.reop.CertificateRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by wu on 17-8-14.
 */
@Service
public class CertificateService {

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private DesService desService;

    public List<Certificate> findcertificate(String name, String cardid){
        String descardid=this.desService.encrypt(cardid);
        return this.certificateRepository.findByNameAndCardid(name,descardid);
    }

    public List<Certificate> upbinding(String cardid,String id){
        List<Certificate> a=this.certificateRepository.findByCardid(cardid);
        for (int i=0;i<a.size();i++){
            a.get(i).setBinding(id);
        }
        this.certificateRepository.save(a);
        return a;
    }

    public List<Certificate> findbynameandbirthdate(String usercardid,String name){
        String birthdate=usercardid;
        String birthdates=usercardid.substring(6,14);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
        birthdate = sdf.parse(birthdates).getTime()+"";//毫秒
        }catch (ParseException e){
            e.printStackTrace();
        }
        return this.certificateRepository.findByNameAndBirthdate(name,birthdate);
    }
}
