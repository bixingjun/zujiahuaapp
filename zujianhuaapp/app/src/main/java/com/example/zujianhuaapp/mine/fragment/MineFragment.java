package com.example.zujianhuaapp.mine.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;


import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zujianhuaapp.R;
import com.example.zujianhuaapp.base.BaseFragment;
import com.example.zujianhuaapp.manager.UserManager;
import com.example.zujianhuaapp.mine.activity.LoginActivity;
import com.example.zujianhuaapp.mine.activity.SettingActivity;
import com.example.zujianhuaapp.module.update.UpdateModel;
import com.example.zujianhuaapp.okhttp.RequestCenter;
import com.example.zujianhuaapp.okhttp.listener.DisposeDataListener;
import com.example.zujianhuaapp.service.update.UpdateService;
import com.example.zujianhuaapp.utils.Constant;
import com.example.zujianhuaapp.utils.ImageLoaderUtil;
import com.example.zujianhuaapp.utils.Util;
import com.example.zujianhuaapp.view.CommonDialog;
import com.example.zujianhuaapp.view.MyQrCodeDialog;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author: vision
 * @function: 个人信息fragment
 * @date: 16/7/14
 */
public class MineFragment extends BaseFragment
        implements OnClickListener {

    /**
     * UI
     */
    private View mContentView;
    private RelativeLayout mLoginLayout;
    private CircleImageView mPhotoView;
    private TextView mLoginInfoView;
    private TextView mLoginView;
    private RelativeLayout mLoginedLayout;
    private TextView mUserNameView;
    private TextView mTickView;
    private TextView mVideoPlayerView;
    private TextView mShareView;
    private TextView mQrCodeView;
    private TextView mUpdateView;

    //自定义了一个广播接收器
    private LoginBroadcastReceiver receiver =
            new LoginBroadcastReceiver();

    public MineFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registerBroadcast();
    }

    @Override
    public View initView() {
        mContentView = View.inflate(mcontext,R.layout.fragment_mine_layout, null);
            mLoginLayout = (RelativeLayout) mContentView.findViewById(R.id.login_layout);
            mLoginLayout.setOnClickListener(this);
            mLoginedLayout = (RelativeLayout) mContentView.findViewById(R.id.logined_layout);
            mLoginedLayout.setOnClickListener(this);

            mPhotoView = (CircleImageView) mContentView.findViewById(R.id.photo_view);
            mPhotoView.setOnClickListener(this);
            mLoginView = (TextView) mContentView.findViewById(R.id.login_view);
            mLoginView.setOnClickListener(this);
            mVideoPlayerView = (TextView) mContentView.findViewById(R.id.video_setting_view);
            mVideoPlayerView.setOnClickListener(this);
            mShareView = (TextView) mContentView.findViewById(R.id.share_imooc_view);
            mShareView.setOnClickListener(this);
            mQrCodeView = (TextView) mContentView.findViewById(R.id.my_qrcode_view);
            mQrCodeView.setOnClickListener(this);
            mLoginInfoView = (TextView) mContentView.findViewById(R.id.login_info_view);
            mUserNameView = (TextView) mContentView.findViewById(R.id.username_view);
            mTickView = (TextView) mContentView.findViewById(R.id.tick_view);

            mUpdateView = (TextView) mContentView.findViewById(R.id.update_view);
            mUpdateView.setOnClickListener(this);
            return  mContentView;
    }


    @Override
    public void onResume() {
        super.onResume();
        //根据用户信息更新我们的fragment
        if (UserManager.getInstance().hasLogined()) {
            if (mLoginedLayout.getVisibility() == View.GONE) {
                mLoginLayout.setVisibility(View.GONE);
                mLoginedLayout.setVisibility(View.VISIBLE);
                mUserNameView.setText(UserManager.getInstance().getUser().data.name);
                mTickView.setText(UserManager.getInstance().getUser().data.tick);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcast();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_imooc_view:
                //分享Imooc课网址
                //shareFriend();
                break;
            case R.id.login_layout:
            case R.id.login_view:
                //未登陆，则跳轉到登陸页面
                if (!UserManager.getInstance().hasLogined()) {
                    toLogin();
                }
                break;
            case R.id.my_qrcode_view:
                if (!UserManager.getInstance().hasLogined()) {
                    //未登陆，去登陆。
                    toLogin();
                } else {
                    //已登陆根据用户ID生成二维码显示
                    MyQrCodeDialog dialog = new MyQrCodeDialog(mcontext);
                    dialog.show();
                }
                break;
            case R.id.video_setting_view:
                mcontext.startActivity(new Intent(mcontext, SettingActivity.class));
                break;
            case R.id.update_view:
                if (hasPermission(Constant.WRITE_READ_EXTERNAL_PERMISSION)) {
                    checkVersion();
                } else {
                    requestPermission(Constant.WRITE_READ_EXTERNAL_CODE, Constant.WRITE_READ_EXTERNAL_PERMISSION);
                }
                break;
        }
    }

    @Override
    public void doWriteSDCard() {
        checkVersion();
    }

    /**
     * 去登陆页面
     */
    private void toLogin() {
        Intent intent = new Intent(mcontext, LoginActivity.class);
        mcontext.startActivity(intent);
    }

    /**
     * 分享慕课网给好友
     */
   /* private void shareFriend() {
        ShareDialog dialog = new ShareDialog(mContext, false);
        dialog.setShareType(Platform.SHARE_IMAGE);
        dialog.setShareTitle("慕课网");
        dialog.setShareTitleUrl("http://www.imooc.com");
        dialog.setShareText("慕课网");
        dialog.setShareSite("imooc");
        dialog.setShareSiteUrl("http://www.imooc.com");
        dialog.setImagePhoto(Environment.getExternalStorageDirectory() + "/test2.jpg");
        dialog.show();
    }*/

    private void registerBroadcast() {

        IntentFilter filter =
                new IntentFilter(LoginActivity.LOGIN_ACTION);
        LocalBroadcastManager.getInstance(mcontext)
                .registerReceiver(receiver, filter);
    }

    private void unregisterBroadcast() {
        LocalBroadcastManager.getInstance(mcontext)
                .unregisterReceiver(receiver);
    }

    //发送版本检查更新请求
    private void checkVersion() {
        RequestCenter.checkVersion(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                final UpdateModel updateModel = (UpdateModel) responseObj;
                if (Util.getVersionCode(mcontext) < updateModel.data.currentVersion) {
                    //说明有新版本,开始下载
                    CommonDialog dialog = new CommonDialog(mcontext, getString(R.string.update_new_version),
                            getString(R.string.update_title), getString(R.string.update_install),
                            getString(R.string.cancel), new CommonDialog.DialogClickListener() {
                        @Override
                        public void onDialogClick() {
                            Intent intent = new Intent(mcontext, UpdateService.class);
                            mcontext.startService(intent);
                        }
                    });
                    dialog.show();
                } else {
                    //弹出一个toast提示当前已经是最新版本等处理
                }
            }

            @Override
            public void onFailure(Object reasonObj) {

            }
        });
    }

    /**
     * 接收mina发送来的消息，并更新UI
     */
    private class LoginBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (UserManager.getInstance().hasLogined()) {
                //更新我们的fragment
                if (mLoginedLayout.getVisibility() == View.GONE) {
                    mLoginLayout.setVisibility(View.GONE);
                    mLoginedLayout.setVisibility(View.VISIBLE);
                    mUserNameView.setText(UserManager.getInstance().getUser().data.name);
                    mTickView.setText(UserManager.getInstance().getUser().data.tick);
                    ImageLoaderUtil.getInstance(mcontext).displayImage(mPhotoView, UserManager.getInstance().getUser().data.photoUrl);
                }
            }
        }
    }
}
