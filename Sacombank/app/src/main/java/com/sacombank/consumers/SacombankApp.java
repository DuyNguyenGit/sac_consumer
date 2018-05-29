package com.sacombank.consumers;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.sacombank.consumers.api.ApiManager;
import com.sacombank.consumers.utils.constant.ShareReference;
import com.sacombank.consumers.utils.preference.StringPrefs;
import io.fabric.sdk.android.Fabric;

/**
 * Created by DUY on 7/15/2017.
 */

public class SacombankApp extends MultiDexApplication {
    private String TAG = SacombankApp.class.getSimpleName();


    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        saveCommonSystemInfo();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    private void saveCommonSystemInfo() {
        String deviceID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        ApiManager.setModel(Build.MODEL);
        ApiManager.setOSVersion(Build.VERSION.RELEASE);
        ApiManager.setDeviceId(deviceID);
        Log.e(TAG, "saveCommonSystemInfo: >>>" + deviceID);
    }


    public final StringPrefs userAccountPref = new StringPrefs(this, ShareReference.USER, "");

    public final StringPrefs firebaseTokenPref = new StringPrefs(this, ShareReference.FIREBASE_TOKEN, "");


}
