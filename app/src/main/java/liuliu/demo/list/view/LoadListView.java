package liuliu.demo.list.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import liuliu.demo.list.R;

/**
 * 加载更多的ListView
 * <p/>
 * Created by Administrator on 2015/12/11.
 */
public class LoadListView extends ListView implements AbsListView.OnScrollListener {
    View footer;
    LinearLayout footerView;
    TextView tv;
    int lastVisibleItem;
    int totalItemCount;
    boolean isLoading;
    public onLoadListener listener;

    public LoadListView(Context context) {
        super(context);
        initView(context);
    }

    public LoadListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        footer = inflater.inflate(R.layout.loadmore_footer, null);//获得底部布局
        footerView = (LinearLayout) footer.findViewById(R.id.load_more_layout);
        tv = (TextView) footer.findViewById(R.id.footer_tv);
        this.addFooterView(footer);
        this.setOnScrollListener(this);
        footerView.setVisibility(GONE);
    }

    /**
     * @param view
     * @param scrollState
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        footerView.setVisibility(VISIBLE);
        if (totalItemCount == lastVisibleItem && scrollState == SCROLL_STATE_IDLE) {//滚动停止，且滚动到底部
            if (!isLoading && !isBottom) {//活动到最下面
                isLoading = true;
                listener.onLoad();
                tv.setText("载入中，请稍候...");
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.lastVisibleItem = firstVisibleItem + visibleItemCount;
        this.totalItemCount = totalItemCount;
    }

    //加载更多数据的回调接口
    public interface onLoadListener {
        void onLoad();
    }

    //加载完毕
    public void loadComplete(boolean result) {
        isLoading = false;
        footerView.setVisibility(GONE);
        if (result) {
            footerView.setVisibility(VISIBLE);
            tv.setText("没有更多数据");
        }
        isBottom = result;
    }

    private boolean isBottom;

    public void setOnLoadListener(onLoadListener listener) {
        this.listener = listener;
    }

}
