package liuliu.demo.list.ui.first_frag;

import android.util.Log;

import liuliu.demo.list.R;
import liuliu.demo.list.base.BaseFragment;

/**
 * Created by Administrator on 2016/1/6.
 */
public class WodeFragment extends BaseFragment {
    @Override
    public void initViews() {
        setContentView(R.layout.frag_wode);
    }

    @Override
    public void initEvents() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.i("hidden", hidden + "");
        super.onHiddenChanged(hidden);
    }
}
