package child.ryl.child.listener;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import child.ryl.child.adapter.MyNestAdapter;
import child.ryl.child.adapter.Nest1Adapter;
import child.ryl.child.bean.SecondBean;

/**
 * 重写ClickableSpan：
 * 去掉下划线、设定点击事件
 */
public class MyClickableSpan extends ClickableSpan {
    private Context context;
    private int i;//0.userName 1.省略号
    private Nest1Adapter nest1Adapter;
    private MyNestAdapter myNestAdapter;
    private List<SecondBean> list;//二级listView数据
    private int position;//二级listView第几条

    public MyClickableSpan(Context context, int i, int position, List<SecondBean> list, Nest1Adapter nest1Adapter, MyNestAdapter myNestAdapter) {
        super();
        this.i = i;
        this.context = context;
        this.position = position;
        this.list = list;
        this.nest1Adapter = nest1Adapter;
        this.myNestAdapter = myNestAdapter;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(false); //去掉下划线
    }

    /**
     * 0.用户名点击事件
     * 1.当textView超出两行带省略号的点击事件
     *
     * @param widget
     */
    @Override
    public void onClick(View widget) {
        if (i == 0) {
            Toast.makeText(context, "userNameId:" + list.get(position).getmId(), Toast.LENGTH_SHORT).show();
//            myNestAdapter.notifyDataSetChanged();//刷新数据不能写反
//            nest1Adapter.notifyDataSetChanged();
        } else if (i == 1) {//实现点击省略号显示数据
            if (list.get(position).getmFlag()){
                list.get(position).setmFlag(false);
            }else {
                list.get(position).setmFlag(true);
            }
            myNestAdapter.notifyDataSetChanged();//刷新数据不能写反
            nest1Adapter.notifyDataSetChanged();
        }

    }
}
