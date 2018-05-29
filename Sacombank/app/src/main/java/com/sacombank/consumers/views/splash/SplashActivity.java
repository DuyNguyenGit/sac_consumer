package com.sacombank.consumers.views.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sacombank.consumers.MainActivity;
import com.sacombank.consumers.R;
import com.sacombank.consumers.SacombankApp;
import com.sacombank.consumers.api.ApiManager;
import com.stb.api.bo.AppSessionHandshake;
import com.stb.api.listeners.ApiResponse;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    private static int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ApiManager.clearAllData();
        ApiManager.requestHandshake(Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID), new ApiResponse<AppSessionHandshake>() {
            @Override
            public void onSuccess(AppSessionHandshake result) {
                Log.e(TAG, "onSuccess: >>> Handshake is successful");
                setTimeOut();
            }

            @Override
            public void onError(AppSessionHandshake error) {
                Log.e(TAG, "onSuccess: >>> Handshake is failed");
                setTimeOut();
            }
        });
    }


    private void setTimeOut() {

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT );
    }

}
