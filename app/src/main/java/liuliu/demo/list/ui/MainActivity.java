package liuliu.demo.list.ui;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import liuliu.demo.list.R;

public class MainActivity extends AppCompatActivity {
    public static MainActivity mIntails;
    FrameLayout frag_ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIntails = this;
        frag_ll = (FrameLayout) findViewById(R.id.frag_ll);
        setItem(0);
    }

    private void setItem(int position) {
        // 开启一个Fragment事务
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        hideFragments(transaction);

        switch (position) {
            case 0:
                if (shouye == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    shouye = new ShouyeFragment();
                    transaction.add(R.id.frag_ll, shouye);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(shouye);
                }
                break;
        }
        transaction.commit();
    }

    private ShouyeFragment shouye;

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (shouye != null) {
            transaction.hide(shouye);
        }
    }
}
