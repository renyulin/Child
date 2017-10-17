package child.ryl.child.danli;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import child.ryl.child.R;
import child.ryl.child.utils.Utils;

/**
 * 单例模式测试two
 */
public class SingletonActTwo extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_header);

        TextView second_button1 = (TextView) findViewById(R.id.second_button1);
        second_button1.setOnClickListener(this);
        TextView second_button2 = (TextView) findViewById(R.id.second_button2);
        second_button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.second_button1:
                TestSingleton singleton = TestSingleton.getInstance();
                String str = singleton.getName();
                Utils.smallToast(this, str);
                break;
            case R.id.second_button2:
                finish();
                break;
        }
    }
}
