package view.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Objects;

public class DatePickerFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {

    public interface DateSet {
        void onDateSet(int year, int month, int day);
    }


    public DatePickerFragment(DateSet dateSet, String type) {
        this.dateSet = dateSet;
        this.type = type;
    }

    public DatePickerFragment(String type) {
        this.type = type;
    }

    private DateSet dateSet = null;
    private String type;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (type.equals("birthday")) {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            //return new DatePickerDialog(Objects.requireNonNull(getActivity()), this, year, month, day);

            c.set(year, month, day);
            DatePickerDialog picker = new DatePickerDialog(Objects.requireNonNull(getActivity()), this, year, month, day);
            picker.getDatePicker().setMaxDate(c.getTime().getTime());
            return picker;
        } else {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            c.set(year, month, day);
            DatePickerDialog picker = new DatePickerDialog(Objects.requireNonNull(getActivity()), this, year, month, day);
            picker.getDatePicker().setMinDate(c.getTime().getTime());
            return picker;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        if (dateSet != null) {
            dateSet.onDateSet(year, month, day);
        }
    }
}