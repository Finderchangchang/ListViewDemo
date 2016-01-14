package liuliu.demo.list.ui.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.tsz.afinal.annotation.view.CodeNote;

import java.util.ArrayList;
import java.util.List;

import liuliu.demo.list.R;
import liuliu.demo.list.base.BaseActivity;
import liuliu.demo.list.base.Utils;
import liuliu.demo.list.model.ChangeItemModel;
import liuliu.demo.list.model.ItemModel;
import liuliu.demo.list.ui.first_frag.FenleiFragment;
import liuliu.demo.list.ui.last_frag.GouwucheFragment;
import liuliu.demo.list.ui.first_frag.ShouyeFragment;
import liuliu.demo.list.ui.first_frag.WodeFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    public static MainActivity mIntails;
    FrameLayout frag_ll;
    LinearLayout shouye_ll;
    ImageView shouye_iv;
    TextView shouye_tv;
    LinearLayout fenlei_ll;
    ImageView fenlei_iv;
    TextView fenlei_tv;
    LinearLayout wode_ll;
    ImageView wode_iv;
    TextView wode_tv;
    LinearLayout gouwuche_ll;
    ImageView gouwuche_iv;
    TextView gouwuche_tv;
    int mClick;//被点击的项
    List<ChangeItemModel> listbtn;//生成的按钮集合（需要颜色改变的view）
    List<ItemModel> mItems;
    @CodeNote(id = R.id.key_word_shouye_et, click = "onClick")
    EditText key_word_et;
    FragmentTransaction transaction = null;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_main);
        mIntails = this;
        listbtn = new ArrayList<>();
        mItems = new ArrayList();
        frag_ll = (FrameLayout) findViewById(R.id.frag_ll);
        loadUi();
        setItem(0);
        now_pressed = mClick;
    }

    @Override
    public void initEvents() {

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.key_word_shouye_et:
                mUtils.IntentPost(SearchActivity.class);
                break;
            case R.id.total_bottom_shouye_ll:
                if (now_pressed != 0) {
                    setItem(0);
                    now_pressed = 0;
                }
                break;
            case R.id.total_bottom_fenlei_ll:
                if (now_pressed != 1) {
                    setItem(1);
                    now_pressed = 1;
                }
                break;
            case R.id.total_bottom_wode_ll:
                if (now_pressed != 2) {
                    setItem(2);
                    now_pressed = 2;
                }
                break;
            case R.id.total_bottom_gouwuche_ll:
                setItem(3);
                break;
        }
    }

    private void loadUi() {
        shouye_ll = (LinearLayout) findViewById(R.id.total_bottom_shouye_ll);
        shouye_ll.setOnClickListener(this);
        fenlei_ll = (LinearLayout) findViewById(R.id.total_bottom_fenlei_ll);
        fenlei_ll.setOnClickListener(this);
        wode_ll = (LinearLayout) findViewById(R.id.total_bottom_wode_ll);
        wode_ll.setOnClickListener(this);
        gouwuche_ll = (LinearLayout) findViewById(R.id.total_bottom_gouwuche_ll);
        gouwuche_ll.setOnClickListener(this);
        shouye_tv = (TextView) findViewById(R.id.total_bottom_shouye_tv);
        fenlei_tv = (TextView) findViewById(R.id.total_bottom_fenlei_tv);
        wode_tv = (TextView) findViewById(R.id.total_bottom_wode_tv);
        gouwuche_tv = (TextView) findViewById(R.id.total_bottom_gouwuche_tv);
        shouye_iv = (ImageView) findViewById(R.id.total_bottom_shouye_iv);
        fenlei_iv = (ImageView) findViewById(R.id.total_bottom_fenlei_iv);
        wode_iv = (ImageView) findViewById(R.id.total_bottom_wode_iv);
        gouwuche_iv = (ImageView) findViewById(R.id.total_bottom_gouwuche_iv);
        mItems.add(new ItemModel("首页", R.mipmap.shouye_normal, R.mipmap.shouye_normal_pressed));
        mItems.add(new ItemModel("分类", R.mipmap.fenlei_normal, R.mipmap.fenlei_normal_pressed));
        mItems.add(new ItemModel("我的", R.mipmap.wode_normal, R.mipmap.wode_normal_pressed));
        mItems.add(new ItemModel("购物车", R.mipmap.gouwuche_normal, R.mipmap.gouwuche_normal_pressed));
        listbtn.add(new ChangeItemModel(shouye_tv, shouye_iv));//添加组件到listview
        listbtn.add(new ChangeItemModel(fenlei_tv, fenlei_iv));//添加组件到listview
        listbtn.add(new ChangeItemModel(wode_tv, wode_iv));//添加组件到listview
        listbtn.add(new ChangeItemModel(gouwuche_tv, gouwuche_iv));//添加组件到listview
    }

    private void setItem(int position) {

        if (position < 3) {
            //恢复成未点击状态
            listbtn.get(mClick).getTv().setTextColor(mIntails.getResources().getColor(R.color.main_item_normal));
            Bitmap bitmap = Utils.readBitMap(mIntails, mItems.get(mClick).getNormal_img());
            listbtn.get(mClick).getIv().setImageBitmap(bitmap);
            //设置为点击状态
            listbtn.get(position).getTv().setTextColor(mIntails.getResources().getColor(R.color.main_item_pressed));
            listbtn.get(position).getIv().setImageBitmap(Utils.readBitMap(mIntails, mItems.get(position).getPressed_img()));
            mClick = position;
            // 开启一个Fragment事务
            transaction = getFragmentManager().beginTransaction();
            hideFragments(transaction);
        }
        if (position == 0) {
            if (shouye == null) {
                // 如果MessageFragment为空，则创建一个并添加到界面上
                shouye = new ShouyeFragment();
                shouye.setOnItemClick(new ShouyeFragment.OnItemClick() {
                    @Override
                    public void onItemClick(Object value) {
                        now_pressed = (Integer) value;
                        setItem(now_pressed);
                    }
                });
                transaction.add(R.id.frag_ll, shouye);
            } else {
                // 如果MessageFragment不为空，则直接将它显示出来
                transaction.show(shouye);
            }
        } else if (position == 1) {
            if (fenlei == null) {
                // 如果MessageFragment为空，则创建一个并添加到界面上
                fenlei = new FenleiFragment();
                transaction.add(R.id.frag_ll, fenlei);
            } else {
                // 如果MessageFragment不为空，则直接将它显示出来
                transaction.show(fenlei);
            }
        } else if (position == 2) {
            if (wode == null) {
                // 如果MessageFragment为空，则创建一个并添加到界面上
                wode = new WodeFragment();
                transaction.add(R.id.frag_ll, wode);
            } else {
                // 如果MessageFragment不为空，则直接将它显示出来
                transaction.show(wode);
            }
        } else if (position == 3) {
            mUtils.IntentPost(DetailListsActivity.class, new Utils.putListener() {
                @Override
                public void put(Intent intent) {//跳转到第二个Activity（用来显示）
                    intent.putExtra("desc", "gwc%");
                }
            });
        }
        if (position < 3) {
            transaction.commit();
        }
    }

    private ShouyeFragment shouye;
    private FenleiFragment fenlei;
    private WodeFragment wode;
    private GouwucheFragment gouwuche;

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (shouye != null) {
            transaction.hide(shouye);
        }
        if (fenlei != null) {
            transaction.hide(fenlei);
        }
        if (wode != null) {
            transaction.hide(wode);
        }
        if (gouwuche != null) {
            transaction.hide(gouwuche);
        }
    }

    private int now_pressed = -1;
}
