package liuliu.demo.list.ui;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.List;

import liuliu.demo.list.R;
import liuliu.demo.list.base.BaseFragment;
import liuliu.demo.list.base.Utils;
import liuliu.demo.list.control.base.CommonAdapter;
import liuliu.demo.list.control.base.CommonViewHolder;
import liuliu.demo.list.control.base.JSONAnalyze;
import liuliu.demo.list.control.shouye.ShouyeListener;
import liuliu.demo.list.model.GoodModel;
import liuliu.demo.list.model.ImageModel;
import liuliu.demo.list.view.GridLinearLayout;

/**
 * Created by Administrator on 2015/12/29.
 */
public class ShouyeFragment extends BaseFragment {
    private GridLinearLayout guanggao_view;
    MainActivity mIntails;
    //http://api.map.baidu.com/telematics/v3/weather?location=淇濆畾&output=json&ak=XAUTG3wLFCte206ZrMVunjbG&mcode=5F:33:8B:DD:33:47:51:54:BD:52:04:11:97:3D:82:9D:21:23:BB:AA;liuliu.demo.list
    private String mUrl = "http://api.juheapi.com/japi/toh?v=1.0&month=12&day=24&key=adee859f57cade911dbfe1050666153d";
    //    private String mUrl = "http://api.map.baidu.com/telematics/v3/weather?location=";
    private String mGoodUrl = "http://www.hesq.com.cn/fresh/fore/logic/app/home/product.php";
    ScrollView mScrollView;
    private GridLinearLayout hotgood_view;
    private GridLinearLayout goodtype_view;
    SwipeRefreshLayout mSwipe;

    JSONAnalyze<GoodModel> json;
    CommonAdapter<ImageModel> mAdapter;
    CommonAdapter<GoodModel> mAdapters;
    ShouyeListener mListener;

    @Override
    public void initViews() {
        setContentView(R.layout.frag_shouye);
        mIntails = MainActivity.mIntails;
    }

    @Override
    public void initEvents(View view) {
        guanggao_view = (GridLinearLayout) view.findViewById(R.id.guanggao_main);
        hotgood_view = (GridLinearLayout) view.findViewById(R.id.hot_good_main);
        goodtype_view = (GridLinearLayout) view.findViewById(R.id.goodtype_main);
        mScrollView = (ScrollView) view.findViewById(R.id.scroll_main);
        mSwipe = (SwipeRefreshLayout) view.findViewById(R.id.srf_layout_main);

        mListener = new ShouyeListener(mIntails);
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDatas();
            }
        });
        loadDatas();
    }

    private void loadDatas() {
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

        json = new JSONAnalyze<GoodModel>(mIntails, "GoodModel");
        json.getJson(new JSONAnalyze.OnLoadData() {
            @Override
            public void load(final List list) {
                mAdapters = new CommonAdapter<GoodModel>(mIntails, list, R.layout.item_good_layout) {
                    @Override
                    public void convert(CommonViewHolder holder, List<GoodModel> list, int position) {
                        GoodModel model = list.get(position);
                        holder.setImageByUrl(R.id.iv_icon, model.getImage(), R.mipmap.error);
                        holder.setText(R.id.tv_title, model.getName());
                        holder.setText(R.id.tv_desc, model.getPrice());
                    }
                };
                hotgood_view.setAdapter(mAdapters);
                hotgood_view.setColumns(2);
                hotgood_view.bindLinearLayout();
                mSwipe.setRefreshing(false);
            }
        }, mGoodUrl);
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
}
