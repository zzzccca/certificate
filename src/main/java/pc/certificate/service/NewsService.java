package pc.certificate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pc.certificate.domain.News;
import pc.certificate.reop.NewsRepository;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wu on 17-9-1.
 */
@Service
public class NewsService {

    @Autowired
    private UseIdGenerate useIdGenerate;

    @Autowired
    private NewsRepository newsRepository;

    @Value("${upload.path}")
    private String image;

    public Object addnews(String content, String userid) {
        News n = new News();
        n.setContent(content);
        n.setUserid(userid);
        n.setType("未读");
        return this.newsRepository.save(n);
    }

    public Object uploadimg(MultipartFile img, String content, String userid) throws Exception {

        String imgname = this.useIdGenerate.createid("img");//生成图片保存在本地的名称
        String file = image + imgname;//设置文件保存的路劲
        Files.copy(img.getInputStream(), Paths.get(file));//将文件流copy到设置路径下

        News n = new News();
        n.setContent(content);
        n.setUserid(userid);
        n.setType("未读");
        n.setImagename(imgname);
        return this.newsRepository.save(n);
    }

    public void uptype(String userid) {
        List<News> listn = this.newsRepository.findByUserid(userid);
        for (int i = 0; i < listn.size(); i++) {
            listn.get(i).setType("已读");
            this.newsRepository.save(listn);
        }
    }

    public Map view(String userid) {
        String type = "未读";
        List<News> listn = this.newsRepository.findByUseridAndType(userid, type);
        List<News> list = this.newsRepository.findByUserid(userid);
        Map m = new HashMap();
        m.put("number", listn.size());
        m.put("contents", list);
        return m;
    }
}
