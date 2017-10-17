package child.ryl.child.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 数据存储工具SharedPreferences
 */
public class Constant {
    public static final String HTML_ACT = "html_act";

    private static String SharedPreferencesKey = "myChild";

    private static SharedPreferences getShare(Context context) {
        return context.getSharedPreferences(SharedPreferencesKey, Context.MODE_ENABLE_WRITE_AHEAD_LOGGING);
    }


    public static void editSharedPreferences(String key, String value, Context context) {
        SharedPreferences.Editor edit = getShare(context).edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static String getSharedPreferencesValueByKeyString(String key, Context context) {
        return getShare(context).getString(key, "");
    }
}
