package child.ryl.child.listener;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import child.ryl.child.activity.ListViewButtonActivity;
import child.ryl.child.adapter.ConflictAdapter;
import child.ryl.child.bean.ConflictBean;

/**
 * conflictAdapter的button监听事件
 */
public class ConflictListener implements View.OnClickListener {
    private Button button;
    private ConflictAdapter conflictAdapter;
    private List<ConflictBean> list;
    private ListViewButtonActivity activity;
    private int position;
    /**
     * 1
     */
    public ConflictListener(Button button, ConflictAdapter conflictAdapter, List<ConflictBean> list,
                            ListViewButtonActivity activity) {
        this.button = button;
        this.activity = activity;
        this.list = list;
        this.conflictAdapter = conflictAdapter;
    }

    @Override
    public void onClick(View v) {
        int position = (int) button.getTag();
        list.remove(position);
        Toast.makeText(activity, "delete" + position, Toast.LENGTH_SHORT).show();
        conflictAdapter.notifyDataSetChanged();
    }

    /**
     * 2
     */
//    public ConflictListener(int position, ConflictAdapter conflictAdapter, List<ConflictBean> list,
//                            ListViewButtonActivity activity) {
//        this.position = position;
//        this.activity = activity;
//        this.list = list;
//        this.conflictAdapter = conflictAdapter;
//    }
//
//    @Override
//    public void onClick(View v) {
//        Toast.makeText(activity, "delete" + position + ":" + list.get(position).getButton().toString(), Toast.LENGTH_SHORT).show();
//        list.remove(position);
//        conflictAdapter.notifyDataSetChanged();
//    }
}
