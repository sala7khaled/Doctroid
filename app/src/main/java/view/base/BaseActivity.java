package view.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.s7k.doctroid.R;

public abstract class BaseActivity extends AppCompatActivity {

    private int mActivityLayout;
    private boolean showToolbar;
    protected ViewGroup contentLayout;
    protected Toolbar toolbar;
    protected ImageView toolbarBackImageView;
    protected TextView toolbarTextView;

    public BaseActivity(int activityLayout, boolean showToolbar) {
        this.mActivityLayout = activityLayout;
        this.showToolbar = showToolbar;
    }

    protected abstract void doOnCreate(Bundle bundle);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        initToolbar();

        contentLayout = findViewById(R.id.base_frameLayout);
        LayoutInflater.from(this).inflate(mActivityLayout, contentLayout, true);
        doOnCreate(savedInstanceState);

        if (showToolbar) {
            toolbar.setVisibility(View.VISIBLE);
        } else {
            toolbar.setVisibility(View.GONE);
        }
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbarTextView = findViewById(R.id.toolbar_textView);
        toolbarBackImageView = findViewById(R.id.toolbar_back_imageView);
        setSupportActionBar(toolbar);

        toolbarBackImageView.setOnClickListener(view -> finish());
        // setOnClick for more icons and call iconFunction(); for it
    }

    private void iconFunction() {

    }
}
