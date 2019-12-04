package dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;

import com.s7k.doctroid.R;

import java.util.Objects;

import engine.UIEngine;
import utilities.Utilities;

public class ErrorDialog {

    private static AlertDialog alert;

    public static void showMessageDialog(final String title, final String message, final Activity activity) {
        showMessageDialog(title, message, activity, null, false);
    }

    private static void showMessageDialog(final String title, final String message, final Activity activity, final Runnable runnable, final boolean isShowCancelButton) {
        if (activity == null)
            return;
        UIEngine.initialize(activity.getApplicationContext());
        activity.runOnUiThread(() -> {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
            if (!Utilities.isNullString(title))
                dialog.setTitle(null);

            dialog.setMessage(UIEngine.createSpannableString(message, UIEngine.Fonts.APP_FONT_BOOK));
            dialog.setCancelable(false);

            dialog.setPositiveButton(activity.getString(R.string.ok), (dialog1, whichButton) -> {
                if (runnable != null) {
                    runnable.run();
                }
            });

            if (isShowCancelButton) {
                dialog.setNegativeButton(activity.getString(R.string.cancel), (dialog12, whichButton) -> {
                    if (alert != null && alert.isShowing())
                        alert.dismiss();
                });
            }

            if (alert != null && alert.isShowing())
                alert.dismiss();
            alert = dialog.create();
            try {
                alert.show();
            } catch (Exception e) {
                Log.e("Exception", Objects.requireNonNull(e.getMessage()));
            }
        });
    }
}

//ErrorDialog.showMessageDialog(getString(R.string.no_internet_connection), "xd", SignInActivity.this);

//            PopupDialog popupDialog = new PopupDialog(new PopupDialog.ErrorDialogListener() {
//                @Override
//                public void onOkClick() {
//                    Toast.makeText(SignInActivity.this, "Hiii", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onCancelClick() {
//
//                }
//            });
//            popupDialog.showMessageDialog("lol", "xd", this);
