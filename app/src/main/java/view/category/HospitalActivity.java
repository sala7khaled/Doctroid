package view.category;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.s7k.doctroid.R;

import java.util.Objects;

import customView.CustomToast;
import customView.CustomToastType;
import helpers.Navigator;
import network.model.Hospital;
import utilities.InternetUtilities;
import view.base.BaseActivity;
import view.fragment.HospitalAboutFragment;
import view.fragment.HospitalLocationFragment;
import view.fragment.HospitalPhotosFragment;

public class HospitalActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemReselectedListener {

    ImageView phone, facebook, website;
    BottomNavigationView bottomNav;

    public HospitalActivity() {
        super(R.layout.activity_hospital, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        toolbarTextView.setText(R.string.hospital);
        toolbarBackImageView.setVisibility(View.VISIBLE);

        bottomNav = findViewById(R.id.hospital_bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        setFragment(new HospitalLocationFragment());
        bottomNav.setOnNavigationItemReselectedListener(this);
        bottomNav.getMenu().findItem(R.id.hospital_nav_location).setChecked(true);

        initializeComponents();
        setListeners();
    }

    private void initializeComponents() {
        phone = findViewById(R.id.hospital_phone_icon);
        facebook = findViewById(R.id.hospital_facebook_icon);
        website = findViewById(R.id.hospital_website_icon);
    }

    private void setListeners() {

        phone.setOnClickListener(View -> Navigator.callPhoneNumber(HospitalActivity.this, getString(R.string.hospital_phone)));
        facebook.setOnClickListener(View -> Navigator.openUrlInBrowser(HospitalActivity.this, getString(R.string.hospital_facebook)));
        website.setOnClickListener(View -> Navigator.openUrlInBrowser(HospitalActivity.this, getString(R.string.hospital_website)));

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.hospital_nav_about:
                        selectedFragment = new HospitalAboutFragment();
                        break;
                    case R.id.hospital_nav_location:
                        selectedFragment = new HospitalLocationFragment();
                        break;
                    case R.id.hospital_nav_photos:
                        selectedFragment = new HospitalPhotosFragment();
                        break;
                }

                setFragment(selectedFragment);
                return true;
            };

    private void setFragment(Fragment selectedFragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.hospital_frameLayout, selectedFragment).commit();

    }

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
    }
}
