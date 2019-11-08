package dialog;

import android.app.Activity;
import android.app.AlertDialog;

import com.s7k.doctroid.R;

import engine.UIEngine;
import utilities.Utilities;

public class PopupDialog {

    private static AlertDialog alert;
    private ErrorDialogListener listener;

    public PopupDialog(ErrorDialogListener listener) {
        this.listener = listener;
    }

    public void showMessageDialog(final String title, final String message, final Activity activity) {
        if (activity == null)
            return;
        UIEngine.initialize(activity.getApplicationContext());
        activity.runOnUiThread(() -> {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
            if (!Utilities.isNullString(title))
                dialog.setTitle(UIEngine.createSpannableString(title, UIEngine.Fonts.APP_FONT_LIGHT));
            dialog.setMessage(UIEngine.createSpannableString(message, UIEngine.Fonts.APP_FONT_LIGHT));
            dialog.setCancelable(false);

            dialog.setPositiveButton(activity.getString(R.string.ok), (dialog1, whichButton) -> listener.onOkClick());

            dialog.setNegativeButton(activity.getString(R.string.cancel), (dialog12, whichButton) -> {
                if (alert != null && alert.isShowing())
                    alert.dismiss();
                listener.onCancelClick();
            });

            if (alert != null && alert.isShowing())
                alert.dismiss();
            alert = dialog.create();
            alert.show();
        });
    }

    public interface ErrorDialogListener {
        void onOkClick();

        void onCancelClick();
    }
}
