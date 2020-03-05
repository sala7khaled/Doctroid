package view.category;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.s7k.doctroid.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import dialog.ProgressViewDialog;
import es.dmoral.toasty.Toasty;
import network.api.ApiClient;
import network.api.ApiInterface;
import network.model.Appoint;
import network.model.PatientID;
import network.model.RequestIDs;
import network.model.UsersRequests;
import presenter.adapter.AppointAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utilities.PrefManager;
import view.base.BaseActivity;

public class AppointmentActivity extends BaseActivity {

    SwipeRefreshLayout swipeRefresh;

    TextView emptyAppoint;
    LinearLayout searchLinear;
    SearchView searchView;
    RecyclerView appointRecyclerView;
    AppointAdapter appointAdapter;
    List<Appoint> appoints = new ArrayList<>();

    ProgressViewDialog progressViewDialog;

    public AppointmentActivity() {
        super(R.layout.activity_appointment, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        toolbarTextView.setText("Appointments");
        toolbarBackImageView.setVisibility(View.VISIBLE);
        emptyAppoint = findViewById(R.id.appoint_emptyAppoint);
        emptyAppoint.setVisibility(View.GONE);

        callAPI();
        initializeComponents();
        setListeners();

    }

    private void initializeComponents() {

        swipeRefresh = findViewById(R.id.appoint_SwipeRefresh);

        searchView = findViewById(R.id.appoint_searchView);
        searchLinear = findViewById(R.id.appoint_searchLinear);
        searchView.setVisibility(View.GONE);
        searchLinear.setVisibility(View.GONE);

        appointRecyclerView = findViewById(R.id.appoint_recyclerView);
        appointRecyclerView.setLayoutManager(new LinearLayoutManager(AppointmentActivity.this));
        appointRecyclerView.setHasFixedSize(true);

    }

    private void callAPI() {

        progressViewDialog = new ProgressViewDialog(AppointmentActivity.this);
        progressViewDialog.isShowing();
        progressViewDialog.setDialogCancelable(false);
        progressViewDialog.setCanceledOnTouchOutside(false);
        progressViewDialog.showProgressDialog("Getting Appointments information");

        PatientID p_id = new PatientID(PrefManager.getP_id(AppointmentActivity.this));

        HashMap<String, String> headers = ApiClient.getHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<RequestIDs> call = apiService.getPatientRequests(headers, p_id);
        call.enqueue(new Callback<RequestIDs>() {
            @Override
            public void onResponse(@NotNull Call<RequestIDs> call,
                                   @NotNull Response<RequestIDs> response) {

                if (response.isSuccessful()) {
                    RequestIDs requestIDs = response.body();
                    String[] ids = Objects.requireNonNull(requestIDs).getRequests();
                    if (ids.length != 0) {
                        compareRequests(ids);
                    } else {
                        emptyAppoint.setVisibility(View.VISIBLE);
                        progressViewDialog.hideDialog();
                    }
                } else {
                    Toasty.error(AppointmentActivity.this, "Error getting user appointments").show();
                }

            }

            @Override
            public void onFailure(@NotNull Call<RequestIDs> call,
                                  @NotNull Throwable t) {

            }
        });

    }

    private void compareRequests(String[] ids) {

        HashMap<String, String> headers = ApiClient.getHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<UsersRequests>> call = apiService.getRequests(headers);
        call.enqueue(new Callback<List<UsersRequests>>() {
            @Override
            public void onResponse(@NotNull Call<List<UsersRequests>> call,
                                   @NotNull Response<List<UsersRequests>> response) {

                if (response.isSuccessful()) {
                    for (UsersRequests req : Objects.requireNonNull(response.body())) {
                        for (String id : ids) {
                            if (id.equals(req.getId())) {
                                String[] ans = req.getAnswers();
                                appoints.add(new Appoint(req.getTitle(), ans[0], ans[1],
                                        ans[2], req.getDate(), req.getTime(), req.getStatus()));
                            }
                        }

                        appointAdapter = new AppointAdapter(AppointmentActivity.this, appoints, position -> {
                            Toasty.info(AppointmentActivity.this, "clicked " + position).show();
                        });
                        appointRecyclerView.setAdapter(appointAdapter);
                        searchView.setVisibility(View.VISIBLE);
                        searchLinear.setVisibility(View.VISIBLE);
                        progressViewDialog.hideDialog();
                    }
                } else {
                    Toasty.error(AppointmentActivity.this, "Error getting data").show();
                }

            }

            @Override
            public void onFailure(@NotNull Call<List<UsersRequests>> call,
                                  @NotNull Throwable t) {
                Toasty.error(AppointmentActivity.this, t.getMessage()).show();

            }
        });
    }

    private void setListeners() {

        swipeRefresh.setOnRefreshListener(() -> {
            appoints.clear();
            appointAdapter.notifyDataSetChanged();

            callAPI();
            swipeRefresh.setRefreshing(false);
        });

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
                appointAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}