package child.ryl.child.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据转换为byte和object，并在bundle传递数据
 */
public class ByteTest extends Activity {
    private List<Map<String, Object>> mapList = new ArrayList<>();
    private boolean flag = true;
    private byte[] bytes;
    private Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final TextView textView = new TextView(this);
        setContentView(textView);
        textView.setPadding(50, 50, 50, 50);
        textView.setTextSize(50);
        textView.setText("ByteTest");
        data();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    Object o = mapList;
                    bytes = toByteArray(o);
                    bundle.putByteArray("byte",bytes);
                    textView.setText(bytes.toString());
                } else {
                    Object ob = toObject(bundle.getByteArray("byte"));
                    List<Map<String, Object>> lls = (List<Map<String, Object>>) ob;
                    textView.setText(lls.toString());
                }
                flag = !flag;
            }
        });
    }

    private void data() {
        Map<String, Object> map = null;
        for (int i = 0; i < 5; i++) {
            map = new HashMap<>();
            map.put("s" + i, "d" + i);
            mapList.add(map);
        }
    }

    /**
     * 对象转数组
     *
     * @param obj
     * @return
     */
    public byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }

    /**
     * 数组转对象
     *
     * @param bytes
     * @return
     */
    public Object toObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return obj;
    }
}
