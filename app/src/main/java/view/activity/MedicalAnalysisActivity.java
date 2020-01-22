package view.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.s7k.doctroid.R;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import network.model.MedicalAnalysis;
import presenter.adapter.MedicalAnalysisAdapter;
import view.base.BaseActivity;
public class MedicalAnalysisActivity extends BaseActivity {

    androidx.appcompat.widget.SearchView searchView;
    RecyclerView recyclerView;
    List<MedicalAnalysis> medicalAnalyses = new ArrayList<>();
    MedicalAnalysisAdapter medicalAnalysisAdapter;

    public MedicalAnalysisActivity() {
        super(R.layout.activity_medical_analysis, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        toolbarBackImageView.setVisibility(View.VISIBLE);

        initializeComponents();
        setListeners();
    }

    private void initializeComponents() {

        searchView = findViewById(R.id.medicalAnalysis_searchView);

        medicalAnalyses = (List<MedicalAnalysis>) getIntent().getSerializableExtra("medicalAnalysis");
        String categoryname = getIntent().getStringExtra("categoryName");
        toolbarTextView.setText(categoryname);

        recyclerView = findViewById(R.id.medicalAnalysis_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MedicalAnalysisActivity.this));
        recyclerView.setHasFixedSize(true);

        medicalAnalysisAdapter = new MedicalAnalysisAdapter(MedicalAnalysisActivity.this,
                medicalAnalyses, position -> {

            Toasty.info(this, "clicked "+position).show();
        });

        recyclerView.setAdapter(medicalAnalysisAdapter);


    }

    private void setListeners() {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                medicalAnalysisAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }

}
