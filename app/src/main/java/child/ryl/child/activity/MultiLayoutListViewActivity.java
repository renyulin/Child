package child.ryl.child.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import child.ryl.child.R;
import child.ryl.child.adapter.MultiLayoutListViewAdapter;
import child.ryl.child.bean.MultiLayoutListViewBean;

/**
 * 多布局listView
 */
public class MultiLayoutListViewActivity extends Activity implements AdapterView.OnItemClickListener {
    private ListView listView;
    private List<MultiLayoutListViewBean> beanList;
    private MultiLayoutListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_layout);
        listView = (ListView) findViewById(R.id.activity_multi_layout_listView);
        init();
        adapter = new MultiLayoutListViewAdapter(beanList, this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
    }

    private void init() {
        beanList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            MultiLayoutListViewBean bean = new MultiLayoutListViewBean();
            bean.setType(0);
            bean.setPicture(R.drawable.food);
            bean.setTitle("美食");
            beanList.add(bean);
        }
        for (int i = 0; i < 2; i++) {
            MultiLayoutListViewBean bean = new MultiLayoutListViewBean();
            bean.setType(1);
            bean.setTitle("旅游");
            bean.setContent("领略美丽的欧式风光");
            beanList.add(bean);
        }
        for (int i = 0; i < 5; i++) {
            MultiLayoutListViewBean bean = new MultiLayoutListViewBean();
            bean.setType(2);
            bean.setContent("0123456789..........");
            beanList.add(bean);
        }
    }
}
