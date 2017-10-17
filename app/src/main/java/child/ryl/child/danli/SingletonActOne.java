package child.ryl.child.danli;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import child.ryl.child.R;

/**
 * 单例模式测试one
 */
public class SingletonActOne extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_header);
        TestSingleton singleton=TestSingleton.getInstance();
        singleton.setName("name");
        TextView second_button1= (TextView) findViewById(R.id.second_button1);
        second_button1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this,SingletonActTwo.class));
    }
}
