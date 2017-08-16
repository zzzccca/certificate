package pc.certificate.contorl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pc.certificate.domain.User;
import pc.certificate.domain.enums.ErrorCode;
import pc.certificate.service.CertificateService;
import pc.certificate.service.SmsService;
import pc.certificate.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
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
    private CertificateService certificateService;

    @RequestMapping("/user/code")
    public ErrorCode smscode(String phone){
        String code=String.valueOf((Math.random()*9+1)*100000);
        try {
        this.smsService.Sms(code,phone);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ErrorCode.SUCCESS;
    }

    @RequestMapping("/user/zhuce")
    public Object zhuche(String name, String cardid, String phone,String password, String code, HttpServletRequest request) {
        Map map = new HashMap();
        Map image = new HashMap();
        try {
            map = this.smsService.checkMsg(phone, code);//验证短信验证码返回的状态码
        } catch (IOException e) {
            e.printStackTrace();
        }
        image = this.imagegenContorl.checkimagecode(request);
        if (this.certificateService.findcertificate(name, cardid).size() > 0 && this.userService.finduser(cardid)!=null) {
            if (map.get("errorcode").equals(200) && image.get("errorcode").equals(200)) {
                User user = new User();
                user.setName(name);
                user.setCardid(cardid);
                user.setPhone(phone);
                user.setPassword(password);
                this.userService.useradd(user);
                String id=this.userService.finduser(cardid).getId();
                Map idmap=new HashMap();
                idmap.put("errorcode",0);
                idmap.put("errorinfo",id);
                return idmap;
            } else if (!map.get("errorcode").equals(200)) {
                return map;
            } else {
                return image;
            }
        } else
            return ErrorCode.NOCERTIFICATE;
    }

    @RequestMapping("/user/login")
    public Object login(String cardid,String password,HttpServletRequest request){
        Map image=new HashMap();
        image = this.imagegenContorl.checkimagecode(request);
        String card=this.userService.encryptcardid(cardid);
        String pwd=this.userService.encryptpassword(password);
        User user=this.userService.login(card,pwd);
        if (image.get("errorcode").equals(200)) {
            if (user != null) {
                this.certificateService.upbinding(card,user.getId());
                Map map = new HashMap();
                map.put("errorcode", '0');
                map.put("errorinfo", user.getId());
                return map;
            } else
                return ErrorCode.NAMEORPWDERROR;
        }else
            return image;
    }


    @RequestMapping("/user/findone")
    public User upphone(String id){
       return this.userService.findone(id);
    }

    @RequestMapping("/user/upphone")
    public ErrorCode upphone(String id,String phone){
        this.userService.upphone(id,phone);
        return ErrorCode.SUCCESS;
    }

    @RequestMapping("/user/uppassword")
    public ErrorCode uppassward(String id,String password){
        this.userService.uppassword(id,password);
        return ErrorCode.SUCCESS;
    }

}
