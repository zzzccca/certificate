package pc.certificate.domain;

import com.tim.bos.BaseEntity;
import com.tim.bos.Bostype;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by wu on 17-9-1.
 */
@Bostype("N05")
@Entity
@Table(name = "c_news")
public class News extends BaseEntity implements Serializable {

    private String content;//消息内容
    private String userid;//消息发送对象
    private String imagename;//消息图片
    private String type;//消息状态

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
