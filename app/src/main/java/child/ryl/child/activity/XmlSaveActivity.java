package child.ryl.child.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import child.ryl.child.R;
import child.ryl.child.utils.Constant;

/**
 * 数据存储xml：SharedPreferences
 */
public class XmlSaveActivity extends Activity implements View.OnClickListener {
    private EditText value;
    private EditText key;
    private Button save;
    private Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_save);
        value = (EditText) findViewById(R.id.activity_xml_save_value);
        key = (EditText) findViewById(R.id.activity_xml_save_key);
        save = (Button) findViewById(R.id.activity_xml_save_save);
        search = (Button) findViewById(R.id.activity_xml_save_serch);
        save.setOnClickListener(this);
        search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_xml_save_save:
                if (value.getText().toString() == null || key.getText().toString() == null ||
                        value.getText().toString().equals("") || key.getText().toString().equals("")) {
                    Toast.makeText(this, "value/key不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    Constant.editSharedPreferences(key.getText().toString().trim(), value.getText().toString().trim(), this);
                    key.setText("");
                    value.setText("");
                }
                break;
            case R.id.activity_xml_save_serch:
                String content = "";
                if (key.getText().toString() == null || key.getText().toString().equals("")) {
                    Toast.makeText(this, "key不能为空", Toast.LENGTH_SHORT).show();
                } else if (key.getText().toString() == null || key.getText().toString().equals(null)) {
                    Toast.makeText(this, "key不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    content = Constant.getSharedPreferencesValueByKeyString(key.getText().toString().trim(), this);
                    if (content == null || content.equals("")) {
                        Toast.makeText(this, "key输入错误", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
