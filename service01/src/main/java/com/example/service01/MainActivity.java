package com.example.service01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private String path = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void startService(View view) {
        Intent intent = new Intent(this,DownLoadService.class);
        startService(intent);
    }

    public void download(View view) {
        DownLoadManager manager = DownLoadService.getInstance();
        manager.addTask(path);
    }
}
