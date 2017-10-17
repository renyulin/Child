package child.ryl.child.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

//import com.example.administrator.myapplication.R;
//import com.example.administrator.myapplication.adapter.Nest1Adapter;
//import com.example.administrator.myapplication.bean.Bean;
//import com.example.administrator.myapplication.bean.SecondBean;

import java.util.ArrayList;
import java.util.List;

import child.ryl.child.R;
import child.ryl.child.adapter.Nest1Adapter;
import child.ryl.child.bean.Bean;
import child.ryl.child.bean.SecondBean;

/**
 * 嵌套listView
 */
public class NestListViewActivity extends Activity {
    private ListView listView;
    private int i = 0;
    private Nest1Adapter adapter;
    private List<SecondBean> list;
    private Boolean flag = true;//true:2 false:size
    private List<Bean> strings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nest_listview);
        listView = (ListView) findViewById(R.id.activity_list);
        init();
    }

    private void init() {


        strings = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Bean bean = new Bean();
            bean.setFlag(true);
            bean.setGroup(i + "");
            bean.setName("name" + i + getString(R.string.colon));
            bean.setId("id" + i);
            list = new ArrayList<>();
            for (int j = 0; j < 25; j++) {
                SecondBean secondBean = new SecondBean();
                secondBean.setmFlag(false);
                secondBean.setmGroup("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss ssssssssssssssss");
                secondBean.setmName("name" + j + getString(R.string.colon));
                secondBean.setmId("id" + j);
                list.add(secondBean);
            }
            bean.setList(list);
            strings.add(bean);
        }
        adapter = new Nest1Adapter(this, strings);
        listView.setAdapter(adapter);
    }
}
