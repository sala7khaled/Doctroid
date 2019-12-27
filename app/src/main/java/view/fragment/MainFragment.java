package view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.s7k.doctroid.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import es.dmoral.toasty.Toasty;
import helpers.Navigator;
import network.model.Category;
import network.model.Image;
import presenter.adapter.MainAdapter;
import presenter.adapter.ViewPagerAdapter;
import view.category.AppointmentActivity;
import view.category.EmergencyActivity;
import view.category.HospitalActivity;
import view.category.MedicalActivity;
import view.category.MedicineActivity;
import view.category.ResultActivity;

public class MainFragment extends Fragment {

    public Context context;
    private DatabaseReference database;

    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;
    private ArrayList<Category> categoryList;

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private List<Image> imageUrls = new ArrayList<>();
    private LinearLayout dots;
    private int currentPosition = 0;

    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        context = Objects.requireNonNull(getActivity()).getApplicationContext();

        initializeComponents(view);
        setListeners();
        return view;
    }

    private void initializeComponents(View view) {
        progressBar = view.findViewById(R.id.mainFragment_progressBar);
        dots = view.findViewById(R.id.mainFragment_dots);
        viewPager = view.findViewById(R.id.mainFragment_viewPager);
        viewPagerAdapter = new ViewPagerAdapter(context, imageUrls);
        viewPager.setAdapter(viewPagerAdapter);

        recyclerView = view.findViewById(R.id.mainFragment_recyclerView);
        categoryList = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity().getBaseContext(), 2));
        recyclerView.setHasFixedSize(true);

        mainAdapter = new MainAdapter(categoryList, getContext(), position -> {
            String categoryName = categoryList.get(position).getName();
            switch (categoryName) {
                case "O6U Hospital":
                    startActivity(new Intent(getActivity(), HospitalActivity.class));
                    break;
                case "Medical Analysis":
                    startActivity(new Intent(getActivity(), MedicalActivity.class));
                    break;
                case "Appointment":
                    startActivity(new Intent(getActivity(), AppointmentActivity.class));
                    break;
                case "Result":
                    startActivity(new Intent(getActivity(), ResultActivity.class));
                    break;
                case "Medicine":
                    startActivity(new Intent(getActivity(), MedicineActivity.class));
                    break;
                case "Emergency":
                    startActivity(new Intent(getActivity(), EmergencyActivity.class));
                    break;
            }
        });
        recyclerView.setAdapter(mainAdapter);

        database = FirebaseDatabase.getInstance().getReference().child("O6U").child("news");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Image image = snapshot.getValue(Image.class);
                    if (image != null) {
                        imageUrls.add(image);
                        viewPagerAdapter.notifyDataSetChanged();
                    } else {
                        Toasty.error(context, "No photos added yet!").show();
                    }
                }

                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                Toasty.error(context, databaseError.getMessage()).show();
            }
        });

    }

    private void setListeners() {

        categoryList.add(new Category("O6U Hospital", R.drawable.icon_1_hospital));
        categoryList.add(new Category("Medical Analysis", R.drawable.icon_2_medical_analysis));
        categoryList.add(new Category("Appointment", R.drawable.icon_3_appointment));
        categoryList.add(new Category("Result", R.drawable.icon_4_result));
        categoryList.add(new Category("Medicine", R.drawable.icon_5_medicine));
        categoryList.add(new Category("Emergency", R.drawable.icon_6_emergency));

        prepareDots(currentPosition);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentPosition = position;
            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                if (position == viewPagerAdapter.getCount()) {
                    currentPosition = 0;
                }
                prepareDots(currentPosition);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        createSlideShow();
    }

    private void createSlideShow() {

        Timer timer = new Timer();
        final Handler handler = new Handler();
        final Runnable runnable = () -> {
            currentPosition++;
            if (currentPosition >= viewPagerAdapter.getCount()) {
                currentPosition = 0;
            }
            viewPager.setCurrentItem(currentPosition, true);
        };

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 1000, 3500);
    }

    private void prepareDots(int currentPosition) {

        if (dots.getChildCount() > 0) {
            dots.removeAllViews();
        }

        ImageView[] dotsIV = new ImageView[viewPagerAdapter.getCount()];

        for (int i = 0; i < viewPagerAdapter.getCount(); i++) {
            dotsIV[i] = new ImageView(context);

            if (i == currentPosition) {
                dotsIV[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.slider_active_dot));
            } else {
                dotsIV[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.slider_inactive_dot));
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(8, 0, 8, 0);
            dots.addView(dotsIV[i], layoutParams);

        }
    }
}
