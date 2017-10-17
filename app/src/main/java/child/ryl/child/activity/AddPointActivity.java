package child.ryl.child.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import child.ryl.child.R;

/**
 * 在布局中添加点点
 */
public class AddPointActivity extends Activity {
    private LinearLayout add_point_ll;
    private ArrayList<ImageView> pointArrayA = new ArrayList<ImageView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_point);
        add_point_ll = (LinearLayout) findViewById(R.id.add_point_ll);
        add_point_ll.setGravity(Gravity.CENTER_HORIZONTAL);
        addPoint();
    }

    private void addPoint() {
        for (int i = 0; i < 3; i++) {
            ImageView point = new ImageView(this);
            point.setImageResource(R.drawable.point);
            point.setScaleType(ImageView.ScaleType.FIT_CENTER);
            int width = (int) getResources().getDimension(R.dimen.ad_nav_point_size);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, width);
            lp.setMargins(5, 0, 5, 0);
            point.setLayoutParams(lp);
            pointArrayA.add(point);
            add_point_ll.addView(point);
        }
        pointArrayA.get(0).setEnabled(false);
    }

    private int j = 0;

    public void textOnclick(View view) {
        float t = new Float(18);
        Log.d("tttttt", t + "");
        for (int i = 0; i < 3; i++) {
            pointArrayA.get(i).setEnabled(true);
        }
        j++;
        if (j >= 3) {
            j = 0;
        }
        pointArrayA.get(j).setEnabled(false);
    }
}
