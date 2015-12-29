package liuliu.demo.list;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.List;

import liuliu.demo.list.control.CommonAdapter;
import liuliu.demo.list.control.CommonViewHolder;
import liuliu.demo.list.control.GoodAnalyze;
import liuliu.demo.list.control.JSONAnalyze;
import liuliu.demo.list.model.GoodModel;
import liuliu.demo.list.model.NewsBean;
import liuliu.demo.list.view.GridLinearLayout;

public class MainActivity extends AppCompatActivity {
    private GridLinearLayout listView;
    //http://api.map.baidu.com/telematics/v3/weather?location=保定&output=json&ak=XAUTG3wLFCte206ZrMVunjbG&mcode=5F:33:8B:DD:33:47:51:54:BD:52:04:11:97:3D:82:9D:21:23:BB:AA;liuliu.demo.list
    private String mUrl = "http://api.juheapi.com/japi/toh?v=1.0&month=12&day=24&key=adee859f57cade911dbfe1050666153d";
    //    private String mUrl = "http://api.map.baidu.com/telematics/v3/weather?location=";
    private String mGoodUrl = "http://www.hesq.com.cn/fresh/fore/logic/app/home/product.php";
    public static MainActivity mIntails;
    ScrollView mScrollView;
    GridView gridView;
    SwipeRefreshLayout mSwipe;
    JSONAnalyze<NewsBean> jsonAnalyze;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (GridLinearLayout) findViewById(R.id.lv_main);
        mScrollView = (ScrollView) findViewById(R.id.scroll_main);
        mSwipe = (SwipeRefreshLayout) findViewById(R.id.srf_layout_main);
        gridView = (GridView) findViewById(R.id.gv_main);
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
    GoodAnalyze json;
    CommonAdapter<GoodModel> mAdapters;

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
                listView.setColumns(2);
                listView.bindLinearLayout();
                mScrollView.scrollTo(0, 0);
                mSwipe.setRefreshing(false);
            }
        }, mUrl);

        json = new GoodAnalyze<GoodModel>(mIntails, "GoodModel");
        json.getJson(new GoodAnalyze.OnLoadData() {
            @Override
            public void load(final List list) {
                mAdapters = new CommonAdapter<GoodModel>(mIntails, list, R.layout.item_layout) {
                    @Override
                    public void convert(CommonViewHolder holder, GoodModel model, int position) {
                        holder.setImageByUrl(R.id.iv_icon, model.getImage(), R.mipmap.error);
                        holder.setText(R.id.tv_title, model.getName());
                        holder.setText(R.id.tv_desc, model.getPrice());
                    }
                };
                gridView.setAdapter(mAdapters);
                setListViewHeightBasedOnChildren(gridView, 2);
                mScrollView.scrollTo(0, 0);
                mSwipe.setRefreshing(false);
            }
        }, mGoodUrl);
    }

    public static void setListViewHeightBasedOnChildren(GridView listView, int col) {
        // ��ȡlistview��adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // �̶��п��ж�����
//        int col = 2;// listView.getNumColumns();
        int totalHeight = 0;
        // iÿ�μ�4���൱��listAdapter.getCount()С�ڵ���4ʱ ѭ��һ�Σ�����һ��item�ĸ߶ȣ�
        // listAdapter.getCount()С�ڵ���8ʱ�������θ߶����
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // ��ȡlistview��ÿһ��item
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            // ��ȡitem�ĸ߶Ⱥ�
            totalHeight += listItem.getMeasuredHeight();
        }
        // ��ȡlistview�Ĳ��ֲ���
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // ���ø߶�
        params.height = totalHeight;
        // ����margin
        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        // ���ò���
        listView.setLayoutParams(params);
    }
}
