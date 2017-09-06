package pc.certificate.contorl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pc.certificate.domain.enums.ErrorCode;
import pc.certificate.service.NewsService;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by wu on 17-9-1.
 */
@RestController
@CrossOrigin(origins = {}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class NewsContorl {

    @Autowired
    private NewsService newsService;

    @RequestMapping("/news/uptype")
    public ErrorCode uptype(String id) {
        this.newsService.uptype(id);
        return ErrorCode.SUCCESS;
    }

    @RequestMapping("/news/view")
    public Map view(HttpSession session) {
        String userid = session.getAttribute("userid").toString();
        return this.newsService.view(userid);
    }
}
