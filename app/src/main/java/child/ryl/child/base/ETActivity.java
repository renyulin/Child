package child.ryl.child.base;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

/**
 * base
 */
public class ETActivity extends Activity {
    protected void toast(String t) {
        Toast.makeText(this, t, Toast.LENGTH_SHORT).show();
    }

    protected void e(String t) {
        Log.e(this.getClass().getName(), t);//下属activityName
    }
}
