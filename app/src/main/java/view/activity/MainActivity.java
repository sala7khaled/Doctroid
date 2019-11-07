package view.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.s7k.doctroid.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import network.model.Category;
import presenter.adapter.MainAdapter;
import view.base.BaseActivity;

public class MainActivity extends BaseActivity {


    public MainActivity() {
        super(R.layout.activity_main, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {

        BottomNavigationView bottomNav = findViewById(R.id.main_bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.base_frameLayout,
                new MainFragment()).commit();

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

                getSupportFragmentManager().beginTransaction().replace(R.id.base_frameLayout, selectedFragment).commit();

                return true;
            };
}