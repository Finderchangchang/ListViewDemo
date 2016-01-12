package liuliu.demo.list.control.manager;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import liuliu.demo.list.base.Utils;
import liuliu.demo.list.control.base.AnalyzeBase;
import liuliu.demo.list.control.base.AnalyzeBase;
import liuliu.demo.list.model.GoodModel;
import liuliu.demo.list.model.ImageModel;

/**
 * 首页数据管理
 * Created by Administrator on 2015/12/29.
 */
public class ShouyeListener {
    Context mContext;
    AnalyzeBase guanggao;

    public ShouyeListener(Context mContext) {
        this.mContext = mContext;
        guanggao = new AnalyzeBase(mContext);
    }

    public void loadTop(boolean isRefresh, final OnLoadTop load, String url) {
        guanggao.getJson("topimg", isRefresh, new AnalyzeBase.OnLoadData() {
            ImageModel model;

            @Override
            public void load(boolean result, final Object object) {
                try {
                    if (object != null) {
                        JSONArray json = new JSONArray(object.toString());
                        List list = new ArrayList();
                        for (int i = 0; i < json.length(); i++) {
                            model = new ImageModel();
                            model.setImage(json.getJSONObject(i).getString("img"));
                            model.setLink(json.getJSONObject(i).getString("link"));
                            list.add(model);
                        }
                        load.load(list);
                    } else {
                        load.load(null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, url);
    }

    public void loadGuanggao(boolean isRefresh, final OnLoad load, String url) {
        guanggao.getJson("guanggao", isRefresh, new AnalyzeBase.OnLoadData() {
            ImageModel model;

            @Override
            public void load(boolean result, final Object object) {
                try {
                    if (object != null) {
                        JSONObject json = new JSONObject(object.toString());
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
                    } else {
                        load.load(0, null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, url);
    }

    public void loadGoodType(boolean isRefresh, final OnLoad load, String url) {
        guanggao.getJson("goodtype", isRefresh, new AnalyzeBase.OnLoadData() {
            ImageModel model;

            @Override
            public void load(boolean result, final Object object) {
                try {
                    if (object != null) {
                        JSONArray array = new JSONArray(object.toString());
                        List list = new ArrayList();
                        for (int i = 0; i < array.length(); i++) {
                            model = (ImageModel) Utils.getObject("ImageModel", array.getJSONObject(i));
                            list.add(model);
                        }
                        load.load(0, list);
                    } else {
                        load.load(0, null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, url);
    }

    public void loadHotGood(boolean isRefresh, final OnLoadHot load, String url) {
        guanggao.getJson("hotgood", isRefresh, new AnalyzeBase.OnLoadData() {
            GoodModel model;

            @Override
            public void load(boolean result, final Object object) {
                try {
                    if (object != null) {
                        JSONArray array = new JSONArray(object.toString());
                        List[] lists = new List[array.length()];
                        List list;
                        for (int i = 0; i < array.length(); i++) {
                            list = new ArrayList();
                            JSONArray arr = array.getJSONArray(i);
                            for (int j = 0; j < arr.length(); j++) {
                                model = new GoodModel();
                                model = (GoodModel) Utils.getObject("GoodModel", arr.getJSONObject(j));
                                list.add(model);
                            }
                            lists[i] = list;
                        }
                        load.load(lists);
                    } else {
                        load.load(null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, url);
    }

    public interface OnLoad {
        void load(int type, List list);
    }

    public interface OnLoadHot {
        void load(List[] lists);
    }

    public interface OnLoadTop {
        void load(List list);
    }
}
