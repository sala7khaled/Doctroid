package view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.s7k.doctroid.R;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import network.model.Category;
import presenter.adapter.MainAdapter;
import view.category.AppointmentActivity;
import view.category.EmergencyActivity;
import view.category.HospitalActivity;
import view.category.MedicalActivity;
import view.category.MedicineActivity;
import view.category.ResultActivity;

public class MainFragment extends Fragment {

    public RecyclerView recyclerView;
    public MainAdapter mainAdapter;
    public ArrayList<Category> categoryList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        categoryList = new ArrayList<Category>();
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity().getBaseContext(),2));
        recyclerView.setHasFixedSize(true);
        initializeComponents();
        setItems();
        return view;
    }

    private void initializeComponents() {
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
    }
    private void setItems() {
        categoryList.add(new Category("O6U Hospital", R.drawable.icon_1_hospital));
        categoryList.add(new Category("Medical Analysis", R.drawable.icon_2_medical_analysis));
        categoryList.add(new Category("Appointment", R.drawable.icon_3_appointment));
        categoryList.add(new Category("Result", R.drawable.icon_4_result));
        categoryList.add(new Category("Medicine", R.drawable.icon_5_medicine_shop));
        categoryList.add(new Category("Emergency", R.drawable.icon_6_emergency));
    }
}
