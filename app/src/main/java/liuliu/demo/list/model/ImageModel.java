package liuliu.demo.list.model;

import java.util.List;

/**
 * Created by Administrator on 2015/12/29.
 */
public class ImageModel {

    /**
     * image : http://img.hesq.com.cn/fresh/upload/ad/20151130/1448836122892.jpg
     * title : 精品主食
     * t1 : 速冻水饺
     * t2 : 馄饨 方便面
     * link : ../product/list.php?bid=69
     */
    private String image;
    private String title;
    private String t1;
    private String t2;
    private String link;

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setT1(String t1) {
        this.t1 = t1;
    }

    public void setT2(String t2) {
        this.t2 = t2;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getT1() {
        return t1;
    }

    public String getT2() {
        return t2;
    }

    public String getLink() {
        return link;
    }
}
