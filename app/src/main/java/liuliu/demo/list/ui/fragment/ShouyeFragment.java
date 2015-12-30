package liuliu.demo.list.ui.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import net.tsz.afinal.annotation.view.CodeNote;

import java.util.ArrayList;
import java.util.List;

import liuliu.demo.list.R;
import liuliu.demo.list.base.BaseFragment;
import liuliu.demo.list.base.Utils;
import liuliu.demo.list.control.base.CommonAdapter;
import liuliu.demo.list.control.base.CommonViewHolder;
import liuliu.demo.list.control.base.JSONAnalyze;
import liuliu.demo.list.control.shouye.ShouyeListener;
import liuliu.demo.list.model.ChangeItemModel;
import liuliu.demo.list.model.GoodModel;
import liuliu.demo.list.model.ImageModel;
import liuliu.demo.list.model.ItemModel;
import liuliu.demo.list.ui.activity.MainActivity;
import liuliu.demo.list.view.BannerView;
import liuliu.demo.list.view.GridLinearLayout;

/**
 * Created by Administrator on 2015/12/29.
 */
public class ShouyeFragment extends BaseFragment{
    @CodeNote(id=R.id.guanggao_main)
    GridLinearLayout guanggao_view;
    MainActivity mIntails;
    //http://api.map.baidu.com/telematics/v3/weather?location=淇濆畾&output=json&ak=XAUTG3wLFCte206ZrMVunjbG&mcode=5F:33:8B:DD:33:47:51:54:BD:52:04:11:97:3D:82:9D:21:23:BB:AA;liuliu.demo.list
    private String mUrl = "http://api.juheapi.com/japi/toh?v=1.0&month=12&day=24&key=adee859f57cade911dbfe1050666153d";
    //    private String mUrl = "http://api.map.baidu.com/telematics/v3/weather?location=";
    private String mGoodUrl = "http://www.hesq.com.cn/fresh/fore/logic/app/home/product.php";
    @CodeNote(id=R.id.scroll_main)
    ScrollView mScrollView;
    @CodeNote(id=R.id.hot_good_main)
    GridLinearLayout hotgood_view;
    @CodeNote(id=R.id.goodtype_main)
    GridLinearLayout goodtype_view;
    @CodeNote(id=R.id.srf_layout_main)
    SwipeRefreshLayout mSwipe;
    @CodeNote(id=R.id.banner_view_main)
    BannerView top_banner;
    JSONAnalyze<GoodModel> json;
    CommonAdapter<ImageModel> mAdapter;
    CommonAdapter<GoodModel> mAdapters;
    ShouyeListener mListener;

    List<ItemModel> mItems;
    List list[];
    ChangeItemModel normalModel;
    ChangeItemModel pressedModel;
    int now_preaaed = -1;//当前点击的底部菜单
    @CodeNote(id=R.id.hot_tejia_rl,click = "onClick")
    RelativeLayout hot_tejia_rl;
    @CodeNote(id=R.id.hot_tejia_ll)
    LinearLayout hot_tejia_ll;
    @CodeNote(id=R.id.hot_tejia_iv)
    ImageView hot_tejia_iv;
    @CodeNote(id=R.id.hot_tejia_tv)
    TextView hot_tejia_tv;
    @CodeNote(id=R.id.hot_jingpin_rl,click = "onClick")
    RelativeLayout hot_jingpin_rl;
    @CodeNote(id=R.id.hot_jingpin_ll)
    LinearLayout hot_jingpin_ll;
    @CodeNote(id=R.id.hot_jingpin_iv)
    ImageView hot_jingpin_iv;
    @CodeNote(id=R.id.hot_jingpin_tv)
    TextView hot_jingpin_tv;
    @CodeNote(id=R.id.hot_zuixin_rl,click = "onClick")
    RelativeLayout hot_zuixin_rl;
    LinearLayout hot_zuixin_ll;
    @CodeNote(id=R.id.hot_zuixin_iv)
    ImageView hot_zuixin_iv;
    @CodeNote(id=R.id.hot_zuixin_tv)
    TextView hot_zuixin_tv;
    private int index = 0;

    @Override
    public void initViews() {
        setContentView(R.layout.frag_shouye);
        mIntails = MainActivity.mIntails;
    }

    @Override
    public void initEvents() {
        mItemList = new ArrayList<>();
        mItemList = new ArrayList<>();
        mItemList.add(new ChangeItemModel(hot_tejia_rl, hot_tejia_ll, hot_tejia_tv, hot_tejia_iv));
        mItemList.add(new ChangeItemModel(hot_jingpin_rl, hot_jingpin_ll, hot_jingpin_tv, hot_jingpin_iv));
        mItemList.add(new ChangeItemModel(hot_zuixin_rl, hot_zuixin_ll, hot_zuixin_tv, hot_zuixin_iv));
        mItems = new ArrayList<>();
        mItems.add(new ItemModel("首页", R.mipmap.shouye_normal, R.mipmap.shouye_normal_pressed));
        mItems.add(new ItemModel("分类", R.mipmap.fenlei_normal, R.mipmap.fenlei_normal_pressed));
        mItems.add(new ItemModel("我的", R.mipmap.wode_normal, R.mipmap.wode_normal_pressed));
        mListener=new ShouyeListener(mIntails);
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDatas();
            }
        });
        loadDatas();
    }

    private void loadDatas() {
        mListener.loadTop(new ShouyeListener.OnLoadTop() {
            @Override
            public void load(final List list) {
                top_banner.setBannerView(list);
            }
        }, "http://www.hesq.com.cn/fresh/fore/logic/app/home/focus.php");
        mListener.loadGuanggao(new ShouyeListener.OnLoad() {
            @Override
            public void load(final int type, final List list) {
                mAdapter = new CommonAdapter<ImageModel>(mIntails, list, R.layout.recycle_view_item_home) {
                    @Override
                    public void convert(CommonViewHolder holder, List<ImageModel> imageModel, int position) {
                        loadGG(mIntails, type, holder, list, position);
                    }
                };
                guanggao_view.setAdapter(mAdapter);
                guanggao_view.setColumns(1);
                guanggao_view.bindLinearLayout();
                mSwipe.setRefreshing(false);
            }
        }, "http://www.hesq.com.cn/fresh/fore/logic/app/home/ad.php");
        mListener.loadGoodType(new ShouyeListener.OnLoad() {
            @Override
            public void load(int type, List list) {
                mAdapter = new CommonAdapter<ImageModel>(mIntails, list, R.layout.item_main_fenlei) {
                    @Override
                    public void convert(CommonViewHolder holder, List<ImageModel> list, int position) {
                        ImageModel model = list.get(position);
                        holder.setImageByUrl(R.id.type_iv, model.getImage(), R.mipmap.error);
                        holder.setText(R.id.type_title_tv, model.getTitle());
                        holder.setText(R.id.type_desc1_tv, model.getT1());
                        holder.setText(R.id.type_desc2_tv, model.getT2());
                    }
                };
                goodtype_view.setAdapter(mAdapter);
                goodtype_view.setColumns(2);
                goodtype_view.bindLinearLayout();
                mSwipe.setRefreshing(false);
            }
        }, "http://www.hesq.com.cn/fresh/fore/logic/app/home/category.php");
        mListener.loadHotGood(new ShouyeListener.OnLoadHot() {
            @Override
            public void load(List[] lists) {
                list = lists;
                HotClick(0, lists[0]);
            }
        }, mGoodUrl);
    }

    List<ChangeItemModel> mItemList;
    private int clickItem;//热门商品点击的项

    /**
     * 热门商品选择
     *
     * @param position 选中的位置
     * @param mList    商品信息集合
     */
    private void HotClick(final int position, final List mList) {
        normalModel = mItemList.get(clickItem);
        pressedModel = mItemList.get(position);
        //恢复成未点击状态
        normalModel.getTv().setTextColor(mIntails.getResources().getColor(R.color.main_item_normal));
        Bitmap bitmap = Utils.readBitMap(mIntails, mItems.get(clickItem).getNormal_img());
        normalModel.getIv().setImageBitmap(bitmap);
        normalModel.getRl().setBackgroundColor(mIntails.getResources().getColor(R.color.total_bg));
        normalModel.getLl().setVisibility(View.VISIBLE);
        //设置为点击状态
        pressedModel.getTv().setTextColor(mIntails.getResources().getColor(R.color.main_item_pressed));
        pressedModel.getIv().setImageBitmap(Utils.readBitMap(mIntails, mItems.get(position).getPressed_img()));
        pressedModel.getRl().setBackgroundColor(mIntails.getResources().getColor(R.color.white));
        pressedModel.getLl().setVisibility(View.GONE);
        clickItem = position;
        mAdapters = new CommonAdapter<GoodModel>(mIntails, mList, R.layout.item_main_hot_good) {
            @Override
            public void convert(CommonViewHolder holder, List<GoodModel> list, int position) {
                GoodModel model = list.get(position);
                holder.setImageByUrl(R.id.good_iv, model.getImage(), R.mipmap.error);
                if (model.getName().length() > 8) {
                    holder.setText(R.id.good_name_tv, model.getName().substring(0, 8) + "..");
                } else {
                    holder.setText(R.id.good_name_tv, model.getName());
                }
                holder.setText(R.id.good_price_tv, model.getPrice());
            }
        };
        hotgood_view.setAdapter(mAdapters);
        hotgood_view.setColumns(2);
        hotgood_view.bindLinearLayout();
        mSwipe.setRefreshing(false);
    }

    //加载广告布局
    private void loadGG(Context context, int type, CommonViewHolder holder, final List list, int position) {
        int width = Utils.getScannerWidth(context);//获得屏幕宽度
        switch (type) {
            case 3:
                if (position == 0) {
                    holder.setHeight(R.id.total_left_ll, (width - 15) / 2);
                    holder.setImageByUrl(R.id.total_left_one_iv, list.get(0));
                    holder.setImageByUrl(R.id.total_right_two_iv, list.get(3));
                    holder.setImageByUrl(R.id.total_right_three_iv, list.get(4));
                    holder.setMargin(R.id.total_right_ll_right, 0, 0, 0, 0);
                    holder.setVisible(R.id.total_right_ll_right, true);
                } else if (position == 1) {
                    holder.setHeight(R.id.total_left_ll, (width - 20) / 4);
                    holder.setVisible(R.id.total_right_ll_right, true);
                    holder.setImageByUrl(R.id.total_left_one_iv, list.get(1));
                    holder.setImageByUrl(R.id.total_left_two_iv, list.get(2));
                    holder.setImageByUrl(R.id.total_right_one_iv, list.get(5));
                    holder.setImageByUrl(R.id.total_right_two_iv, list.get(6));
                } else {
                    setOtherGone(holder);
                }
                break;
        }
    }

    //设置weight 2:1
    private void setWeight(CommonViewHolder holder) {
        LinearLayout.LayoutParams param;
        param = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.MATCH_PARENT, 2.5f);
        param.setMargins(5, 5, 5, 5);
        holder.getView(R.id.total_left_ll).setLayoutParams(param);
        param = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        param.setMargins(5, 5, 5, 5);
        holder.getView(R.id.total_right_ll).setLayoutParams(param);
    }

    //设置其他内容隐藏
    private void setOtherGone(CommonViewHolder holder) {
        holder.setVisible(R.id.totalItem_ll, false);
        holder.setVisible(R.id.total_left_ll, false);
        holder.setVisible(R.id.total_right_ll, false);
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hot_tejia_rl:
                HotClick(0, list[0]);
                break;
            case R.id.hot_jingpin_rl:
                HotClick(1, list[1]);
                break;
            case R.id.hot_zuixin_rl:
                HotClick(2, list[2]);
                break;
        }
    }
}
