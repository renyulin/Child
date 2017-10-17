package child.ryl.child.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import child.ryl.child.bean.SerializeBean;

/**
 * 序列化接收类
 */
public class SerializeTestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        SerializeBean bean = intent.getParcelableExtra("Parcelable");
        Log.i("aaa", "str:" + bean.getName() + "*int:" + bean.getAge());
    }
}
