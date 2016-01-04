package liuliu.demo.list.base;

import android.app.Application;
import android.content.Context;

import in.srain.cube.Cube;

/**
 * Created by Administrator on 2015/12/25.
 */
public class BaseApplication extends Application {
    private static BaseApplication sInstance;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Cube.onCreate(this);
        sInstance = this;
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

    public static BaseApplication getInstance() {
        return sInstance;
    }

    //系统处于资源匮乏的状态
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
