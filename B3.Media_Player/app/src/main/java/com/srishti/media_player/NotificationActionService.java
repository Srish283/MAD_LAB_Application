package com.srishti.media_player;


import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;

public class NotificationActionService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.sendBroadcast(new Intent("TRACKS_TRACKS")
                .putExtra("actionname", intent.getAction()));
    }
}