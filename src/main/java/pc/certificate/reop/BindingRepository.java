package pc.certificate.reop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pc.certificate.domain.Binding;

/**
 * Created by wu on 17-8-28.
 */
@Repository
public interface BindingRepository extends CrudRepository<Binding,String>{

    Page<Binding> findByTypeOrderByCreatetimeDesc(Pageable pageable,String type);

    Page<Binding> findAllByOrderByCreatetimeDesc(Pageable pageable);

    Binding findById(String id);
}
