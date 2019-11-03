package view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.s7k.doctroid.R;

import view.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    public LoginActivity() {
        super(R.layout.activity_login, true);
    }
    
    EditText username,password;
    TextView login, forgetPassword, createAccount;

    @Override
    protected void doOnCreate(Bundle bundle) {
        toolbarTextView.setText("Sign in");
        initializeComponents();
        setListeners();
    }

    private void initializeComponents() {
        username = findViewById(R.id.login_username_editText);
        password = findViewById(R.id.login_password_editText);
        login = findViewById(R.id.login_login_button);
        forgetPassword = findViewById(R.id.login_forgetPassword_textView);
        createAccount = findViewById(R.id.login_createAccount_textView);
    }

    private void setListeners() {
        login.setOnClickListener(view -> {
            // TODO Login Validation
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        });
        forgetPassword.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
        });
        createAccount.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        });
    }

}
