package view.category;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.s7k.doctroid.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import dialog.ProgressViewDialog;
import es.dmoral.toasty.Toasty;
import network.api.ApiClient;
import network.api.ApiInterface;
import network.model.Medicine;
import network.model.PatientID;
import network.model.UserProfile;
import presenter.adapter.MedicineAdapter;
import presenter.adapter.MedicineType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utilities.PrefManager;
import view.activity.MainActivity;
import view.base.BaseActivity;

import static es.dmoral.toasty.Toasty.LENGTH_LONG;

public class MedicineActivity extends BaseActivity {

    Dialog customDialog;
    SearchView searchView;
    ImageView medicinePhoto, userPhoto;
    TextView name, quantity, price, description;
    Button button;
    MedicineAdapter medicineAdapter;
    RecyclerView medicineRecyclerView;
    ProgressViewDialog progressViewDialog;
    List<Medicine> medicinesAPI = new ArrayList<>();
    String[] medicinesUser;

    public MedicineActivity() {
        super(R.layout.activity_medicine, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        toolbarTextView.setText("Medicine");
        toolbarBackImageView.setVisibility(View.VISIBLE);

        initializeComponents();

        getUserMedicines();

        setListeners();

    }


    private void initializeComponents() {

        searchView = findViewById(R.id.medicine_searchView);

        medicineRecyclerView = findViewById(R.id.medicine_recyclerView);
        medicineRecyclerView.setLayoutManager(new GridLayoutManager(MedicineActivity.this, 2));
        medicineRecyclerView.setHasFixedSize(true);

        customDialog = new Dialog(MedicineActivity.this);
        customDialog.setContentView(R.layout.item_medicine_dialog);
        customDialog.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        customDialog.setCancelable(true);
        Objects.requireNonNull(customDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        medicinePhoto = customDialog.findViewById(R.id.item_medicineDialog_photo);
        userPhoto = customDialog.findViewById(R.id.item_medicineDialog_userPhoto);

        name = customDialog.findViewById(R.id.item_medicineDialog_name);
        quantity = customDialog.findViewById(R.id.item_medicineDialog_quantity);
        price = customDialog.findViewById(R.id.item_medicineDialog_price);
        description = customDialog.findViewById(R.id.item_medicineDialog_description);
        button = customDialog.findViewById(R.id.item_medicineDialog_button);


    }

    private void getUserMedicines() {

        progressViewDialog = new ProgressViewDialog(MedicineActivity.this);
        progressViewDialog.isShowing();
        progressViewDialog.setDialogCancelable(false);
        progressViewDialog.setCanceledOnTouchOutside(false);
        progressViewDialog.showProgressDialog("Getting medicines information");

        if (PrefManager.getP_id(MedicineActivity.this) != null) {

            PatientID patientID = new PatientID(PrefManager.getP_id(MedicineActivity.this));

            HashMap<String, String> headers = ApiClient.getHeaders();
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<UserProfile>> call = apiService.getUser(headers, patientID);
            call.enqueue(new Callback<List<UserProfile>>() {
                @Override
                public void onResponse(@NonNull Call<List<UserProfile>> call,
                                       @NonNull Response<List<UserProfile>> response) {

                    if (response.isSuccessful()) {

                        List<UserProfile> users = response.body();

                        if (users != null) {
                            for (UserProfile user : users) {
                                medicinesUser = new String[user.getMedicines().length];
                                medicinesUser = user.getMedicines();
                                getMedicines();
                                progressViewDialog.hideDialog();
                            }
                        }
                    }

                }

                @Override
                public void onFailure(@NonNull Call<List<UserProfile>> call,
                                      @NonNull Throwable t) {
                    Toasty.error(MedicineActivity.this, t.getMessage(), LENGTH_LONG).show();
                }
            });
        }
    }

    private void getMedicines() {

        HashMap<String, String> headers = ApiClient.getHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Medicine>> call = apiService.getMedicine(headers);
        call.enqueue(new Callback<List<Medicine>>() {
            @Override
            public void onResponse(@NonNull Call<List<Medicine>> call,
                                   @NonNull Response<List<Medicine>> response) {

                if (response.isSuccessful()) {

                    medicinesAPI = response.body();

                    if (medicinesAPI != null) {
                        medicineAdapter = new MedicineAdapter(medicinesAPI, medicinesUser, MedicineActivity.this,
                                position ->
                                {
                                    for (String s : medicinesUser) {
                                        if (s.equals(medicinesAPI.get(position).getId())) {
                                            userPhoto.setVisibility(View.VISIBLE);
                                        } else {
                                            userPhoto.setVisibility(View.GONE);
                                        }
                                    }
                                    name.setText(medicinesAPI.get(position).getName());
                                    quantity.setText("Quantity: " + medicinesAPI.get(position).getQuantity());
                                    price.setText("Price: " + medicinesAPI.get(position).getPrice() + " LE");
                                    description.setText("Description: " + medicinesAPI.get(position).getDescription());

                                    customDialog.show();
                                }
                                , MedicineType.LIST);

                        medicineRecyclerView.setAdapter(medicineAdapter);
                    } else {
                        Toasty.error(MedicineActivity.this, "There's no medicines").show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Medicine>> call,
                                  @NonNull Throwable t) {
                Toasty.error(MedicineActivity.this, t.getMessage(), LENGTH_LONG).show();
            }
        });
    }

    private void setListeners() {
        button.setOnClickListener(v -> {
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                medicineAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}
