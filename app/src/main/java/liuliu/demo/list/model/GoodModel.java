package liuliu.demo.list.model;

import java.util.List;

/**
 * Created by Administrator on 2015/12/28.
 */
public class GoodModel {
    /**
     * id : 145
     * name : 洋葱（黄色）
     * image : http://img.hesq.com.cn/fresh/upload/product/20150804/1438657275267.jpg
     * price : 2
     * priceSales : 2
     */
    private String id;
    private String name;
    private String image;
    private String price;
    private String priceSales;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceSales() {
        return priceSales;
    }

    public void setPriceSales(String priceSales) {
        this.priceSales = priceSales;
    }
}
