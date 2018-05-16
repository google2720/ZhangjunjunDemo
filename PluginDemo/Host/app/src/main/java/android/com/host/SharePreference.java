package android.com.host;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zhangjunjun
 */

public class SharePreference {

    private static final String TAG = SharePreference.class.getSimpleName();

    public static void putStringValue(Context context, String key, String value){
            SharedPreferences sp = context.getSharedPreferences("launcher", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(key,value);
            editor.commit();
    }

    public static String getStringValue(Context c, String key){
        SharedPreferences sp = c.getSharedPreferences("launcher", Context.MODE_PRIVATE);
        String value = sp.getString(key,"");
        return value;
    }
}
