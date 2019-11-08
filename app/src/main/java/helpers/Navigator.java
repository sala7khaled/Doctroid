package helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.s7k.doctroid.R;

import utilities.Utilities;

public class Navigator {

    public static void sendSMS(Context context, String tel, String body) {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", tel);
        if (!Utilities.isNullString(body)) smsIntent.putExtra("sms_body", body);
        smsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(smsIntent, "Select SMS App"));
    }

    public static void openEmail(Context context, String emailAddress, String subject, String body) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", emailAddress, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);
        Intent chooser = Intent.createChooser(emailIntent, "");
        context.startActivity(chooser);
    }

    public static void openUrlInBrowser(Context context, String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(Uri.parse(url));
        Intent chooser = Intent.createChooser(browserIntent, "");
        context.startActivity(chooser);
    }

    public static void callPhoneNumber(Context context, String phoneNumber) {
        if (context == null || Utilities.isNullString(phoneNumber))
            return;
        Uri number = Uri.parse("tel:" + phoneNumber);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);

        Intent chooser = Intent.createChooser(callIntent, "");
        context.startActivity(chooser);

    }

    public static void share(Activity activity, String quote) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, quote);
        try {
            shareIntent.putExtra("android.intent.extra.TEXT", quote);
        } catch (Exception e) {
            e.printStackTrace();
        }
        activity.startActivity(Intent.createChooser(shareIntent, activity.getString(R.string.app_name)));
    }
}
