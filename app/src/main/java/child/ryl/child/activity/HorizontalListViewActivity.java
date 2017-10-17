package child.ryl.child.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
 ;import child.ryl.child.R;
import child.ryl.child.adapter.HorizontalListViewAdapter;
import child.ryl.child.my_view.HorizontalListView;

/**
 * 横滑activity
 */
public class HorizontalListViewActivity extends Activity implements GestureDetector.OnGestureListener, AdapterView.OnItemClickListener {

    private HorizontalListViewAdapter hlva;
    private HorizontalListView hlv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horizontallistview_act);
        hlv = (HorizontalListView) findViewById(R.id.horizontal_listView1);
        hlva = new HorizontalListViewAdapter(this);
        hlva.notifyDataSetChanged();
        hlv.setAdapter(hlva);
        hlv.setOnItemClickListener(this);
    }

    private final String TAG = "HorizontalListViewActivity";

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this,position+"",Toast.LENGTH_SHORT).show();
    }
}
