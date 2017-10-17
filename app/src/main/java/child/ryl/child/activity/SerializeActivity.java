package child.ryl.child.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import child.ryl.child.R;
import child.ryl.child.bean.SerializeBean;

/**
 * 序列化
 */
public class SerializeActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serialize);
        Intent intent=new Intent(this,SerializeTestActivity.class);
        SerializeBean bean=new SerializeBean();
        bean.setAge(8);
        bean.setName("myNameIs");
        Bundle bundle=new Bundle();
        bundle.putParcelable("Parcelable",bean);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
