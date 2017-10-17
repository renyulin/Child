package child.ryl.child.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import child.ryl.child.R;


/**
 * Created by Administrator on 2016/6/12 0012.
 */
public class FileActivity extends Activity implements View.OnClickListener {
    private Button button1;
    private Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        button1= (Button) findViewById(R.id.file_button1);
        button2= (Button) findViewById(R.id.file_button2);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.file_button1:
                startActivity(new Intent(this,FileTestActivity.class));
                break;
            case R.id.file_button2:
                startActivity(new Intent(this,SDCardTestActivity.class));
                break;
        }
    }
}
