package liuliu.demo.list.ui.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.tsz.afinal.annotation.view.CodeNote;

import liuliu.demo.list.R;
import liuliu.demo.list.base.BaseActivity;

/**
 * 1.用来显示余额的页面
 * 2.积分
 * 3.收货地址
 * 4.代金券
 * 5.站内消息
 * Created by Administrator on 2016/1/15.
 */
public class YuEActivity extends BaseActivity {
    @CodeNote(id = R.id.my_title_tv)
    TextView my_title_tv;
    @CodeNote(id = R.id.my_yue_tv)
    TextView my_yue_tv;
    @CodeNote(id = R.id.my_back_ll, click = "onClick")
    LinearLayout my_back_iv;
    @CodeNote(id = R.id.my_yue_show_ll)
    LinearLayout my_yue_show_ll;
    String result;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_yue);
        result = mUtils.IntentGet(getIntent(), "desc");
    }

    @Override
    public void initEvents() {
        if (result.contains("%")) {
            switch (result.split("%")[0]) {
                case "yue":
                    my_title_tv.setText("我的余额");
                    my_yue_show_ll.setVisibility(View.VISIBLE);
                    my_yue_tv.setText(result.split("%")[1]);
                    break;
            }
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_back_ll:
                this.finish();
                break;
        }
    }
}
