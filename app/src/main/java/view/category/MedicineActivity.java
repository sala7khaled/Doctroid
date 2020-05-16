package view.category;

import android.app.ActionBar;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.s7k.doctroid.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import customView.CustomToast;
import customView.CustomToastType;
import dialog.ProgressViewDialog;
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
import utilities.Utilities;
import view.base.BaseActivity;

public class MedicineActivity extends BaseActivity {

    LinearLayout userLinear, searchLinear;
    Dialog customDialog;
    SearchView searchView;
    ImageView userPhoto, medicineImage;
    ImageView quantityIcon;
    TextView name, quantity, price, unit, add, remove;
    Button preOrderButton;
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
        toolbarTextView.setText(R.string.Medicine);
        toolbarBackImageView.setVisibility(View.VISIBLE);

        initializeComponents();
        callAPI();
        setListeners();
    }


    private void initializeComponents() {

        searchView = findViewById(R.id.medicine_searchView);
        searchLinear = findViewById(R.id.medicine_searchLinear);
        userLinear = findViewById(R.id.medicine_userLinear);

        medicineRecyclerView = findViewById(R.id.medicine_recyclerView);
        medicineRecyclerView.setLayoutManager(new GridLayoutManager(MedicineActivity.this, 2));
        medicineRecyclerView.setHasFixedSize(true);

        customDialog = new Dialog(MedicineActivity.this);
        customDialog.setContentView(R.layout.item_medicine_dialog);
        customDialog.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        customDialog.setCancelable(true);
        Objects.requireNonNull(customDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        userPhoto = customDialog.findViewById(R.id.item_medicineDialog_userPhoto);

        name = customDialog.findViewById(R.id.item_medicineDialog_name);
        quantityIcon = customDialog.findViewById(R.id.item_medicineDialog_quantityIcon);
        quantity = customDialog.findViewById(R.id.item_medicineDialog_quantity);
        price = customDialog.findViewById(R.id.item_medicineDialog_price);
        preOrderButton = customDialog.findViewById(R.id.item_medicineDialog_preOrder_button);
        unit = customDialog.findViewById(R.id.item_medicineDialog_unit);
        add = customDialog.findViewById(R.id.item_medicineDialog_add);
        remove = customDialog.findViewById(R.id.item_medicineDialog_remove);
        medicineImage = customDialog.findViewById(R.id.item_medicineDialog_photo);


    }

    private void callAPI() {

        progressViewDialog = new ProgressViewDialog(MedicineActivity.this);
        progressViewDialog.isShowing();
        progressViewDialog.setDialogCancelable(false);
        progressViewDialog.setCanceledOnTouchOutside(false);
        progressViewDialog.showProgressDialog("Getting medicines information");

        if (PrefManager.getP_id(MedicineActivity.this) != null) {

            PatientID patientID = new PatientID(PrefManager.getP_id(MedicineActivity.this));

            HashMap<String, String> headers = ApiClient.getHeaders();
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<UserProfile> call = apiService.getUser(headers, patientID);
            call.enqueue(new Callback<UserProfile>() {
                @Override
                public void onResponse(@NonNull Call<UserProfile> call,
                                       @NonNull Response<UserProfile> response) {

                    if (response.isSuccessful()) {

                        UserProfile user = response.body();

                        if (user != null) {
                            medicinesUser = new String[user.getMedicines().length];
                            medicinesUser = user.getMedicines();
                            getMedicines();
                            userLinear.setVisibility(View.VISIBLE);
                            searchLinear.setVisibility(View.VISIBLE);
                            searchView.setVisibility(View.VISIBLE);
                            progressViewDialog.hideDialog();
                        }
                    } else {
                        CustomToast.Companion.darkColor(MedicineActivity.this, CustomToastType.ERROR, "Error getting your appointments");
                        progressViewDialog.hideDialog();
                    }

                }

                @Override
                public void onFailure(@NonNull Call<UserProfile> call,
                                      @NonNull Throwable t) {
                    CustomToast.Companion.darkColor(MedicineActivity.this, CustomToastType.ERROR, Objects.requireNonNull(t.getMessage()));
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
                                            remove.setVisibility(View.VISIBLE);
                                            add.setVisibility(View.GONE);
                                        } else {
                                            userPhoto.setVisibility(View.GONE);
                                            remove.setVisibility(View.GONE);
                                            add.setVisibility(View.VISIBLE);
                                        }
                                    }
                                    setUpDialog(position);
                                    customDialog.show();
                                }
                                , MedicineType.LIST);

                        medicineRecyclerView.setAdapter(medicineAdapter);
                    } else {
                        CustomToast.Companion.darkColor(MedicineActivity.this, CustomToastType.WARNING, "There's no medicines.");
                    }
                }
            }



            @Override
            public void onFailure(@NonNull Call<List<Medicine>> call,
                                  @NonNull Throwable t) {
                CustomToast.Companion.darkColor(MedicineActivity.this, CustomToastType.ERROR, Objects.requireNonNull(t.getMessage()));
            }
        });
    }
    private void setUpDialog(int position) {

        add.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MedicineActivity.this);
            builder.setTitle(medicinesAPI.get(position).getName());
            builder.setMessage("Are you sure to add ''" + medicinesAPI.get(position).getName()+"'' to your taken medicines?");

            builder.setPositiveButton("Yes",
                    (dialogInterface, i) -> {

                    });

            builder.setNegativeButton("No",
                    (dialogInterface, i) -> {

                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        remove.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MedicineActivity.this);
            builder.setTitle(medicinesAPI.get(position).getName());
            builder.setMessage("Are you sure to remove ''" + medicinesAPI.get(position).getName()+"'' from your taken medicines?");

            builder.setPositiveButton("Yes",
                    (dialogInterface, i) -> {

                    });

            builder.setNegativeButton("No",
                    (dialogInterface, i) -> {

                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
        name.setText(medicinesAPI.get(position).getName());

        if (Integer.parseInt(medicinesAPI.get(position).getQuantity()) == 0) {
            quantity.setTextColor(getResources().getColor(R.color.colorGray));
            quantityIcon.setImageDrawable(getResources().getDrawable(R.drawable.icon_medicine_quantity_gray));
            quantity.setText("Quantity: " + medicinesAPI.get(position).getQuantity()+ " (Out of stock)");
        } else if (Integer.parseInt(medicinesAPI.get(position).getQuantity()) < 10) {
            quantity.setTextColor(getResources().getColor(R.color.colorRed));
            quantityIcon.setImageDrawable(getResources().getDrawable(R.drawable.icon_medicine_quantity_red));
            quantity.setText("Quantity: " + medicinesAPI.get(position).getQuantity());

        } else {
            quantity.setTextColor(getResources().getColor(R.color.colorGreen));
            quantityIcon.setImageDrawable(getResources().getDrawable(R.drawable.icon_medicine_quantity));
            quantity.setText("Quantity: " + medicinesAPI.get(position).getQuantity());
        }
        price.setText("Price: " + medicinesAPI.get(position).getPrice() + " LE");
        unit.setText(medicinesAPI.get(position).getUnit());

        Picasso.get()
                .load(medicinesAPI.get(position).getImage())
                .fit()
                .error(R.drawable.icon_no_connection)
                .into(medicineImage);

        preOrderButton.setOnClickListener(v -> {

            //TODO Pre-Order API

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
                medicineAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }

}