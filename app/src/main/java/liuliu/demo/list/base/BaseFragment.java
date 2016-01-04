package liuliu.demo.list.base;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalDb;

/**
 * Created by Administrator on 2015/11/25.
 */
public abstract class BaseFragment extends Fragment {
    int mLayId;
    private View viewRoot = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initViews();
        if (viewRoot == null) {
            viewRoot = inflater.inflate(mLayId, container, false);
        }
        ViewGroup parent = (ViewGroup) viewRoot.getParent();
        if (parent != null) {
            parent.removeView(viewRoot);
        }
        FinalActivity.initInjectedView(this, viewRoot);
        initEvents();
        return viewRoot;
    }

    public abstract void initViews();

    public abstract void initEvents();

    public void setContentView(int layoutId) {
        mLayId = layoutId;
    }
}
