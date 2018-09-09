package com.medical.myknee.classes;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.medical.myknee.R;
import com.medical.myknee.SignInActivity;

/**
 * Created by Belal on 8/29/2017.
 */

//class extending the Broadcast Receiver
public class Alarm extends BroadcastReceiver {

    //the method will be fired when the alarm is triggerred
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager mNotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("ركبتي عزيزتي")
                        .setContentText("حان موعد التمرين").setSound( Uri.parse("android.resource://"
                        + context.getPackageName() + "/" + R.raw.bell));
        Intent resultIntent = new Intent(context, SignInActivity.class); //to open an activity on touch notification
        PendingIntent resultPendingIntent = PendingIntent
                .getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        Notification notification = mBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        mNotifyManager.notify(1, notification);

        Log.d("MyAlarmBelal", "Alarm just fired");
    }

}