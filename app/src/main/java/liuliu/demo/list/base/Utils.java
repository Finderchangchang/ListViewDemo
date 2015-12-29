package liuliu.demo.list.base;

import android.content.Context;
import android.view.WindowManager;

import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2015/12/29.
 */
public class Utils {
    public static Object getObject(String modelName, JSONObject jsonObject) {
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

    public static int getScannerWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getWidth();
    }
}
