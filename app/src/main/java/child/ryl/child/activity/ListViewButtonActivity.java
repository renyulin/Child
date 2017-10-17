package child.ryl.child.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

//import com.example.administrator.myapplication.R;
//import com.example.administrator.myapplication.adapter.ConflictAdapter;
//import com.example.administrator.myapplication.bean.ConflictBean;

import java.util.ArrayList;
import java.util.List;

import child.ryl.child.R;
import child.ryl.child.adapter.ConflictAdapter;
import child.ryl.child.bean.ConflictBean;

/**
 * listView和button冲突Activity
 */
public class ListViewButtonActivity extends Activity {
    private ListView listView;
    private List<ConflictBean> list;
    private ConflictAdapter conflictAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conflict);
        listView = (ListView) findViewById(R.id.conflict_listView);
        init();
        conflictAdapter = new ConflictAdapter(this, list);
        listView.setAdapter(conflictAdapter);
    }

    private void init() {
        list = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            ConflictBean bean = new ConflictBean();
            bean.setButton(i + "");
            bean.setText(i + "");
            list.add(bean);
        }
    }
}
