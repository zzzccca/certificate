package pc.certificate.contorl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pc.certificate.domain.Certificate;
import pc.certificate.domain.User;
import pc.certificate.domain.enums.ErrorCode;
import pc.certificate.service.CertificateService;
import pc.certificate.service.DesService;
import pc.certificate.service.SmsService;
import pc.certificate.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wu on 17-8-14.
 */
@RestController
@CrossOrigin(origins = {}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class UserContorl {

    @Autowired
    private UserService userService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private ImagegenContorl imagegenContorl;

    @Autowired
    private DesService desService;

    @Autowired
    private CertificateService certificateService;

    @RequestMapping("/user/code")
    public ErrorCode smscode(String phone) {
        String code = String.valueOf((Math.random() * 9 + 1) * 100000);
        try {
            this.smsService.Sms(code, phone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ErrorCode.SUCCESS;
    }

    @RequestMapping("/user/zhuce")
    public Object zhuche(String name, String cardid, String phone, String password, String code, HttpServletRequest request) {
        Map map = new HashMap();
        Map image = new HashMap();
        try {
            map = this.smsService.checkMsg(phone, code);//验证短信验证码返回的状态码
        } catch (IOException e) {
            e.printStackTrace();
        }
        image = this.imagegenContorl.checkimagecode(request);
        List<Certificate> c = this.certificateService.findcertificate(name, cardid);
        List<Certificate> e = this.certificateService.findbynameandbirthdate(cardid, name);
        if ((c.size() > 0 || e.size() > 0) && this.userService.finduser(cardid) == null) {
            if (map.get("errorcode").equals(200) && image.get("errorcode").equals(200)) {
                User user = new User();
                user.setName(name);
                user.setCardid(cardid);
                user.setPhone(phone);
                user.setPassword(password);
                this.userService.useradd(user);
                return ErrorCode.SUCCESS;
            } else if (!map.get("errorcode").equals(200)) {
                return map;
            } else {
                return image;
            }
        } else
            return ErrorCode.NOCERTIFICATE;
    }

    @RequestMapping("/user/login")
    public Object login(String cardid, String password, HttpServletRequest request, HttpSession session) {
        Map image = new HashMap();
        image = this.imagegenContorl.checkimagecode(request);
        String card = this.userService.encryptcardid(cardid);
        String pwd = this.userService.encryptpassword(password);
        User user = this.userService.login(card, pwd);
        if (image.get("errorcode").equals(200)) {
            if (user != null) {
                List<Certificate> shuliang = this.certificateService.upbinding(card, user.getId());
                session.setAttribute("userid", user.getId());
                Map map = new HashMap();
                map.put("errorcode", shuliang.size());
                return map;
            } else
                return ErrorCode.NAMEORPWDERROR;
        } else
            return image;
    }


    @RequestMapping("/user/findone")
    public User findone(String id, HttpSession session) {
        User user = null;
        if (StringUtils.hasText(id)) {
            user = this.userService.findone(id);
        } else {
            user = this.userService.findone(session.getAttribute("userid").toString());
        }

        String phonedes = this.desService.decrypt(user.getPhone());
        String cardiddes = this.desService.decrypt(user.getCardid());
        user.setPhone(phonedes);
        user.setCardid(cardiddes);
        return user;
    }

    @RequestMapping("/user/upphone")
    public Object upphone(HttpSession session, String phone, String code) throws IOException {
        Map map = new HashMap();
        map = this.smsService.checkMsg(phone, code);//验证短信验证码返回的状态码
        String id = session.getAttribute("userid").toString();
        if (map.get("errorcode").equals(200)) {
            this.userService.upphone(id, phone);
            return ErrorCode.SUCCESS;
        } else
            return map;
    }

    @RequestMapping("/user/uppassword")
    public ErrorCode uppassword(HttpSession session, String password) {
        String id = session.getAttribute("userid").toString();
        this.userService.uppassword(id, password);
        return ErrorCode.SUCCESS;
    }

    @RequestMapping("/user/forgetpassword")
    public Object forgetpassward(String cardid, String code, String password, HttpServletRequest request) {

        User user = this.userService.finduser(cardid);
        if (user != null) {
            String newphone = this.desService.decrypt(user.getPhone());
            Map map = new HashMap();
            Map image = new HashMap();
            try {
                map = this.smsService.checkMsg(newphone, code);//验证短信验证码返回的状态码
            } catch (IOException e) {
                e.printStackTrace();
            }
            image = this.imagegenContorl.checkimagecode(request);//图形验证码返回的状态码
            if (map.get("errorcode").equals(200) && image.get("errorcode").equals(200)) {
                this.userService.uppassword(user.getId(), password);
                return ErrorCode.SUCCESS;
            } else if (!map.get("errorcode").equals(200)) {
                return map;
            } else {
                return image;
            }
        } else
            return ErrorCode.NOCARD;
    }

    @RequestMapping("/user/checkcode")
    public Object checkcode(String cardid) {
        User user = this.userService.finduser(cardid);
        String newphone = this.desService.decrypt(user.getPhone());
        this.smscode(newphone);
        return ErrorCode.SUCCESS;
    }

    @RequestMapping("/user/delete")
    public Object deluser(String id) throws Exception {
        try {
            this.userService.deluser(id);
            return ErrorCode.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ErrorCode.NULLTEL;
        }
    }


    @RequestMapping("/user/fuzzy")
    public Object fuzzy(int page, int row, String fuzzy) {
        return this.userService.fuzzy(page, row, fuzzy);
    }


}
