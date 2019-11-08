package helpers;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

import com.s7k.doctroid.R;

public class Validator {

    public static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(target.toString().trim()).matches();
        }
    }

    public static boolean isValidPhoneNumber(CharSequence phoneNumber) {
        String regex = "^[+]?[0-9]{10,13}$";
        return phoneNumber.toString().matches(regex);
    }

    public static boolean isConfirmPassMatchPass(CharSequence target1, CharSequence target2) {
        return target1.equals(target2);
    }

    public static boolean registerValidation(Context context, EditText firstNameEdt, EditText lastNameEdt,
                                             EditText emailEdt, EditText passwordEdt, EditText confirmPasswordEdt,
                                             EditText phoneEdt) {

        String firstName = firstNameEdt.getText().toString().trim();
        String lastName = lastNameEdt.getText().toString().trim();
        String email = emailEdt.getText().toString().trim();
        String password = passwordEdt.getText().toString().trim();
        String confirmPassword = confirmPasswordEdt.getText().toString().trim();
        String phone = phoneEdt.getText().toString().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()
                || !Patterns.EMAIL_ADDRESS.matcher(email).matches() || phone.isEmpty() || phone.length() < 11
                || password.isEmpty() || confirmPassword.isEmpty() || password.length() < 6
                || !password.equals(confirmPassword)) {

            if (firstName.isEmpty()) {
                firstNameEdt.setError(context.getString(R.string.first_name_required));
            }

            if (lastName.isEmpty()) {
                lastNameEdt.setError(context.getString(R.string.last_name_required));
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEdt.setError(context.getString(R.string.email_not_valid));
            }

            if (email.isEmpty()) {
                emailEdt.setError(context.getString(R.string.email_required));
            }

            if (phone.length() < 11) {
                phoneEdt.setError(context.getString(R.string.phone_not_valid));
            }

            if (phone.isEmpty()) {
                phoneEdt.setError(context.getString(R.string.phone_required));
            }

            if (password.length() < 6) {
                passwordEdt.setError(context.getString(R.string.password_not_valid));
            }

            if (password.isEmpty()) {
                passwordEdt.setError(context.getString(R.string.password_required));
            }

            if (confirmPassword.isEmpty()) {
                confirmPasswordEdt.setError(context.getString(R.string.confirm_password_required));
            }

            if (!password.equals(confirmPassword)) {
                passwordEdt.setError(context.getString(R.string.password_not_matched));
                confirmPasswordEdt.setError(context.getString(R.string.password_not_matched));
            }
            return false;
        }
        return true;
    }
}
