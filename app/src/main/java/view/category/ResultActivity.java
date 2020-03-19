package view.category;

import android.os.Bundle;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.s7k.doctroid.R;

import view.base.BaseActivity;
import www.sanju.motiontoast.MotionToast;

public class ResultActivity extends BaseActivity {


    public ResultActivity() {
        super(R.layout.activity_result, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        toolbarTextView.setText("Medical CV");
        toolbarBackImageView.setVisibility(View.VISIBLE);

        callAPI();
        initializeComponents();
        setListeners();
    }

    private void callAPI() {

        MotionToast.Companion.darkToast(ResultActivity.this, "Completed!",
                MotionToast.Companion.getTOAST_SUCCESS(),
                MotionToast.Companion.getGRAVITY_BOTTOM(),
                MotionToast.Companion.getLONG_DURATION(),
                ResourcesCompat.getFont(ResultActivity.this, R.font.gotham_rounded_medium));

    }

    private void initializeComponents() {
    }

    private void setListeners() {
    }
}
