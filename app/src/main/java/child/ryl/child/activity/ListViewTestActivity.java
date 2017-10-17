package child.ryl.child.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import child.ryl.child.R;

/**
 * 只有头部的listView
 */
public class ListViewTestActivity extends Activity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_test);
        listView = (ListView) findViewById(R.id.activity_listView_test);
        init();
    }

    private void init() {
        View view1 = LayoutInflater.from(this).inflate(R.layout.activity_access, null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.activity_file, null);
        View view3 = LayoutInflater.from(this).inflate(R.layout.activity_ioc, null);
        View view4 = LayoutInflater.from(this).inflate(R.layout.test, null);
        listView.addHeaderView(view1);
        listView.addHeaderView(view2);
        listView.addHeaderView(view3);
        listView.addHeaderView(view4);
        listView.setAdapter(null);
    }
}
