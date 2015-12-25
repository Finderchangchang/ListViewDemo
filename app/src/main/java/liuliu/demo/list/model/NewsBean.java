package liuliu.demo.list.model;

/**
 * Created by Administrator on 2015/12/24.
 */
public class NewsBean {
    private String _id;//唯一编码
    private int day;//日期
    private String des;//描述
    private String lunar;
    private int month;//当前月
    private String pic;//内容显示图片
    private String title;//消息标题
    private int year;//所属年份

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setLunar(String lunar) {
        this.lunar = lunar;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String get_id() {
        return _id;
    }

    public int getDay() {
        return day;
    }

    public String getDes() {
        return des;
    }

    public String getLunar() {
        return lunar;
    }

    public int getMonth() {
        return month;
    }

    public String getPic() {
        return pic;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }
}
