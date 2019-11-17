package view.activity;

import android.os.Bundle;
import android.view.View;

import com.s7k.doctroid.R;

import view.base.BaseActivity;

public class AddMedicineActivity extends BaseActivity {

    public AddMedicineActivity() {
        super(R.layout.activity_add_medicine, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        toolbarTextView.setText("Add Medicine");
        toolbarBackImageView.setVisibility(View.VISIBLE);
    }
}
