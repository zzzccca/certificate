package pc.certificate.contorl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pc.certificate.domain.enums.ErrorCode;
import pc.certificate.service.ExpressageService;

/**
 * Created by wu on 17-8-30.
 */
@RestController
@CrossOrigin(origins = {}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class ExpressageContorl {

    @Autowired
    private ExpressageService expressageService;

    @RequestMapping("/expressage/addexpressage")
    public Object addperessage(String phone, String address, String certificateid, String userid) {
        return this.expressageService.addexpressage(phone, address, certificateid, userid);
    }

    @RequestMapping("/expressage/pageall")
    public Object pageall(int page, int row, String type, String fuzzy) {
        return this.expressageService.pageall(page, row, type, fuzzy);
    }

    @RequestMapping("/expressage/success")
    public ErrorCode success(String expressageid, String oddnumber) {
        return this.expressageService.success(expressageid, oddnumber);
    }

    @RequestMapping("/expressage/reject")
    public ErrorCode reject(String expressageid, String reject) {
        return this.expressageService.reject(expressageid, reject);
    }

    @RequestMapping("/expressage/uptype")
    public ErrorCode uptype() {
        return this.expressageService.uptype();
    }
}
