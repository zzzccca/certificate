package pc.certificate.reop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pc.certificate.domain.Binding;

import java.util.List;

/**
 * Created by wu on 17-8-28.
 */
@Repository
public interface BindingRepository extends CrudRepository<Binding, String> {

    Page<Binding> findByTypeOrderByCreatetimeDesc(Pageable pageable, String type);

    Page<Binding> findAllByOrderByCreatetimeDesc(Pageable pageable);

    Binding findById(String id);

    List<Binding> findByUserid(String userid);

    @Query("select b from Binding b where b.certificatename like %?1% or b.certificatenumber like %?1% or b.name like %?1% order by b.createtime DESC")
    Page<Binding> findByNameOrCertificatenumberOrCertificatename(Pageable pageable, String fuzzy);

    @Query("select b from Binding b where b.type = ?2 and (b.certificatename like %?1% or b.certificatenumber like %?1% or b.name like %?1%)  order by b.createtime DESC")
    Page<Binding> findByNameOrCertificatenumberOrCertificatenameAndType(Pageable pageable, String fuzzy, String type);
}
