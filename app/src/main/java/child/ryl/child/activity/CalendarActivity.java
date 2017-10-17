package child.ryl.child.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import child.ryl.child.R;
import child.ryl.child.listener.DatePickerListener;
import child.ryl.child.my_view.CalendarView;
import child.ryl.child.my_view.pickerview.TimePopupWindow;

/**
 * 日历
 */
public class CalendarActivity extends Activity implements View.OnClickListener {
    private CalendarView calendar;
    private ImageButton calendarLeft;
    private TextView calendarCenter;
    private ImageButton calendarRight;
    private SimpleDateFormat format;
    private String time;
    private Button confirm;
    private Button secondCalendar;
    private Button activity_calendar_select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        confirm = (Button) findViewById(R.id.activity_calendar_button);
        secondCalendar = (Button) findViewById(R.id.activity_calendar_second_button);
        activity_calendar_select = (Button) findViewById(R.id.activity_calendar_select);
        activity_calendar_select.setOnClickListener(this);
        secondCalendar.setOnClickListener(this);
        confirm.setOnClickListener(this);
        format = new SimpleDateFormat("yyyy-MM-dd");
        //获取日历控件对象
        calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.setSelectMore(false); //单选

        calendarLeft = (ImageButton) findViewById(R.id.calendarLeft);
        calendarCenter = (TextView) findViewById(R.id.calendarCenter);
        calendarRight = (ImageButton) findViewById(R.id.calendarRight);
        try {
            //设置日历日期
            Date date = format.parse("2015-01-01");
            calendar.setCalendarData(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //获取日历中年月 ya[0]为年，ya[1]为月（格式大家可以自行在日历控件中改）
        String[] ya = calendar.getYearAndmonth().split("-");
        calendarCenter.setText(ya[0] + "年" + ya[1] + "月");
        calendarLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //点击上一月 同样返回年月
                String leftYearAndmonth = calendar.clickLeftMonth();
                String[] ya = leftYearAndmonth.split("-");
                calendarCenter.setText(ya[0] + "年" + ya[1] + "月");
            }
        });

        calendarRight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //点击下一月
                String rightYearAndmonth = calendar.clickRightMonth();
                String[] ya = rightYearAndmonth.split("-");
                calendarCenter.setText(ya[0] + "年" + ya[1] + "月");
            }
        });

        //设置控件监听，可以监听到点击的每一天（大家也可以在控件中根据需求设定）
        calendar.setOnItemClickListener(new CalendarView.OnItemClickListener() {

            @Override
            public void OnItemClick(Date selectedStartDate,
                                    Date selectedEndDate, Date downDate) {
                if (calendar.isSelectMore()) {
                    Toast.makeText(getApplicationContext(), format.format(selectedStartDate) + "到" + format.format(selectedEndDate), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), format.format(downDate), Toast.LENGTH_SHORT).show();
                }
                time = format.format(downDate);

            }
        });
        initDate();
    }

    /**
     * 获取日期
     */
    private void initDate() {
        long time = System.currentTimeMillis() + 1000 * 60 * 60 * 24;
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 EEEE");
        Log.e("timetime", "time1=" + format.format(date));
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Log.e("timetime", "time2=" + format.format(date));
        format = new SimpleDateFormat("yyyy/MM/dd");
        Log.e("timetime", "time3=" + format.format(date));
        format = new SimpleDateFormat("HH:mm:ss");
        Log.e("timetime", "time4=" + format.format(date));
        format = new SimpleDateFormat("EEEE");
        Log.e("timetime", "time5=" + format.format(date));
        format = new SimpleDateFormat("E");
        Log.e("timetime", "time6=" + format.format(date));
        format = new SimpleDateFormat("MM-dd");
        Log.e("timetime", "time7=" + format.format(date));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_calendar_button:
                Log.i("aaa", "time2:" + time);
                if ("".equals(time) || time == null) {
                    Toast.makeText(this, "请选择时间", Toast.LENGTH_SHORT).show();
                    return;
                } else try {
                    if ((((new Date()).getTime() / (24 * 3600 * 1000)) - 1) >= format.parse(time).getTime() / (24 * 3600 * 1000)) {
                        Toast.makeText(this, "不能小于当前时间", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        Toast.makeText(this, "right", Toast.LENGTH_SHORT).show();
//                        finish();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.activity_calendar_second_button:
                Calendar calendarSystem = Calendar.getInstance();
                Dialog dialog = new DatePickerDialog(this, new DatePickerListener(this, secondCalendar),
                        calendarSystem.get(Calendar.YEAR), calendarSystem.get(Calendar.MONTH),
                        calendarSystem.get(Calendar.DAY_OF_MONTH));
                dialog.show();
                break;
            case R.id.activity_calendar_select:
                TimePopupWindow tWindow = new TimePopupWindow(this, TimePopupWindow.Type.YEAR_MONTH_DAY);
                tWindow.setOnTimeSelectListener(new TimePopupWindow.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date) {
                        String time = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA).format(date);
                        activity_calendar_select.setText(time);
                    }
                });
                tWindow.showAtLocation(activity_calendar_select, Gravity.BOTTOM, 0, 0);
                break;
        }
    }
}
