package view.category;

import android.os.Bundle;
import android.view.View;

import com.s7k.doctroid.R;

import view.base.BaseActivity;

public class MedicineActivity extends BaseActivity {

    public MedicineActivity() {
        super(R.layout.activity_medicine, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        toolbarTextView.setText("Medicine Shop");
        toolbarBackImageView.setVisibility(View.VISIBLE);
    }
}
