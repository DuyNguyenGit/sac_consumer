package com.sacombank.consumers.models.jsonobjects;

/**
 * Created by DUY on 9/8/2017.
 */

public class PasswordUpdating {

    private String oldPass;
    private String newPass;

    public PasswordUpdating(String oldPass, String newPass) {
        this.oldPass = oldPass;
        this.newPass = newPass;
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
}
