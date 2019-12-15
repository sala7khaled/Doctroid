package view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.s7k.doctroid.R;

import view.base.BaseActivity;

public class ForgetPasswordActivity extends BaseActivity {

    public ForgetPasswordActivity() {
        super(R.layout.activity_forget_password, true);
    }
    EditText email;

    @Override
    protected void doOnCreate(Bundle bundle) {
        toolbarBackImageView.setVisibility(View.VISIBLE);
        toolbarTextView.setText("Forget Password");

    }
}
