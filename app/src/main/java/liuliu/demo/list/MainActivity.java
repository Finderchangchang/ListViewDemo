package liuliu.demo.list;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.List;

import liuliu.demo.list.control.CommonAdapter;
import liuliu.demo.list.control.CommonViewHolder;
import liuliu.demo.list.control.JSONAnalyze;
import liuliu.demo.list.model.NewsBean;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    //百度天气查询接口
    //http://api.map.baidu.com/telematics/v3/weather?location=保定&output=json&ak=XAUTG3wLFCte206ZrMVunjbG&mcode=5F:33:8B:DD:33:47:51:54:BD:52:04:11:97:3D:82:9D:21:23:BB:AA;liuliu.demo.list
    private String mUrl = "http://api.juheapi.com/japi/toh?v=1.0&month=12&day=24&key=adee859f57cade911dbfe1050666153d";
    //    private String mUrl = "http://api.map.baidu.com/telematics/v3/weather?location=";
    public static MainActivity mIntails;
    ScrollView mScrollView;
    SwipeRefreshLayout mSwipe;
    JSONAnalyze<NewsBean> jsonAnalyze;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.lv_main);
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
//        try {
//            mUrl += URLEncoder.encode("保定", "UTF-8") + "&output=json&ak=XAUTG3wLFCte206ZrMVunjbG&mcode=5F:33:8B:DD:33:47:51:54:BD:52:04:11:97:3D:82:9D:21:23:BB:AA;liuliu.demo.list";
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        loadDatas();
    }

    CommonAdapter<NewsBean> mAdapter;

    private void loadDatas() {
        jsonAnalyze = new JSONAnalyze<NewsBean>(mIntails, "NewsBean");
        jsonAnalyze.getJson(new JSONAnalyze.OnLoadData() {
            @Override
            public void load(final List list) {
                mAdapter = new CommonAdapter<NewsBean>(mIntails, list, R.layout.item_layout) {
                    @Override
                    public void convert(CommonViewHolder holder, NewsBean newsBean, int position) {
                        holder.setImageByUrl(R.id.iv_icon, newsBean.getPic(), R.mipmap.error);
                        holder.setText(R.id.tv_title, newsBean.getTitle());
                        holder.setText(R.id.tv_desc, newsBean.getDes());
                    }
                };
                listView.setAdapter(mAdapter);
                mScrollView.scrollTo(0, 0);
                mSwipe.setRefreshing(false);
            }
        }, mUrl);
    }
}
