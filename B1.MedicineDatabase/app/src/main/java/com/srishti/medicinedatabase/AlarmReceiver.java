package com.srishti.medicinedatabase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"Time to take Medicine",Toast.LENGTH_SHORT).show();
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (uri==null){
            uri =RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        }
        Ringtone ringtone=RingtoneManager.getRingtone(context,uri);
        ringtone.play();
    }
}
