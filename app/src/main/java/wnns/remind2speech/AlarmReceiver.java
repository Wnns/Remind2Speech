package wnns.remind2speech;

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

/**
 * Created by Dominik on 2016-11-17.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        DBManager dbManager = new DBManager(context);

        String alarmType = sharedPreferences.getString("alarmtype", "null");
        String alarmID =  intent.getStringExtra("ID");
        Cursor alarmData = dbManager.getAlarm(alarmID);

        // If alarm does not exists in database
        if(alarmData.getCount() == 0){

            return;
        }

        // Alarm notification
        if(alarmType.equals("notification")){

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            int intAlarmID = Integer.parseInt(intent.getStringExtra("ID"));

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle(alarmData.getString(alarmData.getColumnIndex("alarmTitle")));
            builder.setContentText(alarmData.getString(alarmData.getColumnIndex("alarmText")));
            builder.setSmallIcon(R.drawable.notification_icon);

            // Launch app on notification tap
            String currentPackageName = context.getPackageName();
            Intent launchApp = context.getPackageManager().getLaunchIntentForPackage(currentPackageName);
            PendingIntent pendingLaunchApp = PendingIntent.getActivity(context, 0, launchApp, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingLaunchApp);

            notificationManager.notify(intAlarmID, builder.build());

            Uri alarmAudioFileUri = Uri.parse("file://" + context.getExternalFilesDir(null) + "/" + alarmID + ".mp3");

            // Play alarm audio
            final MediaPlayer mediaPlayer = new MediaPlayer();

            mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build());

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                    mp.release();
                }
            });

            try {

                mediaPlayer.setDataSource(context, alarmAudioFileUri);
                mediaPlayer.prepare();
            } catch (IOException e) {

                e.printStackTrace();
                return;
            }
            mediaPlayer.start();
        }

    }
}
