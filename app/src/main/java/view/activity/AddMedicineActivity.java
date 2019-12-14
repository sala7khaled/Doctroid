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
    public ArrayList<Medicine> medicineArrayList;

    String citySTR = "Empty", dateSTR = "Empty";

    public AddMedicineActivity() {
        super(R.layout.activity_add_medicine, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        toolbarTextView.setText("Add Medicine");
        toolbarTextView.setTextSize(20);
        toolbarBackImageView.setVisibility(View.VISIBLE);

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
        medicineArrayList = new ArrayList<Medicine>();
        recyclerView.setLayoutManager(new GridLayoutManager(AddMedicineActivity.this, 2));
        recyclerView.setHasFixedSize(true);

        medicineAdapter = new MedicineAdapter(medicineArrayList, AddMedicineActivity.this,
                position -> medicineAdapter.deleteItem(position));
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

        addImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!medicineAutoComplete.getText().toString().trim().isEmpty())
                {
                    Medicine medicine = new Medicine(medicineAutoComplete.getText().toString().trim());
                    medicineAdapter.addItem(medicine);
                    medicineAutoComplete.setText("");
                }
            }
        });

        medicineAutoComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddMedicineActivity.this, medicineAutoComplete.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        signUp.setOnClickListener(View -> {

            String snnSTR = snn.getText().toString().toLowerCase().trim();

            if (snnSTR.isEmpty()) {
                Toast.makeText(this, "Please Enter your SNN", Toast.LENGTH_SHORT).show();
            } else if (dateSTR.equals("Empty")) {
                Toast.makeText(this, "Please select your Birthday", Toast.LENGTH_SHORT).show();
            } else if (citySTR.equals("Empty")) {
                Toast.makeText(this, "Please select your City", Toast.LENGTH_SHORT).show();
            } else if (true) {
                Toast.makeText(this, "Please add your Medicine", Toast.LENGTH_SHORT).show();
            }
            if (!dateSTR.equals("Empty") && !citySTR.equals("Empty")) {
                Toast.makeText(this, dateSTR + " " + citySTR, Toast.LENGTH_SHORT).show();

                Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show();
//                dateSTR ="xxxx";
//                citySTR = "zzzzzz";
//                boolean confirm = true;
//                callAPI(dateSTR, citySTR, confirm);
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

                    List<Medicine> medicines = response.body();
                    if (medicines != null) {

                        ArrayAdapter<Medicine> adapter = new ArrayAdapter<Medicine>(AddMedicineActivity.this,
                                android.R.layout.simple_list_item_1, medicines);
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
