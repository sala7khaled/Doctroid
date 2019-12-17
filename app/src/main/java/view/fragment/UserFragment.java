package view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.s7k.doctroid.R;

import utilities.PrefManager;
import view.activity.SignInActivity;

public class UserFragment extends Fragment {

    public Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);
        context = getActivity().getApplicationContext();

        initializeComponents(view);

        return view;
    }

    private void initializeComponents(View view) {
    }


}
