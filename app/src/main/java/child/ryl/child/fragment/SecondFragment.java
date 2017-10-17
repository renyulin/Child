package child.ryl.child.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import child.ryl.child.R;
import child.ryl.child.activity.ListViewTestActivity;
import child.ryl.child.activity.NestListViewActivity;
import child.ryl.child.adapter.SecondAdapter;
import child.ryl.child.danli.SingletonActOne;
import child.ryl.child.utils.Constant;


public class SecondFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private View view;
    private ListView listView;
    private View headerView;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private List<String> strings;
    private SecondAdapter secondAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_second, null);
            init(view);
        }
        return view;
    }

    private void init(View view) {
        listView = (ListView) view.findViewById(R.id.second_listView);
        headerView = LayoutInflater.from(getActivity()).inflate(R.layout.second_header, null);
        button1 = (Button) headerView.findViewById(R.id.second_button1);
        button1.setText("无主体listView");
        button2 = (Button) headerView.findViewById(R.id.second_button2);
        button2.setText("嵌套listView");
        button3 = (Button) headerView.findViewById(R.id.second_button3);
        button3.setText("action跳转Html页面");
        button4 = (Button) headerView.findViewById(R.id.second_button4);
        button4.setText("单例模式测试");
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        listView.addHeaderView(headerView);
        content();
        secondAdapter = new SecondAdapter(getActivity(), strings);
        listView.setAdapter(secondAdapter);
        listView.setOnItemClickListener(this);
    }

    private void content() {
        strings = new ArrayList<>();
        for (int i = 0; i < 33; i++) {
            strings.add(i + "");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.second_button1://无主体listView
                startActivity(new Intent(getActivity(), ListViewTestActivity.class));
                break;
            case R.id.second_button2://嵌套listView
                startActivity(new Intent(getActivity(), NestListViewActivity.class));
                break;
            case R.id.second_button3:
                Intent intent = new Intent();
                intent.setAction(Constant.HTML_ACT);
                startActivity(intent);
                break;
            case R.id.second_button4:
                startActivity(new Intent(getActivity(), SingletonActOne.class));
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("aaa", "" + position);
        Toast.makeText(getActivity(), "您点击了第" + (position - 1) + "条", Toast.LENGTH_SHORT).show();
    }
}
