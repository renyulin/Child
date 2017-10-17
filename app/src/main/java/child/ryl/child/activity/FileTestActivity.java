package child.ryl.child.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import child.ryl.child.R;

/**
 * Created by Administrator on 2016/6/12 0012.
 */
public class FileTestActivity extends Activity {

    final String FILE = "jason.bin";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_text);

        Button read = (Button) findViewById(R.id.read);
        Button write = (Button) findViewById(R.id.write);

        final EditText edit1 = (EditText) findViewById(R.id.edit1);
        final EditText edit2 = (EditText) findViewById(R.id.edit2);


        write.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                write(edit1.getText().toString());
                edit1.setText("");
            }
        });

        read.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                edit2.setText(read());
            }
        });
    }


    private String read() {
        try {
            FileInputStream fis = openFileInput(FILE);
            byte[] buffer = new byte[1024];
            int hasRead = 0;
            StringBuilder sb = new StringBuilder("");
            while ((hasRead = fis.read(buffer)) > 0) {
                sb.append(new String(buffer, 0, hasRead));
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void write(String content) {
        try {
            // 以追加模式打开文件输出流
            FileOutputStream fos = openFileOutput(FILE, MODE_APPEND);
            // 将FileOutputStream包装成PrintStream
            PrintStream ps = new PrintStream(fos);
            // 输出文件内容
            ps.println(content);
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
