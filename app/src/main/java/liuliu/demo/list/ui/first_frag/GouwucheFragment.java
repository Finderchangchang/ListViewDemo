package liuliu.demo.list.ui.first_frag;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.LruCache;
import android.view.View;
import android.widget.GridView;

import com.android.volley.RequestQueue;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import net.tsz.afinal.annotation.view.CodeNote;

import java.util.List;

import liuliu.demo.list.R;
import liuliu.demo.list.base.BaseFragment;
import liuliu.demo.list.base.Utils;
import liuliu.demo.list.control.base.CommonAdapter;
import liuliu.demo.list.control.base.CommonViewHolder;
import liuliu.demo.list.control.base.GouwucheAdapter;
import liuliu.demo.list.control.base.GouwucheViewHolder;
import liuliu.demo.list.control.manager.GouwucheListener;
import liuliu.demo.list.control.manager.ShouyeListener;
import liuliu.demo.list.model.FacesModel;
import liuliu.demo.list.model.ImageModel;
import liuliu.demo.list.ui.activity.DetailListsActivity;
import liuliu.demo.list.ui.activity.MainActivity;
import liuliu.demo.list.view.GridLinearLayout;
import me.xiaopan.sketch.SketchImageView;

/**
 * Created by Administrator on 2016/1/6.
 */
public class GouwucheFragment extends BaseFragment {
    //    @CodeNote(id = R.id.iv)
//    NetworkImageView iv;
//    @CodeNote(id = R.id.image_head)
//    SketchImageView sketchImageView;
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
        String imageUrl = "http://avatar.csdn.net/6/6/D/1_lfdfhl.jpg";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.mIntails);
        final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(20);
//        ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
//            @Override
//            public void putBitmap(String key, Bitmap value) {
//                lruCache.put(key, value);
//            }
//
//            @Override
//            public Bitmap getBitmap(String key) {
//                return lruCache.get(key);
//            }
//        };
//        ImageLoader imageLoader = new ImageLoader(requestQueue, imageCache);
//        iv.setTag("url");
//        iv.setImageUrl(imageUrl, imageLoader);
//        sketchImageView.displayImage("http://b.zol-img.com.cn/desk/bizhi/image/4/1366x768/1387347695254.jpg");

        mListener.loadFace(new GouwucheListener.OnLoad() {
            @Override
            public void load(List list) {
                mAdapter = new GouwucheAdapter<FacesModel>(mIntails, list, R.layout.item_main_gouwuche, mImageLoader) {
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
