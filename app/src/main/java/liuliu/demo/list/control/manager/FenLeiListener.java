package liuliu.demo.list.control.manager;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import liuliu.demo.list.control.base.AnalyzeBase;
import liuliu.demo.list.model.TypeModel;

/**
 * Created by Administrator on 2015/12/31.
 */
public class FenLeiListener {
    Context mContext;
    AnalyzeBase jsonbase;
    TypeModel model;

    public FenLeiListener(Context mContext) {
        this.mContext = mContext;
        jsonbase = new AnalyzeBase(mContext);
    }

    public void loadFenlei(final OnLoad load, String url) {
        jsonbase.getJson(new AnalyzeBase.OnLoadData() {


            @Override
            public void load(boolean result, final Object object) {
                JSONArray json = (JSONArray) object;
                try {
                    List[] lists = new List[json.length()];
                    for (int i = 0; i < json.length(); i++) {
                        List<TypeModel> list = new ArrayList<TypeModel>();
                        JSONObject obj = json.getJSONObject(i);
                        list.add(loadModel(obj));
                        JSONArray array = obj.getJSONArray("small");
                        for (int j = 0; j < array.length(); j++) {
                            list.add(loadModel(array.getJSONObject(j)));
                        }
                        lists[i] = list;
                    }
                    load.load(lists);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, url);
    }

    private TypeModel loadModel(JSONObject object) throws JSONException {
        model = new TypeModel();
        model.setBid(object.getString("bid"));
        model.setSid(object.getString("id"));
        model.setImage(object.getString("image"));
        model.setIsPreferential(object.getBoolean("isPreferential"));
        model.setIsPresent(object.getBoolean("isPresent"));
        model.setName(object.getString("name"));
        return model;
    }

    public interface OnLoad {
        void load(List[] list);
    }
}
