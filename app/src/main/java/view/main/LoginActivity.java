package view.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.s7k.doctroid.R;

import view.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    public LoginActivity() {
        super(R.layout.activity_login, false);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        toolbarBackImageView.setVisibility(View.VISIBLE);
    }

}
