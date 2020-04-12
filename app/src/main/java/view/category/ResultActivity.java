package view.category;

import android.os.Bundle;
import android.view.View;

import com.s7k.doctroid.R;

import customView.*;
import view.base.BaseActivity;

public class ResultActivity extends BaseActivity {


    public ResultActivity() {
        super(R.layout.activity_result, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        toolbarTextView.setText(R.string.medical_cv);
        toolbarBackImageView.setVisibility(View.VISIBLE);

        callAPI();
        initializeComponents();
        setListeners();
    }

    private void callAPI() {

    }

    private void initializeComponents() {
    }

    private void setListeners() {
    }
}
