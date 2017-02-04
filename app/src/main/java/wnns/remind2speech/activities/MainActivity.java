package wnns.remind2speech.activities;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import wnns.remind2speech.SpeechManager;
import wnns.remind2speech.db.DBManager;
import wnns.remind2speech.R;
import wnns.remind2speech.activities.fragments.adapters.RecyclerViewAdapter;
import wnns.remind2speech.activities.fragments.adapters.RecyclerViewItem;
import wnns.remind2speech.RemindAlarmManager;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;
    ArrayList<RecyclerViewItem> recyclerViewItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Custom actionbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(myToolbar);

        // RecyclerView for saved alarms
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerViewItems= new ArrayList<>();
        recyclerView.setAdapter(new RecyclerViewAdapter(recyclerViewItems, recyclerView));

    }

    private void loadAlarms(){

        recyclerViewItems.clear();

        DBManager dbManager = new DBManager(getApplicationContext());
        Cursor allAlarms = dbManager.getAllAlarms();
        allAlarms.moveToFirst();

        while (!allAlarms.isAfterLast()){

            RecyclerViewItem newRecyclerViewItem = new RecyclerViewItem(
                    allAlarms.getString(allAlarms.getColumnIndex("alarmID")),
                    allAlarms.getString(allAlarms.getColumnIndex("alarmTitle"))
                    ,allAlarms.getString(allAlarms.getColumnIndex("alarmText"))
                    ,allAlarms.getString(allAlarms.getColumnIndex("alarmDate")));

            recyclerViewItems.add(newRecyclerViewItem);

            allAlarms.moveToNext();
        }

        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.item_activity_add){

            Intent openAddActivityIntent = new Intent(this, ActivityAddAlarm.class);
            startActivity(openAddActivityIntent);
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadAlarms();
    }
}
