package com.david.timaandattandace;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {

    public static final int sec = 2000;
    private boolean flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                if (!flag) {
                    Intent intent = new Intent(SplashActivity.this, EmployeeListActivity.class);
                    startActivity(intent);
                }
            }
        }, sec);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        flag = true;
    }
}


