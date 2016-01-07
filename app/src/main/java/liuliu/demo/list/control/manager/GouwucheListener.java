package liuliu.demo.list.control.manager;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import liuliu.demo.list.base.Utils;
import liuliu.demo.list.control.base.AnalyzeBase;
import liuliu.demo.list.control.base.JsonBase;
import liuliu.demo.list.model.FacesModel;
import liuliu.demo.list.model.TypeModel;

/**
 * Created by Administrator on 2015/12/31.
 */
public class GouwucheListener {
    Context mContext;
    JsonBase jsonbase;
    TypeModel model;

    public GouwucheListener(Context mContext) {
        this.mContext = mContext;
        jsonbase = new JsonBase(mContext);
    }

    public void loadFace(final OnLoad load, String url) {
        jsonbase.getJson(new JsonBase.OnLoadData() {
            @Override
            public void load(boolean result, final Object object) {
                JSONArray json = (JSONArray) object;
                List<FacesModel> lists = new ArrayList<FacesModel>();
                try {
                    FacesModel model;
                    for (int i = 0; i < json.length(); i++) {
                        JSONObject obj = json.getJSONObject(i);
                        model = (FacesModel) Utils.getObject("FacesModel", obj);
                        lists.add(model);
                    }
                    load.load(lists);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, url);
    }

    public interface OnLoad {
        void load(List list);
    }
}
