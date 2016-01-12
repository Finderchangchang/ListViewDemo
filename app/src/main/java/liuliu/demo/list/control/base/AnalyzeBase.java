package liuliu.demo.list.control.base;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import net.tsz.afinal.FinalDb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import liuliu.demo.list.base.Utils;
import liuliu.demo.list.model.CacheModel;

/**
 * 解析json的方法
 * Created by Administrator on 2015/12/25.
 */
public class AnalyzeBase {
    private Context mIntails;
    private FinalDb mDB;

    public AnalyzeBase(Context mIntails) {
        this.mIntails = mIntails;
        mDB = FinalDb.create(mIntails);
    }

    public void getJson(boolean isRefresh, final OnLoadData loadData, String url) {
        getJson("", isRefresh, loadData, url);
    }

    public void getJson(final String type, boolean isRefresh, final OnLoadData loadData, String url) {
        //刷新，并且联网状态才访问网络数据//联网（访问网络数据）
        if (isRefresh) {
            RequestQueue mQueue = Volley.newRequestQueue(mIntails);
            JsonRequest jsonObjectRequest = new JsonObjectRequest(url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getString("return").equals("OK")) {//请求成功触发事件
                                    if (type != "") {//类型不为空，在缓存中读取数据
                                        List<CacheModel> list = mDB.findAllByWhere(CacheModel.class, "type='" + type + "'");
                                        CacheModel model = new CacheModel();
                                        model.setType(type);
                                        model.setData(response.getString("data"));
                                        model.setError(response.getString("error"));
                                        if (list.size() == 1) {
                                            model.setId(list.get(0).getId());
                                            mDB.update(model);
                                        } else if (list.size() > 1) {
                                            mDB.deleteByWhere(CacheModel.class, "type='" + type + "'");
                                            mDB.save(model);
                                        } else if (list.size() == 0) {
                                            mDB.save(model);
                                        }
                                    }
                                    loadData.load(true, response.get("data"));
                                }
                            } catch (JSONException e) {
                                loadData.load(false, null);
                            }
                        }
                    }
                    , new Response.ErrorListener()

            {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loadData.load(false, null);
                }
            }
            );
            mQueue.add(jsonObjectRequest);
        } else {//未联网
            List<CacheModel> list = mDB.findAllByWhere(CacheModel.class, "type='" + type + "'");
            if (list.size() > 0) {
                loadData.load(true, list.get(0).getData());
            } else {
                loadData.load(false, null);
            }
        }
    }

    /**
     * 获得json
     */
    public interface OnLoadData {
        void load(boolean result, Object object);
    }

    private Object getObject(String modelName, JSONObject jsonObject) {
        Object objectModel = new Object();
        Field[] fields;
        if (jsonObject != null) {
            if (!modelName.equals("")) {
                try {
                    objectModel = getBean(modelName);
                    fields = objectModel.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        setProperty(objectModel, field.getName(), jsonObject.getString(field.getName()), field.getType().getSimpleName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return objectModel;
    }

    //创建的model对象，字段名，字段值
    public static void setProperty(Object bean, String propertyName, String propertyValue, String type) throws Exception {
        Class cls = bean.getClass();
        Method[] methos = cls.getMethods();//得到所有的方法
        for (Method m : methos) {
            if (m.getName().equalsIgnoreCase("set" + propertyName)) {
                //找到方法就注入
                if (type.equals("String")) {
                    m.invoke(bean, propertyValue);
                } else if (type.equals("int")) {
                    m.invoke(bean, new Integer(propertyValue.toString()));
                } else if (type.equals("boolean")) {
                    m.invoke(bean, new Boolean(propertyValue.toString()));
                }
                break;
            }
        }
    }

    //包名+model名
    public static Object getBean(String className) throws Exception {
        Class cls;
        try {
            cls = Class.forName("liuliu.demo.list.model." + className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new Exception("类错误");
        }
        Constructor[] cons;
        try {
            cons = cls.getConstructors();//得到所有构造器
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("构造器错误");
        }
        //如果上面没错，就有构造方法
        Constructor defcon = cons[0];//得到默认构造器，第0个事默认构造器，无参构造方法
        Object obj = defcon.newInstance();//实例化，得到一个对象
        return obj;
    }
}
