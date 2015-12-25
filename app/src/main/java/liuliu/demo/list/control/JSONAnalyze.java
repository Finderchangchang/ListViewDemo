package liuliu.demo.list.control;

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

import java.util.ArrayList;
import java.util.List;

import liuliu.demo.list.model.NewsBean;

/**
 * 解析json的方法
 * Created by Administrator on 2015/12/25.
 */
public class JSONAnalyze {
    private Context mIntails;

    public JSONAnalyze(Context mIntails) {
        this.mIntails = mIntails;
    }

    public void getJson(final OnLoadData loadData, String url) {
        RequestQueue mQueue = Volley.newRequestQueue(mIntails);
        JsonRequest jsonObjectRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    List<NewsBean> beans = new ArrayList<>();
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray json = response.getJSONArray("result");
                            NewsBean ben;
                            for (int i = 0; i < json.length(); i++) {
                                JSONObject data = (JSONObject) json.get(i);
                                ben = new NewsBean();
                                ben.set_id(data.getString("_id"));
                                ben.setDay(data.getInt("day"));
                                ben.setDes(data.getString("des"));
                                ben.setLunar(data.getString("lunar"));
                                ben.setPic(data.getString("pic"));
                                ben.setMonth(data.getInt("month"));
                                ben.setTitle(data.getString("title"));
                                ben.setYear(data.getInt("year"));
                                beans.add(ben);
                            }
                            loadData.load(beans);
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
        void load(List<NewsBean> list);
    }
}
