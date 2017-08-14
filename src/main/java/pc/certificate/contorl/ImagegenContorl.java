package pc.certificate.contorl;

/**
 * Created by Administrator on 2017/1/10.
 */

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pc.certificate.domain.enums.ErrorCode;
import pc.certificate.service.ImageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ImagegenContorl {
    @RequestMapping(value = "/toImg")
    public String toImg() {
        return "image/image";
    }

    //登录获取验证码
    @RequestMapping("/imagecode")
    public String getSysManageLoginCode(HttpServletResponse response,
                                        HttpServletRequest request) {
        response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Set-Cookie", "name=value; HttpOnly");//设置HttpOnly属性,防止Xss攻击
        response.setDateHeader("Expire", 0);
        ImageService randomValidateCode = new ImageService();
        try {
            randomValidateCode.getRandcode(request, response, "imagecode");// 输出图片方法
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //验证码验证
//    @RequestMapping(value = "/checkimagecode")
    public Map checkimagecode(HttpServletRequest request) {
        String validateCode = request.getParameter("validateCode");
        //1:获取session验证码的信息
        String code = (String) request.getSession().getAttribute(request.getSession().getId() + "imagecode");
        //2:判断验证码是否正确
            Map map=new HashMap();
        if (!StringUtils.isEmpty(validateCode) && validateCode.equalsIgnoreCase(code)) {
            request.getSession().removeAttribute(request.getSession().getId() + "imagecode");//注销session
            map.put("errorcode",200);
            map.put("errorinfo","图形验证成功");
            return map;
        }
        map.put("errorcode",404);
        map.put("errorinfo","图形验证失败");
        return map;
    }

}
