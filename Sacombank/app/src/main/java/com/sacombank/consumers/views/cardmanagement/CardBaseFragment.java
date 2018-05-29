package com.sacombank.consumers.views.cardmanagement;

import com.sacombank.consumers.views.BaseFragment;
import com.stb.api.bo.consumer.L2.CardObject;

/**
 * Created by DUY on 9/14/2017.
 */

public abstract class CardBaseFragment extends BaseFragment {
    public CardObject cardObject;
    protected abstract void updateUI(CardObject cardObject);
}
