package child.ryl.child.ioc;

import android.app.Activity;
import android.os.Bundle;

import child.ryl.child.ioc.view.ViewInjectUtils;


public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectUtils.inject(this);
    }
}
