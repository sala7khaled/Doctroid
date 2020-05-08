package view.category;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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

        if (ContextCompat.checkSelfPermission(ResultActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(ResultActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(ResultActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }

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
