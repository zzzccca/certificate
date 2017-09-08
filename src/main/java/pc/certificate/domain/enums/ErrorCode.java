package pc.certificate.domain.enums;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by liutim on 2016/10/4.
 */
@JsonSerialize(using = ErrorCodeSerializer.class)
public enum ErrorCode {
    NOLOGIN(7777,"请登陆")
    ,NAMEORPWDERROR(7001,"用户名密码不正确")
    ,REPEAT(7002,"证书已现场领取")
    ,SUCCESS(0,"操作成功")
    ,NOCERTIFICATE(5555,"查无您的证书或已经注册,无法注册！")
    ,NOCARD(6666,"身份证错误!")
    ,NOCERTIFICATEID(4444,"查无此证!")
    ,PASSPORTFORBID(77778,"该账号已经被禁用或未扫码绑定!")
    ,CANNOTBINDSECOND(77779,"绑定不成功：一个微信智能绑定一个账号")
    ,SHANGJIA(8888,"该商品属于禁用分类中，上架失败!")
    ,WEIXINTUI(8877,"该商品属于禁用分类中，微信推送失败!")
    ,ERRORFILESIZE(8866,"文件太大")
    ,ERRORFILE(8867,"错误文件")
    ,REPEATNUMBER(3,"该手机号已存在")
    ,NULLTEL(2,"查不到该用户")
    ,NULL(4,"参数为空")
    ,ERRORCERTIFICATE(8,"不可寄送该证书")
    ,Firstpage(6,"第一页")
    ,Lastpage(7,"最后一页")
    ,AUTHNUMBERFALSE(5,"手机号错误")
    ,AUTHCODEFALSE(1,"验证码错误");



    public int getErrorcode() {
        return errorcode;
    }

    public String getErrorinfo() {
        return errorinfo;
    }

    private int errorcode;
    private String errorinfo;

    private ErrorCode(int errorcode,String errorinfo){
        this.errorcode=errorcode;
        this.errorinfo=errorinfo;
    }

    public String toString(){
        return "{\"errorcode\":"+this.errorcode+",\"errorinfo\":\""+this.errorinfo+"\"}";
    }

}
