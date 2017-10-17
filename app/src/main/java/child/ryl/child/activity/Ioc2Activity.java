package child.ryl.child.activity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import child.ryl.child.R;
import child.ryl.child.ioc.BaseActivity;
import child.ryl.child.ioc.view.annotation.ContentView;
import child.ryl.child.ioc.view.annotation.OnClick;
import child.ryl.child.ioc.view.annotation.ViewInject;

/**
 * ioc
 */
@ContentView(value = R.layout.activity_ioc_test)
public class Ioc2Activity extends BaseActivity {
    @ViewInject(R.id.activity_ioc_test_btn)
    private Button mBtn1;
    @ViewInject(R.id.activity_ioc_test_btn02)
    private Button mBtn2;

    @OnClick({R.id.activity_ioc_test_btn, R.id.activity_ioc_test_btn02})
    public void clickBtnInvoked(View view) {
        switch (view.getId()) {
            case R.id.activity_ioc_test_btn:
                Toast.makeText(this, "Inject Btn01 !", Toast.LENGTH_SHORT).show();
                break;
            case R.id.activity_ioc_test_btn02:
                Toast.makeText(this, "Inject Btn02 !", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
