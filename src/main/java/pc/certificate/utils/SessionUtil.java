package pc.certificate.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;

/**
 * Created by wu on 17-9-22.
 */
public class SessionUtil {


    public static boolean islogin(HttpSession session) {
        return StringUtils.hasText((String) session.getAttribute("userid"));
    }
}
