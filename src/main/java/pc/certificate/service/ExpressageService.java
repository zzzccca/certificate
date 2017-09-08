package pc.certificate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pc.certificate.domain.Certificate;
import pc.certificate.domain.Expressage;
import pc.certificate.domain.News;
import pc.certificate.domain.User;
import pc.certificate.domain.enums.ErrorCode;
import pc.certificate.reop.ExpressageRepository;
import pc.certificate.reop.NewsRepository;

/**
 * Created by wu on 17-8-30.
 */
@Service
public class ExpressageService {

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private UserService userService;

    @Autowired
    private ExpressageRepository expressageRepository;

    @Autowired
    private NewsRepository newsRepository;


    public Object addexpressage(String phone, String address, String certificateid, String userid) {
        if (StringUtils.hasText(certificateid) && StringUtils.hasText(userid)) {

            Certificate certificate = this.certificateService.findbyid(certificateid);
            String certificatename = certificate.getCertificatename();
            String certificatenumber = certificate.getCertificatenumber();

            User user = this.userService.findone(userid);
            String name = user.getName();

            Expressage expressage = new Expressage();
            expressage.setCertificatename(certificatename);
            expressage.setCertificatenumber(certificatenumber);
            expressage.setCertificateid(certificateid);
            expressage.setName(name);
            expressage.setUserid(userid);
            expressage.setPhone(phone);
            expressage.setAddress(address);
            expressage.setType("待审核");
            this.expressageRepository.save(expressage);

            News n = new News();
            n.setTitle("发起申请寄件");
            n.setContent(certificatename + ":申请寄件");
            n.setUserid(userid);
            n.setType("未读");
            this.newsRepository.save(n);

            return ErrorCode.SUCCESS;
        } else
            return ErrorCode.NULL;
    }

    public Object pageall(int page, int row, String type, String fuzzy) {
        Pageable pageable = new PageRequest(page - 1, row);
        if (StringUtils.hasText(type) && StringUtils.hasText(fuzzy)) {
            return this.expressageRepository.findByNameOrCertificatenumberOrCertificatenameOrOddnumberAndType(pageable, fuzzy, type);
        } else if (StringUtils.hasText(type)) {
            return this.expressageRepository.findByTypeOrderByCreatetimeDesc(pageable, type);
        } else if (StringUtils.hasText(fuzzy)) {
            return this.expressageRepository.findByNameOrCertificatenumberOrCertificatenameOrOddnumber(pageable, fuzzy);
        } else
            return this.expressageRepository.findAllByOrderByCreatetimeDesc(pageable);
    }

    public ErrorCode success(String expressageid) {
        Expressage expressage = this.expressageRepository.findOne(expressageid);

        String certificatename = expressage.getCertificatename();
        String userid = expressage.getUserid();
        expressage.setType("待寄");
        this.expressageRepository.save(expressage);

        News n = new News();
        n.setTitle("申请证书寄件通过审核");
        n.setContent(certificatename + ":申请寄件通过审核");
        n.setUserid(userid);
        n.setType("未读");
        this.newsRepository.save(n);

        return ErrorCode.SUCCESS;
    }

    public ErrorCode reject(String expressageid, String reject) {
        Expressage expressage = this.expressageRepository.findOne(expressageid);

        String certificatename = expressage.getCertificatename();
        String userid = expressage.getUserid();
        expressage.setReject(reject);
        expressage.setType("");
        this.expressageRepository.save(expressage);

        News n = new News();
        n.setTitle("申请证书寄件失败");
        n.setContent(certificatename + ":申请寄件失败（" + reject + ")");
        n.setUserid(userid);
        n.setType("未读");
        this.newsRepository.save(n);
        return ErrorCode.SUCCESS;
    }

    public Expressage findont(String id) {
        return this.expressageRepository.findOne(id);
    }

    public void addoddnumber(String cardid, Expressage e) {
        String userid = this.userService.finduser(cardid).getId();
        Expressage ex = this.expressageRepository.findByUseridAndCertificatenumber(userid, e.getCertificatenumber());
        ex.setOddnumber(e.getOddnumber());
        ex.setType("已寄");
        this.expressageRepository.save(ex);
    }
}
