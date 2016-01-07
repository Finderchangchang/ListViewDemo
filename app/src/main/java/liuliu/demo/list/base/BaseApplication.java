package liuliu.demo.list.base;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import in.srain.cube.Cube;

/**
 * Created by Administrator on 2015/12/25.
 */
public class BaseApplication extends Application {
    public static BaseApplication myApplication;
    private static Context context;

    public static BaseApplication newInstance() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Cube.onCreate(this);
        myApplication = this;
        context = getApplicationContext();
        initImageLoader(context);
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(config);
    }

    public static Context getContext() {
        return context;
    }

    //系统处于资源匮乏的状态
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
