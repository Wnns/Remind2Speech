package wnns.remind2speech;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.NotificationCompat;

import wnns.remind2speech.db.DBManager;

public class AlarmNotifications {

    NotificationManager notificationManager;
    Context context;

    public AlarmNotifications(Context context){

        this.context = context;
        notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
    }

    public void createAlarmNotification(Cursor alarmData, String alarmID){

        int intAlarmID = Integer.parseInt(alarmID);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(alarmData.getString(alarmData.getColumnIndex("alarmTitle")));
        builder.setContentText(alarmData.getString(alarmData.getColumnIndex("alarmText")));
        builder.setSmallIcon(R.drawable.notification_icon);

        String currentPackageName = context.getPackageName();
        Intent launchApp = context.getPackageManager().getLaunchIntentForPackage(currentPackageName);
        PendingIntent pendingLaunchApp = PendingIntent.getActivity(context, 0, launchApp, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingLaunchApp);

        notificationManager.notify(intAlarmID, builder.build());
    }


}
