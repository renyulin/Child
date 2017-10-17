package child.ryl.child.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import child.ryl.child.R;


/**
 * ioc模式
 * 1.ViewId http://blog.csdn.net/lmj623565791/article/details/39269193
 * 2.ViewId和OnClick
 * 控制反转：http://baike.baidu.com/link?url=NPyPyLHMe7m_6
 * ZsoiIhCgWbvpt2I-BFWUO21vsSdRSqm4k1RDpQpc_SAOo1ZnsYbcrX
 * P6257LWWl1rPlcjRClnS2ubzURLj9fKz-UkRCnYBA0phhOEqloq1q0KGpF4Pt
 */
public class IOCActivity extends Activity implements View.OnClickListener {
    private TextView ioc1;
    private TextView ioc2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ioc);

        ioc1 = (TextView) findViewById(R.id.activity_ioc_test1);
        ioc2 = (TextView) findViewById(R.id.activity_ioc_test2);

        ioc1.setOnClickListener(this);
        ioc2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_ioc_test1:
                startActivity(new Intent(this, Ioc1Activity.class));
                break;
            case R.id.activity_ioc_test2:
                startActivity(new Intent(this, Ioc2Activity.class));
                break;
        }
    }
}
