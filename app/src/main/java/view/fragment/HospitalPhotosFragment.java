package view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.s7k.doctroid.R;

public class HospitalPhotosFragment extends Fragment {

    public Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hospital_photos_fragment, container, false);
        context = getActivity().getApplicationContext();

        initializeComponents(view);
        setListeners();

        return view;
    }

    private void initializeComponents(View view) {

    }

    private void setListeners() {

    }
}