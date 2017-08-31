package pc.certificate.reop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pc.certificate.domain.Admin;

/**
 * Created by wu on 17-8-16.
 */
@Repository
public interface AdminRepository extends CrudRepository<Admin, String> {
    Admin findByAccountAndPassword(String account, String password);

    Page<Admin> findAll(Pageable pageable);
}
