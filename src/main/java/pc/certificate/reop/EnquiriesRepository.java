package pc.certificate.reop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pc.certificate.domain.Enquiries;

/**
 * Created by wu on 17-8-28.
 */
@Repository
public interface EnquiriesRepository extends CrudRepository<Enquiries,String>{

    Page<Enquiries> findAll(Pageable pageable);

    @Query("select e from Enquiries e where e.name like %?1% or e.certificatenumber like %?1% or e.certificatename like %?1%")
    Page<Enquiries> findByNameOrCertificatenumberOrCertificatename(Pageable pageable,String plain);
}
