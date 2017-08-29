package pc.certificate.domain;

import com.tim.bos.BaseEntity;
import com.tim.bos.Bostype;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by wu on 17-8-28.
 */
@Bostype("B05")
@Entity
@Table(name = "c_binding")
public class Binding extends BaseEntity implements Serializable {

    private String certificateid;//证书id
    private String userid;//申请绑定用户的id
    private String bindingimage;//申请绑定用的图片
    private String type;//绑定状态

    public String getCertificateid() {
        return certificateid;
    }

    public void setCertificateid(String certificateid) {
        this.certificateid = certificateid;
    }

    public String getBindingimage() {
        return bindingimage;
    }

    public void setBindingimage(String bindingimage) {
        this.bindingimage = bindingimage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
