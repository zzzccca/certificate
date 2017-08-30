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
    private String certificatename;//证书名
    private String certificatenumber;//证书编号
    private String name;//申请人姓名
    private String rejuct;//驳回理由

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

    public String getCertificatename() {
        return certificatename;
    }

    public void setCertificatename(String certificatename) {
        this.certificatename = certificatename;
    }

    public String getCertificatenumber() {
        return certificatenumber;
    }

    public void setCertificatenumber(String certificatenumber) {
        this.certificatenumber = certificatenumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRejuct() {
        return rejuct;
    }

    public void setRejuct(String rejuct) {
        this.rejuct = rejuct;
    }
}
