package com.sacombank.consumers.api;

import android.util.Log;

import com.google.gson.Gson;
import com.sacombank.consumers.utils.Utils;
import com.stb.api.STBAppClient;
import com.stb.api.STBGlobal;
import com.stb.api.bo.AppSessionHandshake;
import com.stb.api.bo.consumer.ConsumerAccountLogin;
import com.stb.api.bo.consumer.ConsumerAccountUpdate;
import com.stb.api.bo.consumer.ConsumerAuthCodeVerification;
import com.stb.api.bo.consumer.ConsumerCardBalanceInquiry;
import com.stb.api.bo.consumer.ConsumerCardMaintenance;
import com.stb.api.bo.consumer.ConsumerCardMiniStatement;
import com.stb.api.bo.consumer.ConsumerCardUpdate;
import com.stb.api.bo.consumer.ConsumerCardVerification;
import com.stb.api.bo.consumer.ConsumerPasswordChange;
import com.stb.api.bo.consumer.ConsumerPasswordCreation;
import com.stb.api.bo.consumer.ConsumerPromotionDetails;
import com.stb.api.bo.consumer.ConsumerPublicHomeInquiry;
import com.stb.api.bo.consumer.ConsumerQRPayment;
import com.stb.api.bo.consumer.ConsumerStatementInquiry;
import com.stb.api.bo.consumer.L2.AccountObject;
import com.stb.api.bo.consumer.L2.CardObject;
import com.stb.api.bo.consumer.L2.SuggestionObject;
import com.stb.api.crypto.PinRSA;
import com.stb.api.crypto.TripleDESCrypto;
import com.stb.api.listeners.ApiResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ApiManager {
    /*** define Enum Type ***/
    /*** Auth Type ***/
    public enum AuthType {
        OTP {
            public String toString() {
                return "OTP";
            }
        },

        PIN {
            public String toString() {
                return "PIN";
            }
        }
    }

    public enum CardType {
        MAIN {
            public String toString() {
                return "B";
            }
        },
        SUB {
            public String toString() {
                return "S";
            }
        }
    }

    public enum CardCategory {
        CREDIT {
            public String toString() {
                return "C";
            }
        },
        DEBIT {
            public String toString() {
                return "D";
            }
        },
        PREPAID {
            public String toString() {
                return "P";
            }
        }
    }

    public enum CardStatus {
        ACTIVE {
            public String toString() {
                return "";
            }
        },
        INACTIVE {
            public String toString() {
                return "U";
            }
        },
        LOCK {
            public String toString() {
                return "T";
            }
        }
    }

    public enum TypeOfSSN {
        CMND {
            public String toString() {
                return "1";
            }
        },
        PASSPORT {
            public String toString() {
                return "2";
            }
        }
    }

    public enum CardIssuer {
        Visa{
            @Override
            public String toString() {
                return "Visa";
            }
        },
        Mastercard{
            @Override
            public String toString() {
                return "Mastercard";
            }
        },
        JCB{
            @Override
            public String toString() {
                return "JCB";
            }
        },
        UnionPay{
            @Override
            public String toString() {
                return "UnionPay";
            }
        },
        Sacombank{
            @Override
            public String toString() {
                return "Sacombank";
            }
        }
    }
    /*** define key ***/
    private static String KEYPKCS1PARAMETER = "PKCS1Parameter";
    private static String KEYPASSWORDENCRYPTED = "PasswordEncrypted";
    private static String KEYRN = "RN";

    /*** Variables ***/
    private static final String TAG = ApiManager.class.getSimpleName();
    private static String mMobileNo, mCardNumber, mCardToken, mAuthType, mPKCS1Parameter, mPasswordEncrypted, mRN;
    private static CardObject[] mCardObjectList;
    private static SuggestionObject[] mSuggestionObjectList;
    private static AccountObject mAccountObject;
    private static String mRefNumber = UUID.randomUUID().toString().replaceAll("-", "").substring(12);
    private static String mLanguageID = "VI";
    private static String mDeviceID = "123455666";
    private static String mPlatform = "Android";
    private static String mOSVersion = "5.1.0";
    private static String mModel = "SamSung";


    public static void clearAllData() {
        mCardObjectList = null;
        mSuggestionObjectList = null;
        mAccountObject = null;
        mMobileNo = null;
        mCardNumber = null;
        mCardToken = null;
        mAuthType = null;
        mPKCS1Parameter = null;
        mPasswordEncrypted = null;
        mRN = null;
    }

    /*** properties ***/
    public static void setModel(String model) {
        mModel = model;
    }

    public static void setOSVersion(String OSVersion) {
        mOSVersion = OSVersion;
    }

    public static void setPlatform(String platform) {
        mPlatform = platform;
    }

    public static void setDeviceId(String deviceId) {
        mDeviceID = deviceId;
    }

    public static void setLanguageId(String languageId) {
        mLanguageID = languageId;
    }

    public static String getCurrentAuthType() {
        return mAuthType;
    }

    public static List<CardObject> getVisaMaterCardList(String cardIssuer) {
        List<CardObject> cardObjects = new ArrayList<CardObject>();
        CardObject cardObject;
        for (int i = 0; i < mCardObjectList.length; i++) {
            cardObject = mCardObjectList[i];
            if (cardIssuer != null) {
                if (cardObject.CardIssuer.equals(cardIssuer)) {
                    String fourLastDigital = cardObject.CardNumber.substring(cardObject.CardNumber.length() - 4);
                    String visaMaster = cardObject.CardIssuer + "-" + fourLastDigital;
                    cardObject.VisaMasterCard = visaMaster;
                    cardObjects.add(cardObject);
                }
            } else {
                if (cardObject.CardIssuer.equals(CardIssuer.Visa.toString()) ||
                        cardObject.CardIssuer.equals(CardIssuer.Mastercard.toString())) {
                    cardObjects.add(cardObject);
                }
            }
        }

        return cardObjects;
    }

    public static CardObject[] getCardObjectList() {
        return mCardObjectList;
    }

    public static String getMobileNo() {
        return mMobileNo;
    }

    public static SuggestionObject[] getSuggestObjectList() {
        return mSuggestionObjectList;
    }

    public static AccountObject getAccountObject() {
        return mAccountObject;
    }

    /*** methods ***/
    private static Map<String, String> getPINEncrypted(String PIN) {
        Map<String, String> dict = new HashMap<String, String>();
        String RN = STBGlobal.randomNumber();
        String Hash = "SHA-1";
        String[] result = PinRSA.Encrypt(PIN, STBGlobal.PINPublicKeyExponent, STBGlobal.PINPublicKeyModulus, RN, Hash);
        if (result.length > 1) {
            dict.put(KEYPKCS1PARAMETER, result[0]);
            dict.put(KEYPASSWORDENCRYPTED, result[1]);
            dict.put(KEYRN, RN);
        }
        return dict;
    }

    private static Map<String, String> getPINEncrypted(String OldPassword, String NewPassword) {
        Map<String, String> dict = new HashMap<String, String>();
        String RN = STBGlobal.randomNumber();
        String Hash = "SHA-1";
        String[] result = PinRSA.Encrypt(OldPassword, NewPassword, STBGlobal.PINPublicKeyExponent, STBGlobal.PINPublicKeyModulus, RN, Hash);
        if (result.length > 1) {
            dict.put(KEYPKCS1PARAMETER, result[0]);
            dict.put(KEYPASSWORDENCRYPTED, result[1]);
            dict.put(KEYRN, RN);
        }
        return dict;
    }

    public static void requestHandshake(final String deviceID, final ApiResponse<AppSessionHandshake> listener) {
        // Handshake with Sacombank's API
        STBAppClient.handshake(deviceID, "Vi", new ApiResponse<AppSessionHandshake>() {
            @Override
            public void onSuccess(AppSessionHandshake result) {
                System.out.println("AppSessionHandshake handshake = " + new Gson().toJson(result));
                System.out.println("AppSessionHandshake SessionKey = " + result.SessionKey);
                System.out.println("sessionHandshake.RespCode 2 = " + result.RespCode);
                System.out.println("sessionHandshake.Description = " + result.Description);
                if (result.RespCode != null && result.RespCode.equals("00")) {
                    STBGlobal.SessionTimeout = result.SessionTimeout;
                }

                listener.onSuccess(result);
//                testAPILogin();
            }

            @Override
            public void onError(AppSessionHandshake error) {
                System.out.println("error handshake = " + new Gson().toJson(error));
                listener.onError(error);
            }
        });
    }

    public static void requestHomeData(ConsumerPublicHomeInquiry object, final ApiResponse<ConsumerPublicHomeInquiry> apiResponse) {
        System.out.print("In::requestHomeData\n");
        if (object != null) {
            object.DeviceID = mDeviceID;
            object.LanguageID = mLanguageID;
            object.RefNumber = mRefNumber;
            STBAppClient.send(object, new ApiResponse<ConsumerPublicHomeInquiry>() {
                @Override
                public void onSuccess(ConsumerPublicHomeInquiry result) {
                    String ketqua = new Gson().toJson(result);
                    Log.e(TAG, "requestHomeData - onSuccess: >>>" + ketqua.length());
                    apiResponse.onSuccess(result);
                }

                @Override
                public void onError(ConsumerPublicHomeInquiry error) {
                    Log.e(TAG, "requestHomeData - onError: >>>" + new Gson().toJson(error));
                    apiResponse.onError(error);
                }
            });
        }
        System.out.print("Out::requestHomeData\n");
    }


    /*** Begin: Register ***/
    public static void requestConsumerCardVerification(final ConsumerCardVerification object, final ApiResponse<ConsumerCardVerification> listener) {
        System.out.print("In::requestConsumerCardVerification\n");
        if (object != null) {
            object.DeviceID = mDeviceID;
            object.RefNumber = mRefNumber;
            object.LanguageID = mLanguageID;
            object.CVV = TripleDESCrypto.encrypt(object.CVV, STBAppClient.getSessionKey());
            mCardNumber = object.CardNumber;
            mCardToken = object.CardToken;
            mMobileNo = object.MobileNo;
            STBAppClient.send(object, new ApiResponse<ConsumerCardVerification>() {
                @Override
                public void onSuccess(ConsumerCardVerification result) {
                    Log.e(TAG, "onSuccess: ConsumerCardVerification>>>" + new Gson().toJson(result));
                    mAuthType = result.AuthType;
                    listener.onSuccess(result);
                }

                @Override
                public void onError(ConsumerCardVerification error) {
                    listener.onError(error);
                }
            });
        }
        System.out.print("Out::requestConsumerCardVerification\n");
    }

    public static void requestConsumerAuthCodeVerification(final ConsumerAuthCodeVerification object, final ApiResponse<ConsumerAuthCodeVerification> listener) {
        System.out.print("In::requestConsumerAuthCodeVerification\n");
        if (object != null) {
            Log.d(TAG, "mMobileNo = " + mMobileNo);
            Log.d(TAG, "mAuthType = " + mAuthType);
            Log.d(TAG, "mCardNumber = " + mCardNumber);
            Log.d(TAG, "mCardToken = " + mCardToken);
            object.DeviceID = mDeviceID;
            object.RefNumber = mRefNumber;
            object.LanguageID = mLanguageID;
            object.MobileNo = mMobileNo;
            object.CardNumber = mCardNumber;
            object.CardToken = mCardToken;
            object.AuthType = mAuthType;
            Map<String, String> dict = getPINEncrypted(object.AuthCode);
            object.PKCS1Parameter = dict.get(KEYPKCS1PARAMETER);
            object.PINEncrypted = dict.get(KEYPASSWORDENCRYPTED);
            object.RN = dict.get(KEYRN);

            STBAppClient.send(object, new ApiResponse<ConsumerAuthCodeVerification>() {
                @Override
                public void onSuccess(ConsumerAuthCodeVerification result) {
                    listener.onSuccess(result);
                }

                @Override
                public void onError(ConsumerAuthCodeVerification error) {
                    listener.onError(error);
                }
            });
        }
        System.out.print("Out::requestConsumerAuthCodeVerification\n");
    }

    public static void requestConsumerPasswordCreation(final ConsumerPasswordCreation object, final ApiResponse<ConsumerPasswordCreation> listener) {
        System.out.print("In::requestConsumerPasswordCreation\n");
        if (object != null) {
            Log.d(TAG, "mMobileNo = " + mMobileNo);
            object.DeviceID = mDeviceID;
            object.RefNumber = mRefNumber;
            object.LanguageID = mLanguageID;
            object.Platform = mPlatform;
            object.OSVersion = mOSVersion;
            object.Model = mModel;
            object.MobileNo = mMobileNo;
            Map<String, String> dict = getPINEncrypted(object.Password);
            object.PKCS1Parameter = dict.get(KEYPKCS1PARAMETER);
            object.PasswordEncrypted = dict.get(KEYPASSWORDENCRYPTED);
            object.RN = dict.get(KEYRN);
            object.Password = "";
            STBAppClient.send(object, new ApiResponse<ConsumerPasswordCreation>() {
                @Override
                public void onSuccess(ConsumerPasswordCreation result) {
                    listener.onSuccess(result);
                }

                @Override
                public void onError(ConsumerPasswordCreation error) {
                    listener.onError(error);
                }
            });
        }
        System.out.print("Out::requestConsumerPasswordCreation\n");
    }
    /*** End: Register ***/
    /*** Change pass ***/
    public static void requestConsumerPasswordChange(final ConsumerPasswordChange object, final ApiResponse<ConsumerPasswordChange> listener) {
        System.out.print("In::requestConsumerPasswordChange\n");
        if (object != null) {
            object.DeviceID = mDeviceID;
            object.RefNumber = mRefNumber;
            object.LanguageID = mLanguageID;
            System.out.println("mMobileNo = " + mMobileNo);
            object.MobileNo = mMobileNo;
            Map<String, String> dict = getPINEncrypted(object.OldPassword, object.NewPassword);
            object.PKCS1Parameter = dict.get(KEYPKCS1PARAMETER);
            object.PasswordEncrypted = dict.get(KEYPASSWORDENCRYPTED);
            object.RN = dict.get(KEYRN);
            object.OldPassword = "";
            object.NewPassword = "";
            STBAppClient.send(object, new ApiResponse<ConsumerPasswordChange>() {
                @Override
                public void onSuccess(ConsumerPasswordChange result) {
                    listener.onSuccess(result);
                }

                @Override
                public void onError(ConsumerPasswordChange error) {
                    listener.onError(error);
                }
            });
        }
        System.out.print("Out::requestConsumerPasswordChange\n");
    }

    /*** Login ***/
    public static void requestConsumerLogin(final ConsumerAccountLogin object, final ApiResponse<ConsumerAccountLogin> listener) {
        System.out.print("In::requestConsumerLogin\n");
        if (object != null) {
            object.DeviceID = mDeviceID;
            object.RefNumber = mRefNumber;
            object.LanguageID = mLanguageID;
            Map<String, String> dict = getPINEncrypted(object.Password);
            object.PKCS1Parameter = dict.get(KEYPKCS1PARAMETER);
            object.PasswordEncrypted = dict.get(KEYPASSWORDENCRYPTED);
            object.RN = dict.get(KEYRN);
            object.Password = "";
            mPasswordEncrypted = object.PasswordEncrypted;
            mPKCS1Parameter = object.PKCS1Parameter;
            mRN = object.RN;
            STBAppClient.send(object, new ApiResponse<ConsumerAccountLogin>() {
                @Override
                public void onSuccess(ConsumerAccountLogin result) {
                    System.out.println("onSuccess: >>> ConsumerAccountLogin = " + new Gson().toJson(result));
                    mAccountObject = result.AccountObject;
                    mMobileNo = mAccountObject.MobileNo;
                    mCardObjectList = result.CardObject;
                    mSuggestionObjectList = result.SuggestionObject;
                    listener.onSuccess(result);
                }

                @Override
                public void onError(ConsumerAccountLogin error) {
                    Log.e(TAG, "onError: >>> ConsumerAccountLogin = " + new Gson().toJson(error));
                    listener.onError(error);
                }
            });
        }

        System.out.print("Out::requestConsumerLogin\n");
    }

    /*** Transaction history ***/
    public static void requestConsumerCardMiniStatement(final ConsumerCardMiniStatement object, final ApiResponse<ConsumerCardMiniStatement> listener) {
        System.out.print("In::requestConsumerCardMiniStatment\n");
        if (object != null) {
            object.DeviceID = mDeviceID;
            object.RefNumber = mRefNumber;
            object.LanguageID = mLanguageID;
            STBAppClient.send(object, new ApiResponse<ConsumerCardMiniStatement>() {
                @Override
                public void onSuccess(ConsumerCardMiniStatement result) {
                    listener.onSuccess(result);
                }

                @Override
                public void onError(ConsumerCardMiniStatement error) {
                    listener.onError(error);
                }
            });
        }
        System.out.print("Out::requestConsumerCardMiniStatment\n");
    }

    /*** QR Code Payment***/
    public static void requestConsumerQRPayment(final ConsumerQRPayment object, final ApiResponse<ConsumerQRPayment> listener) {
        System.out.print("In::requestConsumerQRPayment\n");
        if (object != null) {
            object.DeviceID = mDeviceID;
            object.RefNumber = mRefNumber;
            object.LanguageID = mLanguageID;
            Map<String, String> dict = getPINEncrypted(object.ConfirmPassword);
            object.PKCS1Parameter = dict.get(KEYPKCS1PARAMETER);
            object.PasswordEncrypted = dict.get(KEYPASSWORDENCRYPTED);
            object.RN = dict.get(KEYRN);
            object.ConfirmPassword = "";
            STBAppClient.send(object, new ApiResponse<ConsumerQRPayment>() {
                @Override
                public void onSuccess(ConsumerQRPayment result) {
                    listener.onSuccess(result);
                }

                @Override
                public void onError(ConsumerQRPayment error) {
                    listener.onError(error);
                }
            });
        }
        System.out.print("Out::requestConsumerQRPayment\n");
    }

    /*** Begin: Manage Cards ***/
    public static void requestConsumerCardUpdate(final ConsumerCardUpdate object, final ApiResponse<ConsumerCardUpdate> listener) {
        System.out.print("In::requestConsumerCardUpdate\n");
        if (object != null) {
            object.DeviceID = mDeviceID;
            object.RefNumber = mRefNumber;
            object.LanguageID = mLanguageID;
            STBAppClient.send(object, new ApiResponse<ConsumerCardUpdate>() {
                @Override
                public void onSuccess(ConsumerCardUpdate result) {
                    listener.onSuccess(result);
                }

                @Override
                public void onError(ConsumerCardUpdate error) {
                    listener.onError(error);
                }
            });
        }
        System.out.print("Out::requestConsumerCardUpdate\n");
    }

    public static void requestConsumerCardMaintenance(final ConsumerCardMaintenance object, final ApiResponse<ConsumerCardMaintenance> listener) {
        System.out.print("In::requestConsumerCardMaintenance\n");
        if (object != null) {
            object.DeviceID = mDeviceID;
            object.RefNumber = mRefNumber;
            object.LanguageID = mLanguageID;
            Map<String, String> dict = getPINEncrypted(object.Password);
            object.PKCS1Parameter = dict.get(KEYPKCS1PARAMETER);
            object.PasswordEncrypted = dict.get(KEYPASSWORDENCRYPTED);
            object.RN = dict.get(KEYRN);
            object.Password = "";
            STBAppClient.send(object, new ApiResponse<ConsumerCardMaintenance>() {
                @Override
                public void onSuccess(ConsumerCardMaintenance result) {
                    listener.onSuccess(result);
                }

                @Override
                public void onError(ConsumerCardMaintenance error) {
                    listener.onError(error);
                }
            });
        }
        System.out.print("Out::requestConsumerCardMaintenance\n");
    }

    public static void requestConsumerCardBalanceInquiry(final ConsumerCardBalanceInquiry object, final ApiResponse<ConsumerCardBalanceInquiry> listener) {
        System.out.print("In::requestConsumerCardBalanceInquiry\n");
        if (object != null) {
            object.DeviceID = mDeviceID;
            object.RefNumber = mRefNumber;
            object.LanguageID = mLanguageID;
            STBAppClient.send(object, new ApiResponse<ConsumerCardBalanceInquiry>() {
                @Override
                public void onSuccess(ConsumerCardBalanceInquiry result) {
                    listener.onSuccess(result);
                }

                @Override
                public void onError(ConsumerCardBalanceInquiry error) {
                    listener.onError(error);
                }
            });
        }
        System.out.print("Out::requestConsumerCardBalanceInquiry\n");
    }

    public static void requestConsumerStatementInquiry(final ConsumerStatementInquiry object, final ApiResponse<ConsumerStatementInquiry> listener) {
        System.out.print("In::requestConsumerStatementInquiry\n");
        if (object != null) {
            object.DeviceID = mDeviceID;
            object.RefNumber = mRefNumber;
            object.LanguageID = mLanguageID;
            STBAppClient.send(object, new ApiResponse<ConsumerStatementInquiry>() {
                @Override
                public void onSuccess(ConsumerStatementInquiry result) {
                    listener.onSuccess(result);
                }

                @Override
                public void onError(ConsumerStatementInquiry error) {
                    listener.onError(error);
                }
            });
        }
        System.out.print("Out::requestConsumerStatementInquiry\n");
    }
    /*** End: Manage Cards ***/
    /*** Detail Article ***/
    public static void requestConsumerPromotionDetails(final ConsumerPromotionDetails object, final ApiResponse<ConsumerPromotionDetails> listener) {
        System.out.print("In::requestConsumerPromotionDetails\n");
        if (object != null) {
            object.DeviceID = mDeviceID;
            object.RefNumber = mRefNumber;
            object.LanguageID = mLanguageID;
            STBAppClient.send(object, new ApiResponse<ConsumerPromotionDetails>() {
                @Override
                public void onSuccess(ConsumerPromotionDetails result) {
                    listener.onSuccess(result);
                }

                @Override
                public void onError(ConsumerPromotionDetails error) {
                    listener.onError(error);
                }
            });
        }
        System.out.print("Out::requestConsumerPromotionDetails\n");
    }

    /*** update account information ***/
    public static void requestConsumerAccountUpdate(final ConsumerAccountUpdate object, final ApiResponse<ConsumerAccountUpdate> listener) {
        System.out.print("In::requestConsumerAccountUpdate\n");
        if (object != null) {
            object.DeviceID = mDeviceID;
            object.RefNumber = mRefNumber;
            object.LanguageID = mLanguageID;
            object.MobileNo = mMobileNo;
            STBAppClient.send(object, new ApiResponse<ConsumerAccountUpdate>() {
                @Override
                public void onSuccess(ConsumerAccountUpdate result) {
                    listener.onSuccess(result);
                }

                @Override
                public void onError(ConsumerAccountUpdate error) {
                    listener.onError(error);
                }
            });
        }
        System.out.print("Out::requestConsumerAccountUpdate\n");
    }
    /*** unit test *******************************************************************************************/
    /*** unit test *******************************************************************************************/

    /*** Begin:Register ***/
    static void testConsumerCardVerification() {
        ConsumerCardVerification object = new ConsumerCardVerification();
        object.MobileNo = "0916394885";
        object.CardNumber = "9704034915266703";
        object.CVV = "087";
        object.CardToken = "";
        object.ExpiryDate = "";
        requestConsumerCardVerification(object, new ApiResponse<ConsumerCardVerification>() {
            @Override
            public void onSuccess(ConsumerCardVerification result) {
                Log.e(TAG, "onSuccess: ConsumerCardVerification >>>" + new Gson().toJson(result));
                testConsumerAuthCodeVerification();
            }

            @Override
            public void onError(ConsumerCardVerification error) {
                Log.e(TAG, "onError: ConsumerCardVerification >>>" + new Gson().toJson(error));

            }
        });
    }

    static void testConsumerAuthCodeVerification() {
        ConsumerAuthCodeVerification object = new ConsumerAuthCodeVerification();
        object.AuthCode = "123456";
        requestConsumerAuthCodeVerification(object, new ApiResponse<ConsumerAuthCodeVerification>() {
            @Override
            public void onSuccess(ConsumerAuthCodeVerification result) {
                Log.e(TAG, "onSuccess: ConsumerAuthCodeVerification >>>" + new Gson().toJson(result));
                testConsumerPasswordCreation();
            }

            @Override
            public void onError(ConsumerAuthCodeVerification error) {
                Log.e(TAG, "onError: ConsumerAuthCodeVerification >>>" + new Gson().toJson(error));

            }
        });
    }

    static void testConsumerPasswordCreation() {
        ConsumerPasswordCreation object = new ConsumerPasswordCreation();
        object.Password = "666666";
        requestConsumerPasswordCreation(object, new ApiResponse<ConsumerPasswordCreation>() {
            @Override
            public void onSuccess(ConsumerPasswordCreation result) {
                Log.e(TAG, "onSuccess: ConsumerPasswordCreation >>>" + new Gson().toJson(result));
                testAPILogin();
            }

            @Override
            public void onError(ConsumerPasswordCreation error) {
                Log.e(TAG, "onError: ConsumerPasswordCreation >>>" + new Gson().toJson(error));

            }
        });
    }
    /*** End:Register ***/
    /*** Change Password ***/
    static void testConsumerPasswordChange() {
        ConsumerPasswordChange object = new ConsumerPasswordChange();
        object.OldPassword = "666666";
        object.NewPassword = "777777";

        requestConsumerPasswordChange(object, new ApiResponse<ConsumerPasswordChange>() {
            @Override
            public void onSuccess(ConsumerPasswordChange result) {

            }

            @Override
            public void onError(ConsumerPasswordChange error) {

            }
        });
    }

    /*** Login ***/
    static void testAPILogin() {
        ConsumerAccountLogin object = new ConsumerAccountLogin();
        object.MobileNo = "0916394885";
        object.Password = "777777";
        object.FirebaseToken = "asfwtkm9-hyh45yeybnr2353589&*)^9685544wyewy";
        requestConsumerLogin(object, new ApiResponse<ConsumerAccountLogin>() {
            @Override
            public void onSuccess(ConsumerAccountLogin result) {
                Log.e(TAG, "onSuccess: ConsumerAccountLogin >>>" + new Gson().toJson(result));
                testConsumerStatementInquiry();
            }

            @Override
            public void onError(ConsumerAccountLogin error) {

                Log.e(TAG, "onError: ConsumerAccountLogin >>>" + new Gson().toJson(error));
            }
        });
    }

    /*** Home ***/
    static void testAPIHome() {
        ConsumerPublicHomeInquiry object = new ConsumerPublicHomeInquiry();
        requestHomeData(object, new ApiResponse<ConsumerPublicHomeInquiry>() {
            @Override
            public void onSuccess(ConsumerPublicHomeInquiry result) {

            }

            @Override
            public void onError(ConsumerPublicHomeInquiry error) {

            }
        });
    }

    /*** Transaction history ***/
    static void testConsumerCardMiniStatement() {
        ConsumerCardMiniStatement object = new ConsumerCardMiniStatement();
        object.CardToken = "3056199398";
        requestConsumerCardMiniStatement(object, new ApiResponse<ConsumerCardMiniStatement>() {
            @Override
            public void onSuccess(ConsumerCardMiniStatement result) {
                Log.e(TAG, "onSuccess: ConsumerCardMiniStatement " + new Gson().toJson(result));
            }

            @Override
            public void onError(ConsumerCardMiniStatement error) {
                Log.e(TAG, "onError: ConsumerCardMiniStatement " + new Gson().toJson(error));
            }
        });
    }

    /*** Payment ***/
    static void testConsumerQRPayment() {
        ConsumerQRPayment object = new ConsumerQRPayment();
        object.CardNumber = "47899000022";
        object.CardToken = "123455";
        object.BillNumber = "124433322";
        object.Description = "Test";
        object.mVisaMID = "5567777";
        object.MasterPassQRID = "803445555";
        object.Tips = "1";
        object.MCC = "1233";
        object.Amount = "2";
        object.Fee = "1";
        object.CurrencyCode = "01";
        object.MerchantName = "AAA";
        object.MerchantCity = "HCM";
        object.MerchantCountry = "VN";
        requestConsumerQRPayment(object, new ApiResponse<ConsumerQRPayment>() {
            @Override
            public void onSuccess(ConsumerQRPayment result) {

            }

            @Override
            public void onError(ConsumerQRPayment error) {

            }
        });
    }

    /*** Manage Card ***/
    static void testConsumerCardUpdate() {
        ConsumerCardUpdate object = new ConsumerCardUpdate();
        CardObject cardObject = getCardObjectList()[1];
        object.CardNumber = cardObject.CardNumber;
        object.CardToken = cardObject.CardToken;
        object.DefaultIndicator = true;//turn on/off "Thẻ mặc định" trong màn hình thông tin chung của Quản Lý Thẻ
        requestConsumerCardUpdate(object, new ApiResponse<ConsumerCardUpdate>() {
            @Override
            public void onSuccess(ConsumerCardUpdate result) {
                testConsumerCardMaintenance();
            }

            @Override
            public void onError(ConsumerCardUpdate error) {

            }
        });
    }

    static void testConsumerCardMaintenance() {
        ConsumerCardMaintenance object = new ConsumerCardMaintenance();
        CardObject cardObject = getCardObjectList()[0];
        object.CardStatus = CardStatus.INACTIVE.toString();//turn on/off "Thẻ hoạt động" trong màn hình chung cùa Quản Lý
        object.CardNumber = cardObject.CardNumber;
        object.CardToken = cardObject.CardToken;
        object.Password = "777777";
        requestConsumerCardMaintenance(object, new ApiResponse<ConsumerCardMaintenance>() {
            @Override
            public void onSuccess(ConsumerCardMaintenance result) {
                testConsumerCardBalanceInquiry();
            }

            @Override
            public void onError(ConsumerCardMaintenance error) {

            }
        });
    }

    public static void testConsumerCardBalanceInquiry() {
        ConsumerCardBalanceInquiry object = new ConsumerCardBalanceInquiry();
        CardObject cardObject = getCardObjectList()[0];
        object.CardNumber = cardObject.CardNumber;
        object.CardToken = cardObject.CardToken;
        requestConsumerCardBalanceInquiry(object, new ApiResponse<ConsumerCardBalanceInquiry>() {
            @Override
            public void onSuccess(ConsumerCardBalanceInquiry result) {
                Log.e(TAG, "onSuccess: >>>" + new Gson().toJson(result));
            }

            @Override
            public void onError(ConsumerCardBalanceInquiry error) {
                Log.e(TAG, "onError: >>>" + new Gson().toJson(error));
            }
        });
    }

    static void testConsumerStatementInquiry() {
        ConsumerStatementInquiry object = new ConsumerStatementInquiry();
        CardObject cardObject = getCardObjectList()[1];
        object.CardToken = "3333784621";//cardObject.CardToken;
        object.BillId = "20150417";
        requestConsumerStatementInquiry(object, new ApiResponse<ConsumerStatementInquiry>() {
            @Override
            public void onSuccess(ConsumerStatementInquiry result) {

            }

            @Override
            public void onError(ConsumerStatementInquiry error) {

            }
        });
    }

    static void testConsumerAccountUpdate() {
        ConsumerAccountUpdate object = new ConsumerAccountUpdate();
        AccountObject accountObject = new AccountObject();
        accountObject.SSN = "023857phong5";
        accountObject.TypeOfSSN = TypeOfSSN.PASSPORT.toString();
        accountObject.Email = "truong.nguyen@akadigital.vn";
        object.AccountObject = accountObject;
        requestConsumerAccountUpdate(object, new ApiResponse<ConsumerAccountUpdate>() {
            @Override
            public void onSuccess(ConsumerAccountUpdate result) {

            }

            @Override
            public void onError(ConsumerAccountUpdate error) {

            }
        });
    }

    static void testConsumerPromotionDetails() {
        ConsumerPromotionDetails object = new ConsumerPromotionDetails();
        object.PromotionID = 9;
        requestConsumerPromotionDetails(object, new ApiResponse<ConsumerPromotionDetails>() {
            @Override
            public void onSuccess(ConsumerPromotionDetails result) {

            }

            @Override
            public void onError(ConsumerPromotionDetails error) {

            }
        });
    }
}