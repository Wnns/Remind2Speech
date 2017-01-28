package wnns.remind2speech.activities;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.AsyncListUtil;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import wnns.remind2speech.activities.fragments.DatePickerFragment;
import wnns.remind2speech.R;
import wnns.remind2speech.RemindAlarmManager;
import wnns.remind2speech.activities.fragments.TimePickerFragment;

public class ActivityAddAlarm extends AppCompatActivity {

    EditText eTitle;
    EditText eDescription;
    Button bDate;
    Button bTime;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        eTitle = (EditText)findViewById(R.id.editTextTitle);
        eDescription = (EditText)findViewById(R.id.editTextText);
        bDate = (Button)findViewById(R.id.buttonDate);
        bTime = (Button)findViewById(R.id.buttonTime);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_add);
        setSupportActionBar(toolbar);

        Button buttonDate = (Button)findViewById(R.id.buttonDate);
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setAlarmDate();
            }
        });

        Button buttonTime = (Button)findViewById(R.id.buttonTime);
        buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setAlarmTime();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.item_add_confirm:

                    addAlarm();
                break;
        }

        return true;
    }

    private void setAlarmTime() {

        FragmentManager fragmentManager = getFragmentManager();
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(fragmentManager, "timePicker");
    }

    private void setAlarmDate() {

        FragmentManager fragmentManager = getFragmentManager();
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(fragmentManager, "datePicker");
    }

    private void addAlarm(){

        String alarmTitle = eTitle.getText().toString();
        String alarmText = eDescription.getText().toString();

        DateFormat sdfAlarmDate = new SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.ENGLISH);
        Date alarmDate;

        try {
            alarmDate = sdfAlarmDate.parse(bTime.getText() + " " + bDate.getText());
        } catch (ParseException e) {

            Toast.makeText(getApplicationContext(), getString(R.string.toast_date_wrong), Toast.LENGTH_SHORT).show();
            return;
        }

        if(!alarmDate.after(new Date())){

            Toast.makeText(getApplicationContext(), getString(R.string.toast_date_future), Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(alarmTitle)){

            Toast.makeText(getApplicationContext(), getString(R.string.toast_empty_title), Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(alarmText)){

            Toast.makeText(getApplicationContext(), getString(R.string.toast_empty_text), Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog = new ProgressDialog(ActivityAddAlarm.this);
        progressDialog.setMessage(getString(R.string.toast_saving_alarm));
        progressDialog.show();
        progressDialog.setCancelable(false);

        RemindAlarmManager remindAlarmManager = new RemindAlarmManager(getApplicationContext());
        remindAlarmManager.addAlarm(alarmTitle, alarmText, alarmDate, this);

    }

    @Override
    protected void onStop() {
        super.onStop();

        if(progressDialog.isShowing()){

            progressDialog.dismiss();
        }
    }
}
