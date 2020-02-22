package view.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.s7k.doctroid.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dialog.ProgressViewDialog;
import es.dmoral.toasty.Toasty;
import network.api.ApiClient;
import network.api.ApiInterface;
import network.model.ConfirmSignUpForm;
import network.model.Medicine;
import okhttp3.ResponseBody;
import presenter.adapter.MedicineAdapter;
import presenter.adapter.MedicineType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utilities.PrefManager;
import view.base.BaseActivity;
import view.fragment.DatePickerFragment;

import static es.dmoral.toasty.Toasty.LENGTH_LONG;

public class AddMedicineActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    Button signUp, date;
    MaterialSpinner citySpinner;
    ProgressViewDialog progressViewDialog;
    EditText snn;
    AutoCompleteTextView medicineAutoComplete;
    CardView medicineCardView;

    ImageView errorDialog;
    TextView errorMessage;

    public RecyclerView recyclerView;
    public MedicineAdapter medicineAdapter;
    public List<Medicine> medicineArrayList = new ArrayList<>();
    public List<Medicine> medicinesAPI = new ArrayList<>();

    String citySTR = "Empty", dateSTR = "Empty";

    @Override
    public void onBackPressed() {

        Toast.makeText(this, "Please confirm your information", Toast.LENGTH_SHORT).show();
    }

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
        medicineCardView = findViewById(R.id.addMedicine_medicineCardView);

        errorDialog = findViewById(R.id.addMedicine_errorDialog_imageView);
        errorMessage = findViewById(R.id.addMedicine_errorMessage_textView);

        recyclerView = findViewById(R.id.addMedicine_medicineRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(AddMedicineActivity.this, 2));
        recyclerView.setHasFixedSize(true);

        medicineAdapter = new MedicineAdapter(medicineArrayList,null, AddMedicineActivity.this,
                position -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(medicineArrayList.get(position).toString());
                    builder.setMessage("Are you sure to delete this medicine?");

                    builder.setPositiveButton("Yes",
                            (dialogInterface, i) ->
                            {
                                medicineAdapter.deleteItem(position);
                                if (medicineAdapter.getItemCount() == 0) {
                                    medicineCardView.setVisibility(View.INVISIBLE);
                                }
                            });

                    builder.setNegativeButton("No",
                            (dialogInterface, i) -> {
                            });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }, MedicineType.AUTO_COMPLETE);
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
            DialogFragment datePicker = new DatePickerFragment("birthday");
            datePicker.show(getSupportFragmentManager(), "Date Picker");
        });

        citySpinner.setOnItemSelectedListener((MaterialSpinner.OnItemSelectedListener<String>) (view, position, id, item) ->
                citySTR = item);

        medicineAutoComplete.setOnItemClickListener((parent, view, position, id) -> {
            Medicine medicine = (Medicine) medicineAutoComplete.getAdapter().getItem(position);
            if (medicineAdapter.checkExistMedicine(medicine)) {
                Toast.makeText(AddMedicineActivity.this, "This medicine already selected", Toast.LENGTH_SHORT).show();
            } else {
                medicineCardView.setVisibility(View.VISIBLE);
                medicineAdapter.addItem(medicine);
            }
            medicineAutoComplete.setText("");
        });

        signUp.setOnClickListener(View -> {

            String snnSTR = snn.getText().toString().toLowerCase().trim();

            if (dateSTR.equals("Empty")) {
                errorDialog.setVisibility(android.view.View.VISIBLE);
                errorMessage.setText("Please select your Birthday");
            } else if (citySTR.equals("Empty") || citySTR.equals("Select City")) {
                errorDialog.setVisibility(android.view.View.VISIBLE);
                errorMessage.setText("Please select your City");
            } else if (snnSTR.isEmpty()) {
                errorDialog.setVisibility(android.view.View.VISIBLE);
                errorMessage.setText("Please Enter your SNN");
            } else if (medicineAdapter.getItemCount() == 0) {
                errorDialog.setVisibility(android.view.View.VISIBLE);
                errorMessage.setText("Please add your Medicines");
            } else {
                errorDialog.setVisibility(android.view.View.INVISIBLE);
                errorMessage.setVisibility(android.view.View.INVISIBLE);

                signUpAPI(dateSTR, citySTR, snnSTR);

            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        dateSTR = dayOfMonth + "/" + ++month + "/" + year;
        date.setText(String.format("Date: %s", dateSTR));

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

                        ArrayAdapter<Medicine> adapter = new ArrayAdapter<>(AddMedicineActivity.this,
                                android.R.layout.simple_list_item_1, medicinesAPI);
                        medicineAutoComplete.setAdapter(adapter);
                    }
                    progressViewDialog.hideDialog();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Medicine>> call,
                                  @NonNull Throwable t) {
                Toasty.error(AddMedicineActivity.this, t.getMessage(), LENGTH_LONG).show();
            }
        });
    }

    private void signUpAPI(String dateSTR, String citySTR, String snnSTR) {

        progressViewDialog = new ProgressViewDialog(this);
        progressViewDialog.isShowing();
        progressViewDialog.setDialogCancelable(false);
        progressViewDialog.setCanceledOnTouchOutside(false);
        progressViewDialog.showProgressDialog("Finishing sign up");

        String[] medicineIDs = new String[medicineArrayList.size()];
        for (int i = 0; i < medicineArrayList.size(); i++) {
            medicineIDs[i] = medicineArrayList.get(i).getId();
            Log.v("ITEM: " + i, "ID:" + medicineIDs[i]);
        }

        String p_id = PrefManager.getP_id(AddMedicineActivity.this);

        ConfirmSignUpForm confirmSignUpForm = new ConfirmSignUpForm(p_id, citySTR, dateSTR, "true", snnSTR, medicineIDs);

        HashMap<String, String> headers = ApiClient.getHeaders();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.signUpConfirm(headers, confirmSignUpForm).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call,
                                   @NonNull Response<ResponseBody> response) {

                if (response.isSuccessful())
                {
                    PrefManager.saveConfirm(AddMedicineActivity.this, "true");
                    startActivity(new Intent(AddMedicineActivity.this, MainActivity.class));
                    progressViewDialog.hideDialog();
                    Toasty.success(AddMedicineActivity.this, "Account Created", LENGTH_LONG).show();
                    finish();
                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call,
                                  @NonNull Throwable t) {
                Toasty.error(AddMedicineActivity.this, t.getMessage(), LENGTH_LONG).show();

            }
        });

    }

}
