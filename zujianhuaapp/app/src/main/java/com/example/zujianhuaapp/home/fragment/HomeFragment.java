package com.example.zujianhuaapp.home.fragment;
/*
 *  包名: com.example.zujianhuaapp.home.fragment
 * Created by ASUS on 2018/2/15.
 *  描述: TODO
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zujianhuaapp.R;
import com.example.zujianhuaapp.base.BaseFragment;
import com.example.zujianhuaapp.home.activity.AdBrowserActivity;
import com.example.zujianhuaapp.home.activity.PhotoViewActivity;
import com.example.zujianhuaapp.home.adapter.CourseAdapter;
import com.example.zujianhuaapp.module.recommand.BaseRecommandModel;
import com.example.zujianhuaapp.module.recommand.RecommandBodyValue;
import com.example.zujianhuaapp.okhttp.CommonOkHttpClient;
import com.example.zujianhuaapp.okhttp.RequestCenter;
import com.example.zujianhuaapp.okhttp.listener.DisposeDataHandle;
import com.example.zujianhuaapp.okhttp.listener.DisposeDataListener;
import com.example.zujianhuaapp.utils.Constant;
import com.example.zujianhuaapp.view.HomeHeaderLayout;


import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener {



    private static final int REQUEST_QRCODE = 0x01;
    /**
     * UI
     */
    private View mContentView;
    private ListView mListView;
    private TextView mQRCodeView;
    private TextView mCategoryView;
    private TextView mSearchView;
    private ImageView mLoadingView;
    private String TAG=HomeFragment.class.getSimpleName();
    private BaseRecommandModel mRecommandData;
    private CourseAdapter courseAdapter;


    @Override
    public View initView() {
        View mContentView = View.inflate(mcontext, R.layout.fragment_home_layout, null);
        mQRCodeView = (TextView) mContentView.findViewById(R.id.qrcode_view);
        mQRCodeView.setOnClickListener(this);
        mCategoryView = (TextView) mContentView.findViewById(R.id.category_view);
        mCategoryView.setOnClickListener(this);
        mSearchView = (TextView) mContentView.findViewById(R.id.search_view);
        mSearchView.setOnClickListener(this);
        mListView = (ListView) mContentView.findViewById(R.id.list_view);
        mListView.setOnItemClickListener(this);

        mLoadingView = (ImageView) mContentView.findViewById(R.id.loading_view);

        AnimationDrawable anim = (AnimationDrawable) mLoadingView.getDrawable();

        anim.start();

        return mContentView;
    }

    @Override
    public void initData() {
        super.initData();

        requestData();

    }

    private void requestData() {

        RequestCenter.requestRecommandData(new DisposeDataListener(){

            @Override
            public void onSuccess(Object responseObj) {

                Log.i("ssss",responseObj.toString());

                mRecommandData= (BaseRecommandModel) responseObj;

                /*BaseRecommandModel baseRecommandModel = new Gson().fromJson(responseObj.toString(), BaseRecommandModel.class);*/

                Log.i("sss",mRecommandData.toString());

                showSuccessView(mRecommandData);

            }

            @Override
            public void onFailure(Object reasonObj) {
                showErrorView();
            }
        });

    }

    private void showSuccessView(BaseRecommandModel mRecommandData) {

        if(mRecommandData.data.list!=null&&mRecommandData.data.list.size()>0){

            mLoadingView.setVisibility(View.GONE);

            mListView.setVisibility(View.VISIBLE);


            mListView.addHeaderView(new HomeHeaderLayout(mcontext, mRecommandData.data.head));
             courseAdapter = new CourseAdapter(mcontext, mRecommandData.data.list);
             mListView.setAdapter(courseAdapter);
             mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                 @Override
                 public void onScrollStateChanged(AbsListView view, int scrollState) {

                 }

                 @Override
                 public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                     courseAdapter.updateAdInScrollView();
                 }
             });

        }else {
            showErrorView();
        }
    }

    private void showErrorView() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.qrcode_view:
                if (hasPermission(Constant.HARDWEAR_CAMERA_PERMISSION)) {
                    doOpenCamera();
                } else {
                    requestPermission(Constant.HARDWEAR_CAMERA_CODE, Constant.HARDWEAR_CAMERA_PERMISSION);
                }
                break;
        }

    }


    @Override
    public void doOpenCamera() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*switch (requestCode) {
            case REQUEST_QRCODE:
                if (resultCode == Activity.RESULT_OK) {
                    String code = data.getStringExtra("SCAN_RESULT");
                    Log.i("HomeFragment",code);
                    code="http://www.baidu.com";
                    if (code.contains("http") || code.contains("https")) {

                        Intent intent = new Intent(mcontext, AdBrowserActivity.class);
                        intent.putExtra(AdBrowserActivity.KEY_URL, code);
                        startActivity(intent);
                    } else {
                        Toast.makeText(mcontext, code, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }*/



    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RecommandBodyValue value = (RecommandBodyValue) courseAdapter.getItem(position - mListView.getHeaderViewsCount());

        if(value.type!=0) {
            Intent intent = new Intent(mcontext, PhotoViewActivity.class);
            intent.putStringArrayListExtra(PhotoViewActivity.PHOTO_LIST, value.url);
            startActivity(intent);
        }
    }
}
