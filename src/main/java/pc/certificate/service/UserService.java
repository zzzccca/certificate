package pc.certificate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pc.certificate.domain.User;
import pc.certificate.reop.UserRepository;

import java.util.Iterator;

/**
 * Created by wu on 17-8-14.
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DesService desService;

    @Autowired
    private AdminService adminService;

    public User useradd(User user) {
        String phonedes = this.desService.encrypt(user.getPhone());
        String cardiddes = this.desService.encrypt(user.getCardid());
        String passworddes = this.encryptpassword(user.getPassword());
        user.setPhone(phonedes);
        user.setCardid(cardiddes);
        user.setPassword(passworddes);
        return this.userRepository.save(user);
    }

    public User finduser(String cardid) {
        String phonedes = this.desService.encrypt(cardid);
        return this.userRepository.findByCardid(phonedes);
    }

    public User login(String cardid, String password) {
        return this.userRepository.findByCardidAndPassword(cardid, password);
    }

    public User findone(String id) {
        return this.userRepository.findById(id);
    }

    public User upphone(String id, String phone) {
        User a = this.userRepository.findById(id);
        String newphone = this.desService.encrypt(phone);
        a.setPhone(newphone);
        return this.userRepository.save(a);
    }

    public String encryptcardid(String cardid) {
        return this.desService.encrypt(cardid);
    }

    public String encryptpassword(String password) {
        String newpwd = password;
        try {
            newpwd = this.desService.EncoderByMd5(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newpwd;
    }

    public User uppassword(String id, String uppassword) {
        User a = this.userRepository.findById(id);
        String newpwd = uppassword;
        try {
            newpwd = this.desService.EncoderByMd5(uppassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        a.setPassword(newpwd);
        return this.userRepository.save(a);
    }

    public void deluser(String id) {
        this.userRepository.delete(id);
    }

    public Object fuzzy(int page, int row, String fuzzy) {
        Pageable pageable = new PageRequest(page - 1, row);
        Page<User> pageuser = null;
        if (StringUtils.hasText(fuzzy)) {//如果有过滤信息
            String newfuzzy = this.desService.encrypt(fuzzy);
            pageuser = this.userRepository.findByNameLikeOrCardidOrPhone(pageable, fuzzy, newfuzzy);
            Iterator<User> ite = pageuser.iterator();
            while (ite.hasNext()) {
                User u = ite.next();
                u.setPhone(this.desService.decrypt(u.getPhone()));
                u.setCardid(this.desService.decrypt(u.getCardid()));
            }
            return pageuser;
        } else {
            Page<User> list = this.userRepository.findAll(pageable);

            for (int a = 0; a < list.getContent().size(); a++) {
                String newcard = this.desService.decrypt(list.getContent().get(a).getCardid());
                String newphone = this.desService.decrypt(list.getContent().get(a).getPhone());
                list.getContent().get(a).setCardid(newcard);
                list.getContent().get(a).setPhone(newphone);
            }
            return this.adminService.returnpage(page, list);
        }
    }
}
