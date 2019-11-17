package view.category;

import android.os.Bundle;
import android.view.View;

import com.s7k.doctroid.R;

import view.base.BaseActivity;

public class ResultActivity extends BaseActivity {

    public ResultActivity() {
        super(R.layout.activity_result, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        toolbarTextView.setText("Result");
        toolbarBackImageView.setVisibility(View.VISIBLE);
    }
}
