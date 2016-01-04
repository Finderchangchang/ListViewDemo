package liuliu.demo.list.ui.last_frag;

import android.view.View;
import android.widget.LinearLayout;

import net.tsz.afinal.annotation.view.CodeNote;

import java.util.ArrayList;
import java.util.List;

import liuliu.demo.list.R;
import liuliu.demo.list.base.BaseFragment;
import liuliu.demo.list.model.GoodModel;
import liuliu.demo.list.ui.activity.DetailListsActivity;
import liuliu.demo.list.view.LoadListView;

/**
 * 商品列表
 * Created by Administrator on 2015/12/10.
 */
public class FenLeiListFragment extends BaseFragment {
    @CodeNote(id = R.id.type_list_grid_view)
    LoadListView listView;
//    @CodeNote(id = R.id.type_list_iv)
//    MyItemView title;
//    CommonAdapters adapterBase;
//    TypeListListener<GoodModel> listListener;
    int page = 1;
    DetailListsActivity mIntails;
    String term = "";//查询显示的
    List<GoodModel> mGoods;
    private boolean isBottom = false;//是否滑动到最底部

    @CodeNote(id = R.id.is_loading)
    LinearLayout is_loading;//正在加载

    @Override
    public void initViews() {
        setContentView(R.layout.frag_type_list);
        mIntails = DetailListsActivity.mIntails;
//        listListener = new TypeListListener(this, mIntails);
        term = "?page=" + page + "&number=10" + jiexiLink(mIntails.getDesc().split("\\?")[1]);
//        listListener.loadList(term, "GoodModel");//页面加载的时候加载数据
    }

    @Override
    public void initEvents() {

    }

    /**
     * 加载商品信息集合
     *
     * @param result 是否滑动到底部（true,到达底部。false,没有到达底部）
     * @param list   需要加载的数据
     */
    public void loadGoodList(boolean result, List<GoodModel> list) {
        isBottom = result;
        for (GoodModel model : list) {
            mGoods.add(model);
        }
        showList(mGoods);
        page++;
        listView.loadComplete(isBottom);//关闭底部进度条
    }

    /**
     * @param list 商品信息集合
     */
    private void showList(List<GoodModel> list) {
        is_loading.setVisibility(View.GONE);
//        if (adapterBase == null) {
//            adapterBase = new CommonAdapters<GoodModel>(mIntails, list, R.layout.item_good_desc) {
//                @Override
//                public void convert(CommonViewHolders holder, GoodModel goodModel, int position) {
//                    holder.loadImage(R.id.good_iv, imageLoader, goodModel.getImage());
//                    holder.setText(R.id.item_good_list_name, goodModel.getName());
//                    holder.setText(R.id.item_good_list_desc, goodModel.getFeature());
//                    holder.setText(R.id.item_good_list_month_sales, goodModel.getSales());
//                    holder.setText(R.id.item_good_list_stock, goodModel.getStock());
//                    holder.setText(R.id.item_good_list_price, goodModel.getPrice());
//                    if (goodModel.isSales() || goodModel.isRecom() || goodModel.isNew() || goodModel.isLimit() ||
//                            goodModel.isRush() || goodModel.isArea() || goodModel.isPresent() || goodModel.isDrive()) {
//                        holder.setVisible(R.id.item_good_list_type_ll, true);
//                    } else {
//                        holder.setVisible(R.id.item_good_list_type_ll, false);
//                    }
//                    holder.setVisible(R.id.cuxiao_btn, goodModel.isSales());
//                    holder.setVisible(R.id.tuijian_btn, goodModel.isRecom());
//                    holder.setVisible(R.id.xinpin_btn, goodModel.isNew());
//                    holder.setVisible(R.id.xiangou_btn, goodModel.isLimit());
//                    holder.setVisible(R.id.xianshi_btn, goodModel.isRush());
//                    holder.setVisible(R.id.tejia_btn, goodModel.isArea());
//                    holder.setVisible(R.id.maizeng_btn, goodModel.isPresent());
//                    holder.setVisible(R.id.maiyou_btn, goodModel.isDrive());
//                }
//            };
//            listView.setAdapter(adapterBase);
//            //上划加载更多数据
//            listView.setOnLoadListener(new LoadListView.onLoadListener() {
//                @Override
//                public void onLoad() {
//                    if (!isBottom) {
//                        listListener.loadList("?page=" + page + "&number=10&" + mIntails.getDesc().split("\\?")[1], "GoodModel");
//                    }
//                }
//            });
//            adapterBase.notifyDataSetChanged();
//        } else {
//            adapterBase.notifyDataSetChanged();
//        }
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
}
