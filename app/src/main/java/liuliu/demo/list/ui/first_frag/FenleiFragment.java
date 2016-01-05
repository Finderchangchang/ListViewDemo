package liuliu.demo.list.ui.first_frag;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import net.tsz.afinal.annotation.view.CodeNote;

import java.util.ArrayList;
import java.util.List;

import liuliu.demo.list.R;
import liuliu.demo.list.base.BaseFragment;
import liuliu.demo.list.base.Utils;
import liuliu.demo.list.control.base.CommonAdapter;
import liuliu.demo.list.control.base.CommonViewHolder;
import liuliu.demo.list.control.fenlei.FenLeiListener;
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
    int mGoodTypeClick;//被点击的项
    List<Button> good_type_list;
    FenLeiListener mListener;
    MainActivity mActivity;
    CommonAdapter mAdapter;
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
        //设置布局管理器
        good_type_list = new ArrayList<>();
        mListener.loadFenlei(new FenLeiListener.OnLoad() {
            @Override
            public void load(List[] list) {
                loadFenLei(list);
            }
        }, "http://www.hesq.com.cn/fresh/fore/logic/app/product/category.php");
    }

    /**
     * 加载所有数据
     *
     * @param list 所有类型集合
     */
    public void loadFenLei(final List list[]) {
        List<String> title = new ArrayList();
        for (int i = 0; i < list.length; i++) {
            TypeModel model = (TypeModel) list[i].get(0);
            title.add(model.getName());
        }
        mAdapter = new CommonAdapter<String>(mActivity, title, R.layout.recycle_view_item_good_type) {
            @Override
            public void convert(CommonViewHolder holder, List<String> t, final int position) {
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

    private void refreshList(final List list) {
        mAdapter = new CommonAdapter<TypeModel>(mActivity, list, R.layout.item_good_types) {
            @Override
            public void convert(CommonViewHolder holder, List<TypeModel> models, final int position) {
                TypeModel model = models.get(position);
                ImageModel image = new ImageModel();
                image.setImage(model.getImage());
                holder.loadImageByUrl(R.id.good_iv, image);
                String title = "全部商品";
                if (position != 0) {
                    title = model.getName();
                }
                holder.setText(R.id.good_name_tv, title);
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
                        TypeModel model = (TypeModel) list.get(index);
                        TypeModel first = (TypeModel) list.get(0);
                        String link;
                        if (index > 0) {
                            link = "bid=" + first.getSid() + "&sid=" + model.getSid();
                        } else {
                            link = "bid=" + first.getSid();
                        }
                        intent.putExtra("desc", "spfl?" + link);
                    }
                });
            }
        });
    }
}
