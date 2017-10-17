package child.ryl.child.test;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import child.ryl.child.R;
import child.ryl.child.base.ETActivity;

public class TestActivity extends ETActivity implements View.OnClickListener {
    EditText myEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_primary);
        findViewById(R.id.myHello).setOnClickListener(this);
        myEdit = (EditText) findViewById(R.id.myEdit);
    }


    @Override
    public void onClick(View view) {
        String edit = myEdit.getText().toString();
        if (TextUtils.isEmpty(edit)) {
            return;
        }
        Atest atest = new Atest();
        atest.setStr(edit);
    }
}
