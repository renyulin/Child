package child.ryl.child.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import child.ryl.child.R;
import child.ryl.child.bean.MultiLayoutListViewBean;

public class MultiLayoutListViewAdapter extends BaseAdapter {
    private List<MultiLayoutListViewBean> beanList;
    private Context context;
    private final static int TYPE_ZERO = 0;
    private final static int TYPE_ONE = 1;
    private final static int TYPE_TWO = 2;
    private int currentType;

    public MultiLayoutListViewAdapter(List<MultiLayoutListViewBean> beanList, Context context) {
        this.beanList = beanList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return beanList == null ? 0 : beanList.size();
    }

    @Override
    public Object getItem(int position) {
        return beanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return beanList.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view1 = null;
        View view2 = null;
        View view3 = null;
        currentType = getItemViewType(position);
        ViewHolder1 holder1;
        ViewHolder2 holder2;
        ViewHolder3 holder3;
        switch (currentType) {
            case TYPE_ZERO:
                holder1 = new ViewHolder1();
                if (view1 == null) {
                    view1 = LayoutInflater.from(context).inflate(R.layout.multi_layout1, null);
                    holder1.imageView1 = (ImageView) view1.findViewById(R.id.multi_layout1_imageView);
                    holder1.title1 = (TextView) view1.findViewById(R.id.multi_layout1_title);
                    view1.setTag(holder1);
                    convertView = view1;
                } else {
                    holder1 = (ViewHolder1) view1.getTag();
                }
                holder1.imageView1.setImageResource(beanList.get(position).getPicture());
                holder1.title1.setText(beanList.get(position).getTitle());
                break;
            case TYPE_ONE:
                holder2 = new ViewHolder2();
                if (view2 == null) {
                    view2 = LayoutInflater.from(context).inflate(R.layout.multi_layout2, null);
                    holder2.title2 = (TextView) view2.findViewById(R.id.multi_layout2_title);
                    holder2.content2 = (TextView) view2.findViewById(R.id.multi_layout2_content);
                    view2.setTag(holder2);
                    convertView = view2;
                } else {
                    holder2 = (ViewHolder2) view2.getTag();
                }
                holder2.title2.setText(beanList.get(position).getTitle());
                holder2.content2.setText(beanList.get(position).getContent());
                break;
            case TYPE_TWO:
                holder3 = new ViewHolder3();
                if (view3 == null) {
                    view3 = LayoutInflater.from(context).inflate(R.layout.multi_layout3, null);
                    holder3.content3 = (TextView) view3.findViewById(R.id.multi_layout3_content);
                    view3.setTag(holder3);
                    convertView = view3;
                } else {
                    holder3 = (ViewHolder3) view3.getTag();
                }
                holder3.content3.setText(beanList.get(position).getContent());
                break;
        }
        return convertView;
    }

    class ViewHolder1 {
        ImageView imageView1;
        TextView title1;
    }

    class ViewHolder2 {
        TextView title2;
        TextView content2;
    }

    class ViewHolder3 {
        TextView content3;
    }
}
