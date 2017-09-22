package pc.certificate.contorl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pc.certificate.domain.Certificate;
import pc.certificate.domain.enums.ErrorCode;
import pc.certificate.reop.CertificateRepository;
import pc.certificate.service.ExpressageService;
import pc.certificate.service.UploadexlService;
import pc.certificate.utils.SessionUtil;

import javax.servlet.http.HttpSession;

/**
 * Created by wu on 17-8-30.
 */
@RestController
@CrossOrigin(origins = {}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class ExpressageContorl {

    @Autowired
    private ExpressageService expressageService;

    @Autowired
    private UploadexlService uploadexlService;

    @Autowired
    private CertificateRepository certificateRepository;

    @RequestMapping("/expressage/addexpressage")
    public Object addperessage(String phone, String address, String certificateid, HttpSession session) {
        Certificate c = this.certificateRepository.findOne(certificateid);
        if (c.getTrueorfalse().equals("可寄送") && !StringUtils.hasText(c.getGetcardid())) {
            String userid = session.getAttribute("userid").toString();
            return this.expressageService.addexpressage(phone, address, certificateid, userid);
        } else
            return ErrorCode.ERRORCERTIFICATE;
    }

    @RequestMapping("/expressage/pageall")
    public Object pageall(int page, int row, String type, String fuzzy, HttpSession session) {
        if (SessionUtil.issession(session) == false) {
            return ErrorCode.NOLOGIN;
        } else {
            return this.expressageService.pageall(page, row, type, fuzzy);
        }
    }

    @RequestMapping("/expressage/success")
    public ErrorCode success(String expressageid, HttpSession session) {
        if (SessionUtil.issession(session) == false) {
            return ErrorCode.NOLOGIN;
        } else {
            return this.expressageService.success(expressageid);
        }
    }

    @RequestMapping("/expressage/reject")
    public ErrorCode reject(String expressageid, String reject, HttpSession session) {
        if (SessionUtil.issession(session) == false) {
            return ErrorCode.NOLOGIN;
        } else {
            return this.expressageService.reject(expressageid, reject);
        }
    }

    @RequestMapping("/expressage/findone")
    public Object findone(String id, HttpSession session) {
        if (SessionUtil.issession(session) == false) {
            return ErrorCode.NOLOGIN;
        } else {
            return this.expressageService.findont(id);
        }
    }

    @RequestMapping("expressage/uploadexl")
    public Object uploadexl(@RequestParam("filename") MultipartFile exl) {
        String name = exl.getOriginalFilename();
        long size = exl.getSize();
        if (exl == null) {
            return ErrorCode.NULL;
        } else if (name == null || ("").equals(name) || size == 0) {
            return ErrorCode.NULL;//以上4行皆是判断文件是否为空
        }
        boolean a = name.matches("^.+\\.(?i)(xls|xlsx)$");//正则匹配文件后缀
        if (a == false) {
            return ErrorCode.ERRORFILE;
        } else {
            try {
                this.uploadexlService.getoddnumber(exl, name);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ErrorCode.SUCCESS;
        }
    }
}
