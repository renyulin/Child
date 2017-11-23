package child.ryl.child.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import child.ryl.child.R;
import child.ryl.child.fragment.ContentFragment;

/**
 * 碎片activity
 */
public class MyFragmentActivity extends FragmentActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fragment);
        init();
    }

    private void init() {
        ContentFragment fragment = (ContentFragment) getSupportFragmentManager()//getChildFragmentManager fragment下的fragment
                .findFragmentById(R.id.my_fragment_contentValue);
    }
}
