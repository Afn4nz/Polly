package com.example.polly.receivers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import com.example.polly.activities.mypolls;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.polly.R;
import com.example.polly.activities.MainActivity;
import com.example.polly.activities.polldetails;

public class ReminderBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        /*NotificationCompat. Builder builder = new NotificationCompat.Builder( context, "VotingResult ")
                .setSmallIcon (R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle( "Voting Result Notification")
                .setContentText( "Voting Result")
                .setPriority(NotificationCompat . PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat. from( context) ;
        notificationManager. notify( 200, builder.build());*/

        sendNotification(context);

    }

    private void sendNotification(Context context) {
        Intent intent = new Intent(context, polldetails.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = context.getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                        .setContentTitle("Voting Result Notification")
                        .setContentText("Voting Result")
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(200, notificationBuilder.build());
    }
}