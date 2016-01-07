package liuliu.demo.list.model;

import java.util.List;

/**
 * Created by Administrator on 2016/1/7.
 */
public class FacesModel {

    /**
     * id : 1
     * name : 够拽
     * code : face1
     * url : http://wanzao2.b0.upaiyun.com/system/pictures/1/original/27.png
     */
    private int id;
    private String name;
    private String code;
    private String url;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getUrl() {
        return url;
    }
}
