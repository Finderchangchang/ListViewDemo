package liuliu.demo.list.ui.first_frag;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;

import net.tsz.afinal.annotation.view.CodeNote;

import java.util.ArrayList;
import java.util.List;

import liuliu.demo.list.R;
import liuliu.demo.list.base.BaseFragment;
import liuliu.demo.list.base.Utils;
import liuliu.demo.list.control.base.GouwucheAdapter;
import liuliu.demo.list.control.base.GouwucheViewHolder;
import liuliu.demo.list.control.manager.FenLeiListener;
import liuliu.demo.list.model.ImageModel;
import liuliu.demo.list.model.TypeModel;
import liuliu.demo.list.ui.activity.DetailListsActivity;
import liuliu.demo.list.ui.activity.MainActivity;
import liuliu.demo.list.view.GridLinearLayout;

/**
 * Created by Administrator on 2015/12/30.
 */
public class FenleiFragment extends BaseFragment {
    @CodeNote(id = R.id.fenlei_view)
    GridLinearLayout recyclerView;//商品分类
    @CodeNote(id = R.id.fenlei_grid_view)
    GridLinearLayout gridview;
    @CodeNote(id = R.id.fenlei_error_ll)
    LinearLayout fenlei_error;
    @CodeNote(id = R.id.fenlei_view_ll)
    LinearLayout fenlei_view;
    @CodeNote(id = R.id.srf_layout_main)
    SwipeRefreshLayout mSwipe;
    int mGoodTypeClick;//被点击的项
    List<Button> good_type_list;
    FenLeiListener mListener;
    MainActivity mActivity;
    GouwucheAdapter mAdapter;
    MainActivity mIntails;
    Utils mUtils;

    @Override
    public void initViews() {
        setContentView(R.layout.frag_fenlei);
        mIntails = MainActivity.mIntails;
        mUtils = new Utils(mIntails);
    }

    @Override
    public void initEvents() {
        mActivity = MainActivity.mIntails;
        mListener = new FenLeiListener(mActivity);

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDatas();
            }
        });
        loadDatas();
    }

    private void loadDatas() {
        boolean result;
        if (Utils.isNetworkConnected(mIntails)) {//有网从后台读
            result = true;
        } else {//未联网从缓存读
            result = false;
        }
        mListener.loadFenlei(result, new FenLeiListener.OnLoad() {
            @Override
            public void load(List[] list) {
                if (list == null) {//未读取出数据(数据为空)
                    fenlei_error.setVisibility(View.VISIBLE);
                    fenlei_error.setLayoutParams(new RelativeLayout.LayoutParams(Utils.getScannerWidth(mIntails), Utils.getScannerHeight(mIntails) - 250));
                    fenlei_view.setVisibility(View.GONE);
                } else {
                    loadFenLei(list);
                    fenlei_error.setVisibility(View.GONE);
                    fenlei_view.setVisibility(View.VISIBLE);
                }

            }
        }, "http://www.hesq.com.cn/fresh/fore/logic/app/product/category.php");
        mSwipe.setRefreshing(false);
    }

    /**
     * 加载所有数据
     *
     * @param list 所有类型集合
     */
    public void loadFenLei(final List list[]) {
        List<String> title = new ArrayList();
        //设置布局管理器
        good_type_list = new ArrayList<>();
        for (int i = 0; i < list.length; i++) {
            TypeModel model = (TypeModel) list[i].get(0);
            title.add(model.getName());
        }
        mAdapter = new GouwucheAdapter<String>(mActivity, title, R.layout.recycle_view_item_good_type) {
            @Override
            public void convert(GouwucheViewHolder holder, List<String> t, final int position) {
                Button btn = holder.getView(R.id.good_type_rv_button);
                btn.setText(t.get(position).toString());
                btn.setBackgroundResource(R.mipmap.good_type_item);
                good_type_list.add(btn);//将所有按钮添加到list中
                if (position == mGoodTypeClick) {//设置置顶按钮被点击
                    btn.setBackgroundResource(R.mipmap.good_type_item_pressed);
                }
                holder.setOnClickListener(R.id.good_type_rv_button, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                refreshList(list[position]);
                                good_type_list.get(mGoodTypeClick).setBackgroundResource(R.mipmap.good_type_item);
                                good_type_list.get(position).setBackgroundResource(R.mipmap.good_type_item_pressed);
                                mGoodTypeClick = position;
                            }
                        }
                );
            }
        };
        recyclerView.setAdapter(mAdapter);
        recyclerView.setColumns(4);
        recyclerView.bindLinearLayout();
        refreshList(list[mGoodTypeClick]);
    }

    private void refreshList(final List<TypeModel> list) {
        mAdapter = new GouwucheAdapter<TypeModel>(mActivity, list, R.layout.item_good_types) {
            @Override
            public void convert(GouwucheViewHolder holder, List<TypeModel> models, final int position) {
                TypeModel model = models.get(position);
                String title = "全部商品";
                if (position != 0) {
                    title = model.getName();
                }
                holder.setText(R.id.good_name_tv, title);
                holder.loadByImage(R.id.good_iv, model.getImage());
            }
        };
        gridview.setAdapter(mAdapter);
        gridview.setColumns(3);
        gridview.bindLinearLayout();
        gridview.setOnCellClickListener(new GridLinearLayout.OnCellClickListener() {
            @Override
            public void onCellClick(final int index) {
                mUtils.IntentPost(DetailListsActivity.class, new Utils.putListener() {
                    @Override
                    public void put(Intent intent) {
                        TypeModel model = list.get(index);
                        TypeModel first = list.get(0);
                        String link;
                        if (index > 0) {
                            link = "bid=" + first.getSid() + "&sid=" + model.getSid();
                        } else {
                            link = "bid=" + first.getSid();
                        }
                        String name;
                        if (index == 0) {
                            name = "全部商品";
                        } else {
                            name = list.get(index).getName();
                        }
                        intent.putExtra("desc", "spfl%" + name + "?" + link);
                    }
                });
            }
        });
    }
}
