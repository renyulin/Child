package child.ryl.child.activity;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import child.ryl.child.R;

/**
 * ValueAnimation和ViewGroup数据交互使用
 */
public class ViewAndValueActivity extends Activity implements View.OnClickListener {
    private Button activity_view_value_btn;
    private LinearLayout activity_view_value_ll;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_value);
        activity_view_value_btn = (Button) findViewById(R.id.activity_view_value_btn);
        activity_view_value_btn.setOnClickListener(this);
        activity_view_value_ll = (LinearLayout) findViewById(R.id.activity_view_value_ll);
    }

    @Override
    public void onClick(View view) {
        if (flag) {
            animation(0, 80);
        } else {
            animation(80, 0);
        }
        flag = !flag;
        view.setAnimation(AnimationUtils.loadAnimation(this, R.anim.bp_bottom_bg_out));
        view.setAnimation(AnimationUtils.loadAnimation(this, R.anim.bp_bottom_view_in));
    }

    //结束动画数值变化
    public void animation(int start, int end) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        valueAnimator.setDuration(1250);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                changeView(value);
            }
        });
        valueAnimator.start();
    }

    private void changeView(int value) {
        setMargins(activity_view_value_ll, value, value, value, value);
    }

    public void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
}
