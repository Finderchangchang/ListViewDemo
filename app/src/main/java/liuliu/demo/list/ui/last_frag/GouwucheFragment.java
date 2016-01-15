package liuliu.demo.list.ui.last_frag;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.GridView;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.tsz.afinal.annotation.view.CodeNote;

import java.util.List;

import liuliu.demo.list.R;
import liuliu.demo.list.base.BaseFragment;
import liuliu.demo.list.control.base.GouwucheAdapter;
import liuliu.demo.list.control.base.GouwucheViewHolder;
import liuliu.demo.list.control.manager.GouwucheListener;
import liuliu.demo.list.model.FacesModel;
import liuliu.demo.list.ui.activity.MainActivity;

/**
 * Created by Administrator on 2016/1/6.
 */
public class GouwucheFragment extends BaseFragment {
    @CodeNote(id = R.id.gouwuche_main)
    SwipeRefreshLayout gouwuche;
    @CodeNote(id = R.id.gv)
    GridView gv;
    GouwucheListener mListener;
    MainActivity mIntails;
    GouwucheAdapter<FacesModel> mAdapter;
    private ImageLoader mImageLoader;

    @Override
    public void initViews() {
        setContentView(R.layout.frag_gouwuche);
        mIntails = MainActivity.mIntails;
        mListener = new GouwucheListener(mIntails);
        mImageLoader = ImageLoader.getInstance();
    }

    /**
     * 利用NetworkImageView显示网络图片
     */
    private void showImageByNetworkImageView() {
        mListener.loadFace(new GouwucheListener.OnLoad() {
            @Override
            public void load(List list) {
                mAdapter = new GouwucheAdapter<FacesModel>(mIntails, list, R.layout.item_main_gouwuche) {
                    @Override
                    public void convert(GouwucheViewHolder holder, List<FacesModel> list, int position) {
                        FacesModel model = list.get(position);
                        holder.loadByImage(R.id.type_iv, model.getUrl());
                        holder.setText(R.id.type_title_tv, model.getName());
                    }
                };
                gv.setAdapter(mAdapter);
                gv.setNumColumns(2);
            }
        }, "http://api.ibaozou.com/api/v1/faces/all");
    }

    @Override
    public void initEvents() {
        showImageByNetworkImageView();
        gouwuche.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showImageByNetworkImageView();
                gouwuche.setRefreshing(false);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mImageLoader != null) {
            mImageLoader.clearMemoryCache();
            mImageLoader.clearDiscCache();
        }
    }
}
