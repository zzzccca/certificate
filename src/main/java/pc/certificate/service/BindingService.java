package pc.certificate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pc.certificate.domain.Binding;
import pc.certificate.domain.Certificate;
import pc.certificate.domain.News;
import pc.certificate.domain.User;
import pc.certificate.domain.enums.ErrorCode;
import pc.certificate.reop.BindingRepository;
import pc.certificate.reop.CertificateRepository;
import pc.certificate.reop.NewsRepository;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by wu on 17-8-28.
 */
@Service
public class BindingService {

    @Autowired
    private UseIdGenerate useIdGenerate;

    @Autowired
    private AdminService adminService;

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private UserService userService;

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private BindingRepository bindingRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Value("${upload.path}")
    private String image;

    public Object uploadimage(MultipartFile img, String certificateid, String userid) throws Exception {

        String imgname = this.useIdGenerate.createid("img");//生成图片保存在本地的名称
        String file = image + imgname;//设置文件保存的路劲
        Files.copy(img.getInputStream(), Paths.get(file));//将文件流copy到设置路径下

        Certificate certificate = this.certificateService.findbyid(certificateid);
        String certificatename = certificate.getCertificatename();
        String certificatenumber = certificate.getCertificatenumber();

        User user = this.userService.findone(userid);
        String bindingname = user.getName();

        Binding binding = new Binding();
        binding.setBindingimage(imgname);
        binding.setCertificateid(certificateid);
        binding.setUserid(userid);
        binding.setCertificatename(certificatename);
        binding.setCertificatenumber(certificatenumber);
        binding.setName(bindingname);
        binding.setType("待审核");
        this.bindingRepository.save(binding);

        News n = new News();
        n.setTitle("发起申请绑定");
        n.setContent(certificatename + ":申请绑定");
        n.setUserid(userid);
        n.setImagename(imgname);
        n.setType("未读");
        this.newsRepository.save(n);

        return ErrorCode.SUCCESS;
    }


    public Object viewbinding(int page, int row, String type, String fuzzy) {
        Pageable pageable = new PageRequest(page - 1, row);
        Page<Binding> pagebinding = null;
        if (StringUtils.hasText(type) && StringUtils.hasText(fuzzy)) {
            pagebinding = this.bindingRepository.findByNameOrCertificatenumberOrCertificatenameAndType(pageable, fuzzy, type);
        } else if (StringUtils.hasText(type)) {
            pagebinding = this.bindingRepository.findByTypeOrderByCreatetimeDesc(pageable, type);
        } else if (StringUtils.hasText(fuzzy)) {
            pagebinding = this.bindingRepository.findByNameOrCertificatenumberOrCertificatename(pageable, fuzzy);
        } else {
            pagebinding = this.bindingRepository.findAllByOrderByCreatetimeDesc(pageable);
        }
        return this.adminService.returnpage(page, pagebinding);
    }

    public ErrorCode success(String bindingid, String userid, String certificateid) {

        Binding binding = this.bindingRepository.findById(bindingid);
        binding.setType("已审核");
        this.bindingRepository.save(binding);
        Certificate certificate = new Certificate();
        certificate = this.certificateRepository.findById(certificateid);
        certificate.setBinding(userid);
        String certificatename = certificate.getCertificatename();
        this.certificateRepository.save(certificate);


        News n = new News();
        n.setTitle("申请绑定证书通过审核");
        n.setContent(certificatename + ":申请绑定成功");
        n.setUserid(userid);
        n.setType("未读");
        this.newsRepository.save(n);
        return ErrorCode.SUCCESS;
    }

    public ErrorCode reject(String bindingid, String reject) {
        Binding binding = this.bindingRepository.findById(bindingid);
        String userid = binding.getUserid();
        String certificatename = binding.getCertificatename();
        binding.setType("已驳回");
        binding.setReject(reject);
        this.bindingRepository.save(binding);

        News n = new News();
        n.setTitle("申请绑定证书已驳回");
        n.setContent(certificatename + ":驳回理由（" + reject + ")");
        n.setUserid(userid);
        n.setType("未读");
        this.newsRepository.save(n);

        return ErrorCode.SUCCESS;
    }

    public Binding findone(String id) {
        return this.bindingRepository.findOne(id);
    }

    public ErrorCode removebinding(String bindingid){
        Binding b=this.findone(bindingid);
        b.setType("待审核");
        this.bindingRepository.save(b);

        Certificate c=this.certificateRepository.findById(b.getCertificateid());
        c.setBinding("");
        c.setBindingtype("");
        this.certificateRepository.save(c);

        return ErrorCode.SUCCESS;
    }
}
