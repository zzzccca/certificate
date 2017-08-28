package pc.certificate.reop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pc.certificate.domain.User;

import java.util.List;

/**
 * Created by wu on 17-8-14.
 */
@Repository
public interface UserRepository extends CrudRepository<User,String>{
    User findByCardid(String cardid);

    Page<User> findByCardid(Pageable pageable,String cardid);

    User findByCardidAndPassword(String cardid,String password);

    User findById(String id);

    Page<User> findAll(Pageable pageable);

    List<User> findAll();

    @Query("select u from User u where u.name like %?1%")
    Page<User> findByNameLike(Pageable pageable,String name);

    Page<User> findByPhone(Pageable pageable,String phone);

}
