package pc.certificate.reop;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pc.certificate.domain.User;

/**
 * Created by wu on 17-8-14.
 */
@Repository
public interface UserRepository extends CrudRepository<User,String>{
    User findByCardid(String cardid);

    User findByCardidAndPassword(String cardid,String password);

    User findById(String id);
}
