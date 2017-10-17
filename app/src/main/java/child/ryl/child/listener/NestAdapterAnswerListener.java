package child.ryl.child.listener;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import child.ryl.child.adapter.Nest1Adapter;

/**
 * 嵌套listView回复监听事件
 */
public class NestAdapterAnswerListener implements View.OnClickListener {
    private Context context;//context
    private int position;//第几条目
    private Nest1Adapter adapter;//adapter

    public NestAdapterAnswerListener(Context context, Nest1Adapter adapter, int position) {
        this.context = context;
        this.adapter = adapter;
        this.position = position;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(context, "您点击了第" + position + "条的回复哦！！", Toast.LENGTH_SHORT).show();
    }
}
