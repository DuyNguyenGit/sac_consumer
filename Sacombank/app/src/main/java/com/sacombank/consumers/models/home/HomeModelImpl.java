package com.sacombank.consumers.models.home;

import android.util.Log;

import com.google.gson.Gson;
import com.sacombank.consumers.models.jsonobjects.User;
import com.sacombank.consumers.utils.Utils;
import com.stb.api.bo.consumer.ConsumerPublicHomeInquiry;
import com.stb.api.listeners.ApiResponse;

import static com.sacombank.consumers.api.ApiManager.requestHomeData;

/**
 * Created by DUY on 9/2/2017.
 */

public class HomeModelImpl implements HomeModel {

    private static final String TAG = HomeModelImpl.class.getSimpleName();
    HomeDataCallback homeDataCallback;

    public HomeModelImpl(HomeDataCallback homeDataCallback) {
        this.homeDataCallback = homeDataCallback;
    }

    @Override
    public void loadHomeData() {
        ConsumerPublicHomeInquiry object = new ConsumerPublicHomeInquiry();
        requestHomeData(object, new ApiResponse<ConsumerPublicHomeInquiry>() {
            @Override
            public void onSuccess(ConsumerPublicHomeInquiry result) {
                Utils.showLog(TAG, new Gson().toJson(result));
                homeDataCallback.onLoadHomeSuccess(result);
            }

            @Override
            public void onError(ConsumerPublicHomeInquiry error) {
                Log.e(TAG, "onError: >>>" + new Gson().toJson(error));
                homeDataCallback.onLoadHomeError(error);
            }
        });
    }


    private void onResult() {
        User user = new User("Nguyen Van Duy", "male", User.MALE);
        this.homeDataCallback.onLoadHomeSuccess(user);
    }

}
