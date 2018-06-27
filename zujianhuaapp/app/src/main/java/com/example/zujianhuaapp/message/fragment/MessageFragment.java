package com.example.zujianhuaapp.message.fragment;
/*
 *  包名: com.example.zujianhuaapp.home.fragment
 * Created by ASUS on 2018/2/15.
 *  描述: TODO
 */

import android.view.View;
import android.widget.TextView;

import com.example.zujianhuaapp.base.BaseFragment;

public class MessageFragment extends BaseFragment{

    private TextView textView;

    @Override
    public View initView() {
        textView=new TextView(mcontext);
        textView.setTextSize(18);

        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("MessageFragment");
    }

}
