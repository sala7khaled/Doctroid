package utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import app.Constants;

public class PrefManager {

    public static void saveToken(Context context, String accessToken) {
        SharedPreferences.Editor editor =
                PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(Constants.KEY_TOKEN, accessToken);
        editor.apply();
    }

    public static String getToken(Context context) {
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_TOKEN, null);
    }
}
