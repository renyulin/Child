package child.ryl.child.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
public class SecondAdapter extends BaseAdapter {
    private Context context;
    private List<String> strings;

    public SecondAdapter(Context context, List<String> strings) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_second, null);
            holderSecond.textView = (TextView) convertView.findViewById(R.id.adapter2_text);
            convertView.setTag(holderSecond);
        } else {
            holderSecond = (ViewHolderSecond) convertView.getTag();
        }
        holderSecond.textView.setText(strings.get(position));
        if (position==1){
            Drawable drawable1 =  context.getResources().getDrawable(R.mipmap.ic_launcher);
            drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());//必须设置图片大小，否则不显示
            Drawable drawable2 = context.getResources().getDrawable(R.mipmap.ic_launcher);
            drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
            Drawable drawable3 = context.getResources().getDrawable(R.mipmap.ic_launcher);
            drawable3.setBounds(0, 0, drawable3.getMinimumWidth(), drawable3.getMinimumHeight());
            Drawable drawable4 = context.getResources().getDrawable(R.mipmap.ic_launcher);
            drawable4.setBounds(0, 0, drawable4.getMinimumWidth(), drawable4.getMinimumHeight());
            holderSecond.textView.setCompoundDrawables(drawable1,drawable2,drawable3,drawable4);
        }
        return convertView;
    }

    private class ViewHolderSecond {
        TextView textView;
    }
}
