package liuliu.demo.list.model;

/**
 *
 * Created by liuliu on 2015/11/25   14:15
 *
 * @author ��ΰ��
 * @Email 1031066280@qq.com
 */
public class ItemModel {
    private String title;
    private int normal_img;
    private int pressed_img;

    public ItemModel(String title, int normal, int pressed) {
        this.title = title;
        this.normal_img = normal;
        this.pressed_img = pressed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNormal_img() {
        return normal_img;
    }

    public void setNormal_img(int normal_img) {
        this.normal_img = normal_img;
    }

    public int getPressed_img() {
        return pressed_img;
    }

    public void setPressed_img(int pressed_img) {
        this.pressed_img = pressed_img;
    }
}
