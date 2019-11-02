package view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.s7k.doctroid.R;

import view.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    public LoginActivity() {
        super(R.layout.activity_login, false);
    }
    
    EditText username,password;
    TextView login, forgetPassword, createAccount;

    @Override
    protected void doOnCreate(Bundle bundle) {

        initializeComponents();
        setListeners();
    }

    private void initializeComponents() {
        username = findViewById(R.id.login_username_ET);
        password = findViewById(R.id.login_password_ET);
        login = findViewById(R.id.login_login_TV);
        forgetPassword = findViewById(R.id.login_forgetPassword_TV);
        createAccount = findViewById(R.id.login_createAccount_TV);
    }

    private void setListeners() {
        login.setOnClickListener(view -> {
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
