package pc.certificate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pc.certificate.domain.Certificate;
import pc.certificate.reop.CertificateRepository;

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
        String descardid=this.desService.decrypt(cardid);
        return this.certificateRepository.findByNameAndCardid(name,descardid);
    }

    public void upbinding(String cardid,String id){
        List<Certificate> a=this.certificateRepository.findByCardid(cardid);
        for (int i=0;i<a.size();i++){
            a.get(i).setBinding(id);
        }
    }
}
