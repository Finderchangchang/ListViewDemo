package liuliu.demo.list.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import net.tsz.afinal.annotation.view.CodeNote;

import java.util.ArrayList;
import java.util.List;

import liuliu.demo.list.R;
import liuliu.demo.list.base.BaseActivity;
import liuliu.demo.list.base.Utils;
import liuliu.demo.list.control.base.GouwucheAdapter;
import liuliu.demo.list.control.base.GouwucheViewHolder;
import liuliu.demo.list.model.SearchWordModel;

/**
 * 查询页面
 * Created by Administrator on 2016/1/11.
 */
public class SearchActivity extends BaseActivity {
    @CodeNote(id = R.id.search_et)
    EditText search_et;
    @CodeNote(id = R.id.search_btn, click = "onClick")
    EditText search_btn;
    @CodeNote(id = R.id.search_lv)
    ListView search_lv;
    GouwucheAdapter mAdapter;
    List<SearchWordModel> mList;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_search);
//        mList = mDB.findAll(SearchWordModel.class, "Order by id desc");//对数据进行倒序排列
    }

    @Override
    public void initEvents() {
        mAdapter = new GouwucheAdapter<SearchWordModel>(this, mList, R.layout.item_word_search) {
            @Override
            public void convert(GouwucheViewHolder holder, List<SearchWordModel> t, int position) {
                holder.setText(R.id.word_tv, t.get(position).getWord());
            }
        };
        search_lv.setAdapter(mAdapter);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_btn://保存查询的字到本地数据库，并进行数据查询
                final String word = search_et.getText().toString();
                String change = "word='" + word + "'";
                List list = mDB.findAllByWhere(SearchWordModel.class, change);
                if (list.size() > 0) {//存在词，删除以后再添加。为了排序正确
                    mDB.deleteByWhere(SearchWordModel.class, change);
                }
                SearchWordModel model = new SearchWordModel();
                model.setWord(word);
                mDB.save(model);
                //跳页....
                mUtils.IntentPost(DetailListsActivity.class, new Utils.putListener() {
                    @Override
                    public void put(Intent intent) {//跳转到第二个Activity（用来显示）
//                        intent.putExtra("desc", "spfl%" + word);
                    }
                });
                break;
        }
    }
}
