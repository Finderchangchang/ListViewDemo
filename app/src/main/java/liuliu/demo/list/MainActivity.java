package liuliu.demo.list;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.List;

import liuliu.demo.list.control.JSONAnalyze;
import liuliu.demo.list.control.NewsAdapter;
import liuliu.demo.list.model.NewsBean;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ListView two_view;
    private String mUrl = "http://api.juheapi.com/japi/toh?v=1.0&month=12&day=24&key=adee859f57cade911dbfe1050666153d";
    public static MainActivity mIntails;
    NewsAdapter mAdapter;
    ScrollView mScrollView;
    SwipeRefreshLayout mSwipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.lv_main);
        two_view = (ListView) findViewById(R.id.lv_two_main);
        mScrollView = (ScrollView) findViewById(R.id.scroll_main);
        mSwipe = (SwipeRefreshLayout) findViewById(R.id.srf_layout_main);
        mSwipe.setColorSchemeColors(R.color.colorAccent);
        mIntails = this;
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDatas();
            }
        });
        loadDatas();
    }

    private void loadDatas() {
        new JSONAnalyze(mIntails).getJson(new JSONAnalyze.OnLoadData() {
            @Override
            public void load(List<NewsBean> list) {
                mAdapter = new NewsAdapter(mIntails, list);
                listView.setAdapter(mAdapter);
                two_view.setAdapter(mAdapter);
                mScrollView.scrollTo(0, 0);
                mSwipe.setRefreshing(false);
            }
        }, mUrl);
    }
}
