package pc.certificate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pc.certificate.domain.Certificate;
import pc.certificate.domain.Expressage;
import pc.certificate.domain.User;
import pc.certificate.domain.enums.ErrorCode;
import pc.certificate.reop.ExpressageRepository;

import java.util.List;

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

    public ErrorCode success(String expressageid, String oddnumber) {
        Expressage expressage = this.expressageRepository.findOne(expressageid);
        expressage.setOddnumber(oddnumber);
        expressage.setType("待寄");
        this.expressageRepository.save(expressage);
        return ErrorCode.SUCCESS;
    }

    public ErrorCode reject(String expressageid, String reject) {
        Expressage expressage = this.expressageRepository.findOne(expressageid);
        expressage.setReject(reject);
        expressage.setType("");
        this.expressageRepository.save(expressage);
        return ErrorCode.SUCCESS;
    }

    public ErrorCode uptype() {
        String type = "待寄";
        List<Expressage> listtype = this.expressageRepository.findByType(type);
        for (int i = 0; i < listtype.size(); i++) {
            listtype.get(i).setType("已寄");
            this.expressageRepository.save(listtype);
        }
        return ErrorCode.SUCCESS;
    }

    public Expressage findont(String id) {
        return this.expressageRepository.findOne(id);
    }
}
