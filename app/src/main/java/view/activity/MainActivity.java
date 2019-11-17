package view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.s7k.doctroid.R;

import view.base.BaseActivity;
import view.fragment.MainFragment;
import view.fragment.SettingsFragment;
import view.fragment.UserFragment;

public class MainActivity extends BaseActivity {

    public MainActivity() {
        super(R.layout.activity_main, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {

        BottomNavigationView bottomNav = findViewById(R.id.main_bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        setFragment(new MainFragment());

        bottomNav.getMenu().findItem(R.id.nav_home).setChecked(true);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        selectedFragment = new MainFragment();
                        break;
                    case R.id.nav_user:
                        selectedFragment = new UserFragment();
                        break;
                    case R.id.nav_settings:
                        selectedFragment = new SettingsFragment();
                        break;
                }

                setFragment(selectedFragment);
                return true;
            };

    private void setFragment(Fragment selectedFragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, selectedFragment).commit();

    }
    @Override
    public void onBackPressed() {

        showDialog();
    }

    public void showDialog() {
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
}