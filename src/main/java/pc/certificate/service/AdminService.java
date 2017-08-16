package pc.certificate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pc.certificate.domain.Admin;
import pc.certificate.domain.User;
import pc.certificate.domain.enums.ErrorCode;
import pc.certificate.reop.AdminRepository;
import pc.certificate.reop.UserRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wu on 17-8-16.
 */
@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DesService desService;

    public Admin login(String account, String password){
        return this.adminRepository.findByAccountAndPassword(account,password);
    }

    public Object pageall(int page,int row){
        Pageable pageable=new PageRequest(page-1,row);
        Page<User> list=this.userRepository.findAll(pageable);

        for (int a=0;a<list.getContent().size();a++){
            String newcard=this.desService.decrypt(list.getContent().get(a).getCardid());
            String newphone=this.desService.decrypt(list.getContent().get(a).getPhone());
            list.getContent().get(a).setCardid(newcard);
            list.getContent().get(a).setPhone(newphone);
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
