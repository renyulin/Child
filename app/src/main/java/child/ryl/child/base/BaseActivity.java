package child.ryl.child.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import child.ryl.child.R;

/**
 * baseActivity
 */
public class BaseActivity extends Activity{
    public static final String TAG = "--ljj-- BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onBack(View v) {

        finish();

    }


    public void setTitle(String title) {

        TextView textView = (TextView) findViewById(R.id.head_title);
        if (textView != null) {
            textView.setText(title == null ? getTitle() : title);
        }

    }

    public void setTitle(int title) {

        super.setTitle(title);
        TextView textView = (TextView) findViewById(R.id.head_title);
        if (textView != null) {
            textView.setText(title == 0 ? getTitle() : getString(title));
        }

    }

    public void setTitle() {

        setTitle(null);

    }
}
