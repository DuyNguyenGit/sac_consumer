package com.stb.api.bo.consumer.L2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DUY on 8/28/2017.
 */

public class CardObject {

    public String CardNumber;
    public String CardToken;
    /*
    Loại thẻ
    - B: thẻ chính
    - S: thẻ phụ
    */
    public String CardType;
    /*
    Loại thẻ
    - C: Credit
    - D: Debit
    - P: Prepaid
     */
    public String CardCategory;
    /*
    Loại thẻ
    - VIS: Visa
    - MAS: Mastercard
    - JCB: JCB
    - UPI: UPI
    - STB: Sacombank
     */
    public String CardIssuer;
    /*
    Trạng thái thẻ
    - Blank: thẻ đang hoạt động
    - U: thẻ chưa kích hoạt
    - T: thẻ bị khóa bởi chủ thẻ
    - Other: thẻ bị khóa bởi ngân hàng
     */
    public String CardStatus;
    public String CurrencyCode;
    /*
    - True: Thẻ được mặc định
    - False: Thẻ không được mặc định
     */
    public Boolean DefaultIndicator;
    public Integer CardImageID;
    public String CardImageUrl;
    public HashMap<String, String> BillDateList;
    public String ExpiryDate;
    public String VisaMasterCard;
    // check select card in history page
    public Boolean IsChecked = false;
}
