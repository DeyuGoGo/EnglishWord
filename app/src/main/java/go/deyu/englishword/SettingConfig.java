package go.deyu.englishword;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by huangeyu on 15/10/15.
 */
public class SettingConfig {
    private final static String PERFERANCE_SETTING = "Setting_Perferance";
    private final static String KEY_SETTING_SCREEN_LOCK_TEST_OPEN = "screen.lock.test.open";
    private final static String KEY_SETTING_INIT = "samplewords.init";

    public static void setScreenLockStatus(Context context,boolean isOpen) {
        SharedPreferences prefs = context.getSharedPreferences(PERFERANCE_SETTING , Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_SETTING_SCREEN_LOCK_TEST_OPEN , isOpen);
        editor.commit();
    }

    public static boolean getScreenLockStatus(Context context ){
        SharedPreferences prefs = context.getSharedPreferences(PERFERANCE_SETTING , Context.MODE_PRIVATE );
        return prefs.getBoolean(KEY_SETTING_SCREEN_LOCK_TEST_OPEN,false);
    }

    public static void setInitStatus(Context context,boolean isInit) {
        SharedPreferences prefs = context.getSharedPreferences(PERFERANCE_SETTING , Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_SETTING_INIT , isInit);
        editor.commit();
    }
    public static boolean getInitStatus(Context context ){
        SharedPreferences prefs = context.getSharedPreferences(PERFERANCE_SETTING , Context.MODE_PRIVATE );
        return prefs.getBoolean(KEY_SETTING_INIT,false);
    }
}
