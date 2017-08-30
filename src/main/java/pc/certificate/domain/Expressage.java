package pc.certificate.domain;

import com.tim.bos.BaseEntity;
import com.tim.bos.Bostype;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by wu on 17-8-30.
 */
@Bostype("X05")
@Entity
@Table(name = "c_expressage")
public class Expressage extends BaseEntity implements Serializable {

    private String certificatename;//证书名
    private String certificatenumber;//证书编号
    private String name;//申请寄件人姓名
    private String type;//寄件状态
    private String userid;//申请人id
    private String certificateid;//所寄证书id
    private String phone;//派件所用手机
    private String address;//派件地址
    private String oddnumber;//快递单号
    private String reject;//驳回信息

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

    public String getCertificateid() {
        return certificateid;
    }

    public void setCertificateid(String certificateid) {
        this.certificateid = certificateid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOddnumber() {
        return oddnumber;
    }

    public void setOddnumber(String oddnumber) {
        this.oddnumber = oddnumber;
    }

    public String getReject() {
        return reject;
    }

    public void setReject(String reject) {
        this.reject = reject;
    }
}
