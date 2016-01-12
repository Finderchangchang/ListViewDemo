package liuliu.demo.list.control.manager;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import liuliu.demo.list.base.Utils;
import liuliu.demo.list.control.base.AnalyzeBase;
import liuliu.demo.list.model.GoodDetailModel;

/**
 * 商品分类列表
 * Created by Administrator on 2015/12/31.
 */
public class GoodListListener {
    Context mContext;
    AnalyzeBase jsonbase;

    public GoodListListener(Context mContext) {
        this.mContext = mContext;
        jsonbase = new AnalyzeBase(mContext);
    }

    public void loadList(boolean isRefresh, final OnLoad load, String url) {
        jsonbase.getJson(isRefresh, new AnalyzeBase.OnLoadData() {
            @Override
            public void load(boolean result, final Object object) {
                JSONObject obj = (JSONObject) object;
                try {
                    JSONArray array = obj.getJSONArray("data");
                    List<GoodDetailModel> list = new ArrayList<GoodDetailModel>();
                    for (int i = 0; i < array.length(); i++) {
                        GoodDetailModel model = (GoodDetailModel) Utils.getObject("GoodDetailModel", array.getJSONObject(i));
                        list.add(model);
                    }
                    load.load(obj.getBoolean("tail"), list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, url);
    }

    public interface OnLoad {
        void load(boolean tail, List<GoodDetailModel> list);
    }
}
