package liuliu.demo.list.ui.first_frag;

import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import net.tsz.afinal.annotation.view.CodeNote;

import liuliu.demo.list.R;
import liuliu.demo.list.base.BaseFragment;
import liuliu.demo.list.ui.activity.MainActivity;

/**
 * Created by Administrator on 2016/1/6.
 */
public class GouwucheFragment extends BaseFragment {
    @CodeNote(id = R.id.iv)
    NetworkImageView iv;

    @Override
    public void initViews() {
        setContentView(R.layout.frag_gouwuche);
    }

    /**
     * 利用NetworkImageView显示网络图片
     */
    private void showImageByNetworkImageView() {
        String imageUrl = "http://avatar.csdn.net/6/6/D/1_lfdfhl.jpg";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.mIntails);
        final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(20);
        ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String key, Bitmap value) {
                lruCache.put(key, value);
            }

            @Override
            public Bitmap getBitmap(String key) {
                return lruCache.get(key);
            }
        };
        ImageLoader imageLoader = new ImageLoader(requestQueue, imageCache);
        iv.setTag("url");
        iv.setImageUrl(imageUrl, imageLoader);
    }

    @Override
    public void initEvents() {
        showImageByNetworkImageView();
    }
}
