package pc.certificate.reop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
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

    Certificate findById(String id);

    Certificate findByNameAndCertificatenumberAndCertificatename(String name,String certificatenumber,String certificatename);

    Page<Certificate> findAll(Pageable pageable);

    @Query("select u from Certificate u where u.certificatename like %?1%")
    List<Certificate> findByCertificatenameLike(String certificatename);
}
