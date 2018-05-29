package com.sacombank.consumers.models.cardmanagement;

import com.sacombank.consumers.models.jsonobjects.CardMaintenance;
import com.stb.api.bo.consumer.L2.CardObject;

/**
 * Created by DUY on 9/16/2017.
 */

public interface CardUpdateModel {

    void updateCard(CardObject cardObject);

    void updateCardMaintenance(CardMaintenance cardMaintenance);
}
