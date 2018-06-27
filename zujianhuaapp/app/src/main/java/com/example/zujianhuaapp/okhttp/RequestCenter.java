package com.example.zujianhuaapp.okhttp;


import com.example.zujianhuaapp.module.recommand.BaseRecommandModel;
import com.example.zujianhuaapp.module.update.UpdateModel;
import com.example.zujianhuaapp.module.user.User;
import com.example.zujianhuaapp.okhttp.listener.DisposeDataHandle;
import com.example.zujianhuaapp.okhttp.listener.DisposeDataListener;
import com.example.zujianhuaapp.okhttp.request.CommonRequest;
import com.example.zujianhuaapp.okhttp.request.RequestParams;

/**
 * Created by renzhiqiang on 16/10/27.
 *
 * @function sdk请求发送中心
 */
public class RequestCenter {


    //根据参数发送所有post请求
    public static void postRequest(String url, RequestParams params, DisposeDataListener listener, Class<?> clazz) {
        CommonOkHttpClient.get(CommonRequest.
                createGetRequest(url, params), new DisposeDataHandle(listener, clazz));
    }

    public static void requestRecommandData(DisposeDataListener listener) {
        RequestCenter.postRequest(HttpConstants.HOME_RECOMMAND, null, listener, BaseRecommandModel.class);
    }

    /**
     * 应用版本号请求
     *
     * @param listener
     */
    public static void checkVersion(DisposeDataListener listener) {
        RequestCenter.postRequest(HttpConstants.CHECK_UPDATE, null, listener, UpdateModel.class);
    }

    public static void login(String userName, String passwd, DisposeDataListener listener) {

        RequestParams params = new RequestParams();
        params.put("mb", userName);
        params.put("pwd", passwd);
        RequestCenter.postRequest(HttpConstants.LOGIN, params, listener, User.class);
    }



}
