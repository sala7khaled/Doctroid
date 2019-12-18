package view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.s7k.doctroid.BuildConfig;
import com.s7k.doctroid.R;

import utilities.PrefManager;
import view.activity.SignInActivity;

public class SettingsFragment extends Fragment {

    public Context context;
    private Button logout;
    private TextView version;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        context = getActivity().getApplicationContext();

        initializeComponents(view);
        setListeners();

        return view;
    }

    private void initializeComponents(View view) {
        logout = view.findViewById(R.id.settingsFragment_Logout_Button);
        version = view.findViewById(R.id.settingsFragment_version_TV);
    }

    private void setListeners() {
        version.setText("Version: " + BuildConfig.VERSION_NAME);
        logout.setOnClickListener(v -> {
            PrefManager.deleteToken(context);
            PrefManager.deleteConfirm(context);
            PrefManager.deleteP_id(context);
            startActivity(new Intent(context, SignInActivity.class));
            getActivity().finish();
        });
    }

}
