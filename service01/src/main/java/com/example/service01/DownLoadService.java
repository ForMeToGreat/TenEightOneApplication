package com.example.service01;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2017/12/13.
 */

public class DownLoadService extends Service{
    private static DownLoadManager manager;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager = new DownLoadManager(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (manager==null){
            manager = new DownLoadManager(this);
        }
        return super.onStartCommand(intent, flags, startId);
    }
    //获取Manager对象
    public static DownLoadManager getInstance(){
        return manager;
    }
}
