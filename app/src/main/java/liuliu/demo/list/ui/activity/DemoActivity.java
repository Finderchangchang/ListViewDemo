package liuliu.demo.list.ui.activity;

import android.content.Context;
import android.widget.TextView;

import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoader;
import in.srain.cube.image.ImageLoaderFactory;
import in.srain.cube.image.impl.DefaultImageLoadHandler;
import in.srain.cube.request.CacheAbleRequest;
import liuliu.demo.list.R;
import liuliu.demo.list.base.BaseActivity;

/**
 * Created by Administrator on 2016/1/4.
 */
public class DemoActivity extends BaseActivity {
    ImageLoader imageLoader;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_demo);
        CubeImageView imageView = (CubeImageView) findViewById(R.id.iv_item_image_list_big);
        TextView tv = (TextView) findViewById(R.id.tv_desc);
        imageLoader = ImageLoaderFactory.create(this);
        CacheAbleRequest cacheAbleRequest = new CacheAbleRequest();
        cacheAbleRequest.setAssertInitDataPath("/data/data/");
        imageView.loadImage(imageLoader, "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2324704133,1091223696&fm=80");
    }

    @Override
    public void initEvents() {

    }
}
