package view.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.s7k.doctroid.R;

import view.fragment.MainFragment;
import view.fragment.SettingsFragment;
import view.fragment.UserFragment;
import view.base.BaseActivity;

public class MainActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemReselectedListener {

    public MainActivity() {
        super(R.layout.activity_main, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {

        BottomNavigationView bottomNav = findViewById(R.id.main_bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(this);
        bottomNav.setOnNavigationItemReselectedListener(this);
        setFragment(new MainFragment());
        bottomNav.getMenu().findItem(R.id.nav_home).setChecked(true);
    }

    private void setFragment(Fragment selectedFragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, selectedFragment).commit();
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage("Are you sure to exist?");

        builder.setPositiveButton("Yes",
                (dialogInterface, i) -> super.onBackPressed());

        builder.setNegativeButton("No",
                (dialogInterface, i) -> {

                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                setFragment(new MainFragment());
                break;
            case R.id.nav_user:
                setFragment(new UserFragment());
                break;
            case R.id.nav_settings:
                setFragment(new SettingsFragment());
                break;
        }
        return true;
    }

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {
    }
}