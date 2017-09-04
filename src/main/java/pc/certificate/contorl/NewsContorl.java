package pc.certificate.contorl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pc.certificate.domain.enums.ErrorCode;
import pc.certificate.service.NewsService;

import java.util.Map;

/**
 * Created by wu on 17-9-1.
 */
@RestController
@CrossOrigin(origins = {}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class NewsContorl {

    @Autowired
    private NewsService newsService;


    @RequestMapping("/news/addnew")
    public ErrorCode addnews(String content, String userid) {
        this.newsService.addnews(content, userid);
        return ErrorCode.SUCCESS;
    }

    @RequestMapping("/news/addimagenew")
    public ErrorCode addimagenew(@RequestParam("filename") MultipartFile img, String content, String userid) throws Exception {
        this.newsService.uploadimg(img, content, userid);
        return ErrorCode.SUCCESS;
    }

    @RequestMapping("/news/uptype")
    public ErrorCode uptype(String id) {
        this.newsService.uptype(id);
        return ErrorCode.SUCCESS;
    }

    @RequestMapping("/news/view")
    public Map view(String userid) {
        return this.newsService.view(userid);
    }
}
