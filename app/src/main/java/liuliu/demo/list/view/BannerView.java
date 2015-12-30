package liuliu.demo.list.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import liuliu.demo.list.R;
import liuliu.demo.list.control.base.NormalLoadPictrue;
import liuliu.demo.list.model.BannerModel;
import liuliu.demo.list.model.ImageModel;


/**
 * Created by Administrator on 2015/12/29.
 */
public class BannerView extends FrameLayout {
    //自动轮播的时间间隔
    private final static int TIME_INTERVAL = 5;
    //自动轮播启用开关
    private final static boolean isAutoPlay = true;
    //自定义轮播图的资源
    private List<ImageModel> bannerModelList;
    //放轮播图片的imageview的list
    private List<ImageView> imageViewList;
    //放圆点的view的list
    private List<View> dotViewsList;
    private ViewPager viewPager;
    //当前轮播页
    private int currentItem = 0;
    //定时任务
    private ScheduledExecutorService scheduledExecutorService;
    private Context context;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            viewPager.setCurrentItem(currentItem);
        }
    };


    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
    }

    public BannerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
//        imageViewList = new ArrayList<ImageView>();
//        dotViewsList = new ArrayList<View>();
//
//        //bannerModelList = list;
//        initUI(context);
//
//        if (isAutoPlay) {
//            startPlay();
//        }
    }

    public void setBannerView(List<ImageModel> list) {
        bannerModelList = list;
        imageViewList = new ArrayList<ImageView>();
        dotViewsList = new ArrayList<View>();
        initUI(context);
        if (isAutoPlay) {
            startPlay();
        }
    }

    /**
     * 开始
     */
    private void startPlay() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 6, TimeUnit.SECONDS);
    }

    /**
     * 停止
     */
    private void stopPlay() {
        scheduledExecutorService.shutdown();
    }


    /**
     * 初始化views
     *
     * @param context
     */
    private void initUI(Context context) {
        if (bannerModelList == null || bannerModelList.size() == 0) {
            return;
        }
        LayoutInflater.from(context).inflate(R.layout.banner, this, true);
        LinearLayout dotLayout = (LinearLayout) findViewById(R.id.banner_dots);
        dotLayout.removeAllViews();
        //热点个数和图片个数相等
        for (int i = 0; i < bannerModelList.size(); i++) {
            ImageView view = new ImageView(context);
            view.setTag(bannerModelList.get(i).getImage());
            if (i == 0) {
                view.setBackgroundResource(R.mipmap.ic_launcher);
            }
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViewList.add(view);

            //点
            ImageView dotview = new ImageView(context);

            dotview.setBackgroundResource(R.drawable.dot_normarl);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(8, 8);
            params.leftMargin = 5;
            params.rightMargin = 5;
            dotLayout.addView(dotview, params);
            dotViewsList.add(dotview);
        }
        viewPager = (ViewPager) findViewById(R.id.viewpager_banner);
        viewPager.setFocusable(true);
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
    }

    /**
     * 数据
     */
    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(imageViewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageViewList.get(position);
            // imageView.setImageResource(R.mipmap.ic_launcher);
            new NormalLoadPictrue().getPicture(bannerModelList.get(position).getImage(), imageView);
            ((ViewPager) container).addView(imageView);
            return imageViewList.get(position);
        }

        @Override
        public int getCount() {
            return imageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    //viewpager的监听事件
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        boolean isAutoPlay = false;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //改变点的颜色
            currentItem = position;
            for (int i = 0; i < dotViewsList.size(); i++) {
                if (i == position) {
                    ((View) dotViewsList.get(i)).setBackgroundResource(R.drawable.dot_color);
                } else {
                    ((View) dotViewsList.get(i)).setBackgroundResource(R.drawable.dot_normarl);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case 1://手势滑动，空闲中
                    isAutoPlay = false;
                    break;
                case 2://界面切换中
                    isAutoPlay = true;
                    break;
                case 0://滑动结束，即切换完毕或者加载完毕
                    //当前为最好一张，此时从右向左滑，则切换到第一张
                    if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 & !isAutoPlay) {
                        viewPager.setCurrentItem(0);
                    } else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
                        viewPager.setCurrentItem(viewPager.getAdapter().getCount() - 1);
                    }
                    //当前为第一张，此时从左向右滑，则切换到最后一张
                    break;

            }
        }
    }

    /**
     * 执行轮播图切换任务
     */
    private class SlideShowTask implements Runnable {
        @Override
        public void run() {
            synchronized (viewPager) {
                currentItem = (currentItem + 1) % imageViewList.size();
                handler.obtainMessage().sendToTarget();
            }
        }
    }

    /**
     * 销毁ImageView资源，回收内存
     */
    private void destoryBitmaps() {
        for (int i = 0; i < bannerModelList.size(); i++) {
            ImageView imageView = imageViewList.get(i);
            Drawable drawable = imageView.getDrawable();
            if (drawable != null) {
                //解除drawable对view的引用
                drawable.setCallback(null);
            }
        }
    }


}
