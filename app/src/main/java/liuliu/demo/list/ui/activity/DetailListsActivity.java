package liuliu.demo.list.ui.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import net.tsz.afinal.annotation.view.CodeNote;

import java.util.ArrayList;
import java.util.List;

import liuliu.demo.list.R;
import liuliu.demo.list.base.BaseActivity;
import liuliu.demo.list.base.Utils;
import liuliu.demo.list.ui.last_frag.GouwucheFragment;
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
    public Utils mUtils;
    List<Fragment> mFragment;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_detail_lists);
        mIntails = this;
        mUtils = new Utils(mIntails);
        mDesc = mUtils.IntentGet(getIntent(), "desc");//获得传递过来的参数
        mFragment = new ArrayList<>();
    }

    GoodListFragment goodList;
    GoodDetailFragment goodDetail;
    HelpFragment help;
    GouwucheFragment gouwuche;

    @Override
    public void initEvents() {
        loadFragment(mDesc.split("%")[0]);
    }

    FragmentTransaction transaction;

    public void loadFragment(String link) {
        // 开启Fragment事务
        transaction = getFragmentManager().beginTransaction();
        hideFragments(transaction);
        switch (link) {
            case "spfl"://跳转到商品分类列表（结果-- ../product/list.php?type=2）
                if (goodList == null) {
                    goodList = new GoodListFragment();
                    transaction.add(R.id.frag_ll, goodList);

                    goodList.setOnItemClick(new GoodListFragment.OnItemClick() {
                        @Override
                        public void onItemClick(String position) {
                            loadFragment("xq");
                            mDesc = "xq%id?" + position;
                        }
                    });
                } else {
                    transaction.show(goodList);
                }
                mFragment.add(goodList);
                break;
            case "xq"://跳转到商品详情页面
                if (goodDetail == null) {
                    goodDetail = new GoodDetailFragment();
                    transaction.add(R.id.frag_ll, goodDetail);
                    goodDetail.setOnItemClick(new GoodDetailFragment.OnItemClick() {
                        @Override
                        public void onItemClick() {
                            loadFragment("gwc");
                        }
                    });
                } else {
                    transaction.show(goodDetail);
                }
                mFragment.add(goodDetail);
                break;
            case "help"://跳转到帮助中心页面
                if (help == null) {
                    help = new HelpFragment();
                    transaction.add(R.id.frag_ll, help);
                } else {
                    transaction.show(help);
                }
                mFragment.add(help);
                break;
            case "gwc"://购物车
                if (gouwuche == null) {
                    gouwuche = new GouwucheFragment();
                    transaction.add(R.id.frag_ll, gouwuche);
                } else {
                    transaction.show(gouwuche);
                }
                mFragment.add(gouwuche);
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        for (int i = 0; i < mFragment.size(); i++) {
            if (mFragment.get(i) != null) {
                transaction.hide(mFragment.get(i));
            }
        }
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            transaction = getFragmentManager().beginTransaction();
            if (mFragment != null && mFragment.size() > 1) {
                transaction.hide(mFragment.get(mFragment.size() - 1));
                transaction.show(mFragment.get(mFragment.size() - 2));
                mFragment.remove(mFragment.size() - 1);
                transaction.commit();
                return false;
            } else {
                this.finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
