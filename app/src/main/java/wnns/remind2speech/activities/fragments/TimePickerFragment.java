package wnns.remind2speech.activities.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TimePicker;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

import wnns.remind2speech.R;

/**
 * Created by Dominik on 2016-11-19.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();
        int calendarHour = calendar.get(Calendar.HOUR_OF_DAY);
        int calendarMinute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, calendarHour, calendarMinute, true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


        String hourToSet = String.valueOf(hourOfDay);
        if(hourToSet.length() == 1){

            hourToSet = "0" + hourToSet;
        }

        String minutesToSet =  String.valueOf(minute);
        if(minutesToSet.length() == 1){

            minutesToSet = "0" + minutesToSet;
        }

        Button timeButton = (Button)getActivity().findViewById(R.id.buttonTime);
        timeButton.setText(hourToSet + ":" + minutesToSet);
    }

}
