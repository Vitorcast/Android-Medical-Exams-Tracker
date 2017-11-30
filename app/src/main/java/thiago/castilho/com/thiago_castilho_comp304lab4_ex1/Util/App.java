package thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Util;

import android.app.Application;

/**
 * Created by thiag on 30/11/2017.
 */

public class App extends Application {
    public Integer userId;
    public PreferenceClass prefs;

    @Override
    public void onCreate() {
        super.onCreate();

        prefs = new PreferenceClass(this);
        userId = prefs.getUserId();
    }
}
