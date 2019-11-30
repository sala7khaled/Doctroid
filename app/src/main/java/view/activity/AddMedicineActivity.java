package view.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.s7k.doctroid.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;

import network.api.ApiClient;
import network.api.ApiInterface;
import network.model.User;
import network.operation.OperationsManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import view.base.BaseActivity;
import view.fragment.DatePickerFragment;

public class AddMedicineActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    TextView errorMessage;
    ImageView errorDialog;
    Button signUp, date;
    MaterialSpinner spinner;
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
        spinner = findViewById(R.id.addMedicine_spinner);
        signUp = findViewById(R.id.addMedicine_SignUp_button);
        errorMessage = findViewById(R.id.addMedicine_errorMessage_textView);
        errorDialog = findViewById(R.id.addMedicine_errorDialog_imageView);

        spinner.setItems("Select City",
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

        signUp.setOnClickListener(View -> {

            if(dateSTR.equals("Empty"))
            {
                errorDialog.setVisibility(android.view.View.VISIBLE);
                errorMessage.setText("Please select your Birthday");
            }
            else if(citySTR.equals("Empty"))
            {
                errorDialog.setVisibility(android.view.View.VISIBLE);
                errorMessage.setText("Please select your City");
            }
            if(!dateSTR.equals("Empty") && !citySTR.equals("Empty"))
            {
                Toast.makeText(this, dateSTR + " " + citySTR, Toast.LENGTH_SHORT).show();

                dateSTR ="xxxx";
                citySTR = "zzzzzz";
                boolean confirm = true;
                callAPI(dateSTR, citySTR, confirm);
                errorDialog.setVisibility(android.view.View.INVISIBLE);
                errorMessage.setVisibility(android.view.View.INVISIBLE);
            }
        });
    }

    private void setListeners() {

        date.setOnClickListener(View -> {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "Date Picker");
        });

        spinner.setOnItemSelectedListener((MaterialSpinner.OnItemSelectedListener<String>) (view, position, id, item) ->
                citySTR = item);


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        dateSTR = dayOfMonth + "-" + month + "-" + year;
        date.setText("Date: " + dateSTR);

    }

    private void callAPI(String date, String location, boolean confirm) {

        HashMap<String, String> headers = ApiClient.getHeaders();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.signUpConfirm(headers, date, location, confirm);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful())
                {
                    Toast.makeText(AddMedicineActivity.this, response.code(), Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(AddMedicineActivity.this, "updated", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddMedicineActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public ResponseBody createAccountConfitm(String date, String location, boolean confirm) throws Throwable {
        return OperationsManager.getInstance().doSignUpUserConfirm(date, location, confirm);
    }
}
