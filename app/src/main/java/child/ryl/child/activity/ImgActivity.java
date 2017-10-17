package child.ryl.child.activity;

import android.app.Activity;
import android.os.Bundle;

import child.ryl.child.R;

/**
 * 实现intent动画的activity
 */
public class ImgActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);
    }
}
