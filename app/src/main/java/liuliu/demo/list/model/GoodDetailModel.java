package liuliu.demo.list.model;

import java.util.List;

/**
 * 商品列表信息
 * Created by Administrator on 2016/1/5.
 */
public class GoodDetailModel {
    private String id;
    private String name;
    private String image;
    private String feature;
    private String sales;
    private String stock;
    private String price;
    private String priceSales;
    private boolean isSales;
    private boolean isRecom;
    private boolean isNew;
    private boolean isLimit;
    private boolean isRush;
    private boolean isArea;
    private boolean isPresent;
    private boolean isDrive;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public void setIsSales(boolean isSales) {
        this.isSales = isSales;
    }

    public void setIsRecom(boolean isRecom) {
        this.isRecom = isRecom;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public void setIsLimit(boolean isLimit) {
        this.isLimit = isLimit;
    }

    public void setIsRush(boolean isRush) {
        this.isRush = isRush;
    }

    public void setIsArea(boolean isArea) {
        this.isArea = isArea;
    }

    public void setIsPresent(boolean isPresent) {
        this.isPresent = isPresent;
    }

    public void setIsDrive(boolean isDrive) {
        this.isDrive = isDrive;
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

    public String getFeature() {
        return feature;
    }

    public String getSales() {
        return sales;
    }

    public String getStock() {
        return stock;
    }

    public String getPriceSales() {
        return priceSales;
    }

    public void setPriceSales(String priceSales) {
        this.priceSales = priceSales;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isIsSales() {
        return isSales;
    }

    public boolean isIsRecom() {
        return isRecom;
    }

    public boolean isIsNew() {
        return isNew;
    }

    public boolean isIsLimit() {
        return isLimit;
    }

    public boolean isIsRush() {
        return isRush;
    }

    public boolean isIsArea() {
        return isArea;
    }

    public boolean isIsPresent() {
        return isPresent;
    }

    public boolean isIsDrive() {
        return isDrive;
    }
}
