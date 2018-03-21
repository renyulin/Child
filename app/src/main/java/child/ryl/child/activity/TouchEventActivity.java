package child.ryl.child.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

import child.ryl.child.R;

/**
 * touchEvent事件
 */
public class TouchEventActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_event);
    }
    @Override
    public void onUserInteraction() {
        //当此activity在栈顶时，触屏点击按home，back，menu键等都会触发此方法
        super.onUserInteraction();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
