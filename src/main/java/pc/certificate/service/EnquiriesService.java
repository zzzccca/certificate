package pc.certificate.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pc.certificate.domain.Enquiries;
import pc.certificate.domain.enums.ErrorCode;
import pc.certificate.reop.EnquiriesRepository;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wu on 17-8-28.
 */
@Service
public class EnquiriesService {

    @Autowired
    private EnquiriesRepository enquiriesRepository;

    @Autowired
    private AdminService adminService;


    public Object addenquiries(HttpServletRequest request, String certificatenumber, String name, String certificatename) {
        String ip = this.getIp2(request);
        Enquiries enquiries = new Enquiries();
        enquiries.setEnquiriesip(ip);
        enquiries.setCertificatenumber(certificatenumber);
        enquiries.setName(name);
        enquiries.setCertificatename(certificatename);
        this.enquiriesRepository.save(enquiries);
        return ErrorCode.SUCCESS;
    }


    public String getIp2(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    public Object pageall(int page, int row, String fuzzy) {
        Pageable pageable = new PageRequest(page - 1, row);
        if (fuzzy != null && fuzzy != "") {
            Page<Enquiries> pageenquiries = this.enquiriesRepository.findByNameOrCertificatenumberOrCertificatename(pageable, fuzzy);
            return pageenquiries;
        } else {
            Page<Enquiries> list = this.enquiriesRepository.findAll(pageable);
            return this.adminService.returnpage(page, list);
        }
    }


}
