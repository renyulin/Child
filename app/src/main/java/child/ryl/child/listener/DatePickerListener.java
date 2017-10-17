package child.ryl.child.listener;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;

/**
 * 日历（系统）监听事件
 */
public class DatePickerListener implements DatePickerDialog.OnDateSetListener {
    private Context context;
    private Button calendar;

    public DatePickerListener(Context context, Button calendar) {
        this.context = context;
        this.calendar = calendar;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String month;
        String day;
        if (monthOfYear + 1 < 10) {
            month = "0" + (monthOfYear + 1);
        } else {
            month = "" + (monthOfYear + 1);
        }
        if (dayOfMonth < 10) {
            day = "0" + dayOfMonth;
        } else {
            day = "" + dayOfMonth;
        }
        String date = year + "-" + month + "-" + day;
        Log.i("date1", year + "-" + month + "-" + day);
        Log.i("date2", year + month + day + "");
        Log.i("date3", Long.parseLong(year + month + day) + "");
        calendar.setText(date);
    }
}
