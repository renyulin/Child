package child.ryl.child.handler;

import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.util.Log;
import android.widget.TextView;

/**
 * 线程：
 * 1.检验textView每一行的字数
 * 2.
 */
public class LineContent implements Runnable {
    private TextView mTarget;
    private Handler handler;

    public LineContent(TextView target, Handler handler) {
        mTarget = target;
        this.handler = handler;
    }


    public void run() {
        GetEachLineContent();
    }

    private void GetEachLineContent() {
        //得到TextView的布局
        Layout layout = mTarget.getLayout();
        //得到TextView显示有多少行
        int lines = mTarget.getLineCount();
        //为了转换String 到 StringBuilder
        StringBuilder SrcStr = new StringBuilder(mTarget.getText().toString());
        int size = 0;
        for (int i = 0; i < lines; i++) {
            //使用getLineStart 和 getLineEnd 得到指定行的开始和结束的坐标，坐标范围是SrcStr整个字符串范围内。
            String lineStr = SrcStr.subSequence(layout.getLineStart(i), layout.getLineEnd(i)).toString();
            Log.d("text", lineStr);
            size += lineStr.length();

//            if (i == 2) {
//                double w1 = mTarget.getPaint().measureText(lineStr);//文本宽度
//                double w0 = mTarget.getWidth();//控件宽度
//                if (w0 < w1) {//控件宽度小于文本宽度=true;
//                    flag = true;
//                } else {
//                    flag = false;
//                }
//            }
        }
        Log.i("asd", size + "");

        Message msg = handler.obtainMessage();

        //msg.arg1 = 123;//传递整型数据
        //msg.obj = "asd";传递object类型

        //利用bundle对象来传值
//        Bundle b = new Bundle();
//        b.putInt("ID", 12);
//        b.putString("name", "thinkpad");
//        msg.setData(b);
//        msg.sendToTarget();
        msg.arg1 = lines;
        msg.arg2 = size;
//        msg.obj = flag;
        handler.sendMessage(msg);
    }

}
