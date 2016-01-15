package liuliu.demo.list.ui.first_frag;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import net.tsz.afinal.annotation.view.CodeNote;

import liuliu.demo.list.R;
import liuliu.demo.list.base.BaseFragment;
import liuliu.demo.list.view.MyItemView;

/**
 * Created by Administrator on 2016/1/6.
 */
public class WodeFragment extends BaseFragment {
    @CodeNote(id = R.id.wode_ll_yue, click = "onClick")
    LinearLayout wode_ll_yue;
    @CodeNote(id = R.id.wode_ll_libao, click = "onClick")
    LinearLayout wode_ll_libao;
    @CodeNote(id = R.id.wode_ll_jifen, click = "onClick")
    LinearLayout wode_ll_jifen;
    @CodeNote(id = R.id.wode_item_libao, click = "onClick")
    MyItemView wode_item_libao;
    @CodeNote(id = R.id.wode_item_dingdan, click = "onClick")
    MyItemView wode_item_dingdan;
    @CodeNote(id = R.id.wode_item_dizhi, click = "onClick")
    MyItemView wode_item_dizhi;
    @CodeNote(id = R.id.wode_item_daijinquan, click = "onClick")
    MyItemView wode_item_daijinquan;
    @CodeNote(id = R.id.wode_item_xiaoxi, click = "onClick")
    MyItemView wode_item_xiaoxi;
    @CodeNote(id = R.id.wode_item_bangzhu, click = "onClick")
    MyItemView wode_item_bangzhu;
    @CodeNote(id = R.id.wode_item_yijian, click = "onClick")
    MyItemView wode_item_yijian;
    @CodeNote(id = R.id.wode_item_women, click = "onClick")
    MyItemView wode_item_women;

    @Override
    public void initViews() {
        setContentView(R.layout.frag_wode);
    }

    @Override
    public void initEvents() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.i("hidden", hidden + "");
        super.onHiddenChanged(hidden);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.wode_ll_yue:
                break;
            case R.id.wode_ll_libao:
                break;
            case R.id.wode_ll_jifen:
                break;
            case R.id.wode_item_libao:
                break;
            case R.id.wode_item_dingdan:
                break;
            case R.id.wode_item_dizhi:
                break;
            case R.id.wode_item_daijinquan:
                break;
            case R.id.wode_item_xiaoxi:
                break;
            case R.id.wode_item_bangzhu:
                break;
            case R.id.wode_item_yijian:
                break;
            case R.id.wode_item_women:
                break;
        }
    }
}
