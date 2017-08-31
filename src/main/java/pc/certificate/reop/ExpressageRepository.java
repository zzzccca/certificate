package pc.certificate.reop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pc.certificate.domain.Expressage;

import java.util.List;

/**
 * Created by wu on 17-8-30.
 */
@Repository
public interface ExpressageRepository extends CrudRepository<Expressage, String> {

    Page<Expressage> findByTypeOrderByCreatetimeDesc(Pageable pageable, String type);

    List<Expressage> findByType(String type);

    Page<Expressage> findAllByOrderByCreatetimeDesc(Pageable pageable);

    @Query("select e from Expressage e where e.certificatename like %?1% or e.certificatenumber like %?1% or e.name like %?1% or e.oddnumber like %?1% order by e.createtime DESC")
    Page<Expressage> findByNameOrCertificatenumberOrCertificatenameOrOddnumber(Pageable pageable, String fuzzy);

    @Query("select e from Expressage e where e.type = ?2 and (e.certificatename like %?1% or e.certificatenumber like %?1% or e.name like %?1% or e.oddnumber like %?1%)  order by e.createtime DESC")
    Page<Expressage> findByNameOrCertificatenumberOrCertificatenameOrOddnumberAndType(Pageable pageable, String fuzzy, String type);
}
