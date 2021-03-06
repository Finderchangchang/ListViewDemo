package liuliu.demo.list.control.base;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析json的方法
 * Created by Administrator on 2015/12/25.
 */
public class JSONAnalyze<T> {
    private Context mIntails;
    private String mClassName;//类名

    public JSONAnalyze(Context mIntails, String className) {
        this.mIntails = mIntails;
        this.mClassName = className;
    }

    public void getJson(final OnLoadData loadData, String url) {
        RequestQueue mQueue = Volley.newRequestQueue(mIntails);
        JsonRequest jsonObjectRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    List<T> beans = new ArrayList<>();

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.isNull("error_code")) {
                                if (response.getString("error_code").equals("0")) {
                                    JSONArray json = response.getJSONArray("result");
                                    for (int i = 0; i < json.length(); i++) {
                                        JSONObject data = (JSONObject) json.get(i);
                                        T ben = (T) getObject(mClassName, data);
                                        beans.add(ben);
                                    }
                                    loadData.load(beans);
                                }
                            } else if (!response.isNull("return")) {
                                if (response.getString("return").equals("OK")) {//请求成功触发事件
                                    if (response.getString("return").equals("OK")) {//请求成功触发事件
                                        JSONArray json = response.getJSONArray("data");
                                        for (int i = 0; i < json.getJSONArray(0).length(); i++) {
                                            JSONObject data = (JSONObject) json.getJSONArray(0).get(i);
                                            T ben = (T) getObject(mClassName, data);
                                            beans.add(ben);
                                        }
                                        loadData.load(beans);
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                , new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        );
        mQueue.add(jsonObjectRequest);
    }

    /**
     * 获得json
     */
    public interface OnLoadData {
        void load(List list);
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
