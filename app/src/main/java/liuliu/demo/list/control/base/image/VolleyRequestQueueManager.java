package liuliu.demo.list.control.base.image;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import liuliu.demo.list.base.BaseApplication;

/**
 * 9  * 请求队列处理类
 * 10  * 获取RequestQueue对象
 * 11
 */
public class VolleyRequestQueueManager {
    // 获取请求队列类
    public static RequestQueue mRequestQueue = Volley.newRequestQueue(BaseApplication.newInstance());

    //添加任务进任务队列
    public static void addRequest(Request<?> request, Object tag) {
        if (tag != null) {
            request.setTag(tag);
        }
        mRequestQueue.add(request);
    }

    //取消任务
    public static void cancelRequest(Object tag) {
        mRequestQueue.cancelAll(tag);
    }
}
