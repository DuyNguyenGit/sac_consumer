package com.sacombank.consumers.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.sacombank.consumers.SacombankApp;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token= FirebaseInstanceId.getInstance().getToken();
        //Save Token
        saveToken(token);
    }

    private void saveToken(String token) {
        //new FirebaseIDTask().execute(token);
        Log.e(TAG, "saveToken: >>> " + token);
        ((SacombankApp) getApplication()).firebaseTokenPref.setValue(token);
    }
}
