package view.category;

import android.os.Bundle;
import android.view.View;

import com.s7k.doctroid.R;

import view.base.BaseActivity;

public class AppointmentActivity extends BaseActivity {

    public AppointmentActivity() {
        super(R.layout.activity_appointment, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        toolbarTextView.setText("Appointment");
        toolbarBackImageView.setVisibility(View.VISIBLE);
    }
}
