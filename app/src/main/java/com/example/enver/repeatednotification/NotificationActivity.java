package com.example.enver.repeatednotification;

import android.app.Activity;
import android.os.Bundle;


public class NotificationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Bildirime basıldıktan sonra yönlendirileceği sayfanın tanımlanması
        setContentView(R.layout.notification_activity);
    }
}
