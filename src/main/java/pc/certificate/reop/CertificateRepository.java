package pc.certificate.reop;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pc.certificate.domain.Certificate;

import java.util.List;

/**
 * Created by wu on 17-8-14.
 */
@Repository
public interface CertificateRepository extends CrudRepository<Certificate,String>{

    List<Certificate> findByNameAndCardid(String name, String cardid);

    List<Certificate> findByCardid(String cardid);

    List<Certificate> findByNameAndBirthdate(String name,String birthdate);
}
