package com.sacombank.consumers.models.history;

import com.sacombank.consumers.models.jsonobjects.HistoryData;
import com.sacombank.consumers.models.jsonobjects.LoginData;

/**
 * Created by DUY on 9/9/2017.
 */

public interface HistoryModel {
    void loadHistoryData(HistoryData historyData);
}
