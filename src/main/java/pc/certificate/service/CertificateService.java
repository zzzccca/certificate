package pc.certificate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pc.certificate.domain.Certificate;
import pc.certificate.domain.enums.ErrorCode;
import pc.certificate.reop.CertificateRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wu on 17-8-14.
 */
@Service
public class CertificateService {

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private DesService desService;

    public List<Certificate> findcertificate(String name, String cardid){
        String descardid=this.desService.encrypt(cardid);
        return this.certificateRepository.findByNameAndCardid(name,descardid);
    }

    public List<Certificate> upbinding(String cardid,String id){
        List<Certificate> a=this.certificateRepository.findByCardid(cardid);
        for (int i=0;i<a.size();i++){
            a.get(i).setBinding(id);
        }
        this.certificateRepository.save(a);
        return a;
    }

    public List<Certificate> findbynameandbirthdate(String usercardid,String name){
        String birthdate=usercardid;
        String birthdates=usercardid.substring(6,14);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
        birthdate = sdf.parse(birthdates).getTime()+"";//毫秒
        }catch (ParseException e){
            e.printStackTrace();
        }
        return this.certificateRepository.findByNameAndBirthdate(name,birthdate);
    }

    public Certificate findbyid(String id){//二维码扫描
        Certificate certificate=new Certificate();
        certificate=this.certificateRepository.findById(id);
        if (certificate.getBirthdate()!=null) {
            certificate.setBirthdate(test(certificate.getBirthdate()));
        }
        if (certificate.getApprovalofdate()!=null) {
            certificate.setApprovalofdate(test(certificate.getApprovalofdate()));
        }
        if (certificate.getIssuanceoftime()!=null) {
            certificate.setIssuanceoftime(test(certificate.getIssuanceoftime()));
        }

        return certificate;
    }

    public List<Certificate> findbycertificatename(String certificatename){
        List<Certificate> certificates= this.certificateRepository.findByCertificatenameLike(certificatename);
        List certificatenames=new ArrayList<>();
        for (int i=0;i<certificates.size();i++){
            certificatenames.add(certificates.get(i).getCertificatename());
        }
        return certificatenames;
    }

    public Certificate findbycertificate(String name,String certificatenumber,String certificatename){
        return this.certificateRepository.findByNameAndCertificatenumberAndCertificatename(name,certificatenumber,certificatename);
    }

    public String test(String milliseconds) {
        long millisecond=Long.parseLong(milliseconds);
        Date date = new Date(millisecond);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date).toString();
    }

    public Object pageall(int page,int row){
        Pageable pageable=new PageRequest(page-1,row);
        Page<Certificate> list=this.certificateRepository.findAll(pageable);

        for (int a=0;a<list.getContent().size();a++){
            String newcard=this.desService.decrypt(list.getContent().get(a).getCardid());
            list.getContent().get(a).setCardid(newcard);

            if (list.getContent().get(a).getBirthdate()!=null) {
                list.getContent().get(a).setBirthdate(test(list.getContent().get(a).getBirthdate()));
            }
            if (list.getContent().get(a).getApprovalofdate()!=null) {
                list.getContent().get(a).setApprovalofdate(test(list.getContent().get(a).getApprovalofdate()));
            }
            if (list.getContent().get(a).getIssuanceoftime()!=null) {
                list.getContent().get(a).setIssuanceoftime(test(list.getContent().get(a).getIssuanceoftime()));
            }
        }

        try {
            if (page>list.getTotalPages()){
                return ErrorCode.Lastpage;
            }else {
                Map map=new HashMap();
                map.put("total",list.getTotalElements());//数据总数
                map.put("totalpage",list.getTotalPages());//总页数
                map.put("rows",list.getContent());//分页应该显示的数据
                return map;
            }
        }catch (IllegalArgumentException e){
            return ErrorCode.Firstpage;
        }
    }
}
