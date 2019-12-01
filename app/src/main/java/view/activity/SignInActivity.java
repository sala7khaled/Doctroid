package view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.s7k.doctroid.R;

import java.util.HashMap;

import androidx.annotation.NonNull;
import dialog.ProgressViewDialog;
import helpers.Validator;
import network.api.ApiClient;
import network.api.ApiInterface;
import network.model.SignInForm;
import network.model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utilities.PrefManager;
import view.base.BaseActivity;

public class SignInActivity extends BaseActivity {

    public SignInActivity() {
        super(R.layout.activity_sign_in, true);
    }

    EditText email, password;
    TextView forgetPassword, createAccount, errorMessage;
    ImageView errorDialog;
    Button signIn;
    ProgressViewDialog progressViewDialog;

    @Override
    protected void onStart() {
        super.onStart();

        //TODO check the token if user already signed

    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        toolbarTextView.setText("Sign in");

        initializeComponents();
        setListeners();
    }

    private void initializeComponents() {
        email = findViewById(R.id.signIn_email_editText);
        email.setText("Sala7KhaledSK@gmail.com");
        password = findViewById(R.id.signIn_password_editText);
        errorMessage = findViewById(R.id.signIn_errorMessage_textView);
        errorDialog = findViewById(R.id.signIn_errorDialog_imageView);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        signIn = findViewById(R.id.signIn_login_button);
        forgetPassword = findViewById(R.id.signIn_forgetPassword_textView);
        createAccount = findViewById(R.id.signIn_createAccount_textView);
    }

    private void setListeners() {
        signIn.setOnClickListener(view -> {

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

            progressViewDialog = new ProgressViewDialog(this);
            progressViewDialog.isShowing();
            progressViewDialog.setDialogCancelable(false);
            progressViewDialog.setCanceledOnTouchOutside(false);
            progressViewDialog.showProgressDialog("Checking information");

            if (validate()) {
                String emailSTR = email.getText().toString().trim();
                String passSTR = password.getText().toString().trim();

                SignInForm signInForm = new SignInForm(emailSTR, passSTR);

                navigateToMain();

                signIn(signInForm);

            } else {
                progressViewDialog.hideDialog();
            }

        });
        forgetPassword.setOnClickListener(view ->
                startActivity(new Intent(SignInActivity.this, ForgetPasswordActivity.class)));
        createAccount.setOnClickListener(view ->
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class)));
    }

    private boolean validate() {
        String emailSTR = email.getText().toString().trim();
        String passSTR = password.getText().toString().trim();

        if (!Validator.isValidEmail(email.getText().toString()) && passSTR.length() < 6) {
            errorDialog.setVisibility(View.VISIBLE);
            errorMessage.setText(getString((R.string.email_and_password_not_valid)));
            email.requestFocus();
            return false;
        }

        if (Validator.isValidEmail(emailSTR) && passSTR.length() < 6) {
            errorDialog.setVisibility(View.VISIBLE);
            errorMessage.setText(getString(R.string.password_not_valid));
            password.requestFocus();
            return false;
        }
        if (!Validator.isValidEmail(email.getText().toString()) && passSTR.length() >= 6) {
            errorDialog.setVisibility(View.VISIBLE);
            errorMessage.setText(getString(R.string.email_not_valid));
            return false;
        }

        if (Validator.isValidEmail(emailSTR) && passSTR.length() >= 6) {
            errorDialog.setVisibility(View.VISIBLE);
            errorMessage.setText(getString(R.string.loading));
            return true;
        }
        return false;
    }

    private void signIn(SignInForm signInForm) {
        HashMap<String, String> headers = ApiClient.getHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<User> call = apiService.signIn(headers, signInForm);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call,
                                   @NonNull Response<User> response) {

                if (response.isSuccessful()) {

                    // TODO: Safe the token
                    //PrefManager.saveToken(SignInActivity.this, response.headers().toString());

                    User user = new User();
                    user.setFirstName(response.body().getFirstName());
                    Toast.makeText(SignInActivity.this, "Welcome "+user.getFirstName(), Toast.LENGTH_SHORT).show();

                    progressViewDialog.hideDialog();
                    navigateToMain();

                } else {

                    Toast.makeText(SignInActivity.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                    progressViewDialog.hideDialog();
                }

            }
            @Override
            public void onFailure(@NonNull Call<User> call,
                                  @NonNull Throwable t) {
                Toast.makeText(SignInActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void navigateToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
