package pc.certificate.reop;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pc.certificate.domain.News;

import java.util.List;

/**
 * Created by wu on 17-9-1.
 */
@Repository
public interface NewsRepository extends CrudRepository<News, String> {

    List<News> findByUserid(String userid);

    List<News> findByUseridAndType(String userid, String type);
}
