package child.ryl.child.activity;

import android.os.Bundle;
import android.widget.TextView;

import child.ryl.child.R;
import child.ryl.child.base.BaseActivity;

/**
 * 标题测试类
 */
public class TitleTestActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_test);
        setTitle(R.string.TitleTest);

        TextView activity_title_test_text = (TextView) findViewById(R.id.activity_title_test_text);
        activity_title_test_text.setFocusable(true);
        activity_title_test_text.requestFocus();
    }
}
