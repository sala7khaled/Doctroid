package view.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.s7k.doctroid.R;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

import app.App;
import dialog.ProgressViewDialog;
import es.dmoral.toasty.Toasty;
import network.api.ApiClient;
import network.api.ApiInterface;
import network.model.Appoint;
import network.model.AppointRequest;
import network.model.MedicalAnalysis;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utilities.PrefManager;
import view.category.AppointmentActivity;

import static android.view.View.inflate;

public class BottomSheetFragment extends BottomSheetDialogFragment implements DatePickerFragment.DateSet,
        TimePickerFragment.TimeSet {

    private BottomSheetBehavior bottomSheetBehavior;
    private ProgressViewDialog progressViewDialog;

    private TextView medicalTitle;

    private TextView question1TV;
    private TextView question2TV;
    private TextView question3TV;

    private EditText question1Answer;
    private EditText question2Answer;
    private EditText question3Answer;

    private LinearLayout question1Linear;
    private LinearLayout question2Linear;
    private LinearLayout question3Linear;

    private String c_id;
    private String dateSTR = "Empty";
    private int appointDay;
    private int appointMonth;
    private String appointPeriod = "Empty";
    private String timeSTR = "Empty";
    private Button dateBTN, timeBTN, requestBTN;

    private MedicalAnalysis medicalAnalysis;
    private String[] questions;

    public BottomSheetFragment(MedicalAnalysis medicalAnalysis, String c_id) {
        this.medicalAnalysis = medicalAnalysis;
        this.c_id = c_id;
        this.questions = medicalAnalysis.getQuestions();
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        BottomSheetDialog bottomSheet = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = inflate(getContext(), R.layout.fragment_appoint, null);
        bottomSheet.setContentView(view);
        bottomSheetBehavior = BottomSheetBehavior.from((View) (view.getParent()));

        initView(view);
        return bottomSheet;
    }

    private void initView(View view) {

        medicalTitle = view.findViewById(R.id.appoint_medicalAnalysis_title);

        question1TV = view.findViewById(R.id.appoint_question1_textView);
        question2TV = view.findViewById(R.id.appoint_question2_textView);
        question3TV = view.findViewById(R.id.appoint_question3_textView);

        question1Answer = view.findViewById(R.id.appoint_question1_editText);
        question2Answer = view.findViewById(R.id.appoint_question2_editText);
        question3Answer = view.findViewById(R.id.appoint_question3_editText);

        question1Linear = view.findViewById(R.id.appoint_question1_linearLayout);
        question2Linear = view.findViewById(R.id.appoint_question2_linearLayout);
        question3Linear = view.findViewById(R.id.appoint_question3_linearLayout);

        dateBTN = view.findViewById(R.id.appoint_date_BTN);
        timeBTN = view.findViewById(R.id.appoint_time_BTN);
        timeBTN.setEnabled(false);
        timeBTN.setBackground(Objects.requireNonNull(getContext()).getResources().getDrawable(R.drawable.back_solid_gray));
        timeBTN.setTextColor(Objects.requireNonNull(getContext()).getResources().getColor(R.color.colorWhite));

        requestBTN = view.findViewById(R.id.appoint_request_button);

    }

    @Override
    public void onStart() {
        super.onStart();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        setListeners();
    }

    private void setListeners() {

        medicalTitle.setText(medicalAnalysis.getTitle());

        question1TV.setText(questions[0]);
        question2TV.setText(questions[1]);
        question3TV.setText(questions[2]);

        requestBTN.setText("Request (Price: " + medicalAnalysis.getPrice() + " LE)");

        dateBTN.setOnClickListener(v ->
        {
            DialogFragment datePicker = new DatePickerFragment(this, "appoint");
            datePicker.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "Date Picker");
        });

        timeBTN.setOnClickListener(v ->
        {
            DialogFragment timePicker = new TimePickerFragment(this);
            timePicker.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "time picker");
        });

        requestBTN.setOnClickListener(v ->
        {
            if (question1Answer.getText().toString().trim().length() == 0
                    || question2Answer.getText().toString().trim().length() == 0
                    || question3Answer.getText().toString().trim().length() == 0) {

                Toasty.error(Objects.requireNonNull(getActivity()), "Please answer all the question!").show();

            } else if (dateSTR.equals("Empty") || timeSTR.equals("Empty")) {
                Toasty.error(Objects.requireNonNull(getActivity()), "Please pick date and time!").show();
            } else {
                callAPI();
            }
        });

    }

    private void callAPI() {
        progressViewDialog = new ProgressViewDialog(getContext());
        progressViewDialog.isShowing();
        progressViewDialog.setDialogCancelable(false);
        progressViewDialog.setCanceledOnTouchOutside(false);
        progressViewDialog.showProgressDialog("Requesting an appointment...");

        String p_id = PrefManager.getP_id(getActivity());
        String[] answers = {question1Answer.getText().toString().trim(),
                question2Answer.getText().toString().trim(),
                question3Answer.getText().toString().trim()};

        AppointRequest appointRequest = new AppointRequest(c_id, medicalAnalysis.getId(), p_id,
                "Pending", timeSTR, dateSTR, answers, "", "", "");

        HashMap<String, String> headers = ApiClient.getHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.requestAppoint(headers, appointRequest);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call,
                                   @NotNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toasty.info(Objects.requireNonNull(getContext()), "The request has been sent. Check Appointment for the Accepting/Rejecting!", Toasty.LENGTH_LONG).show();
                    progressViewDialog.hideDialog();
                    dismiss();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call,
                                  @NotNull Throwable t) {
                Toasty.error(Objects.requireNonNull(getContext()), Objects.requireNonNull(t.getMessage())).show();
            }
        });
    }

    @Override
    public void onDateSet(int year, int month, int day) {
        appointDay = day;
        appointMonth = month;
        dateSTR = day + "/" + ++month + "/" + year;
        dateBTN.setText("Date: " + dateSTR);
        timeBTN.setEnabled(true);
        timeBTN.setBackground(Objects.requireNonNull(getContext()).getResources().getDrawable(R.drawable.back_solid_white));
        timeBTN.setTextColor(Objects.requireNonNull(getContext()).getResources().getColor(R.color.colorPrimaryDark));
        timeBTN.setText("Select Time");
        timeSTR = "Empty";
    }

    @Override
    public void onTimeSet(int appointHour, int minute) {

        String min;
        if (minute <= 9) {
            min = "0" + minute;
        } else {
            min = "" + minute;
        }

        Calendar c = Calendar.getInstance();
        int currentDay = c.get(Calendar.DAY_OF_MONTH);
        int currentHour = c.get(Calendar.HOUR_OF_DAY);
        int currentMonth = c.get(Calendar.MONTH);
        int currentPeriod = c.get(Calendar.AM_PM);

        // ------------------------------------------------

        if (appointMonth == currentMonth) {

            if (appointDay == currentDay) {

                if (appointHour >= 12) {
                    appointPeriod = "PM";
                    appointHour -= 12;
                    if (appointHour == 0) {
                        appointHour = 12;
                    }
                } else {
                    appointPeriod = "AM";
                    if (appointHour == 0) {
                        appointHour = 12;
                    }
                }

                if (appointPeriod.equals("AM") && currentPeriod == Calendar.AM) {

                    if (appointHour > currentHour) {
                        timeSTR = appointHour + ":" + min + " " + appointPeriod;
                        timeBTN.setText("Time: " + timeSTR);
                    } else {
                        Toasty.warning(Objects.requireNonNull(getActivity()), "Please choose an incoming hour!").show();
                        timeBTN.setText("Select Time");
                        timeSTR = "Empty";
                    }

                } else if (appointPeriod.equals("AM") && currentPeriod == Calendar.PM) {

                    Toasty.warning(Objects.requireNonNull(getActivity()), "Please choose an incoming hour!").show();
                    timeBTN.setText("Select Time");
                    timeSTR = "Empty";

                } else if (appointPeriod.equals("PM") && currentPeriod == Calendar.AM) {

                    timeSTR = appointHour + ":" + min + " " + appointPeriod;
                    timeBTN.setText("Time: " + timeSTR);

                } else if (appointPeriod.equals("PM") && currentPeriod == Calendar.PM) {

                    if (appointHour > currentHour) {
                        timeSTR = appointHour + ":" + min + " " + appointPeriod;
                        timeBTN.setText("Time: " + timeSTR);
                    } else {
                        Toasty.warning(Objects.requireNonNull(getActivity()), "Please choose an incoming hour!").show();
                        timeBTN.setText("Select Time");
                        timeSTR = "Empty";
                    }

                }

            } else {
                if (appointHour >= 12) {
                    appointPeriod = "PM";
                    appointHour -= 12;
                    if (appointHour == 0) {
                        appointHour = 12;
                    }
                } else {
                    appointPeriod = "AM";
                    if (appointHour == 0) {
                        appointHour = 12;
                    }
                }
                timeSTR = appointHour + ":" + min + " " + appointPeriod;
                timeBTN.setText("Time: " + timeSTR);
            }
        } else {
            if (appointHour >= 12) {
                appointPeriod = "PM";
                appointHour -= 12;
                if (appointHour == 0) {
                    appointHour = 12;
                }
            } else {
                appointPeriod = "AM";
                if (appointHour == 0) {
                    appointHour = 12;
                }
            }
            timeSTR = appointHour + ":" + min + " " + appointPeriod;
            timeBTN.setText("Time: " + timeSTR);
        }

    }
}
