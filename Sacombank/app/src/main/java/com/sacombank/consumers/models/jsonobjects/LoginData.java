package com.sacombank.consumers.models.jsonobjects;

/**
 * Created by DUY on 7/15/2017.
 */

public class LoginData {
    String mobileNo;
    String password;
    String firebaseToken;

    public LoginData() {
    }

    public LoginData(String mobileNo, String password, String firebaseToken) {
        this.mobileNo = mobileNo;
        this.password = password;
        this.firebaseToken = firebaseToken;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }
}
