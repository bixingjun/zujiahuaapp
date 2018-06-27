package com.example.zujianhuaapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zujianhuaapp.base.BaseFragment;
import com.example.zujianhuaapp.home.fragment.HomeFragment;
import com.example.zujianhuaapp.message.fragment.MessageFragment;
import com.example.zujianhuaapp.mine.fragment.MineFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    private static final int REQUEST_QRCODE = 0x01;

    private RelativeLayout mHomeLayout;
    private RelativeLayout mPondLayout;
    private RelativeLayout mMessageLayout;
    private RelativeLayout mMineLayout;
    private TextView mHomeView;
    private TextView mPondView;
    private TextView mMessageView;
    private TextView mMineView;
    private int position=0;
    private ArrayList<BaseFragment> fragments;
    private BaseFragment Fragment;
    private BaseFragment baseFragment=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_layout);
        initView();
        initFragment();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new MessageFragment());
        fragments.add(new MineFragment());

         baseFragment = getFragment(position);

        switchFragment(Fragment, baseFragment);
    }

    private void initView() {
        mHomeLayout = (RelativeLayout) findViewById(R.id.home_layout_view);
        mHomeLayout.setOnClickListener(this);
        mPondLayout = (RelativeLayout) findViewById(R.id.pond_layout_view);
        mPondLayout.setOnClickListener(this);
        mMessageLayout = (RelativeLayout) findViewById(R.id.message_layout_view);
        mMessageLayout.setOnClickListener(this);
        mMineLayout = (RelativeLayout) findViewById(R.id.mine_layout_view);
        mMineLayout.setOnClickListener(this);

        mHomeView = (TextView) findViewById(R.id.home_image_view);
        mPondView = (TextView) findViewById(R.id.fish_image_view);
        mMessageView = (TextView) findViewById(R.id.message_image_view);
        mMineView = (TextView) findViewById(R.id.mine_image_view);
        mHomeView.setBackgroundResource(R.drawable.comui_tab_home_selected);
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.home_layout_view:
                mHomeView.setBackgroundResource(R.drawable.comui_tab_home_selected);
                mPondView.setBackgroundResource(R.drawable.comui_tab_pond);
                mMessageView.setBackgroundResource(R.drawable.comui_tab_message);
                mMineView.setBackgroundResource(R.drawable.comui_tab_person);
                position = 0;
                baseFragment= getFragment(position);
                switchFragment(Fragment, baseFragment);
                break;
            case R.id.message_layout_view:
                mMessageView.setBackgroundResource(R.drawable.comui_tab_message_selected);
                mHomeView.setBackgroundResource(R.drawable.comui_tab_home);
                mPondView.setBackgroundResource(R.drawable.comui_tab_pond);
                mMineView.setBackgroundResource(R.drawable.comui_tab_person);
                position = 1;
                 baseFragment = getFragment(position);
                switchFragment(Fragment, baseFragment);
                break;
            case R.id.mine_layout_view:
                mMineView.setBackgroundResource(R.drawable.comui_tab_person_selected);
                mHomeView.setBackgroundResource(R.drawable.comui_tab_home);
                mPondView.setBackgroundResource(R.drawable.comui_tab_pond);
                mMessageView.setBackgroundResource(R.drawable.comui_tab_message);
                position = 2;
                 baseFragment = getFragment(position);
                switchFragment(Fragment, baseFragment);

                break;
        }


    }

    private BaseFragment getFragment(int position) {
        if (fragments != null && fragments.size() > 0) {
            BaseFragment baseFragment = fragments.get(position);
            return baseFragment;
        }
        return null;
    }

    private void switchFragment(Fragment fromFragment, BaseFragment nextFragment) {
        if (Fragment != nextFragment) {
            Fragment = nextFragment;
            if (nextFragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //判断nextFragment是否添加
                if (!nextFragment.isAdded()) {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.add(R.id.content_layout, nextFragment).commit();
                } else {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.show(nextFragment).commit();
                }
            }
        }
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

        if (requestCode == REQUEST_QRCODE && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra("SCAN_RESULT");
                Log.i("HomeFragment",content);
                Toast.makeText(MainActivity.this, content, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
