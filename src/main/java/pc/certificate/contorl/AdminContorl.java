package pc.certificate.contorl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pc.certificate.domain.Admin;
import pc.certificate.domain.enums.ErrorCode;
import pc.certificate.service.AdminService;

import javax.servlet.http.HttpSession;

/**
 * Created by wu on 17-8-16.
 */
@RestController
@CrossOrigin(origins = {}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class AdminContorl {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/admin/login")
    public ErrorCode login(String account, String password, HttpSession session) {
        Admin admin = this.adminService.login(account, password);
        if (admin != null) {
            session.setAttribute("userid", admin.getId());
            return ErrorCode.SUCCESS;
        } else
            return ErrorCode.NAMEORPWDERROR;
    }

    @RequestMapping("/admin/logout")
    public ErrorCode logout(HttpSession session) {
        session.invalidate();
        return ErrorCode.SUCCESS;
    }
}
