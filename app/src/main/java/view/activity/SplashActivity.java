package view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.s7k.doctroid.R;

import app.Constants;
import view.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    public SplashActivity() {
        super(R.layout.activity_splash_screen, false);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        new Handler().postDelayed(this::navigateToLogin, Constants.SPLASH_TIME_OUT);
    }

    private void navigateToLogin() {
        Intent i = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}