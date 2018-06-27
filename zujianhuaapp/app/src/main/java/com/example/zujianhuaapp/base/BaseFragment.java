package com.example.zujianhuaapp.base;
/*
 *  包名: com.example.zujianhuaapp.base
 * Created by ASUS on 2018/2/15.
 *  描述: TODO
 */

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zujianhuaapp.utils.Constant;

public abstract class BaseFragment extends Fragment {

    public Context mcontext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mcontext=getActivity();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return initView();
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();

    }

    public void initData() {

    }

    public abstract View initView() ;



    /**
     * 申请指定的权限.
     */
    public void requestPermission(int code, String... permissions) {

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(permissions, code);
        }
    }

    /**
     * 判断是否有指定的权限
     */
    public boolean hasPermission(String... permissions) {

        for (String permisson : permissions) {
            if (ContextCompat.checkSelfPermission(getActivity(), permisson)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Constant.HARDWEAR_CAMERA_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doOpenCamera();
                }
                break;
            case Constant.WRITE_READ_EXTERNAL_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doWriteSDCard();
                }
                break;
        }
    }

    public void doOpenCamera() {

    }

    public void doWriteSDCard() {

    }
}
