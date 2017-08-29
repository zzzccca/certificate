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
import pc.certificate.domain.enums.ErrorCode;
import pc.certificate.reop.BindingRepository;
import pc.certificate.reop.CertificateRepository;

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
    private CertificateRepository certificateRepository;

    @Autowired
    private BindingRepository bindingRepository;

    @Value("${upload.path}")
    private String image;

    public Object uploadimage(MultipartFile img,String certificateid,String userid) throws Exception{

        String imgname=this.useIdGenerate.createid("img");//生成图片保存在本地的名称
        String file=image+imgname;//设置文件保存的路劲

        Files.copy(img.getInputStream(), Paths.get(file));//将文件流copy到设置路径下

        Binding binding=new Binding();
        binding.setBindingimage(imgname);
        binding.setCertificateid(certificateid);
        binding.setUserid(userid);
        binding.setType("待审核");
        this.bindingRepository.save(binding);
        return ErrorCode.SUCCESS;
    }


    public Object viewbinding(int page,int row,String type) {
        Pageable pageable = new PageRequest(page - 1, row);
        Page<Binding> pagebinding = null;
        if (StringUtils.hasText(type)) {
            pagebinding = this.bindingRepository.findByTypeOrderByCreatetimeDesc(pageable, type);
        } else {
            pagebinding = this.bindingRepository.findAllByOrderByCreatetimeDesc(pageable);
        }
        return this.adminService.returnpage(page,pagebinding);
    }

    public ErrorCode success(String bindingid,String userid,String certificateid){

        Binding binding=this.bindingRepository.findById(bindingid);
        binding.setType("已审核");
        this.bindingRepository.save(binding);
        Certificate certificate=new Certificate();
        certificate=this.certificateRepository.findById(certificateid);
        certificate.setBinding(userid);
        this.certificateRepository.save(certificate);

        return ErrorCode.SUCCESS;
    }

    public ErrorCode reject(String bindingid){
        Binding binding=this.bindingRepository.findById(bindingid);
        binding.setType("已驳回");
        this.bindingRepository.save(binding);

        return ErrorCode.SUCCESS;
    }
}
