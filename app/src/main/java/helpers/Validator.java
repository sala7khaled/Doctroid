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
        String regex = "^[+]?[0-9]{11,13}$";
        return phoneNumber.toString().matches(regex);
    }

    public static boolean isConfirmPassMatchPass(CharSequence target1, CharSequence target2) {
        if (target1.length() >= 6 && target2.length() >= 6) {
            return target1.equals(target2);
        } else {
            return false;
        }
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

            return false;
        }
        return true;
    }
}
