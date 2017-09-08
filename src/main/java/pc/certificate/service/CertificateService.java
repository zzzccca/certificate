package pc.certificate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pc.certificate.domain.Certificate;
import pc.certificate.domain.enums.ErrorCode;
import pc.certificate.reop.CertificateRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wu on 17-8-14.
 */
@Service
public class CertificateService {

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private DesService desService;

    @Autowired
    private AdminService adminService;

    public List<Certificate> findcertificate(String name, String cardid) {
        String descardid = this.desService.encrypt(cardid);
        return this.certificateRepository.findByNameAndCardid(name, descardid);
    }

    public List<Certificate> upbinding(String cardid, String id) {
        List<Certificate> a = this.certificateRepository.findByCardid(cardid);
        for (int i = 0; i < a.size(); i++) {
            a.get(i).setBinding(id);
        }
        this.certificateRepository.save(a);
        return a;
    }

    public List<Certificate> findbynameandbirthdate(String usercardid, String name) {
        String birthdates = usercardid.substring(6, 14);
        String birthdate = null;
        try {
            birthdate = this.tomilliseconds(birthdates);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.certificateRepository.findByNameAndBirthdateAndBindingIsNull(name, birthdate);
    }

    public Object findbybinding(String userid) {
        return this.certificateRepository.findByBinding(userid);
    }

    public String tomilliseconds(String birthdates) throws Exception {
        String birthdate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        birthdate = sdf.parse(birthdates).getTime() + "";//毫秒
        return birthdate;
    }

    public Certificate findbyid(String id) {//二维码扫描
        return this.certificateRepository.findById(id);
    }

    public List<Certificate> findbycertificatename(String certificatename) {
        List<Certificate> certificates = this.certificateRepository.findByCertificatenameLike(certificatename);
        List certificatenames = new ArrayList<>();
        for (int i = 0; i < certificates.size(); i++) {
            certificatenames.add(certificates.get(i).getCertificatename());
        }
        return certificatenames;
    }

    public List<Certificate> certificatename(String certificatename) {
        return this.certificateRepository.findByCertificatenameLike(certificatename);

    }

    public Certificate findbycertificate(String name, String certificatenumber, String certificatename) {
        return this.certificateRepository.findByNameAndCertificatenumberAndCertificatename(name, certificatenumber, certificatename);
    }

    public String todata(String milliseconds) {//此功能交付前台完成
        long millisecond = Long.parseLong(milliseconds);
        Date date = new Date(millisecond);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date).toString();
    }


    public void addcertificate(Certificate certificate) {
        if (certificate.getCardid() != null)
            certificate.setCardid(this.desService.encrypt(certificate.getCardid()));//添加时加密身份证好
        try {
            if (certificate.getBirthdate() != null)
                certificate.setBirthdate(tomillisecond(certificate.getBirthdate()));//将出生日期转成毫秒数
            if (certificate.getApprovalofdate() != null)
                certificate.setApprovalofdate(tomillisecond(certificate.getApprovalofdate()));//将批准日期转成毫秒数
            if (certificate.getIssuanceoftime() != null)
                certificate.setIssuanceoftime(tomillisecond(certificate.getIssuanceoftime()));//将签发时间转成毫秒数
        } catch (Exception e) {
            e.printStackTrace();
        }
        certificate.setGettype("未领取");
        this.certificateRepository.save(certificate);
    }

    public String tomillisecond(String birthdates) throws Exception {
        String birthdate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        birthdate = sdf.parse(birthdates).getTime() + "";//毫秒
        return birthdate;
    }

    public Object upcertificate(String id, String certificatename, String name, String cardid, String birthdate, String certificatenumber, String issuanceagencies, String approvalofdate, String issuanceoftime) {
        Certificate certificate = this.certificateRepository.findById(id);
        if (certificate != null) {
            certificate.setCertificatename(certificatename);
            certificate.setName(name);
            certificate.setCardid(this.desService.encrypt(cardid));
            certificate.setCertificatenumber(certificatenumber);
            certificate.setIssuanceagencies(issuanceagencies);
            certificate.setApprovalofdate(approvalofdate);
            certificate.setBirthdate(birthdate);
            certificate.setIssuanceoftime(issuanceoftime);
            this.certificateRepository.save(certificate);
            return ErrorCode.SUCCESS;
        } else
            return ErrorCode.NOCERTIFICATEID;
    }


    public Object fuzzy(int page, int row, String fuzzy) {
        Pageable pageable = new PageRequest(page - 1, row);
        if (StringUtils.hasText(fuzzy)) {//如果有过滤信息
            Page<Certificate> pagecertificate = this.certificateRepository.findByCertificatenameOrNameOrCertificatenumber(pageable, fuzzy);
            Iterator<Certificate> ite = pagecertificate.iterator();
            while (ite.hasNext()) {
                Certificate c = ite.next();
                c.setCardid(this.desService.decrypt(c.getCardid()));
                if (StringUtils.hasText(c.getGetcardid())) {
                    c.setGetcardid(this.desService.decrypt(c.getGetcardid()));
                }
            }
            return pagecertificate;
        } else {
            Page<Certificate> list = this.certificateRepository.findAll(pageable);

            for (int a = 0; a < list.getContent().size(); a++) {
                String newcard = this.desService.decrypt(list.getContent().get(a).getCardid());
                list.getContent().get(a).setCardid(newcard);
                if (StringUtils.hasText(list.getContent().get(a).getGetcardid())) {
                    String newgetcard = this.desService.decrypt(list.getContent().get(a).getGetcardid());
                    list.getContent().get(a).setGetcardid(newgetcard);
                }
            }

            return this.adminService.returnpage(page, list);
        }
    }

    public ErrorCode getcertificate(String certificateid, String getcertificate, String getcardid) {
        Certificate c = this.certificateRepository.findOne(certificateid);
        c.setGetcertificate(getcertificate);
        c.setGetcardid(this.desService.encrypt(getcardid));
        c.setGettype("现场领取");
        Date nowtime = new Date(System.currentTimeMillis());
        String t = String.valueOf(nowtime);
        c.setGettime(t);
        this.certificateRepository.save(c);
        return ErrorCode.SUCCESS;
    }
}