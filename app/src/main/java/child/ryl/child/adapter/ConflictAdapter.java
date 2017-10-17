package child.ryl.child.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import child.ryl.child.R;
import child.ryl.child.activity.ListViewButtonActivity;
import child.ryl.child.bean.ConflictBean;
import child.ryl.child.listener.ConflictListener;

/**
 * button和listView冲突
 */
public class ConflictAdapter extends BaseAdapter {
    private Context context;
    private List<ConflictBean> list;

    public ConflictAdapter(Context context, List<ConflictBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.conflict_adapter, null);
            holder.textView = (TextView) convertView.findViewById(R.id.conflict_textView);
            holder.button = (Button) convertView.findViewById(R.id.conflict_button);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.button.setText(list.get(position).getButton());
        holder.textView.setText(list.get(position).getText());

        holder.button.setOnClickListener(new ConflictListener(
                holder.button, this, list, (ListViewButtonActivity) context));
        holder.button.setTag(position);

//        holder.button.setOnClickListener(new ConflictListener(position, this, list, (ListViewButtonActivity) context));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "textView" + position + ":" + list.get(position).getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView textView;
        private Button button;
    }
}
