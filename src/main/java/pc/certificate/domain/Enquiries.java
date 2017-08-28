package pc.certificate.domain;

import com.tim.bos.BaseEntity;
import com.tim.bos.Bostype;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by wu on 17-8-28.
 */
@Bostype("E05")
@Entity
@Table(name = "c_enquiries")
public class Enquiries extends BaseEntity implements Serializable {//第三方查证记录

    private String certificatenumber;//证书编号
    private String name;//持有者姓名
    private String certificatename;//证书名
    private String enquiriesip;//第三方查询ip地址

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

    public String getCertificatename() {
        return certificatename;
    }

    public void setCertificatename(String certificatename) {
        this.certificatename = certificatename;
    }

    public String getEnquiriesip() {
        return enquiriesip;
    }

    public void setEnquiriesip(String enquiriesip) {
        this.enquiriesip = enquiriesip;
    }
}
