package liuliu.demo.list.control.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public abstract class GouwucheAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private int layoutId;
    private ImageLoader mImageLoader;

    public GouwucheAdapter(Context context, List<T> datas, int layoutId, ImageLoader loader) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        this.layoutId = layoutId;
        this.mImageLoader = loader;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GouwucheViewHolder holder = getViewHolder(position, convertView, parent);
        convert(holder, mDatas, position);
        return holder.getConvertView();
    }

    private GouwucheViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return GouwucheViewHolder.get(mContext, convertView, parent, layoutId, position, mImageLoader);
    }

    public abstract void convert(GouwucheViewHolder holder, List<T> t, int position);
}
