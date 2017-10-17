package child.ryl.child.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
//
//import com.example.administrator.myapplication.R;
//import com.example.administrator.myapplication.bean.Bean;
//import com.example.administrator.myapplication.bean.SecondBean;
//import com.example.administrator.myapplication.handler.LineContent;
//import com.example.administrator.myapplication.listener.MyClickableSpan;

import java.util.List;

import child.ryl.child.R;
import child.ryl.child.bean.SecondBean;
import child.ryl.child.handler.LineContent;
import child.ryl.child.listener.MyClickableSpan;

/**
 * 嵌套listView第二级适配器
 */
public class MyNestAdapter extends BaseAdapter {
    private List<SecondBean> list;
    private Context context;
    private Boolean flag;//true:2 false:size
    private Nest1Adapter adapter;

    public MyNestAdapter(List<SecondBean> list, Context context, Boolean flag, Nest1Adapter adapter) {
        this.list = list;
        this.context = context;
        this.flag = flag;
        this.adapter = adapter;
    }

    @Override
    public int getCount() {
        int count = 0;
        if (list != null) {
            if (flag) {
                count = Math.min(list.size(), 2);
            } else {
                count = list.size();
            }
        }
        return count;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_my_nest, null);
            holder.textView = (TextView) convertView.findViewById(R.id.adapter_my_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (list.get(position).getmFlag()) {
            holder.textView.setMaxLines(100);
            holder.textView.setEllipsize(null);
        } else {
            holder.textView.setMaxLines(2);
            holder.textView.setEllipsize(TextUtils.TruncateAt.END);
        }

        String userName = list.get(position).getmName();
        String content = list.get(position).getmGroup();
        final int userNameSize = userName.length();
        final SpannableString spannableString = new SpannableString(userName + content);
        spannableString.setSpan(new MyClickableSpan(context, 0, position, list, adapter, this), 0,
                userNameSize, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//设定用户名可点击
        spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), 0,
                userNameSize, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//设定可点击字体颜色：纯黑

        holder.textView.setText(spannableString);
        holder.textView.setHighlightColor(context.getResources().getColor(R.color.white));
        holder.textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean ret = false;
                CharSequence text = ((TextView) v).getText();
                Spannable stext = Spannable.Factory.getInstance().newSpannable(text);
                TextView widget = (TextView) v;
                int action = event.getAction();

                if (action == MotionEvent.ACTION_UP ||
                        action == MotionEvent.ACTION_DOWN) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    x -= widget.getTotalPaddingLeft();
                    y -= widget.getTotalPaddingTop();

                    x += widget.getScrollX();
                    y += widget.getScrollY();

                    Layout layout = widget.getLayout();
                    int line = layout.getLineForVertical(y);
                    int off = layout.getOffsetForHorizontal(line, x);

                    ClickableSpan[] link = stext.getSpans(off, off, ClickableSpan.class);

                    if (link.length != 0) {
                        if (action == MotionEvent.ACTION_UP) {
                            link[0].onClick(widget);
                        }
                        ret = true;
                    }
                }
                return ret;
            }
        });
        final ViewHolder finalHolder = holder;
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int k = msg.arg1;
                if (k == 2) {
                    try {
                        int i = msg.arg2;
                        int j = i - 3 <= userNameSize ? userNameSize : (i - 3);
                        spannableString.setSpan(new ForegroundColorSpan(Color.GRAY), i - 1, i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spannableString.setSpan(new MyClickableSpan(context, 1, position, list, adapter, MyNestAdapter.this)
                                , j, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        finalHolder.textView.setText(spannableString);
                    } catch (Exception e) {
                    }
                }

            }
        };
        holder.textView.post(new LineContent(holder.textView, handler));
        return convertView;
    }

    class ViewHolder {
        TextView textView;
    }
}
