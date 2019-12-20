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
import androidx.viewpager.widget.ViewPager;

import com.s7k.doctroid.R;

import presenter.adapter.ViewPagerAdapter;

public class HospitalPhotosFragment extends Fragment {

    public Context context;

    ViewPager viewPager;
    private String[] imageUrls = new String[]{
            "https://cdn.pixabay.com/photo/2016/11/11/23/34/cat-1817970_960_720.jpg",
            "https://cdn.pixabay.com/photo/2017/12/21/12/26/glowworm-3031704_960_720.jpg",
            "https://cdn.pixabay.com/photo/2017/12/24/09/09/road-3036620_960_720.jpg",
            "https://cdn.pixabay.com/photo/2017/11/07/00/07/fantasy-2925250_960_720.jpg",
            "https://cdn.pixabay.com/photo/2017/10/10/15/28/butterfly-2837589_960_720.jpg"
    };

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

        viewPager = view.findViewById(R.id.hospitalPhotosFragment_viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(context, imageUrls);
        viewPager.setAdapter(viewPagerAdapter);

    }

    private void setListeners() {

    }
}