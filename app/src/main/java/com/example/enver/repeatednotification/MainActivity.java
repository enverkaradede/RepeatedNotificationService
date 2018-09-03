package com.example.enver.repeatednotification;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.Calendar;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Zaman ayarlı offline bildirim gönderimi için alarm servisi aktivasyonu
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //MainActivity classı ile AlarmReceiver classının uygulama başlangıcında birleştirilmesini sağlayan yapı (RunTime Binding)
        Intent notificationIntent = new Intent(this, AlarmReceiver.class);

        //Uygulama kapalı olsa dahi bildirimin uygulama yetkilerine sahip olmasını sağlayan yapı
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Uygulama başlatıldıktan 5 saniye sonrasında bildirim göndermek için Calendar kütüphanesinin kullanılması
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 5);

        //Bildirimin dakikada bir gönderilmesini sağlamak için kullanılan yapı
        if(alarmManager != null) //--> Kullanılması gerekli değil fakat NullPointerException hatasını önlemek için kullanıldı.
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 1000 * 60, broadcast);

    }
}
