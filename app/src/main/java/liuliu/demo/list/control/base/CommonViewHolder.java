package liuliu.demo.list.control.base;

import android.content.Context;
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

import net.tsz.afinal.FinalDb;

import java.util.List;

import liuliu.demo.list.R;
import liuliu.demo.list.control.base.image.ImageCacheManager;
import liuliu.demo.list.model.BannerModel;
import liuliu.demo.list.model.ImageModel;

/**
 * Created by liuliu on 2015/11/16   16:29
 *
 * @author 柳伟杰
 * @Email 1031066280@qq.com
 */
public class CommonViewHolder {
    private final SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;
    private ImageLoader mImageLoader;
    private Context mContext;
    FinalDb mDB;

    private CommonViewHolder(Context context, ViewGroup parent, int layoutId,
                             int position) {
        this.mContext = context;
        this.mPosition = position;
        this.mViews = new SparseArray<View>();
//        mDB = FinalDb.create(mContext);
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        mConvertView.setTag(this);
        mImageLoader = new ImageLoader(mContext);
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
    public static CommonViewHolder get(Context context, View convertView,
                                       ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new CommonViewHolder(context, parent, layoutId, position);
        }
        return (CommonViewHolder) convertView.getTag();
    }

    public View getConvertView() {
        return mConvertView;
    }

    public CommonViewHolder setHeight(int viewId, int height) {
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
    public CommonViewHolder setText(int viewId, Object text) {
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
    public CommonViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
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
    public CommonViewHolder setImageResource(int viewId, int drawableId) {
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
    public CommonViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    public CommonViewHolder setImageDrawable(int viewId, Drawable bm) {
        ImageView view = getView(viewId);
        view.setImageDrawable(bm);
        return this;
    }

    public CommonViewHolder setImageByUrl(int viewId, String url) {
        mImageLoader.showImageBuThread(setImageTag(viewId, url), url, 0);
        return this;
    }

    public CommonViewHolder setImageByUrl(int viewId, Object model) {
        ImageModel m = (ImageModel) model;
        mImageLoader.showImageBuThread(setImageTag(viewId, m.getImage()), m.getImage(), 0);
        return this;
    }

    public CommonViewHolder loadImageByUrl(int viewId, Object model) {
        ImageModel m = (ImageModel) model;
        ImageView view = getView(viewId);
        view.setVisibility(View.VISIBLE);
        ImageCacheManager.loadImage(m.getImage(), view, getBitmapFromRes(R.mipmap.ic_launcher), getBitmapFromRes(R.mipmap.ic_launcher));
        return this;
    }

    public CommonViewHolder loadByUrl(int viewId, String link) {
        ImageView view = getView(viewId);
        view.setVisibility(View.VISIBLE);
        ImageCacheManager.loadImage(link, view, getBitmapFromRes(R.mipmap.ic_launcher), getBitmapFromRes(R.mipmap.ic_launcher));
        return this;
    }

    public Bitmap getBitmapFromRes(int resId) {
        Resources res = mContext.getResources();
        return BitmapFactory.decodeResource(res, resId);
    }

    /**
     * 通过url设置图片显示
     *
     * @param viewId   ImageView组件Id
     * @param url      图片路径
     * @param def_view 默认图片id
     */
    public CommonViewHolder setImageByUrl(int viewId, String url, int def_view) {
        mImageLoader.showImageBuThread(setImageTag(viewId, url), url, def_view);
        return this;
    }

    /**
     * 通过url设置图片显示
     *
     * @param viewId  ImageView组件Id
     * @param url     图片路径
     * @param def_url 默认图片路径
     */
    public CommonViewHolder setImageByUrl(int viewId, String url, String def_url) {
        mImageLoader.showImageBuThread(setImageTag(viewId, url), url, def_url);
        return this;
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
    public CommonViewHolder setVisible(int viewId, boolean result) {
        View view = getView(viewId);
        if (result) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
        return this;
    }

    public CommonViewHolder setMargin(int viewId, int left, int right, int top, int bottom) {
        LinearLayout ll = getView(viewId);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll.getLayoutParams();
        layoutParams.setMargins(left, right, top, bottom);//4个参数按顺序分别是左上右下
        ll.setLayoutParams(layoutParams);
        return this;
    }

    public int getPosition() {
        return mPosition;
    }
}
