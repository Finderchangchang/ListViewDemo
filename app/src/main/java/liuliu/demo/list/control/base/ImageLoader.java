package liuliu.demo.list.control.base;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import liuliu.demo.list.R;

/**
 * 加载图片
 * Created by Administrator on 2015/12/25.
 */
public class ImageLoader {
    private ImageView mImageView;
    private int mDefImg;
    private LruCache<String, Bitmap> mCaches;

    public ImageLoader() {
        //获得最大内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 4;
        mCaches = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //每次存入缓存的时候调用
                return value.getByteCount();
            }
        };
    }

    /**
     * 增加图片到缓存
     *
     * @param bitmap 图片的Bitmap对象
     * @param url    图片的网络地址
     */
    public void addBitmapToCache(String url, Bitmap bitmap) {
        if (getBitmapByCache(url) == null) {
            mCaches.put(url, bitmap);
        }
    }

    /**
     * 根据指定url获得图片对象
     *
     * @param url 图片的网络地址
     * @return 图片对象
     */
    public Bitmap getBitmapByCache(String url) {
        if (url == null) {
            return null;
        }
        return mCaches.get(url);
    }

    /**
     * 设置图片相关方法
     *
     * @param imageView 需要设置的组件
     * @param url       需要显示的图片路径
     * @param defImg    默认本地图片
     */
    public void showImageBuThread(ImageView imageView, final String url, int defImg) {
        mImageView = imageView;
        mDefImg = defImg;
        Bitmap bitmap = getBitmapByCache(url);
        imageView.setVisibility(View.VISIBLE);
        if (bitmap == null) {
            new NewsAsyncTask(mImageView, url).execute(url);
        } else {
            mImageView.setImageBitmap(bitmap);
        }
    }

    /**
     * 设置图片相关方法
     *
     * @param imageView 需要设置的组件
     * @param url       需要显示的图片路径
     * @param defurl    默认本地图片
     */
    public void showImageBuThread(ImageView imageView, final String url, String defurl) {
        mImageView = imageView;
        Bitmap bitmap = getBitmapByCache(url);
        if (bitmap == null) {
            new NewsAsyncTask(mImageView, url, defurl).execute(url);
        } else {
            mImageView.setImageBitmap(bitmap);
        }
    }

    private class NewsAsyncTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView mImageView;
        private String mUrl;
        private String mDefUrl;

        public NewsAsyncTask(ImageView mImageView, String mUrl) {
            this.mImageView = mImageView;
            this.mUrl = mUrl;
        }

        public NewsAsyncTask(ImageView mImageView, String mUrl, String defurl) {
            this.mImageView = mImageView;
            this.mUrl = mUrl;
            this.mDefUrl = defurl;
        }

        Bitmap def_bitmap;

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bitmap = getBitmapByUrl(url);
            if (bitmap != null) {
                addBitmapToCache(url, bitmap);
            }

            if (mDefUrl != null) {
                def_bitmap = getBitmapByCache(mDefUrl);
                if (def_bitmap == null) {
                    def_bitmap = getBitmapByUrl(mDefUrl);
                    if (def_bitmap != null) {
                        addBitmapToCache(mDefUrl, def_bitmap);
                    } else {
                        mDefUrl = null;
                    }
                }
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (mImageView.getTag().equals(mUrl)) {
                if (!mUrl.equals("")) {
                    mImageView.setImageBitmap(bitmap);
                } else {
                    if (mDefUrl != null) {
                        mImageView.setImageBitmap(def_bitmap);
                    } else if (mDefImg != 0) {
                        mImageView.setImageResource(mDefImg);//设置为默认图片
                    } else {
                        mImageView.setImageResource(R.mipmap.ic_launcher);//设置为默认图片
                    }

                }
            }
        }
    }

    /**
     * 根据url获得Bitmap
     *
     * @param urlString 图片url
     * @return Bitmap对象
     */
    public Bitmap getBitmapByUrl(String urlString) {
        Bitmap bitmap = null;
        InputStream is = null;
        if (!urlString.trim().equals("")) {
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                is = new BufferedInputStream(connection.getInputStream());
                bitmap = BitmapFactory.decodeStream(is);
                connection.disconnect();
                return bitmap;
            } catch (Exception e) {
                return null;
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
