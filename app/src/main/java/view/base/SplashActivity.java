package view.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.race604.drawable.wave.WaveDrawable;
import com.s7k.doctroid.R;

import app.Constants;

public class SplashActivity extends BaseActivity {

    View view;

    public SplashActivity() {
        super(R.layout.activity_splash_screen, false);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {

        view = findViewById(R.id.view);
        waveLoading();
        new Handler().postDelayed(this::navigateToLogin, Constants.SPLASH_TIME_OUT);
    }

    private void waveLoading() {

        WaveDrawable colorWave = new WaveDrawable(this, R.drawable.back_circle);
        view.setBackground(colorWave);
        colorWave.setIndeterminate(true);
    }

    private void navigateToLogin() {
        Intent i = new Intent(SplashActivity.this, SliderActivity.class);
        startActivity(i);
        finish();
    }
}