package liuliu.demo.list.ui.activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoaderFactory;
import in.srain.cube.image.impl.DefaultImageLoadHandler;
import in.srain.cube.request.CacheAbleRequest;
import liuliu.demo.list.R;
import liuliu.demo.list.base.BaseActivity;
import liuliu.demo.list.control.base.ImageLoader;
import liuliu.demo.list.control.base.image.ImageCacheManager;

/**
 * Created by Administrator on 2016/1/4.
 */
public class DemoActivity extends BaseActivity {
    ImageView imageView;
    Button btn;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_demo);
        imageView = (ImageView) findViewById(R.id.iv_item_image_list_big);
        btn = (Button) findViewById(R.id.btn_desc);
        final String url = "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2324704133,1091223696&fm=80";
        String urls = "http://img.blog.csdn.net/20130702124537953?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdDEyeDM0NTY=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast";

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageCacheManager.loadImage(url, imageView, getBitmapFromRes(R.mipmap.ic_launcher), getBitmapFromRes(R.mipmap.ic_launcher));
            }
        });
    }

    public Bitmap getBitmapFromRes(int resId) {
        Resources res = this.getResources();
        return BitmapFactory.decodeResource(res, resId);
    }

    @Override
    public void initEvents() {

    }
}
