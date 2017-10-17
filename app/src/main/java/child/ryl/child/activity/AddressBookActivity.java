package child.ryl.child.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import child.ryl.child.R;
import child.ryl.child.fragment.ContactTabFragment;

/**
 * 仿微信通讯录
 */
public class AddressBookActivity extends FragmentActivity implements ContactTabFragment.ConnWithActivity {
    private ContactTabFragment contactTabFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_layout);
        String aa = "你好";
        Log.i("sdfsafsfs", aa.charAt(0) + "");
        init();
    }

    /**
     * 初始化数据
     */
    private void init() {
        contactTabFragment = new ContactTabFragment();
        this.getSupportFragmentManager().beginTransaction()
                .add(R.id.comment_ll, contactTabFragment, "contactTabFragment").commit();
    }

    @Override
    public void toActivity(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }
}