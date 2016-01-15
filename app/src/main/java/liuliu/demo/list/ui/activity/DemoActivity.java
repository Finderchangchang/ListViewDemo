package liuliu.demo.list.ui.activity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.frakbot.jumpingbeans.JumpingBeans;

import java.util.ArrayList;
import java.util.List;

import liuliu.demo.list.R;
import liuliu.demo.list.base.BaseActivity;
import liuliu.demo.list.control.base.image.ImageCacheManager;
import me.next.tagview.TagCloudView;

/**
 * Created by Administrator on 2016/1/4.
 */
public class DemoActivity extends BaseActivity {
    ImageView imageView;
    Button btn;
    JumpingBeans jumpingBeans1;
    TextView textView1;
    Button bt1;

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
        textView1 = (TextView) findViewById(R.id.jumping_text_1);
        bt1 = (Button) findViewById(R.id.bt);
    }

    public Bitmap getBitmapFromRes(int resId) {
        Resources res = this.getResources();
        return BitmapFactory.decodeResource(res, resId);
    }

    @Override
    public void initEvents() {
        loadJumpingBeans();
        loadTag();
    }

    /**
     * 1.文字后面加动态的“...”
     * 2.整个文本波浪抖动
     */
    private void loadJumpingBeans() {
        jumpingBeans1 = JumpingBeans.with(textView1)
                .appendJumpingDots()
                .build();
//        jumpingBeans1 = JumpingBeans.with(textView1)
//                .makeTextJump(0, textView1.getText().toString().indexOf(' '))
//                .setIsWave(false)
//                .setLoopDuration(1000)
//                .build();
    }

    private void loadTag() {
        List<String> tags = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tags.add("标签" + i);
        }

        TagCloudView tagCloudView1 = (TagCloudView) findViewById(R.id.tag_cloud_view);
        tagCloudView1.setTags(tags);
        tagCloudView1.setOnTagClickListener(new TagCloudView.OnTagClickListener() {
            @Override
            public void onTagClick(int position) {
                if (position == -1) {
                    Toast.makeText(getApplicationContext(), "点击末尾文字",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "点击 position : " + position,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        tagCloudView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "TagView onClick",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
