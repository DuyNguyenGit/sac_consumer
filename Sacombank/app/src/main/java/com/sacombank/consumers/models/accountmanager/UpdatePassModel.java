package com.sacombank.consumers.models.accountmanager;

import com.sacombank.consumers.models.jsonobjects.LoginData;
import com.sacombank.consumers.models.jsonobjects.PasswordUpdating;

/**
 * Created by DUY on 9/8/2017.
 */

public interface UpdatePassModel {

    void updatePass(PasswordUpdating passwordUpdating);
}
