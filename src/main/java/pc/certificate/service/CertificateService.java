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

    public List<Certificate> findcertificate(String name, String cardid){
        return this.certificateRepository.findByNameAndCardid(name,cardid);
    }
}
