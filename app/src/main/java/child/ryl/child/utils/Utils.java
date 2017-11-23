package child.ryl.child.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import child.ryl.child.R;
import child.ryl.child.dialog.TelDialog;
import child.ryl.child.listener.Clickable;
import child.ryl.child.listener.TextViewNotUnderLine;

/**
 * 工具类
 */
public class Utils {
    public static void smallToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void smallToast(Context context, int msg) {
        String mMsg = context.getString(msg);
        Toast.makeText(context, mMsg, Toast.LENGTH_SHORT).show();
    }

    /**
     * String类型的时间转化为毫秒数
     *
     * @param date
     * @return
     */
    public static long changeDateToTime(String date) {
        Calendar c = Calendar.getInstance();
        if (TextUtils.isEmpty(date)) {
            return 0;
        }
        try {
            c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return c.getTimeInMillis();
    }

    /**
     * 修改中间字体颜色
     * textView.setText(textAddImg(s,m,e))
     *
     * @param start
     * @param middle
     * @param end
     * @return
     */
    public static SpannableStringBuilder changeTextColor(String start, String middle, String end) {
        SpannableStringBuilder builder = new SpannableStringBuilder(start + middle + end);
        if (TextUtils.isEmpty(start) || TextUtils.isEmpty(middle) || TextUtils.isEmpty(end)) {
            return builder;
        }
        int startCount = start.length();
        int middleCount = middle.length();
        int endCount = end.length();
        //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
        ForegroundColorSpan themeColor = new ForegroundColorSpan(Color.parseColor("#FF5200"));
        builder.setSpan(themeColor, startCount, startCount + middleCount, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    /**
     * 文本添加图片
     * textView.setText(textAddImg(c,b))
     *
     * @param context
     * @param bitmap
     * @return
     */
    public static SpannableString textAddImg(Context context, Bitmap bitmap) {
        SpannableString spanString = new SpannableString("icon");
        if (bitmap == null) {
            return spanString;
        }
        ImageSpan imgSpan = new ImageSpan(context, bitmap);
        spanString.setSpan(imgSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanString;
    }

    /**
     * 获取SpannableStringBuilder对象给textView用
     * 电话号颜色改变并可以点击
     *
     * @return
     */
    public static SpannableStringBuilder getSpan(
            final Context context, String content, int start, int end, final String tel) {
        SpannableStringBuilder style = new SpannableStringBuilder(content);
        //监听器
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TelDialog dialog = new TelDialog(context, R.style.MyDialogBackgroundBlack);
                dialog.setContent(tel);
                dialog.show();
            }
        };
        style.setSpan(new Clickable(listener), start, end, Spanned.SPAN_MARK_MARK);
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#ff5200")), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        TextViewNotUnderLine mNoUnderlineSpan = new TextViewNotUnderLine();
        style.setSpan(mNoUnderlineSpan, start, end, Spanned.SPAN_MARK_MARK);
        return style;
    }

    //获取屏幕的高度
    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }

    /**
     * 判断是否是规则手机号码
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^[1][34578][0-9]{9}");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 保留两位小数
     *
     * @param f
     * @return
     */
    public static String DFTwoPont(double f) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(f);
    }

    /**
     * 保留两位小数不四舍五入
     *
     * @param
     * @return
     */
    public static String DFTwoPont2(double f) {
        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(2);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.FLOOR);
        return formater.format(f);
    }

    /**
     * 保留一位小数
     *
     * @param f
     * @return
     */
    public static String DFOnePont(double f) {
        DecimalFormat df = new DecimalFormat("#.0");
        return df.format(f);
    }

    /**
     * 没有小数
     *
     * @param
     * @return
     */
    public static String DFNoPont(double d) {
//		DecimalFormat df = new DecimalFormat("#");
//		return df.format(f);
        String s = String.valueOf(d);
        String newD = s.substring(0, s.indexOf("."));
        return newD;
    }

    /**
     * 清除文件
     *
     * @param oldPath
     */
    public static void deleteFile(File oldPath) {
        if (oldPath.isDirectory()) {
            File[] files = oldPath.listFiles();
            for (File file : files) {
                deleteFile(file);
            }
        } else {
            oldPath.delete();
        }
    }

    /**
     * String转Int
     *
     * @param str
     * @return
     */
    public static int stringToInt(String str, int no) {
        int i = no;
        if (TextUtils.isEmpty(str)) {
            return i;
        }
        try {
            i = Integer.parseInt(str);
        } catch (Exception e) {
            i = 0;
        }
        return i;
    }

    /**
     * 强转StringToDouble
     *
     * @param no
     * @return
     */
    public static Double stringToDouble(String no) {
        Double d = 0.00;
        if (TextUtils.isEmpty(no)) {
            return d;
        }
        try {
            d = Double.parseDouble(no);
        } catch (Exception e) {
            d = 0.00;
        }
        return d;
    }

    /**
     * String类型的时间转化为毫秒数
     *
     * @param date
     * @return
     */
    public static long changeDateToTime(String date, String type) {
        Calendar c = Calendar.getInstance();
        if (TextUtils.isEmpty(date)) {
            return 0;
        }
        try {
            c.setTime(new SimpleDateFormat(type).parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return c.getTimeInMillis();
    }

    /**
     * 获取系统时间
     *
     * @return
     */
    public static String getSystemDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String date = formatter.format(curDate);
        return date;
    }

    /**
     * 秒数转换为分钟数
     *
     * @param time
     * @return
     */
    public static String secondToMinutes(int time) {
        StringBuilder sb = new StringBuilder();
        sb.append(time / 60 + ":");
        int remain = time % 60;
        if (remain < 10) {
            sb.append(0);
        }
        sb.append(remain);
        return sb.toString();
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    /**
     * 流式存储
     *
     * @param data
     */
    public static void saveStreamData(Context context, String data) {
        FileOutputStream outputStream;
        BufferedWriter bufferedWriter = null;
        try {
            outputStream = context.openFileOutput("data", Context.MODE_PRIVATE);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getStreamData(Context context, String data) {
        StringBuilder stringBuilder = new StringBuilder();
        FileInputStream inputStream;
        BufferedReader bufferedReader = null;
        try {
            inputStream = context.openFileInput("data");
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }
}
