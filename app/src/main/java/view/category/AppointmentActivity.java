package view.category;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.s7k.doctroid.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import app.App;
import customView.CustomToast;
import customView.CustomToastType;
import dialog.ErrorDialog;
import dialog.PopupDialog;
import dialog.ProgressViewDialog;
import es.dmoral.toasty.Toasty;
import network.api.ApiClient;
import network.api.ApiInterface;
import network.model.Appoint;
import network.model.DeleteRequestForm;
import network.model.PatientID;
import network.model.Precautions;
import network.model.RequestIDs;
import network.model.UsersRequests;
import okhttp3.ResponseBody;
import presenter.adapter.AppointAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utilities.InternetUtilities;
import utilities.PrefManager;
import view.activity.SignInActivity;
import view.base.BaseActivity;
import www.sanju.motiontoast.MotionToast;

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
        toolbarTextView.setText(R.string.appointment);
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
                    CustomToast.Companion.darkColor(AppointmentActivity.this, CustomToastType.ERROR, "Error getting your appointments");
                    progressViewDialog.hideDialog();
                }

            }

            @Override
            public void onFailure(@NotNull Call<RequestIDs> call,
                                  @NotNull Throwable t) {
                CustomToast.Companion.darkColor(AppointmentActivity.this, CustomToastType.ERROR, Objects.requireNonNull(t.getMessage()));
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

                assert response.body() != null;
                if (response.isSuccessful()) {
                    for (UsersRequests req : Objects.requireNonNull(response.body())) {
                        for (String id : ids) {
                            if (id.equals(req.getId())) {
                                getPrecaustion(req);
                            }
                        }

                        appointAdapter = new AppointAdapter(AppointmentActivity.this, appoints, position -> {

                            AlertDialog.Builder builder = new AlertDialog.Builder(AppointmentActivity.this);
                            builder.setTitle("Are you sure to delete this request?");
                            builder.setMessage("This can't be undone.");

                            builder.setPositiveButton("Yes",
                                    (dialogInterface, i) -> {

                                        String p_id = PrefManager.getP_id(AppointmentActivity.this);
                                        DeleteRequestForm deleteRequestForm = new DeleteRequestForm(p_id, appoints.get(position).getId());
                                        deleteRequest(deleteRequestForm, appoints.get(position));

                                    });

                            builder.setNegativeButton("No",
                                    (dialogInterface, i) -> {

                                    });

                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        });
                        appointRecyclerView.setAdapter(appointAdapter);
                        searchView.setVisibility(View.VISIBLE);
                        searchLinear.setVisibility(View.VISIBLE);
                        progressViewDialog.hideDialog();
                    }
                } else {
                    CustomToast.Companion.darkColor(AppointmentActivity.this, CustomToastType.ERROR, "Error getting your data!");
                    progressViewDialog.hideDialog();
                }

            }

            @Override
            public void onFailure(@NotNull Call<List<UsersRequests>> call,
                                  @NotNull Throwable t) {
                CustomToast.Companion.darkColor(AppointmentActivity.this, CustomToastType.ERROR, Objects.requireNonNull(t.getMessage()));

            }
        });
    }

    private void getPrecaustion(UsersRequests req) {

        HashMap<String, String> headers = ApiClient.getHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Map<String, String> body = new HashMap<>();
        body.put("t_id", req.getT_id());


        Call<Precautions> call = apiService.getPrecautions(headers, body, req.getC_id());
        call.enqueue(new Callback<Precautions>() {
            @Override
            public void onResponse(@NotNull Call<Precautions> call,
                                   @NotNull Response<Precautions> response) {
                if (response.isSuccessful()) {
                    Precautions precautions = response.body();
                    assert precautions != null;
                    appoints.add(new Appoint(req.getId(), req.getTitle(),
                            req.getComment(), req.getDate(), req.getTime(), req.getStatus(), req.getNotes(), precautions.getEnglish(), precautions.getArabic()));
                    appointAdapter.notifyDataSetChanged();
                } else {
                    CustomToast.Companion.darkColor(AppointmentActivity.this, CustomToastType.ERROR, "Error getting Precautions");
                }
            }

            @Override
            public void onFailure(@NotNull Call<Precautions> call,
                                  @NotNull Throwable t) {
                CustomToast.Companion.darkColor(AppointmentActivity.this, CustomToastType.ERROR, Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    private void deleteRequest(DeleteRequestForm deleteRequestForm, Appoint appoint) {

        ProgressViewDialog p2 = new ProgressViewDialog(AppointmentActivity.this);
        p2.isShowing();
        p2.setDialogCancelable(false);
        p2.setCanceledOnTouchOutside(false);
        p2.showProgressDialog("Deleting Request");

        HashMap<String, String> headers = ApiClient.getHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.deleteRequest(headers, deleteRequestForm);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call,
                                   @NotNull Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    appoints.remove(appoint);
                    appointAdapter.notifyDataSetChanged();
                    if (appoints.isEmpty()) {
                        emptyAppoint.setVisibility(View.VISIBLE);
                    }
                    p2.hideDialog();
                } else {
                    CustomToast.Companion.darkColor(AppointmentActivity.this, CustomToastType.ERROR, "Unable to delete this!");
                    p2.hideDialog();
                }

            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call,
                                  @NotNull Throwable t) {
                CustomToast.Companion.darkColor(AppointmentActivity.this, CustomToastType.ERROR, Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    private void setListeners() {

        swipeRefresh.setOnRefreshListener(() -> {
            if (!appoints.isEmpty()) {
                appoints.clear();
                appointAdapter.notifyDataSetChanged();
            }
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