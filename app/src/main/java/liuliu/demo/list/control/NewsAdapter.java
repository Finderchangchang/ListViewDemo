package liuliu.demo.list.control;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import liuliu.demo.list.R;
import liuliu.demo.list.model.NewsBean;

/**
 * Created by Administrator on 2015/12/25.
 */
public class NewsAdapter extends BaseAdapter{
    private List<NewsBean> mList;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;

    public NewsAdapter(Context context, List<NewsBean> mList) {
        this.mList = mList;
        this.mInflater = LayoutInflater.from(context);
        mImageLoader = new ImageLoader();
        URLS = new String[mList.size()];
        for (int i = 0; i < mList.size(); i++) {
            URLS[i] = mList.get(i).getPic();
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_layout, null);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tvDesc = (TextView) convertView.findViewById(R.id.tv_desc);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        holder.ivIcon.setImageResource(R.mipmap.ic_launcher);
        String url = mList.get(position).getPic();
        holder.ivIcon.setTag(url);
        mImageLoader.showImageBuThread(holder.ivIcon, url, R.mipmap.ic_launcher);
        holder.tvTitle.setText(mList.get(position).getTitle());
        holder.tvDesc.setText(mList.get(position).getDes());
        return convertView;
    }


    private int mStart;
    private int mEnd;
    private static String[] URLS;


    class ViewHolder {
        public TextView tvTitle, tvDesc;
        public ImageView ivIcon;

    }
}
