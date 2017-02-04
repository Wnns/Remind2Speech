package wnns.remind2speech;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.NotificationCompat;

import java.io.IOException;

import wnns.remind2speech.db.DBManager;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        DBManager dbManager = new DBManager(context);
        String alarmID =  intent.getStringExtra("ID");
        Cursor alarmData = dbManager.getAlarm(alarmID);

        AlarmNotifications alarmNotifications = new AlarmNotifications(context);
        alarmNotifications.createAlarmNotification(alarmData, alarmID);

        Uri alarmAudioFileUri = Uri.parse("file://" + context.getExternalFilesDir(null) + "/" + alarmID + ".mp3");

        AlarmAudioPlayer alarmAudioPlayer = new AlarmAudioPlayer(context);
        alarmAudioPlayer.setAlarmSound(alarmAudioFileUri);
        alarmAudioPlayer.playAlarm();

    }
}
