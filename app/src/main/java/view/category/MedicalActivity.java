package view.category;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.s7k.doctroid.R;

import view.base.BaseActivity;

public class MedicalActivity extends BaseActivity {

    public MedicalActivity() {
        super(R.layout.activity_medical, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        toolbarTextView.setText("Medical Analysis");
        toolbarBackImageView.setVisibility(View.VISIBLE);
    }
}
