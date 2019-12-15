package view.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.s7k.doctroid.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dialog.ProgressViewDialog;
import network.api.ApiClient;
import network.api.ApiInterface;
import network.model.Category;
import network.model.Hospital;
import network.model.Medicine;
import network.operation.OperationsManager;
import okhttp3.ResponseBody;
import presenter.adapter.MainAdapter;
import presenter.adapter.MedicineAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utilities.PrefManager;
import view.base.BaseActivity;
import view.category.AppointmentActivity;
import view.category.EmergencyActivity;
import view.category.HospitalActivity;
import view.category.MedicalActivity;
import view.category.MedicineActivity;
import view.category.ResultActivity;
import view.fragment.DatePickerFragment;

public class AddMedicineActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    Button signUp, date;
    MaterialSpinner citySpinner;
    ProgressViewDialog progressViewDialog;
    EditText snn;
    AutoCompleteTextView medicineAutoComplete;
    ImageView addImageView;

    public RecyclerView recyclerView;
    public MedicineAdapter medicineAdapter;
    public List<Medicine> medicineArrayList = new ArrayList<>();
    public List<Medicine> medicinesAPI = new ArrayList<>();

    String citySTR = "Empty", dateSTR = "Empty";

    public AddMedicineActivity() {
        super(R.layout.activity_add_medicine, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        toolbarTextView.setText("Confirm Information");

        initializeComponents();
        setListeners();
    }

    private void initializeComponents() {

        date = findViewById(R.id.addMedicine_date_button);
        citySpinner = findViewById(R.id.addMedicine_citySpinner);
        signUp = findViewById(R.id.addMedicine_SignUp_button);
        snn = findViewById(R.id.addMedicine_SNN_ET);
        medicineAutoComplete = findViewById(R.id.addMedicine_medicineAutoComplete);
        addImageView = findViewById(R.id.addMedicine_addMedicine);

        recyclerView = findViewById(R.id.addMedicine_medicineRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(AddMedicineActivity.this, 2));
        recyclerView.setHasFixedSize(true);

        medicineAdapter = new MedicineAdapter(medicineArrayList, AddMedicineActivity.this,
                position -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(medicineArrayList.get(position).toString());
                    builder.setMessage("Are you sure to delete this medicine?");

                    builder.setPositiveButton("Yes",
                            (dialogInterface, i) -> medicineAdapter.deleteItem(position));

                    builder.setNegativeButton("No",
                            (dialogInterface, i) -> {
                            });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                });
        recyclerView.setAdapter(medicineAdapter);

        citySpinner.setItems("Select City",
                "6th of October City",
                "10th of Ramadan",
                "Al-Mansura",
                "Al-Minya",
                "Alexandria",
                "Arish",
                "Aswan",
                "Asyut",
                "Banha",
                "Beni Suef",
                "Cairo",
                "Dakahlia",
                "Damanhur",
                "Damietta",
                "Fayyum",
                "Giza",
                "Hurghada",
                "Ismailia",
                "Kafr El-Sheikh",
                "Luxor",
                "Marsa Matruh",
                "Monufia",
                "Port Said",
                "Qalyub",
                "Qena",
                "Sohag",
                "Suez",
                "Tanta",
                "Zagazig");

        getMedicines();
    }

    private void setListeners() {

        date.setOnClickListener(View -> {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "Date Picker");
        });

        citySpinner.setOnItemSelectedListener((MaterialSpinner.OnItemSelectedListener<String>) (view, position, id, item) ->
                citySTR = item);

        addImageView.setOnClickListener(v -> {

            if (!medicineAutoComplete.getText().toString().trim().isEmpty()) {

                Medicine medicine = new Medicine(medicineAutoComplete.getText().toString().trim());

                for (Medicine med : medicinesAPI) {

                    if (medicine.getName().equals(med.getName())) {

                        if (medicineArrayList.size() != 0) {
                            for (Medicine med2 : medicineArrayList)
                                if (medicine.getName().equals(med2.getName())) {
                                    Toast.makeText(AddMedicineActivity.this, "This medicine already selected", Toast.LENGTH_SHORT).show();
                                } else {
                                    medicineAdapter.addItem(medicine);
                                    medicineAutoComplete.setText("");
                                }

                        }
                        else
                        {
                            medicineAdapter.addItem(medicine);
                            medicineAutoComplete.setText("");
                        }
                    }
                }

            }
        });

        signUp.setOnClickListener(View -> {

            String snnSTR = snn.getText().toString().toLowerCase().trim();

            if (dateSTR.equals("Empty")) {
                Toast.makeText(this, "Please select your Birthday", Toast.LENGTH_SHORT).show();
            } else if (citySTR.equals("Empty")) {
                Toast.makeText(this, "Please select your City", Toast.LENGTH_SHORT).show();
            } else if (snnSTR.isEmpty()) {
                Toast.makeText(this, "Please Enter your SNN", Toast.LENGTH_SHORT).show();
            } else if (true) {
                Toast.makeText(this, "Please add your Medicine", Toast.LENGTH_SHORT).show();
            } else {
                // TODO: Send shit to API
            }
        });


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        dateSTR = dayOfMonth + "-" + month + "-" + year;
        date.setText("Date: " + dateSTR);

    }

    private void getMedicines() {
        progressViewDialog = new ProgressViewDialog(this);
        progressViewDialog.isShowing();
        progressViewDialog.setDialogCancelable(false);
        progressViewDialog.setCanceledOnTouchOutside(false);
        progressViewDialog.showProgressDialog("Getting medicines information");

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

                        ArrayAdapter<Medicine> adapter = new ArrayAdapter<Medicine>(AddMedicineActivity.this,
                                android.R.layout.simple_list_item_1, medicinesAPI);
                        medicineAutoComplete.setAdapter(adapter);
                    }
                    progressViewDialog.hideDialog();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Medicine>> call,
                                  @NonNull Throwable t) {
                Toast.makeText(AddMedicineActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}
