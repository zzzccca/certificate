package pc.certificate.contorl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pc.certificate.domain.Admin;
import pc.certificate.domain.enums.ErrorCode;
import pc.certificate.service.AdminService;

/**
 * Created by wu on 17-8-16.
 */
@RestController
@CrossOrigin(origins = {}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class AdminContorl {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/admin/login")
    public ErrorCode login(String account,String password){
        Admin admin=this.adminService.login(account,password);
        if (admin!=null){
            return ErrorCode.SUCCESS;
        }else
            return ErrorCode.NAMEORPWDERROR;
    }

    @RequestMapping("/admin/pageall")
    public Object pageall(int page,int row){
        return this.adminService.pageall(page,row);
    }
}
