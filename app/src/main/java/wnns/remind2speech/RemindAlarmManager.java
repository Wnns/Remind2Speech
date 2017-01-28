package wnns.remind2speech;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import wnns.remind2speech.activities.ActivityAddAlarm;
import wnns.remind2speech.db.DBManager;

/**
 * Created by Dominik on 2016-11-17.
 */
public class RemindAlarmManager {

    AlarmManager alarmManager;
    Context context;

    public RemindAlarmManager(Context _context){

        this.context = _context;
        this.alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void addAlarm(String alarmTitle, String alarmText, Date alarmDate, Activity _activity){

        DBManager dbManager = new DBManager(context);
        SpeechManager speechManager = new SpeechManager(context);

        Date dateNow = new Date();
        String alarmID = dbManager.getFreeID();

        // Generate audio file
        speechManager.speechToFile(alarmID, alarmText, _activity);

        // Add alarm to database
        dbManager.addAlarm(alarmTitle, alarmText, alarmDate);

        // Set android alarm
        int intAlarmID = Integer.parseInt(alarmID);
        long alarmTime = alarmDate.getTime()-dateNow.getTime();

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("ID", alarmID);
        intent.putExtra("TITLE", alarmTitle);
        intent.putExtra("TEXT", alarmText);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, intAlarmID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + alarmTime, pendingIntent);
        //alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 10*1000, pendingIntent);

    }

    public void removeAlarm(String alarmID){

        // Remove audio file
        File audioFile = new File(context.getExternalFilesDir(null), alarmID + ".mp3");
        audioFile.delete();

        // Remove alarm from database
        DBManager dbManager = new DBManager(context);
        dbManager.removeAlarm(alarmID);

        // Remove android alarm
        int intAlarmID = Integer.parseInt(alarmID);

        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, intAlarmID, intent, PendingIntent.FLAG_ONE_SHOT);

        alarmManager.cancel(pendingIntent);

    }
}
