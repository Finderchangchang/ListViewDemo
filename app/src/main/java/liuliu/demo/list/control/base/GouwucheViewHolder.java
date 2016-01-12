package liuliu.demo.list.control.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import liuliu.demo.list.R;
import liuliu.demo.list.base.Utils;
import liuliu.demo.list.control.base.image.ImageCacheManager;
import liuliu.demo.list.model.ImageModel;
import liuliu.demo.list.ui.activity.DetailListsActivity;
import liuliu.demo.list.ui.activity.MainActivity;
import me.xiaopan.sketch.SketchImageView;

/**
 * Created by liuliu on 2015/11/16   16:29
 *
 * @author 柳伟杰
 * @Email 1031066280@qq.com
 */
public class GouwucheViewHolder {
    private final SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;
    private ImageLoader mImageLoader;
    private Context mContext;
    private DisplayImageOptions mDisplayImageOptions;
    private ImageLoadingListenerImpl mImageLoadingListenerImpl;

    private GouwucheViewHolder(Context context, ViewGroup parent, int layoutId,
                               int position) {
        this.mContext = context;
        this.mPosition = position;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        mConvertView.setTag(this);
        mImageLoader = ImageLoader.getInstance();
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.ic_default_adimage)
                .showImageForEmptyUri(R.mipmap.ic_default_adimage)
                .showImageOnFail(R.mipmap.ic_default_adimage)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .resetViewBeforeLoading()
                .build();
        mImageLoadingListenerImpl = new ImageLoadingListenerImpl();
    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static GouwucheViewHolder get(Context context, View convertView,
                                         ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new GouwucheViewHolder(context, parent, layoutId, position);
        }
        return (GouwucheViewHolder) convertView.getTag();
    }

    public View getConvertView() {
        return mConvertView;
    }

    public GouwucheViewHolder setHeight(int viewId, int height) {
        LinearLayout view = getView(viewId);
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.height = height;
        view.setLayoutParams(lp);
        return this;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public GouwucheViewHolder setText(int viewId, Object text) {
        TextView view = getView(viewId);
        view.setText(text + "");
        return this;
    }

    //获取知道textview的值
    public String getText(int viewId) {
        TextView view = getView(viewId);
        return view.getText().toString().trim();
    }

    /**
     * 设置监听时间
     *
     * @param viewId
     * @param listener
     * @return
     */
    public GouwucheViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public GouwucheViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param bm
     * @return
     */
    public GouwucheViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    public GouwucheViewHolder setImageDrawable(int viewId, Drawable bm) {
        ImageView view = getView(viewId);
        view.setImageDrawable(bm);
        return this;
    }

    public GouwucheViewHolder loadImageByUrl(int viewId, Object model) {
        ImageModel m = (ImageModel) model;
        ImageView view = getView(viewId);
        view.setVisibility(View.VISIBLE);
        ImageCacheManager.loadImage(m.getImage(), view, getBitmapFromRes(R.mipmap.ic_launcher), getBitmapFromRes(R.mipmap.ic_launcher));
        return this;
    }

    public GouwucheViewHolder loadSketchByUrl(int viewId, String link) {
        SketchImageView view = getView(viewId);
        view.setVisibility(View.VISIBLE);
        view.displayImage(link);
        return this;
    }

    public GouwucheViewHolder loadByUrl(int viewId, String link) {
        ImageView view = getView(viewId);
        view.setVisibility(View.VISIBLE);
        ImageCacheManager.loadImage(link, view, getBitmapFromRes(R.mipmap.ic_launcher), getBitmapFromRes(R.mipmap.ic_launcher));
        return this;
    }

    public GouwucheViewHolder loadByImage(int viewId, String link) {
        ImageView view = getView(viewId);
        view.setVisibility(View.VISIBLE);
        try {
            //加载图片
            mImageLoader.displayImage(
                    link,
                    view,
                    mDisplayImageOptions,
                    mImageLoadingListenerImpl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public GouwucheViewHolder loadGuanggaoByImage(int viewId, final ImageModel model) {
        ImageView view = getView(viewId);
        view.setVisibility(View.VISIBLE);
        try {
            //加载图片
            mImageLoader.displayImage(
                    model.getImage(),
                    view,
                    mDisplayImageOptions,
                    mImageLoadingListenerImpl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jiexiLink(model.getLink());
            }
        });
        return this;
    }

    public Bitmap getBitmapFromRes(int resId) {
        Resources res = mContext.getResources();
        return BitmapFactory.decodeResource(res, resId);
    }


    private ImageView setImageTag(int viewId, String url) {
        ImageView view = getView(viewId);
        view.setTag(url);
        return view;
    }

    /**
     * 设置控件显示隐藏
     *
     * @param viewId（控件id）
     * @param result(控件显示隐藏)
     */
    public GouwucheViewHolder setVisible(int viewId, boolean result) {
        View view = getView(viewId);
        if (result) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
        return this;
    }

    public GouwucheViewHolder setMargin(int viewId, int left, int right, int top, int bottom) {
        LinearLayout ll = getView(viewId);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll.getLayoutParams();
        layoutParams.setMargins(left, right, top, bottom);//4个参数按顺序分别是左上右下
        ll.setLayoutParams(layoutParams);
        return this;
    }

    public int getPosition() {
        return mPosition;
    }

    //监听图片异步加载
    public static class ImageLoadingListenerImpl extends SimpleImageLoadingListener {

        public static final List<String> displayedImages =
                Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap bitmap) {
            if (bitmap != null) {
                ImageView imageView = (ImageView) view;
                boolean isFirstDisplay = !displayedImages.contains(imageUri);
                if (isFirstDisplay) {
                    //图片的淡入效果
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                    System.out.println("===> loading " + imageUri);
                }
            }
        }
    }

    /**
     * 解析link
     *
     * @param link = "../product/detail.php?id=852";
     */
    private void jiexiLink(final String link) {
        MainActivity.mIntails.mUtils.IntentPost(DetailListsActivity.class, new Utils.putListener() {
            @Override
            public void put(Intent intent) {
                String desc = "";
                if (link.contains("product")) {
                    if (link.contains("detail.php")) {//跳转到商品的详细页面
                        desc = "xq%id?" + link.split("=")[1];
                    } else if (link.contains("list.php")) {//跳转到商品分类
                        desc = "spfl%" + "name" + "?" + link.split("\\?")[1];
                    }
                } else if (link.contains("user")) {//跳转到帮助中心
                    if (link.contains("help.php")) {
                        desc = "help%";
                    }
                }
                intent.putExtra("desc", desc);
            }
        });
    }
}
