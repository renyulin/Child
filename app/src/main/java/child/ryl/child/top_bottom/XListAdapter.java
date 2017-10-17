package child.ryl.child.top_bottom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import child.ryl.child.R;

/**
 * Created by Administrator on 2016/4/29 0029.
 */
public class XListAdapter extends BaseAdapter {
    private Context context;
    private List<String> strings;

    public XListAdapter(Context context, List<String> strings) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderSecond holderSecond;
        if (convertView == null) {
            holderSecond = new ViewHolderSecond();
            convertView = LayoutInflater.from(context).inflate(R.layout.x_list, null);
            holderSecond.textView = (TextView) convertView.findViewById(R.id.list_item_textview);
            convertView.setTag(holderSecond);
        } else {
            holderSecond = (ViewHolderSecond) convertView.getTag();
        }
        holderSecond.textView.setText(strings.get(position));
        return convertView;
    }

    private class ViewHolderSecond {
        TextView textView;
    }
}
