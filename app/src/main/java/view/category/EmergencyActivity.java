package view.category;

import android.os.Bundle;
import android.view.View;

import com.s7k.doctroid.R;

import view.base.BaseActivity;

public class EmergencyActivity extends BaseActivity {

    public EmergencyActivity() {
        super(R.layout.activity_emergency, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        toolbarTextView.setText(R.string.emergency);
        toolbarBackImageView.setVisibility(View.VISIBLE);
    }
}
