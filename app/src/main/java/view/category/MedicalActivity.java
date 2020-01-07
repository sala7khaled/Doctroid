package view.category;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.s7k.doctroid.R;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import network.model.MedicalCategory;
import network.model.Medicine;
import presenter.adapter.MedicalCategoryAdapter;
import presenter.adapter.MedicineAdapter;
import view.base.BaseActivity;

public class MedicalActivity extends BaseActivity {

    LinearLayout searchLinear;
    SearchView searchView;
    RecyclerView medicalRecyclerView;
    MedicalCategoryAdapter medicalCategoryAdapter;
    List<MedicalCategory> medicalCategories = new ArrayList<>();

    public MedicalActivity() {
        super(R.layout.activity_medical, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        toolbarTextView.setText("Medical Analysis");
        toolbarBackImageView.setVisibility(View.VISIBLE);

        initializeComponents();
        setListeners();
    }

    private void initializeComponents() {

        searchView = findViewById(R.id.medical_searchView);
        searchLinear = findViewById(R.id.medical_searchLinear);

        medicalRecyclerView = findViewById(R.id.medical_recyclerView);
        medicalRecyclerView.setLayoutManager(new LinearLayoutManager(MedicalActivity.this));
        medicalRecyclerView.setHasFixedSize(true);


        medicalCategories.add(new MedicalCategory("0", "Categoryaaaaaaaa1", "ss"));
        medicalCategories.add(new MedicalCategory("1", "C", "ss"));
        medicalCategories.add(new MedicalCategory("2", "Category3", "ss"));
        medicalCategories.add(new MedicalCategory("3", "Category4", "ss"));
        medicalCategories.add(new MedicalCategory("4", "Category4", "ss"));
        medicalCategories.add(new MedicalCategory("5", "Category4", "ss"));

        medicalCategoryAdapter = new MedicalCategoryAdapter(MedicalActivity.this, medicalCategories,
                new MedicalCategoryAdapter.ItemClick() {
                    @Override
                    public void onClick(int position) {
                        Toasty.info(MedicalActivity.this, "Clicked "+position).show();
                    }
                });

        medicalRecyclerView.setAdapter(medicalCategoryAdapter);
    }

    private void setListeners() {

        searchLinear.setOnClickListener(v-> {
            searchView.setFocusable(true);
            searchView.setIconified(false);
            searchView.requestFocusFromTouch();
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                medicalCategoryAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}
