package pc.certificate.contorl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pc.certificate.domain.Binding;
import pc.certificate.domain.enums.ErrorCode;
import pc.certificate.service.BindingService;

/**
 * Created by wu on 17-8-28.
 */
@RestController
@CrossOrigin(origins = {}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class BindingContorl {

    @Autowired
    private BindingService bindingService;

    @RequestMapping("/binding/tobinding")
    public Object tobinding(@RequestParam("filename") MultipartFile img, String certificateid, String userid){
        if (img==null) return ErrorCode.NULL;
        try {
        this.bindingService.uploadimage(img,certificateid,userid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ErrorCode.SUCCESS;
    }

    @RequestMapping("/binding/viewbinding")
    public Object viewbinding(int page,int row,String type){
        return this.bindingService.viewbinding(page,row,type);
    }


    @RequestMapping("/binding/success")
    public ErrorCode successbinding(String bindingid,String userid,String certificateid){
        return this.bindingService.success(bindingid,userid,certificateid);
    }

    @RequestMapping("/binding/reject")
    public ErrorCode reject(String bindingid){
        return this.bindingService.reject(bindingid);
    }

}
