package com.srishti.media_player;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements Playable {
    ImageButton play,pause,back,forward;
    TextView songTime,startTime,songname;
    SeekBar seekBar;
    MediaPlayer mediaPlayer;
    int position = 0;
    boolean isPlaying = false;
    NotificationManager notificationManager;
    Handler handler=new Handler(Looper.myLooper());
    int etime=0,stime=0,otime=0,btime=5000,ftime=5000;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        back = (ImageButton) findViewById(R.id.back);
        play = (ImageButton) findViewById(R.id.play);
        pause = (ImageButton) findViewById(R.id.pause);
        forward = (ImageButton) findViewById(R.id.forward);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        songname = (TextView) findViewById(R.id.tv2);
        songTime = (TextView) findViewById(R.id.songtime);
        startTime = (TextView) findViewById(R.id.startTime);
        seekBar.setClickable(false);
        pause.setEnabled(false);
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.naadiyon);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
            registerReceiver(broadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
            startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
        }


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(MainActivity.this, "Playing Song", Toast.LENGTH_SHORT).show();
                songname.setText("Nadiyon Paar");
                mediaPlayer.start();
                etime = mediaPlayer.getDuration();
                stime = mediaPlayer.getCurrentPosition();
                if (otime == 0) {
                    seekBar.setMax(etime);
                    otime = 1;
                }
                songTime.setText(String.format("%d min %d sec", TimeUnit.MILLISECONDS.toMinutes(etime),
                        TimeUnit.MILLISECONDS.toSeconds(etime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(etime))));
                startTime.setText(String.format("%d min %d sec", TimeUnit.MILLISECONDS.toMinutes(stime),
                        TimeUnit.MILLISECONDS.toSeconds(stime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(stime))));
                seekBar.setProgress(stime);
                handler.postDelayed(UpdateSong, 100);

                pause.setEnabled(true);
                play.setEnabled(false);
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Song Paused", Toast.LENGTH_SHORT).show();
                mediaPlayer.pause();
                pause.setEnabled(false);
                play.setEnabled(true);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((stime - btime) < 0) {
                    Toast.makeText(MainActivity.this, "Cannot Jump Back", Toast.LENGTH_SHORT).show();
                } else {
                    mediaPlayer.seekTo(stime - btime);
                    Toast.makeText(MainActivity.this, "Rewinding Song", Toast.LENGTH_SHORT).show();
                }
                if (!play.isEnabled())
                    play.setEnabled(true);
            }
        });
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((stime + ftime) >= etime) {
                    Toast.makeText(MainActivity.this, "Cannot jump Forward", Toast.LENGTH_SHORT).show();
                } else {
                    mediaPlayer.seekTo(stime + ftime);
                    Toast.makeText(MainActivity.this, "Fast-forwarding Song", Toast.LENGTH_SHORT).show();
                }
                if (!play.isEnabled())
                    play.setEnabled(true);
            }
        });


    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CreateNotification.CHANNEL_ID,
                    "Srish&Sudhi", NotificationManager.IMPORTANCE_LOW);

            notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }

    }


    public  Runnable UpdateSong =new Runnable() {
        @Override
        public void run() {
            stime=mediaPlayer.getCurrentPosition();
            songTime.setText(String.format("%d min %d sec", TimeUnit.MILLISECONDS.toMinutes(etime),
                    TimeUnit.MILLISECONDS.toSeconds(etime)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(etime))));
            startTime.setText(String.format("%d min %d sec", TimeUnit.MILLISECONDS.toMinutes(stime),
                    TimeUnit.MILLISECONDS.toSeconds(stime)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(stime))));
            seekBar.setProgress(stime);
            handler.postDelayed(this,100);
        }
    };

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getExtras().getString("actionname");

            switch (action){

                case CreateNotification.ACTION_PLAY:
                    if (isPlaying){
                        onTrackPause();
                    } else {
                        onTrackPlay();
                    }
                    break;

            }
        }
    };



    @Override
    public void onTrackPlay() {

        CreateNotification.createNotification(MainActivity.this,
                R.drawable.ic_action_pause, position,mediaPlayer.getDuration() );
        play.setImageResource(R.drawable.ic_action_pause);
        songname.setText("Naadiyon Paar");
        isPlaying = true;

    }
    @Override
    public void onTrackPause() {

        CreateNotification.createNotification(MainActivity.this,
                R.drawable.ic_action_play, position, mediaPlayer.getDuration());
        play.setImageResource(R.drawable.ic_action_play);
        songname.setText("Naadiyon Paar");
        isPlaying = false;

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationManager.cancelAll();
        }

        unregisterReceiver(broadcastReceiver);
    }
}