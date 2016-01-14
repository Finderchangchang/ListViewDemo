package liuliu.demo.list.ui.last_frag;

import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import net.tsz.afinal.annotation.view.CodeNote;

import java.util.ArrayList;
import java.util.List;

import liuliu.demo.list.R;
import liuliu.demo.list.base.BaseFragment;
import liuliu.demo.list.control.base.CommonAdapter;
import liuliu.demo.list.control.base.CommonViewHolder;
import liuliu.demo.list.control.manager.FenLeiListener;
import liuliu.demo.list.control.manager.GoodListListener;
import liuliu.demo.list.model.GoodDetailModel;
import liuliu.demo.list.model.GoodModel;
import liuliu.demo.list.model.ImageModel;
import liuliu.demo.list.ui.activity.DetailListsActivity;
import liuliu.demo.list.view.LoadListView;

/**
 * 商品列表
 * Created by Administrator on 2015/12/10.
 */
public class GoodListFragment extends BaseFragment {
    @CodeNote(id = R.id.type_list_grid_view)
    LoadListView listView;
    GoodListListener mListener;
    //    @CodeNote(id = R.id.type_list_iv)
//    MyItemView title;
    CommonAdapter<GoodDetailModel> mAdapters;
    //    TypeListListener<GoodModel> listListener;
    int page = 1;
    DetailListsActivity mIntails;
    public String term = "";//查询显示的
    List<GoodDetailModel> mGoods;
    private boolean isBottom = false;//是否滑动到最底部
    @CodeNote(id = R.id.is_loading)
    LinearLayout is_loading;//正在加载
    String link;
    OnItemClick mClick;

    @Override
    public void initViews() {
        setContentView(R.layout.frag_type_list);
        mIntails = DetailListsActivity.mIntails;
        mListener = new GoodListListener(mIntails);
        mGoods = new ArrayList<>();
        String title = mIntails.getDesc().split("\\?")[0];
        link = mIntails.getDesc().split("\\?")[1];
        term = "http://www.hesq.com.cn/fresh/fore/logic/app/product/list.php";
        loads();
    }

    private void loads() {
        mListener.loadList(true, new GoodListListener.OnLoad() {
            @Override
            public void load(boolean tail, List<GoodDetailModel> list) {
                loadGoodList(tail, list);
            }
        }, this.term + "?page=" + page + "&number=10" + jiexiLink(link));
    }

    /**
     * 加载商品信息集合
     *
     * @param result 是否滑动到底部（true,到达底部。false,没有到达底部）
     * @param list   需要加载的数据
     */
    public void loadGoodList(boolean result, List<GoodDetailModel> list) {
        isBottom = result;
        for (GoodDetailModel model : list) {
            mGoods.add(model);
        }
        loadUi(mGoods);
        page = page + 1;
        listView.loadComplete(isBottom);//关闭底部进度条
    }

    private void loadUi(List<GoodDetailModel> list) {
        is_loading.setVisibility(View.GONE);
        if (mAdapters == null) {
            mAdapters = new CommonAdapter<GoodDetailModel>(mIntails, list, R.layout.item_good_desc) {
                @Override
                public void convert(CommonViewHolder holder, final List<GoodDetailModel> lists, final int position) {
                    GoodDetailModel goodModel = lists.get(position);
                    ImageModel model = new ImageModel();
                    model.setImage(goodModel.getImage());
                    holder.loadImageByUrl(R.id.good_iv, model);
                    holder.setText(R.id.item_good_list_name, goodModel.getName());
                    holder.setText(R.id.item_good_list_desc, goodModel.getFeature());
                    holder.setText(R.id.item_good_list_month_sales, goodModel.getSales());
                    holder.setText(R.id.item_good_list_stock, goodModel.getStock());
                    holder.setText(R.id.item_good_list_price, goodModel.getPrice());
                    if (goodModel.isIsSales() || goodModel.isIsRecom() || goodModel.isIsNew() || goodModel.isIsLimit() ||
                            goodModel.isIsRush() || goodModel.isIsArea() || goodModel.isIsPresent() || goodModel.isIsDrive()) {
                        holder.setVisible(R.id.item_good_list_type_ll, true);
                    } else {
                        holder.setVisible(R.id.item_good_list_type_ll, false);
                    }
                    holder.setVisible(R.id.cuxiao_btn, goodModel.isIsSales());
                    holder.setVisible(R.id.tuijian_btn, goodModel.isIsRecom());
                    holder.setVisible(R.id.xinpin_btn, goodModel.isIsNew());
                    holder.setVisible(R.id.xiangou_btn, goodModel.isIsLimit());
                    holder.setVisible(R.id.xianshi_btn, goodModel.isIsRush());
                    holder.setVisible(R.id.tejia_btn, goodModel.isIsArea());
                    holder.setVisible(R.id.maizeng_btn, goodModel.isIsPresent());
                    holder.setVisible(R.id.maiyou_btn, goodModel.isIsDrive());
                    holder.setOnClickListener(R.id.item_good_desc_ll, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mClick.onItemClick(lists.get(position).getId());
                        }
                    });
                }
            };
            listView.setAdapter(mAdapters);
            //上划加载更多数据
            listView.setOnLoadListener(new LoadListView.onLoadListener() {
                @Override
                public void onLoad() {
                    if (!isBottom) {
                        loads();
                    }
                }
            });
        } else {
            mAdapters.notifyDataSetChanged();
        }
    }

    @Override
    public void initEvents() {

    }

    /**
     * 说明:
     * 1, 如果存在type参数, 则不计算其它参数
     * 2, 如果不存在type, 则首先计算key
     * 3, 如果type和key都不存在, 则计算bid和sid
     * 4, 如果仅有bid参数(即sid不传递或为0), 则为取所有该大类下的商品
     * 5, 无规格商品, 隐藏商品等不会被显示
     */
    private String jiexiLink(String link) {
        if (link.contains("type")) {//如果存在type参数, 则不计算其它参数
            link = "type=" + link.split("&")[0].split("=")[1];
        } else if (link.contains("key")) {//存在type, 则首先计算key
            link = "key=" + link.split("&")[1].split("=")[1];
        }
        return "&" + link;
    }

    public interface OnItemClick {
        void onItemClick(String position);//value为传入的值
    }

    public void setOnItemClick(OnItemClick click) {
        this.mClick = click;
    }
}
