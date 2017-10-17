package child.ryl.child.activity;

import android.app.Activity;
import android.os.Bundle;

import child.ryl.child.R;

/**
 * 自定义显示隐藏布局
 */
public class ExpandableActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable);
    }
}
