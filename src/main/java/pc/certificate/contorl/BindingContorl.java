package pc.certificate.contorl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pc.certificate.domain.enums.ErrorCode;
import pc.certificate.service.BindingService;
import pc.certificate.utils.SessionUtil;

import javax.servlet.http.HttpSession;

/**
 * Created by wu on 17-8-28.
 */
@RestController
@CrossOrigin(origins = {}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class BindingContorl {

    @Autowired
    private BindingService bindingService;

    @RequestMapping("/binding/tobinding")
    public Object tobinding(@RequestParam("filename") MultipartFile img, String certificateid, HttpSession session) {
        String userid = session.getAttribute("userid").toString();
        if (img == null) return ErrorCode.NULL;
        try {
            this.bindingService.uploadimage(img, certificateid, userid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ErrorCode.SUCCESS;
    }

    @RequestMapping("/binding/viewbinding")
    public Object viewbinding(int page, int row, String type, String fuzzy, HttpSession session) {
        if (SessionUtil.islogin(session) == false) {
            return ErrorCode.NOLOGIN;
        } else {
            return this.bindingService.viewbinding(page, row, type, fuzzy);
        }
    }


    @RequestMapping("/binding/success")
    public ErrorCode successbinding(String bindingid, String userid, String certificateid, HttpSession session) {
        if (SessionUtil.islogin(session) == false) {
            return ErrorCode.NOLOGIN;
        } else {
            return this.bindingService.success(bindingid, userid, certificateid);
        }
    }

    @RequestMapping("/binding/reject")
    public ErrorCode reject(String bindingid, String reject, HttpSession session) {
        if (SessionUtil.islogin(session) == false) {
            return ErrorCode.NOLOGIN;
        } else {
            return this.bindingService.reject(bindingid, reject);
        }
    }

    @RequestMapping("/binding/findone")
    public Object findone(String id, HttpSession session) {
        if (SessionUtil.islogin(session) == false) {
            return ErrorCode.NOLOGIN;
        } else {
            return this.bindingService.findone(id);
        }
    }

    @RequestMapping("/binding/remove")
    public ErrorCode removebinding(String bindingid){
        return this.bindingService.removebinding(bindingid);
    }
}
