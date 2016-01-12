package liuliu.demo.list.ui.last_frag;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.tsz.afinal.annotation.view.CodeNote;

import liuliu.demo.list.R;
import liuliu.demo.list.base.BaseFragment;
import liuliu.demo.list.ui.activity.DetailListsActivity;

/**
 * 商品详情
 * Created by Administrator on 2015/12/2.
 */
public class GoodDetailFragment extends BaseFragment {
    @CodeNote(id = R.id.count_jia_btn, click = "onClick")
    Button count_jia_btn;
    @CodeNote(id = R.id.count_jian_btn, click = "onClick")
    Button count_jian_btn;
    @CodeNote(id = R.id.count_gouwuche_btn, click = "onClick")
    Button count_gouwuche_btn;
    @CodeNote(id = R.id.num_count_et)
    EditText num_count;
    @CodeNote(id = R.id.good_name_tv)
    TextView name_tv;//商品名称
    int count;
    String good_id;//需要显示的商品编码
    DetailListsActivity mIntails = DetailListsActivity.mIntails;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_good_detail);
    }

    @Override
    public void initEvents() {
        good_id = mIntails.getDesc().split("\\?")[1];//解析出商品id
        name_tv.setText(good_id);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.count_jia_btn:
                count = Integer.parseInt(num_count.getText().toString());
                num_count.setText((count + 1));
                break;
            case R.id.count_jian_btn:
                count = Integer.parseInt(num_count.getText().toString());
                if (count == 1) {
                    num_count.setText(count);
                } else {
                    num_count.setText((count - 1));
                }
                break;
            case R.id.count_gouwuche_btn:
                break;
        }
    }
}
