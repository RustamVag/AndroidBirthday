package com.dom.viewapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcast extends BroadcastReceiver {
    public MyBroadcast() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Автозапуск сервиса
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            context.startService(new Intent(context, BirthdayService.class));
        }
    }
}
