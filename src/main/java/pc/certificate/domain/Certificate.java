package pc.certificate.domain;

import com.tim.bos.BaseEntity;
import com.tim.bos.Bostype;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by wu on 17-8-14.
 */
@Bostype("C05")
@Entity
@Table(name = "c_certificate")
public class Certificate extends BaseEntity implements Serializable {
    private String cardid;//身份证
    private String certificatenumber;//证书编号
    private String birthdate;//出生年月
    private String certificatename;//证书名称
    private String name;//名字
    private String gender;//性别
    private String approvalofdate;//批准日期
    private String issuanceoftime;//签发时间
    private String issuanceagencies;//签发单位
    private String reviewcommittee;//评审委员会
    private String major;//专业类别
    private String level;//级别
    private String referencenumber;//文号
    private String bindingtype;//绑定状态
    private String bindingimage;//绑定申请图片
    private String bindingphoto;//持证人照片
    private String binding;//绑定人
    private String trueorfalse;//是否可寄
    private String getcertificate;//领证人
    private String getcardid;//领证人身份证
    private String gettype;//领证状态
    private String gettime;//领证时间

    public Certificate() {
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getCertificatenumber() {
        return certificatenumber;
    }

    public void setCertificatenumber(String certificatenumber) {
        this.certificatenumber = certificatenumber;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getCertificatename() {
        return certificatename;
    }

    public void setCertificatename(String certificatename) {
        this.certificatename = certificatename;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getApprovalofdate() {
        return approvalofdate;
    }

    public void setApprovalofdate(String approvalofdate) {
        this.approvalofdate = approvalofdate;
    }

    public String getIssuanceoftime() {
        return issuanceoftime;
    }

    public void setIssuanceoftime(String issuanceoftime) {
        this.issuanceoftime = issuanceoftime;
    }

    public String getIssuanceagencies() {
        return issuanceagencies;
    }

    public void setIssuanceagencies(String issuanceagencies) {
        this.issuanceagencies = issuanceagencies;
    }

    public String getReviewcommittee() {
        return reviewcommittee;
    }

    public void setReviewcommittee(String reviewcommittee) {
        this.reviewcommittee = reviewcommittee;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getReferencenumber() {
        return referencenumber;
    }

    public void setReferencenumber(String referencenumber) {
        this.referencenumber = referencenumber;
    }

    public String getBindingtype() {
        return bindingtype;
    }

    public void setBindingtype(String bindingtype) {
        this.bindingtype = bindingtype;
    }

    public String getBindingimage() {
        return bindingimage;
    }

    public void setBindingimage(String bindingimage) {
        this.bindingimage = bindingimage;
    }

    public String getBindingphoto() {
        return bindingphoto;
    }

    public void setBindingphoto(String bindingphoto) {
        this.bindingphoto = bindingphoto;
    }

    public String getTrueorfalse() {
        return trueorfalse;
    }

    public void setTrueorfalse(String trueorfalse) {
        this.trueorfalse = trueorfalse;
    }

    public String getGetcertificate() {
        return getcertificate;
    }

    public void setGetcertificate(String getcertificate) {
        this.getcertificate = getcertificate;
    }

    public String getGetcardid() {
        return getcardid;
    }

    public void setGetcardid(String getcardid) {
        this.getcardid = getcardid;
    }

    public String getGettype() {
        return gettype;
    }

    public void setGettype(String gettype) {
        this.gettype = gettype;
    }

    public String getGettime() {
        return gettime;
    }

    public void setGettime(String gettime) {
        this.gettime = gettime;
    }
}
