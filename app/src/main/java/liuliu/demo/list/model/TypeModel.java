package liuliu.demo.list.model;

import java.util.List;

/**
 * 分类数据Model
 * Created by Administrator on 2015/12/30.
 */
public class TypeModel {
    private String id;
    private String sid;
    private String bid;
    private String name;
    private String image;
    private boolean isPreferential;
    private boolean isPresent;

    public TypeModel() {

    }

    public TypeModel(String sid, String bid, String name, String image, boolean isPreferential, boolean isPresent) {
        this.sid = sid;
        this.bid = bid;
        this.name = name;
        this.image = image;
        this.isPreferential = isPreferential;
        this.isPresent = isPresent;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setIsPreferential(boolean isPreferential) {
        this.isPreferential = isPreferential;
    }

    public void setIsPresent(boolean isPresent) {
        this.isPresent = isPresent;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public boolean isIsPreferential() {
        return isPreferential;
    }

    public boolean isIsPresent() {
        return isPresent;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }
}
