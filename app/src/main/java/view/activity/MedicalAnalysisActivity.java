package view.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.s7k.doctroid.R;

import java.util.ArrayList;
import java.util.List;

import network.model.MedicalAnalysis;
import presenter.adapter.MedicalAnalysisAdapter;
import view.fragment.BottomSheetFragment;
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
        String categoryName = getIntent().getStringExtra("categoryName");
        toolbarTextView.setText(categoryName);

        recyclerView = findViewById(R.id.medicalAnalysis_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MedicalAnalysisActivity.this));
        recyclerView.setHasFixedSize(true);

        medicalAnalysisAdapter = new MedicalAnalysisAdapter(MedicalAnalysisActivity.this,
                medicalAnalyses, position -> {

            MedicalAnalysis m1;
            m1 = medicalAnalyses.get(position);

            String c_id = getIntent().getStringExtra("c_id");

            BottomSheetFragment bottomSheetFragment = new BottomSheetFragment(m1,c_id);
            bottomSheetFragment.show(getSupportFragmentManager(), "Appoint");
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
