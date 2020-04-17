package view.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
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
import customView.CustomToast;
import customView.CustomToastType;
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

    private LinearLayout precautions;
    private TextView medicalTitle, precautions_en, precautions_ar, comment;

    private String c_id;
    private int appointDay;
    private int appointMonth;
    private String dateSTR = "Empty";
    private String appointPeriod = "Empty";
    private String timeSTR = "Empty";
    private String commentSTR = "Empty";
    private Button dateBTN, timeBTN, requestBTN;

    private MedicalAnalysis medicalAnalysis;

    public BottomSheetFragment(MedicalAnalysis medicalAnalysis, String c_id) {
        this.medicalAnalysis = medicalAnalysis;
        this.c_id = c_id;
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
        precautions = view.findViewById(R.id.appoint_precautions_linearLayout);
        precautions_en = view.findViewById(R.id.appoint_en_precautions_textView);
        precautions_ar = view.findViewById(R.id.appoint_ar_precautions_textView);
        comment = view.findViewById(R.id.appoint_comment_editText);

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

        if (medicalAnalysis.getPrecautions_en().toLowerCase().equals("empty")
                || medicalAnalysis.getPrecautions_ar().toLowerCase().equals("empty")) {
            precautions.setVisibility(View.GONE);
        } else {
            precautions.setVisibility(View.VISIBLE);
            precautions_en.setText(medicalAnalysis.getPrecautions_en());
            precautions_ar.setText(medicalAnalysis.getPrecautions_ar());
        }

        requestBTN.setText("Request (Price: " + medicalAnalysis.getPrice() + " LE)");

        dateBTN.setOnClickListener(v ->
        {
            DialogFragment datePicker = new DatePickerFragment(this, "appoint");
            datePicker.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "Date Picker");
        });

        timeBTN.setOnClickListener(v ->
        {
            Calendar c = Calendar.getInstance();
            int currentDay = c.get(Calendar.DAY_OF_MONTH);
            int currentMonth = c.get(Calendar.MONTH);
            int currentHour = c.get(Calendar.HOUR_OF_DAY);
            int currentMinute = c.get(Calendar.MINUTE);

            if (appointMonth == currentMonth && appointDay == currentDay) {
                RangeTimePickerFragment rangeTimePicker = new RangeTimePickerFragment(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int appointHour, int minute) {
                        String min;
                        if (minute <= 9) {
                            min = "0" + minute;
                        } else {
                            min = "" + minute;
                        }
                        //-----------------------------------------
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
                        //-----------------------------------------
                        timeSTR = appointHour + ":" + min + " " + appointPeriod;
                        timeBTN.setText("Time: " + timeSTR);
                    }
                }, currentHour + 1, currentMinute, false);
                rangeTimePicker.setMin(currentHour + 1, 0);
                rangeTimePicker.show();
            } else {
                RangeTimePickerFragment rangeTimePicker = new RangeTimePickerFragment(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int appointHour, int minute) {
                        String min;
                        if (minute <= 9) {
                            min = "0" + minute;
                        } else {
                            min = "" + minute;
                        }
                        //-----------------------------------------
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
                        //-----------------------------------------
                        timeSTR = appointHour + ":" + min + " " + appointPeriod;
                        timeBTN.setText("Time: " + timeSTR);
                    }
                }, currentHour, currentMinute, false);
                rangeTimePicker.show();
            }

        });

        requestBTN.setOnClickListener(v ->
        {
            if (dateSTR.equals("Empty") || timeSTR.equals("Empty")) {
                CustomToast.Companion.darkColor(getContext(), CustomToastType.ERROR, "Please pick data and time!");
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

        if (comment.getText().length() == 0) {
            commentSTR = "No Comment Added.";
        } else {
            commentSTR = comment.getText().toString();
        }

        AppointRequest appointRequest = new AppointRequest(c_id, medicalAnalysis.getId(), p_id,
                "Pending", timeSTR, dateSTR, commentSTR);

        HashMap<String, String> headers = ApiClient.getHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.requestAppoint(headers, appointRequest);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call,
                                   @NotNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    CustomToast.Companion.darkColor(getContext(), CustomToastType.INFO, "The request has been sent. Check Appointment for the Accepting/Rejecting!");
                    progressViewDialog.hideDialog();
                    dismiss();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call,
                                  @NotNull Throwable t) {
                CustomToast.Companion.darkColor(getContext(), CustomToastType.ERROR, Objects.requireNonNull(t.getMessage()));
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
                        CustomToast.Companion.darkColor(getContext(), CustomToastType.WARNING, "Please choose an incoming hour!");
                        timeBTN.setText("Select Time");
                        timeSTR = "Empty";
                    }

                } else if (appointPeriod.equals("AM") && currentPeriod == Calendar.PM) {

                    CustomToast.Companion.darkColor(getContext(), CustomToastType.WARNING, "Please choose an incoming hour!");
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
                        CustomToast.Companion.darkColor(getContext(), CustomToastType.WARNING, "Please choose an incoming hour!");
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
