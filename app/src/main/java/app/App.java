package app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context = null;
    @SuppressLint("StaticFieldLeak")
    private static App application = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        application = this;

    }
    public static Context getContext() {
        return context;
    }
    public static App getApplication() {
        return application;
    }
}
