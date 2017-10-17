package child.ryl.child.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import child.ryl.child.R;


public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<String> stringList;

    public MyAdapter(List<String> stringList, Context context) {
        this.stringList = stringList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return stringList == null ? 0 : stringList.size();
    }

    @Override
    public Object getItem(int position) {
        return stringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.base_adapter, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.adapter_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(stringList.get(position));
        return convertView;
    }

    class ViewHolder {
        private TextView textView;
    }
}
