package pc.certificate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pc.certificate.domain.User;
import pc.certificate.domain.enums.ErrorCode;
import pc.certificate.reop.UserRepository;

import java.util.List;

/**
 * Created by wu on 17-8-14.
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DesService desService;

    public User useradd(User user){
        String phonedes=this.desService.encrypt(user.getPhone());
        String cardiddes=this.desService.encrypt(user.getCardid());
        String passworddes=this.desService.encrypt(user.getPassword());
        user.setPhone(phonedes);
        user.setCardid(cardiddes);
        user.setPassword(passworddes);
        return this.userRepository.save(user);
    }

    public User finduser(String cardid){
        String phonedes=this.desService.encrypt(cardid);
        return this.userRepository.findByCardid(phonedes);
    }

    public User login(String cardid,String password){
        return this.userRepository.findByCardidAndPassword(cardid,password);
    }

    public User findone(String id){
        User user=this.userRepository.findById(id);
        String phonedes=this.desService.decrypt(user.getPhone());
        String cardiddes=this.desService.decrypt(user.getCardid());
        user.setPhone(phonedes);
        user.setCardid(cardiddes);
        return user;
    }

    public User upphone(String id,String phone){
        User a=this.userRepository.findById(id);
        String newphone=this.desService.encrypt(phone);
        a.setPhone(newphone);
        return this.userRepository.save(a);
    }

    public String  encryptcardid(String cardid){
        return this.desService.encrypt(cardid);
    }

    public String  encryptpassword(String password){
        String newpwd=password;
        try {
            newpwd=this.desService.EncoderByMd5(password);
        }catch (Exception e){
            e.printStackTrace();
        }
        return newpwd;
    }

    public User uppassword(String id,String uppassword){
        User a=this.userRepository.findById(id);
        String newpwd=uppassword;
        try {
        newpwd=this.desService.EncoderByMd5(uppassword);
        }catch (Exception e){
            e.printStackTrace();
        }
        a.setPassword(newpwd);
        return this.userRepository.save(a);
    }

    public void deluser(String id){
        this.userRepository.delete(id);
    }

    public Object fuzzy (String fuzzy){
        if (this.userRepository.findByNameLike(fuzzy).size()!=0){
            return this.userRepository.findByNameLike(fuzzy);
        }else if (this.userRepository.findByCardid(this.desService.encrypt(fuzzy))!=null){
            User user=this.userRepository.findByCardid(this.desService.encrypt(fuzzy));
            user.setCardid(this.desService.decrypt(user.getCardid()));
            user.setPhone(this.desService.decrypt(user.getPhone()));
            return user;
        }else if (this.userRepository.findByPhone(this.desService.encrypt(fuzzy))!=null){
            User user=this.userRepository.findByPhone(this.desService.encrypt(fuzzy));
            user.setCardid(this.desService.decrypt(user.getCardid()));
            user.setPhone(this.desService.decrypt(user.getPhone()));
            return user;
        }else
            return ErrorCode.NULLTEL;
    }
}
