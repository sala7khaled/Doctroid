package view.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.s7k.doctroid.R;

import java.util.Calendar;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import network.model.MedicalAnalysis;

public class BottomSheetFragment extends BottomSheetDialogFragment implements DatePickerFragment.DateSet,
        TimePickerFragment.TimeSet {

    private BottomSheetBehavior bottomSheetBehavior;

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

    private String dateSTR = "Empty";
    private int day;
    private String peroid = "Empty";
    private String timeSTR = "Empty";
    private Button dateBTN, timeBTN, requestBTN;

    private MedicalAnalysis medicalAnalysis;

    public BottomSheetFragment(MedicalAnalysis medicalAnalysis) {
        this.medicalAnalysis = medicalAnalysis;
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        BottomSheetDialog bottomSheet = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.fragment_appoint, null);
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
        requestBTN.setText("Request (Price: " + medicalAnalysis.getPrice() + " LE)");

        dateBTN.setOnClickListener(v ->
        {
            DialogFragment datePicker = new DatePickerFragment(this, "medical");
            datePicker.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "Date Picker");
        });

        timeBTN.setOnClickListener(v ->
        {
            DialogFragment timePicker = new TimePickerFragment(this);
            timePicker.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "time picker");
        });

    }

    @Override
    public void onDateSet(int year, int month, int day) {
        this.day = day;
        dateSTR = day + "/" + ++month + "/" + year;
        dateBTN.setText("Date: " + dateSTR);
        timeBTN.setEnabled(true);
        timeBTN.setBackground(Objects.requireNonNull(getContext()).getResources().getDrawable(R.drawable.back_solid_white));
        timeBTN.setTextColor(Objects.requireNonNull(getContext()).getResources().getColor(R.color.colorPrimaryDark));
    }

    @Override
    public void onTimeSet(int hourOfDay, int minute) {

        String min;
        if (minute <= 9) {
            min = "0" + minute;
        } else {
            min = "" + minute;
        }

        Calendar c = Calendar.getInstance();
        int d = c.get(Calendar.DAY_OF_MONTH);
        int h = c.get(Calendar.HOUR_OF_DAY);

        if (hourOfDay <= h && day == d) {
            Toasty.warning(Objects.requireNonNull(getActivity()), "Please choose an incoming hour").show();
        } else {

            if (hourOfDay >= 12) {
                hourOfDay -= 12;
                if (hourOfDay == 0) {
                    hourOfDay = 12;
                }
                peroid = "PM";
            } else {
                if (hourOfDay == 0) {
                    hourOfDay = 12;
                }
                peroid = "AM";
            }

            timeSTR = hourOfDay + ":" + min + " " + peroid;
            timeBTN.setText("Time: " + timeSTR);
        }
    }

}