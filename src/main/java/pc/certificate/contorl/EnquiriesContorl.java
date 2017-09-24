package pc.certificate.contorl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pc.certificate.domain.enums.ErrorCode;
import pc.certificate.service.EnquiriesService;
import pc.certificate.utils.SessionUtil;

import javax.servlet.http.HttpSession;

/**
 * Created by wu on 17-8-28.
 */
@RestController
@CrossOrigin(origins = {}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class EnquiriesContorl {

    @Autowired
    private EnquiriesService enquiriesService;

    @RequestMapping("/enquiries/pageall")
    public Object pageall(int page, int row, String fuzzy, HttpSession session) {
        if (SessionUtil.islogin(session) == false) {
            return ErrorCode.NOLOGIN;
        } else {
            return this.enquiriesService.pageall(page, row, fuzzy);
        }
    }
}
