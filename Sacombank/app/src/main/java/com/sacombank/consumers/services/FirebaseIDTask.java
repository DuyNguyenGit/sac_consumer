package com.sacombank.consumers.services;

import android.os.AsyncTask;
import android.util.Log;

import java.net.URL;

/**
 * Created by TranVietThuc on 26/07/2017.
 */

public class FirebaseIDTask extends AsyncTask<String,Void,Boolean>{
    @Override
    protected Boolean doInBackground(String... strings) {
        try{
            //Save Token to Database...

        }catch (Exception ex){
            Log.e("LOI",ex.toString());
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
