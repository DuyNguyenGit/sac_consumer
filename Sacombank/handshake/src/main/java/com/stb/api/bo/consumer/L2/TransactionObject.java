package com.stb.api.bo.consumer.L2;

/**
 * Created by nguyenletruong on 9/7/17.
 */

public class TransactionObject {

    public enum TYPE {HEADER, ITEM}

    public TYPE TransactionType;
    public String Description;
    public String TransactionDate;
    public Integer TransactionAmount;
    public String TransactionCurrencyCode;
    public Integer BillingAmount;
}
