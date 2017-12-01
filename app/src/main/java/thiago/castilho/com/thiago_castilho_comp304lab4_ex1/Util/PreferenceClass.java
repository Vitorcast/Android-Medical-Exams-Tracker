package thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by thiag on 30/11/2017.
 */

public class PreferenceClass {
    public static final String USER_ID = "USER_ID";

    private final SharedPreferences sharedpreferences;

    public PreferenceClass(Context context) {
        sharedpreferences = context.getSharedPreferences(USER_ID, Context.MODE_PRIVATE);
    }

    public int getUserId() {
        int id = sharedpreferences.getInt(USER_ID, -1);
        return id;
    }

    public void setUserId(int userId) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(USER_ID, userId);
        editor.commit();
    }

    public void clearUserId() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove(USER_ID);
        editor.commit();
    }
}