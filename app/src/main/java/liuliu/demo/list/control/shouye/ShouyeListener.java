package liuliu.demo.list.control.shouye;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import liuliu.demo.list.base.Utils;
import liuliu.demo.list.control.base.AnalyzeBase;
import liuliu.demo.list.model.ImageModel;
import liuliu.demo.list.model.NewsBean;

/**
 * Created by Administrator on 2015/12/29.
 */
public class ShouyeListener {
    Context mContext;
    AnalyzeBase guanggao;

    public ShouyeListener(Context mContext) {
        this.mContext = mContext;
        guanggao = new AnalyzeBase(mContext);
    }

    public void loadGuanggao(final OnLoad load, String url) {
        guanggao.getJson(new AnalyzeBase.OnLoadData() {
            ImageModel model;

            @Override
            public void load(boolean result, final Object object) {
                JSONObject json = (JSONObject) object;
                try {
                    int type = json.getInt("type");
                    JSONArray array = json.getJSONArray("content");
                    List list = new ArrayList();
                    for (int i = 0; i < array.length(); i++) {
                        model = new ImageModel();
                        model.setImage(array.getJSONObject(i).getString("image"));
                        model.setLink(array.getJSONObject(i).getString("link"));
                        list.add(model);
                    }
                    load.load(type, list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, url);
    }

    public void loadGoodType(final OnLoad load, String url) {
        guanggao.getJson(new AnalyzeBase.OnLoadData() {
            ImageModel model;

            @Override
            public void load(boolean result, final Object object) {
                JSONArray array = (JSONArray) object;
                try {
                    List list = new ArrayList();
                    for (int i = 0; i < array.length(); i++) {
                        model = (ImageModel) Utils.getObject("ImageModel", array.getJSONObject(i));
                        list.add(model);
                    }
                    load.load(0, list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, url);
    }

    public interface OnLoad {
        void load(int type, List list);
    }
}
