package com.example.enver.repeatednotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;


public class AlarmReceiver extends BroadcastReceiver{
    private static final String CHANNEL_ID = "com.example.enver.repeatednotification.channelId";

    @Override
    public void onReceive(Context context, Intent intent) {
        //AlarmReceiver classı ile NotificationActivity classının uygulama başlangıcında birleştirilmesini sağlayan yapı (RunTime Binding)
        Intent notificationIntent = new Intent(context, NotificationActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(NotificationActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        //Uygulama kapalı olsa dahi bildirimin uygulama yetkilerine sahip olmasını sağlayan yapı
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        //Bildirim yapısının oluşturulması
        Notification.Builder builder = new Notification.Builder(context);

        //Bildirim paneli üzerinden gönderilen bildirimde gösterilecek yazılar
        Notification notification = builder.setContentTitle("Demo App Notification")
                .setContentText("New Notification From Demo App..")
                .setTicker("New Message Alert!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent).build();

        //Android versiyon kontrolü bölümü
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //Android Oreo ve üzeri versiyonlar için gruplandırılmış bildirim kanalı tanımlanması
            builder.setChannelId(CHANNEL_ID);
        }

        //Bildirim servisi aktivasyonu
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //Android Oreo ve üzeri versiyonlar için tanımlanmış kanal üzerine bildirimin yönlendirilmesi
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "NotificationDemo",
                    NotificationManager.IMPORTANCE_HIGH
            );
            if(notificationManager != null) //--> Kullanılması gerekli değil fakat NullPointerException hatasını önlemek için kullanıldı.
                notificationManager.createNotificationChannel(channel);
        }

        //Asıl bildirimin gönderilmesi
        if(notificationManager != null) //--> Kullanılması gerekli değil fakat NullPointerException hatasını önlemek için kullanıldı.
            notificationManager.notify(0, notification);
    }
}
