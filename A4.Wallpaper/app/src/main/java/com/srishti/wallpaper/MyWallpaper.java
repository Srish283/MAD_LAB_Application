package com.srishti.wallpaper;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.app.Service;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.IBinder;
import androidx.annotation.Nullable;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class MyWallpaper extends Service  {
    WallpaperManager wpm;
    Timer myTimer;
    Drawable drawable;
    int num=1;

    @Override
    public void onCreate() {
        super.onCreate();
        myTimer=new Timer();
        wpm=WallpaperManager.getInstance(MyWallpaper.this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(num==1)
                {
                    drawable=getResources().getDrawable(R.drawable.im1);
                    num=2;
                }
                else if(num==2)
                {
                    drawable=getResources().getDrawable(R.drawable.im2);
                    num=3;
                }
                else if(num==3)
                {
                    drawable=getResources().getDrawable(R.drawable.im3);
                    num=4;
                }
                else if(num==4)
                {
                    drawable=getResources().getDrawable(R.drawable.im4);
                    num=5;
                }
                else if(num==5)
                {
                    drawable=getResources().getDrawable(R.drawable.im5);
                    num=6;
                }
                else
                {
                    drawable=getResources().getDrawable(R.drawable.im6);
                    num=1;
                }

                Bitmap wallpaper = ((BitmapDrawable)drawable).getBitmap();
                try{
                    wpm.setBitmap(wallpaper);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        },0, 2000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

