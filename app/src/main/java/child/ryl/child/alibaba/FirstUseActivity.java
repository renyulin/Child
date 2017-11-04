package child.ryl.child.alibaba;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import child.ryl.child.R;

/**
 * 第一次使用控件
 */
public class FirstUseActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_layout);
        init();
    }

    private void init() {
        FilterFragment fragment = new FilterFragment();
        this.getSupportFragmentManager().beginTransaction()
                .add(R.id.comment_ll, fragment, "FilterFragment").commit();
    }
}
