package liuliu.demo.list.ui.first_frag;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.squareup.picasso.Picasso;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.CodeNote;
import net.tsz.afinal.cache.ACache;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import liuliu.demo.list.R;
import liuliu.demo.list.base.BaseFragment;
import liuliu.demo.list.base.Utils;
import liuliu.demo.list.control.base.GouwucheAdapter;
import liuliu.demo.list.control.base.CommonViewHolder;
import liuliu.demo.list.control.base.GouwucheAdapter;
import liuliu.demo.list.control.base.GouwucheViewHolder;
import liuliu.demo.list.control.base.image.ImageCacheManager;
import liuliu.demo.list.control.manager.ShouyeListener;
import liuliu.demo.list.model.CacheModel;
import liuliu.demo.list.model.ChangeItemModel;
import liuliu.demo.list.model.GoodModel;
import liuliu.demo.list.model.ImageModel;
import liuliu.demo.list.model.ItemModel;
import liuliu.demo.list.ui.activity.DetailListsActivity;
import liuliu.demo.list.ui.activity.MainActivity;
import liuliu.demo.list.view.GridLinearLayout;

/**
 * Created by Administrator on 2016/1/6.
 */
public class ShouyeFragment extends BaseFragment {
    @CodeNote(id = R.id.guanggao_main)
    GridLinearLayout guanggao_view;
    MainActivity mIntails;
    Utils mUtils;
    @CodeNote(id = R.id.main_good_type_ll, click = "onClick")
    LinearLayout main_good_type_ll;
    @CodeNote(id = R.id.main_my_order_ll, click = "onClick")
    LinearLayout main_my_order_ll;
    @CodeNote(id = R.id.main_user_unit_ll, click = "onClick")
    LinearLayout main_user_unit_ll;
    @CodeNote(id = R.id.main_shoppingcar_ll, click = "onClick")
    LinearLayout main_shoppingcar_ll;
    private String mGoodUrl = "http://www.hesq.com.cn/fresh/fore/logic/app/home/product.php";
    @CodeNote(id = R.id.scroll_main)
    ScrollView mScrollView;
    @CodeNote(id = R.id.hot_good_main)
    GridLinearLayout hotgood_view;
    @CodeNote(id = R.id.goodtype_main)
    GridLinearLayout goodtype_view;
    @CodeNote(id = R.id.srf_layout_main)
    SwipeRefreshLayout mSwipe;
    GouwucheAdapter<ImageModel> mAdapter;
    GouwucheAdapter<GoodModel> mAdapters;
    ShouyeListener mListener;

    List<ItemModel> mItems;
    List list[];
    ChangeItemModel normalModel;
    ChangeItemModel pressedModel;
    int now_preaaed = -1;//当前点击的底部菜单
    @CodeNote(id = R.id.hot_tejia_rl, click = "onClick")
    RelativeLayout hot_tejia_rl;
    @CodeNote(id = R.id.hot_tejia_ll)
    LinearLayout hot_tejia_ll;
    @CodeNote(id = R.id.hot_tejia_iv)
    ImageView hot_tejia_iv;
    @CodeNote(id = R.id.hot_tejia_tv)
    TextView hot_tejia_tv;
    @CodeNote(id = R.id.hot_jingpin_rl, click = "onClick")
    RelativeLayout hot_jingpin_rl;
    @CodeNote(id = R.id.hot_jingpin_ll)
    LinearLayout hot_jingpin_ll;
    @CodeNote(id = R.id.hot_jingpin_iv)
    ImageView hot_jingpin_iv;
    @CodeNote(id = R.id.hot_jingpin_tv)
    TextView hot_jingpin_tv;
    @CodeNote(id = R.id.hot_zuixin_rl, click = "onClick")
    RelativeLayout hot_zuixin_rl;
    @CodeNote(id = R.id.hot_zuixin_ll)
    LinearLayout hot_zuixin_ll;
    @CodeNote(id = R.id.hot_zuixin_iv)
    ImageView hot_zuixin_iv;
    @CodeNote(id = R.id.hot_zuixin_tv)
    TextView hot_zuixin_tv;
    private int index = 0;
    @CodeNote(id = R.id.convenientBanner)
    ConvenientBanner convenientBanner;//顶部广告栏控件
    OnItemClick mClick;
    @CodeNote(id = R.id.fenlei_xiangqing_ll, click = "onClick")
    LinearLayout xiangqing_ll;
    @CodeNote(id = R.id.good_type_iv)
    ImageView good_type;
    @CodeNote(id = R.id.my_order_iv)
    ImageView my_order;
    @CodeNote(id = R.id.user_center_iv)
    ImageView user_center;
    @CodeNote(id = R.id.good_car_iv)
    ImageView good_car;
    @CodeNote(id = R.id.total_type_ll)
    LinearLayout total_type;//商品类型
    @CodeNote(id = R.id.good_lists_ll)
    LinearLayout good_lists;//热门商品
    @CodeNote(id = R.id.isloading_shouye_ll)
    LinearLayout isloading_shouye;
    @CodeNote(id = R.id.isloading_shouye_tv)
    TextView error_tv;

    @Override
    public void initViews() {
        setContentView(R.layout.frag_shouye);
        mIntails = MainActivity.mIntails;
        mUtils = new Utils(mIntails);
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
        good_type.setImageBitmap(Utils.readBitMap(mIntails, R.mipmap.fenlei_white));
        my_order.setImageBitmap(Utils.readBitMap(mIntails, R.mipmap.dingdan_white));
        user_center.setImageBitmap(Utils.readBitMap(mIntails, R.mipmap.wode_white));
        good_car.setImageBitmap(Utils.readBitMap(mIntails, R.mipmap.gouwuche_white));
        mListener = new ShouyeListener(mIntails);
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadUIs();
            }
        });
        loadUIs();
    }

    /**
     *
     */
    private void loadUIs() {
        if (Utils.isNetworkConnected(mIntails)) {//联网状态加载数据
            loadDatas(true);
        } else {//未联网直接关闭
            loadDatas(false);
            mSwipe.setRefreshing(false);
        }
    }

    private List<Boolean> LoadingEnd = new ArrayList<>();

    private void loadDatas(boolean result) {
        //顶部广告
        mListener.loadTop(result, new ShouyeListener.OnLoadTop() {
            @Override
            public void load(final List list) {
                if (list != null) {
                    convenientBanner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
                        @Override
                        public LocalImageHolderView createHolder() {
                            setLoadingGone();
                            return new LocalImageHolderView();
                        }
                    }, list).setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused}).setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Toast.makeText(mIntails, "position:" + position, Toast.LENGTH_SHORT).show();
                        }
                    });
                    LoadingEnd.add(true);
                } else {
                    LoadingEnd.add(false);
                }
                handler.sendEmptyMessage(1);
            }
        }, "http://www.hesq.com.cn/fresh/fore/logic/app/home/focus.php");
        mListener.loadGuanggao(result, new ShouyeListener.OnLoad() {
            @Override
            public void load(final int type, final List list) {
                if (list != null) {
                    mAdapter = new GouwucheAdapter<ImageModel>(mIntails, list, R.layout.recycle_view_item_home) {
                        @Override
                        public void convert(GouwucheViewHolder holder, List<ImageModel> imageModel, int position) {
                            setLoadingGone();
                            guanggao_view.setVisibility(View.VISIBLE);
                            loadGG(mIntails, type, holder, list, position);
                        }
                    };
                    guanggao_view.setAdapter(mAdapter);
                    guanggao_view.setColumns(1);
                    guanggao_view.bindLinearLayout();
                    LoadingEnd.add(true);
                } else {
                    LoadingEnd.add(false);
                }
                mSwipe.setRefreshing(false);
                handler.sendEmptyMessage(1);
            }
        }, "http://www.hesq.com.cn/fresh/fore/logic/app/home/ad.php");
        mListener.loadHotGood(result, new ShouyeListener.OnLoadHot() {
            @Override
            public void load(List[] lists) {
                if (lists != null) {
                    list = lists;
                    HotClick(0, lists[0]);
                    setLoadingGone();
                    good_lists.setVisibility(View.VISIBLE);
                    LoadingEnd.add(true);
                } else {
                    LoadingEnd.add(false);
                }
                handler.sendEmptyMessage(1);
            }
        }, mGoodUrl);

        mListener.loadGoodType(result, new ShouyeListener.OnLoad() {
            @Override
            public void load(int type, final List list) {
                if (list != null) {
                    mAdapter = new GouwucheAdapter<ImageModel>(mIntails, list, R.layout.item_main_fenlei) {
                        @Override
                        public void convert(GouwucheViewHolder holder, List<ImageModel> list, int position) {
                            ImageModel model = list.get(position);
                            holder.loadByImage(R.id.type_iv, model.getImage());
                            holder.setText(R.id.type_title_tv, model.getTitle());
                            holder.setText(R.id.type_desc1_tv, model.getT1());
                            holder.setText(R.id.type_desc2_tv, model.getT2());
                            total_type.setVisibility(View.VISIBLE);
                            setLoadingGone();
                        }
                    };
                    goodtype_view.setAdapter(mAdapter);
                    goodtype_view.setColumns(2);
                    goodtype_view.bindLinearLayout();
                    goodtype_view.setOnCellClickListener(new GridLinearLayout.OnCellClickListener() {
                        @Override
                        public void onCellClick(int index) {
                            final ImageModel model = (ImageModel) list.get(index);
                            mUtils.IntentPost(DetailListsActivity.class, new Utils.putListener() {
                                @Override
                                public void put(Intent intent) {//跳转到第二个Activity（用来显示）
                                    intent.putExtra("desc", "spfl%" + model.getTitle() + "?" + model.getLink().split("\\?")[1]);
                                }
                            });
                        }
                    });
                    LoadingEnd.add(true);
                } else {
                    LoadingEnd.add(false);
                }
                mSwipe.setRefreshing(false);
                handler.sendEmptyMessage(1);
            }
        }, "http://www.hesq.com.cn/fresh/fore/logic/app/home/category.php");
    }

    List<ChangeItemModel> mItemList;
    private int clickItem;//热门商品点击的项

    /**
     * 热门商品选择
     *
     * @param position 选中的位置
     * @param mList    商品信息集合
     */
    private void HotClick(final int position, final List<GoodModel> mList) {
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
        mAdapters = new GouwucheAdapter<GoodModel>(mIntails, mList, R.layout.item_main_hot_good) {
            @Override
            public void convert(GouwucheViewHolder holder, List<GoodModel> list, int position) {
                GoodModel model = list.get(position);
                holder.loadByImage(R.id.good_iv, model.getImage());
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
        hotgood_view.setOnCellClickListener(new GridLinearLayout.OnCellClickListener() {
            @Override
            public void onCellClick(final int index) {
                final GoodModel model = mList.get(index);
                mUtils.IntentPost(DetailListsActivity.class, new Utils.putListener() {
                    @Override
                    public void put(Intent intent) {
                        intent.putExtra("desc", "xq%id?" + model.getId());
                    }
                });
            }
        });
        mSwipe.setRefreshing(false);
    }

    //加载广告布局
    private void loadGG(Context context, int type, GouwucheViewHolder holder, final List<ImageModel> list, int position) {
        int width = Utils.getScannerWidth(context);//获得屏幕宽度
        switch (type) {
            case 3:
                if (position == 0) {
                    holder.setHeight(R.id.total_left_ll, (width - 15) / 2);
                    holder.loadGuanggaoByImage(R.id.total_left_one_iv, list.get(0));
                    holder.loadGuanggaoByImage(R.id.total_right_two_iv, list.get(3));
                    holder.loadGuanggaoByImage(R.id.total_right_three_iv, list.get(4));
                    holder.setMargin(R.id.total_right_ll_right, 0, 0, 0, 0);
                    holder.setVisible(R.id.total_right_ll_right, true);
                } else if (position == 1) {
                    holder.setHeight(R.id.total_left_ll, (width - 20) / 4);
                    holder.setVisible(R.id.total_right_ll_right, true);
                    holder.loadGuanggaoByImage(R.id.total_left_one_iv, list.get(1));
                    holder.loadGuanggaoByImage(R.id.total_left_two_iv, list.get(2));
                    holder.loadGuanggaoByImage(R.id.total_right_one_iv, list.get(5));
                    holder.loadGuanggaoByImage(R.id.total_right_two_iv, list.get(6));
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
    private void setOtherGone(GouwucheViewHolder holder) {
        holder.setVisible(R.id.totalItem_ll, false);
        holder.setVisible(R.id.total_left_ll, false);
        holder.setVisible(R.id.total_right_ll, false);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_good_type_ll:
                mClick.onItemClick(1);
                break;
            case R.id.main_my_order_ll://跳转到订单列表
                MainActivity.mIntails.mUtils.IntentPost(DetailListsActivity.class);
                break;
            case R.id.main_user_unit_ll:
                mClick.onItemClick(2);
                break;
            case R.id.main_shoppingcar_ll:
                mClick.onItemClick(3);
                break;
            case R.id.hot_tejia_rl:
                HotClick(0, list[0]);
                break;
            case R.id.hot_jingpin_rl:
                HotClick(1, list[1]);
                break;
            case R.id.hot_zuixin_rl:
                HotClick(2, list[2]);
                break;
            case R.id.fenlei_xiangqing_ll://点击跳转到商品类型
                mClick.onItemClick(1);
                break;

        }
    }

    public class LocalImageHolderView implements Holder<ImageModel> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, ImageModel data) {
            ImageCacheManager.loadImage(data.getImage(), imageView, Utils.getBitmapFromRes(R.mipmap.ic_launcher), Utils.getBitmapFromRes(R.mipmap.ic_default_adimage));
        }
    }

    public interface OnItemClick {
        void onItemClick(Object value);//value为传入的值
    }

    public void setOnItemClick(OnItemClick click) {
        mClick = click;
    }

    @Override
    public void onResume() {
        super.onResume();
        convenientBanner.startTurning(5000);
    }

    @Override
    public void onPause() {
        super.onPause();
        convenientBanner.stopTurning();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (LoadingEnd.size() == 4) {
                    boolean result = false;
                    for (boolean res : LoadingEnd) {
                        if (res) {
                            result = true;
                        }
                    }
                    if (result) {//隐藏load显示正常数据
                        isloading_shouye.setVisibility(View.GONE);
                    } else {//无网络（显示）
                        error_tv.setText("请检查网络连接");
                    }
                }
            }
        }
    };

    /**
     * 隐藏加载进度条
     */
    private void setLoadingGone() {
        isloading_shouye.setVisibility(View.GONE);
    }
}
