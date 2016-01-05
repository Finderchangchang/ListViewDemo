package liuliu.demo.list.base;

import android.app.Application;
import android.content.Context;

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
