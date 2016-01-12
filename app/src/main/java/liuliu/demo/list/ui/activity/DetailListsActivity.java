package liuliu.demo.list.ui.activity;

import android.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import net.tsz.afinal.annotation.view.CodeNote;

import liuliu.demo.list.R;
import liuliu.demo.list.base.BaseActivity;
import liuliu.demo.list.base.Utils;
import liuliu.demo.list.ui.last_frag.GoodDetailFragment;
import liuliu.demo.list.ui.last_frag.GoodListFragment;
import liuliu.demo.list.ui.last_frag.HelpFragment;

/**
 * 点击进入的页面的Fragment
 * Created by Administrator on 2015/12/10.
 */
public class DetailListsActivity extends BaseActivity {
    public static DetailListsActivity mIntails;
    @CodeNote(id = R.id.frag_ll)
    FrameLayout fra;
    @CodeNote(id = R.id.total_botton)
    LinearLayout bottom_ll;
    String mDesc;
    Utils mUtils;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_detail_lists);
        mIntails = this;
        mUtils = new Utils(mIntails);
        mDesc = mUtils.IntentGet(getIntent(), "desc");//获得传递过来的参数
    }

    @Override
    public void initEvents() {
        // 开启Fragment事务
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        switch (mDesc.split("%")[0]) {
            case "spfl"://跳转到商品分类列表（结果-- ../product/list.php?type=2）
                transaction.replace(R.id.frag_ll, new GoodListFragment());
                break;
            case "xq"://跳转到商品详情页面
                transaction.replace(R.id.frag_ll, new GoodDetailFragment());
                break;
            case "help"://跳转到帮助中心页面
                transaction.replace(R.id.frag_ll, new HelpFragment());
                break;
            case "gwc"://购物车
//                transaction.replace(R.id.frag_ll, new GouwucheFragment());
                break;
            case ""://我的订单
                break;

        }
        transaction.commit();
    }


    /**
     * 1.跳转到详情页面（xq=123）
     */
    public String getDesc() {
        if (mDesc.contains("%")) {
            mDesc = mDesc.split("%")[1];
        }
        return mDesc;
    }
}
