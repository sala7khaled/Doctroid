package view.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
        mainAdapter = new MainAdapter(categoryList, getContext(), new MainAdapter.ItemClick() {
            @Override
            public void onClick(int position) {
                String categoryName = categoryList.get(position).getName();
                switch (categoryName) {
                    case "Hospital":
                        //startActivity(new Intent(MainActivity.this,));
                        Toast.makeText(getContext(), "1 clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case "Medical Analysis":
                        Toast.makeText(getContext(), "2 clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case "Appointment":
                        Toast.makeText(getContext(), "3 clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case "Result":
                        Toast.makeText(getContext(), "4 clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case "Medicine Shop":
                        Toast.makeText(getContext(), "5 clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case "Emergency":
                        Toast.makeText(getContext(), "6 clicked", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        recyclerView.setAdapter(mainAdapter);
    }
    private void setItems() {
        categoryList.add(new Category("Hospital", R.drawable.icon_1_hospital));
        categoryList.add(new Category("Medical Analysis", R.drawable.icon_2_medical_analysis));
        categoryList.add(new Category("Appointment", R.drawable.icon_3_appointment));
        categoryList.add(new Category("Result", R.drawable.icon_4_result));
        categoryList.add(new Category("Medicine Shop", R.drawable.icon_5_medicine_shop));
        categoryList.add(new Category("Emergency", R.drawable.icon_6_emergency));
    }
}
