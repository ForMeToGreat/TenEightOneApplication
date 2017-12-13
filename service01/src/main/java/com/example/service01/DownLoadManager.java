package com.example.service01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/12/13.
 */

public class DownLoadManager {
    private Context context;
    private ThreadPoolExecutor pool;

    public DownLoadManager(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        pool = new ThreadPoolExecutor(5,5,30, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(3000));
    }
    public void addTask(String path){
        //执行线程池
        pool.execute(new MyThread(path));
    }
    class MyThread extends Thread{
        private String path;

        public MyThread(String path) {
            this.path = path;
        }

        @Override
        public void run() {
            super.run();
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                conn.setDoInput(true);
                if (conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                    long maxSize = conn.getContentLength();
                    InputStream ins = conn.getInputStream();
                    byte[]by = new byte[1024];
                    int len = 0;
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    int readTotal = 0;
                    while ((len = ins.read(by))!=-1){
                        bos.write(by,0,len);
                        readTotal+=len;
                        double dd = readTotal/maxSize;
                        DecimalFormat format = new DecimalFormat("##.00%");
                        String value = format.format(dd);
                        Log.i("tag","=======下载完成==="+value);
                    }
                    byte[] bytes = bos.toByteArray();
                    bos.close();
                    ins.close();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
