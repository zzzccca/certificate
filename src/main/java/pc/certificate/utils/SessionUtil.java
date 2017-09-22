package pc.certificate.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;

/**
 * Created by wu on 17-9-22.
 */
public class SessionUtil {


    public static boolean issession(HttpSession session) {
        if (StringUtils.hasText(session.getAttribute("userid").toString())) {
            return false;
        } else
            return true;
    }
}
