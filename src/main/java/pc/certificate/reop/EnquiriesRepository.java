package pc.certificate.reop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pc.certificate.domain.Enquiries;

/**
 * Created by wu on 17-8-28.
 */
@Repository
public interface EnquiriesRepository extends CrudRepository<Enquiries,String>{

    Page<Enquiries> findAll(Pageable pageable);
}
