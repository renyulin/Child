package child.ryl.child.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//import com.example.administrator.myapplication.R;
//import com.example.administrator.myapplication.bean.Bean;
//import com.example.administrator.myapplication.listener.NestAdapterAnswerListener;
//import com.example.administrator.myapplication.my_view.MyListView;

import java.util.List;

import child.ryl.child.R;
import child.ryl.child.bean.Bean;
import child.ryl.child.listener.NestAdapterAnswerListener;
import child.ryl.child.my_view.MyListView;

/**
 * 嵌套listView第一级适配器
 */
public class Nest1Adapter extends BaseAdapter {
    private List<Bean> list;
    private Context context;
    //    private Boolean flag;//true:2 false:size
    private List<Bean> strings;
    private MyNestAdapter adapter;

    public Nest1Adapter(Context context, List<Bean> strings) {
        this.context = context;
        this.strings = strings;
    }

    @Override
    public int getCount() {
        return strings == null ? 0 : strings.size();
    }

    @Override
    public Object getItem(int position) {
        return strings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_nest, null);
            holder.listView = (MyListView) convertView.findViewById(R.id.myListView);
            holder.textView = (TextView) convertView.findViewById(R.id.diandiandian);
            holder.answer = (ImageView) convertView.findViewById(R.id.adapter_nest_answer);
            holder.zoom = (TextView) convertView.findViewById(R.id.zoom);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(strings.get(position).getGroup());
        final ViewHolder finalHolder = holder;
        if (strings.get(position).getFlag()) {
            finalHolder.zoom.setText("显示全部");
        } else {
            finalHolder.zoom.setText("收起");
        }
        holder.zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (strings.get(position).getFlag()) {
                    strings.get(position).setFlag(false);
                    finalHolder.zoom.setText("收起");
                    notifyDataSetChanged();
                } else {
                    strings.get(position).setFlag(true);
                    finalHolder.zoom.setText("显示全部");
                    notifyDataSetChanged();
                }
            }
        });
        holder.answer.setOnClickListener(new NestAdapterAnswerListener(context, this, position));
        adapter = new MyNestAdapter(strings.get(position).getList(), context, strings.get(position).getFlag(), this);
        holder.listView.setAdapter(adapter);
        return convertView;
    }

    class ViewHolder {
        MyListView listView;
        TextView textView;
        ImageView answer;
        TextView zoom;
    }
}
