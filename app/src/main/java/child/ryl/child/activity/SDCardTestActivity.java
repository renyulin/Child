package child.ryl.child.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

import child.ryl.child.R;

/**
 * Created by Administrator on 2016/6/12 0012.
 */
public class SDCardTestActivity extends Activity {

    final String FILE = "/jason.bin";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sd_card);

        Button read = (Button) findViewById(R.id.read);
        Button write = (Button) findViewById(R.id.write);
        // 获取两个文本框
        final EditText edit1 = (EditText) findViewById(R.id.edit1);
        final EditText edit2 = (EditText) findViewById(R.id.edit2);
        // 为write按钮绑定事件监听器
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View source) {
                // 将edit1中的内容写入文件中
                write(edit1.getText().toString());
                edit1.setText("");
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 读取指定文件中的内容，并显示出来
                edit2.setText(read());
            }
        });
    }

    private String read() {
        // 如果手机插入了SD卡，而且应用程序具有访问SD的权限

        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                // 获取SD卡的目录
                File sdDirFile = Environment.getExternalStorageDirectory();
                //获取指定文件对应的输入流
                FileInputStream fis = new FileInputStream(sdDirFile.getCanonicalPath() + FILE);
                Log.i("asd", sdDirFile.getCanonicalPath() + ":" + fis);
                //将指定输入流包装成BufferedReader
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));

                StringBuilder sb = new StringBuilder("");
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                return sb.toString();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void write(String content) {
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File sdDir = Environment.getExternalStorageDirectory();
                File targetFile = new File(sdDir.getCanonicalPath() + FILE);
                RandomAccessFile raf = new RandomAccessFile(targetFile, "rw");
                raf.seek(targetFile.length());
                raf.write(content.getBytes());
                Log.i("aaa", content.getBytes() + "");
                raf.close();
            }
        } catch (Exception e) {
        }
    }
}

