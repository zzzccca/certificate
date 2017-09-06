package pc.certificate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pc.certificate.domain.News;
import pc.certificate.reop.NewsRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wu on 17-9-1.
 */
@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Value("${upload.path}")
    private String image;


    public void uptype(String id) {
        News n = this.newsRepository.findOne(id);
        n.setType("已读");
        this.newsRepository.save(n);
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
