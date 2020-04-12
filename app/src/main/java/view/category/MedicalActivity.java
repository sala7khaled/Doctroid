package view.category;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.s7k.doctroid.R;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import app.App;
import customView.CustomToast;
import customView.CustomToastType;
import dialog.ProgressViewDialog;
import es.dmoral.toasty.Toasty;
import network.api.ApiClient;
import network.api.ApiInterface;
import network.model.MedicalCategory;
import network.model.Medicine;
import presenter.adapter.MedicalCategoryAdapter;
import presenter.adapter.MedicineAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utilities.InternetUtilities;
import view.activity.MedicalAnalysisActivity;
import view.base.BaseActivity;

public class MedicalActivity extends BaseActivity {

    LinearLayout searchLinear;
    SearchView searchView;
    RecyclerView medicalRecyclerView;
    MedicalCategoryAdapter medicalCategoryAdapter;
    List<MedicalCategory> medicalCategories = new ArrayList<>();

    ProgressViewDialog progressViewDialog;

    public MedicalActivity() {
        super(R.layout.activity_medical, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        toolbarTextView.setText(R.string.medical_analysis);
        toolbarBackImageView.setVisibility(View.VISIBLE);

        callAPI();
        initializeComponents();
        setListeners();
    }

    private void initializeComponents() {

        searchView = findViewById(R.id.medical_searchView);
        searchLinear = findViewById(R.id.medical_searchLinear);

        medicalRecyclerView = findViewById(R.id.medical_recyclerView);
        medicalRecyclerView.setLayoutManager(new LinearLayoutManager(MedicalActivity.this));
        medicalRecyclerView.setHasFixedSize(true);

    }

    private void callAPI() {
        progressViewDialog = new ProgressViewDialog(MedicalActivity.this);
        progressViewDialog.isShowing();
        progressViewDialog.setDialogCancelable(false);
        progressViewDialog.setCanceledOnTouchOutside(false);
        progressViewDialog.showProgressDialog("Getting medicals information");

        HashMap<String, String> headers = ApiClient.getHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<MedicalCategory>> call = apiService.getMedicalCategory(headers);
        call.enqueue(new Callback<List<MedicalCategory>>() {
            @Override
            public void onResponse(@NotNull Call<List<MedicalCategory>> call,
                                   @NotNull Response<List<MedicalCategory>> response) {
                if (response.isSuccessful()) {
                    medicalCategories = response.body();

                    if (medicalCategories != null) {

                        medicalCategoryAdapter = new MedicalCategoryAdapter(MedicalActivity.this, medicalCategories,
                                position ->
                                {
                                    Intent intent = new Intent(MedicalActivity.this, MedicalAnalysisActivity.class);
                                    intent.putExtra("medicalAnalysis", (Serializable) medicalCategories.get(position).getMedicalAnalyses());
                                    intent.putExtra("categoryName", medicalCategories.get(position).getName());
                                    intent.putExtra("c_id", medicalCategories.get(position).getId());
                                    startActivity(intent);
                                });
                        medicalRecyclerView.setAdapter(medicalCategoryAdapter);
                    }
                    searchLinear.setVisibility(View.VISIBLE);
                    searchView.setVisibility(View.VISIBLE);
                    progressViewDialog.hideDialog();
                } else {
                    CustomToast.Companion.darkColor(MedicalActivity.this, CustomToastType.ERROR, "Error getting your appointments");
                    progressViewDialog.hideDialog();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<MedicalCategory>> call,
                                  @NotNull Throwable t) {
                CustomToast.Companion.darkColor(MedicalActivity.this, CustomToastType.ERROR, Objects.requireNonNull(t.getMessage()));
            }
        });

    }

    private void setListeners() {

        searchLinear.setOnClickListener(v -> {
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
