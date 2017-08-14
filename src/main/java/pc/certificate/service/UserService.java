package pc.certificate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pc.certificate.domain.User;
import pc.certificate.reop.UserRepository;

/**
 * Created by wu on 17-8-14.
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User useradd(User user){
        return this.userRepository.save(user);
    }

    public User finduser(String cardid){
        return this.userRepository.findByCardid(cardid);
    }
}
