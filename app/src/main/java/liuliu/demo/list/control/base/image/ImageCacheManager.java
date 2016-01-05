package liuliu.demo.list.control.base.image;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;

/**
 * 14  * 图片缓存管理类 获取ImageLoader对象
 * 15  *
 * 16  * @author Rabbit_Lee
 * 17  *
 * 18
 */
public class ImageCacheManager {

    private static String TAG = ImageCacheManager.class.getSimpleName();

    // 获取图片缓存类对象
    private static ImageCache mImageCache = new ImageManager();
    // 获取ImageLoader对象
    public static ImageLoader mImageLoader = new ImageLoader(VolleyRequestQueueManager.mRequestQueue, mImageCache);

    /**
     * 获取ImageListener
     *
     * @param view
     * @param defaultImage
     * @param errorImage
     * @return
     */
    public static ImageListener getImageListener(final ImageView view, final Bitmap defaultImage, final Bitmap errorImage) {

        return new ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // 回调失败
                if (errorImage != null) {
                    view.setImageBitmap(errorImage);
                }
            }

            @Override
            public void onResponse(ImageContainer response, boolean isImmediate) {
                // 回调成功
                if (response.getBitmap() != null) {
                    view.setImageBitmap(response.getBitmap());
                } else if (defaultImage != null) {
                    view.setImageBitmap(defaultImage);
                }
            }
        };

    }

    /**
     * 62      * 提供给外部调用方法
     * 63      *
     * 64      * @param url
     * 65      * @param view
     * 66      * @param defaultImage
     * 67      * @param errorImage
     * 68
     */
    public static void loadImage(String url, ImageView view, Bitmap defaultImage, Bitmap errorImage) {
        mImageLoader.get(url, ImageCacheManager.getImageListener(view, defaultImage, errorImage), 0, 0);
    }


    /**
     * 74      * 提供给外部调用方法
     * 75      *
     * 76      * @param url
     * 77      * @param view
     * 78      * @param defaultImage
     * 79      * @param errorImage
     * 80
     */


    public static void loadImage(String url, ImageView view, Bitmap defaultImage, Bitmap errorImage, int maxWidth, int maxHeight) {
        mImageLoader.get(url, ImageCacheManager.getImageListener(view, defaultImage, errorImage), maxWidth, maxHeight);
    }
}
