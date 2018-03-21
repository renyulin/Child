package child.ryl.child;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import child.ryl.child.fragment.FirstFragment;
import child.ryl.child.fragment.FourthFragment;
import child.ryl.child.fragment.SecondFragment;
import child.ryl.child.fragment.ThirdFragment;

public class MainActivity extends Activity implements View.OnClickListener {
    private RadioButton radio1;
    private RadioButton radio2;
    private RadioButton radio3;
    private RadioButton radio4;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;
    private FourthFragment fourthFragment;
    private long mExitTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radio1 = (RadioButton) findViewById(R.id.activity_radio1);
        radio2 = (RadioButton) findViewById(R.id.activity_radio2);
        radio3 = (RadioButton) findViewById(R.id.activity_radio3);
        radio4 = (RadioButton) findViewById(R.id.activity_radio4);

        FragmentManager manager1 = getFragmentManager();
        FragmentTransaction transaction1 = manager1.beginTransaction();

        radio1.setOnClickListener(this);
        radio2.setOnClickListener(this);
        radio3.setOnClickListener(this);
        radio4.setOnClickListener(this);

        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
        thirdFragment = new ThirdFragment();
        fourthFragment = new FourthFragment();

        transaction1.replace(R.id.activity_fragment, firstFragment);
        transaction1.commit();

        overridePendingTransition(R.anim.zoommin, R.anim.zoomout);
    }

    @Override
    public void onClick(View v) {
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        switch (v.getId()) {
            case R.id.activity_radio1:
                transaction.replace(R.id.activity_fragment, firstFragment);
                break;
            case R.id.activity_radio2:
                transaction.replace(R.id.activity_fragment, secondFragment);
                break;
            case R.id.activity_radio3:
                transaction.replace(R.id.activity_fragment, thirdFragment);
                break;
            case R.id.activity_radio4:
                transaction.replace(R.id.activity_fragment, fourthFragment);
                break;
            default:
                break;
        }
        transaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //System.currentTimeMillis() 获取系统当前时间
            if ((System.currentTimeMillis() - mExitTime) > 800) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
//                finish();
                System.exit(0);
            }
            return true;
        }
        //        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        startActivity(intent);
        return super.onKeyDown(keyCode, event);
    }
}
