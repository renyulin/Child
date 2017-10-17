package child.ryl.child.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import child.ryl.child.R;

/**
 * 流布局总汇
 */
public class FlowAllActivity extends Activity implements View.OnClickListener {
    private TextView flow1;
    private TextView flow2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_all);
        flow1 = (TextView) findViewById(R.id.flow_one);
        flow2 = (TextView) findViewById(R.id.flow_two);
        flow1.setOnClickListener(this);
        flow2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.flow_one:
                startActivity(new Intent(this,FlowLayoutActivity.class));
                break;
            case R.id.flow_two:
                startActivity(new Intent(this,FlowActivity.class));
                break;
        }
    }
}
