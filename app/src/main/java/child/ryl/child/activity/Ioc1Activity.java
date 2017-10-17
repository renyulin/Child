package child.ryl.child.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import child.ryl.child.R;
import child.ryl.child.ioc.view.View2InjectUtils;
import child.ryl.child.ioc.view.annotation.ContentView;
import child.ryl.child.ioc.view.annotation.ViewInject;

/**
 * ioc
 * 省略findviewbyid
 */
@ContentView(value = R.layout.activity_ioc_test)
public class Ioc1Activity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.activity_ioc_test_btn)
    private Button mBtn1;
    @ViewInject(R.id.activity_ioc_test_btn02)
    private Button mBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View2InjectUtils.inject(this);

        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_ioc_test_btn:
                Toast.makeText(Ioc1Activity.this, "Why do you click me ?",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.activity_ioc_test_btn02:
                Toast.makeText(Ioc1Activity.this, "I am sleeping !!!",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
