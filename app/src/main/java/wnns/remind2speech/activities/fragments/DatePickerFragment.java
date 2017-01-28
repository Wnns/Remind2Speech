package wnns.remind2speech.activities.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

import wnns.remind2speech.R;

/**
 * Created by Dominik on 2016-11-19.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();
        int callendarYear = calendar.get(Calendar.YEAR);
        int callendarMonth = calendar.get(Calendar.MONTH);
        int callendarDay = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, callendarYear, callendarMonth, callendarDay);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {

        String dayToSet = String.valueOf(day);
        if(dayToSet.length() == 1){

            dayToSet = "0" + dayToSet;
        }

        month++;
        String monthToSet = String.valueOf(month);
        if(monthToSet.length() == 1){

            monthToSet = "0" + monthToSet;
        }

        Button timeButton = (Button)getActivity().findViewById(R.id.buttonDate);
        timeButton.setText(dayToSet + "." + monthToSet + "." + year);
    }
}
