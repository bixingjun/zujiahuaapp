package com.example.zujianhuaapp.home.adapter;
/*
 *  包名: com.example.zujianhuaapp.home.adapter
 * Created by ASUS on 2018/6/15.
 *  描述: TODO
 */

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.shiping.VideoAdSlot;
import com.example.shiping.module.AdValue;
import com.example.shiping.utilds.LogUtils;
import com.example.zujianhuaapp.R;
import com.example.zujianhuaapp.home.activity.AdBrowserActivity;
import com.example.zujianhuaapp.home.activity.PhotoViewActivity;
import com.example.zujianhuaapp.module.recommand.RecommandBodyValue;
import com.example.zujianhuaapp.share.ShareDialog;
import com.example.zujianhuaapp.utils.ImageLoaderManager;
import com.example.zujianhuaapp.utils.Util;
import com.example.zujianhuaapp.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.sharesdk.framework.Platform;
import de.hdodenhof.circleimageview.CircleImageView;



public class CourseAdapter extends BaseAdapter{

    private static final int CARD_COUNT = 4;
    private static final int VIDOE_TYPE = 0x00;
    private static final int CARD_TYPE_ONE = 0x01;
    private static final int CARD_TYPE_TWO = 0x02;
    private static final int CARD_TYPE_THREE = 0x03;

    private ViewHolder mViewHolder;
    private Context context;
    private ArrayList<RecommandBodyValue> list;
    private LayoutInflater mInflate;

    private ImageLoaderManager mImagerLoader;
    private RecommandBodyValue value;
    private AdValue adValue;
    private VideoAdSlot videoAdSlot;


    public CourseAdapter(Context mcontext, ArrayList<RecommandBodyValue> list) {
        this.context=mcontext;
        this.list=list;
        mInflate = LayoutInflater.from(mcontext);
        mImagerLoader = ImageLoaderManager.getInstance(mcontext);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemViewType(int position) {

         value = (RecommandBodyValue) getItem(position);

        return value.type;
    }

    @Override
    public int getViewTypeCount() {
        return CARD_COUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int itemViewType = getItemViewType(position);

        if(convertView==null) {

            mViewHolder=new ViewHolder();

            switch (itemViewType) {
                case VIDOE_TYPE:
                    convertView = mInflate.inflate(R.layout.item_video_layout, parent, false);


                    mViewHolder.mVieoContentLayout = (RelativeLayout)
                            convertView.findViewById(R.id.video_ad_layout);
                    mViewHolder.mLogoView = (CircleImageView) convertView.findViewById(R.id.item_logo_view);
                    mViewHolder.mTitleView = (TextView) convertView.findViewById(R.id.item_title_view);
                    mViewHolder.mInfoView = (TextView) convertView.findViewById(R.id.item_info_view);
                    mViewHolder.mFooterView = (TextView) convertView.findViewById(R.id.item_footer_view);
                    mViewHolder.mShareView = (ImageView) convertView.findViewById(R.id.item_share_view);

                    String s = new Gson().toJson(value);
                    LogUtils.i("course",s);
                    AdValue adValue = new Gson().fromJson(s, AdValue.class);
                    LogUtils.i("course",adValue.toString());


                     videoAdSlot = new VideoAdSlot(adValue, new VideoAdSlot.AdSDKSlotListener() {
                        @Override
                        public ViewGroup getAdParent() {
                            return mViewHolder.mVieoContentLayout;
                        }

                        @Override
                        public void onAdVideoLoadSuccess() {

                        }

                        @Override
                        public void onAdVideoLoadFailed() {

                        }

                        @Override
                        public void onAdVideoLoadComplete() {

                        }

                        @Override
                        public void onClickVideo(String url) {
                            videoAdSlot. pauseVideo(false);
                        }
                    }, null);

                    //为对应布局创建播放器
                  /*  mAdsdkContext = new VideoAdContext(mViewHolder.mVieoContentLayout,
                            new Gson().toJson(value), null);

                    mAdsdkContext.setAdResultListener(new AdContextInterface() {
                        @Override
                        public void onAdSuccess() {
                        }

                        @Override
                        public void onAdFailed() {
                        }

                        @Override
                        public void onClickVideo(String url) {
                            Intent intent = new Intent(mContext, AdBrowserActivity.class);
                            intent.putExtra(AdBrowserActivity.KEY_URL, url);
                            mContext.startActivity(intent);
                        }
                    });*/

                    break;
                case CARD_TYPE_ONE:

                    convertView = mInflate.inflate(R.layout.item_product_card_one_layout, parent, false);

                    mViewHolder.mLogoView = (CircleImageView) convertView.findViewById(R.id.item_logo_view);
                    mViewHolder.mTitleView = (TextView) convertView.findViewById(R.id.item_title_view);
                    mViewHolder.mInfoView = (TextView) convertView.findViewById(R.id.item_info_view);
                    mViewHolder.mFooterView = (TextView) convertView.findViewById(R.id.item_footer_view);
                    mViewHolder.mPriceView = (TextView) convertView.findViewById(R.id.item_price_view);
                    mViewHolder.mFromView = (TextView) convertView.findViewById(R.id.item_from_view);
                    mViewHolder.mZanView = (TextView) convertView.findViewById(R.id.item_zan_view);
                    mViewHolder.mProductLayout = (LinearLayout) convertView.findViewById(R.id.product_photo_layout);
                    break;
                case CARD_TYPE_TWO:

                    convertView = mInflate.inflate(R.layout.item_product_card_two_layout, parent, false);

                    mViewHolder.mLogoView = (CircleImageView) convertView.findViewById(R.id.item_logo_view);
                    mViewHolder.mTitleView = (TextView) convertView.findViewById(R.id.item_title_view);
                    mViewHolder.mInfoView = (TextView) convertView.findViewById(R.id.item_info_view);
                    mViewHolder.mFooterView = (TextView) convertView.findViewById(R.id.item_footer_view);
                    mViewHolder.mProductView = (ImageView) convertView.findViewById(R.id.product_photo_view);
                    mViewHolder.mPriceView = (TextView) convertView.findViewById(R.id.item_price_view);
                    mViewHolder.mFromView = (TextView) convertView.findViewById(R.id.item_from_view);
                    mViewHolder.mZanView = (TextView) convertView.findViewById(R.id.item_zan_view);
                    break;
                case CARD_TYPE_THREE:
                    convertView = mInflate.inflate(R.layout.item_product_card_three_layout, null, false);

                    mViewHolder.mViewPager = (ViewPager) convertView.findViewById(R.id.pager);
                    //add data

                    ArrayList<RecommandBodyValue> recommandList = Util.handleData(value);

                    mViewHolder.mViewPager.setPageMargin(Utils.dip2px(context, 12));

                    mViewHolder.mViewPager.setAdapter(new HotSalePagerAdapter(context, recommandList));

                    mViewHolder.mViewPager.setCurrentItem(recommandList.size() * 100);

                    break;
            }

            onbind(position,convertView,parent);

            convertView.setTag(mViewHolder);
        }
        else {
             mViewHolder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }

    //加载数据
    private void onbind(int position, View convertView, ViewGroup parent) {

        int type = getItemViewType(position);
        //填充item的数据
        switch (type) {
            case VIDOE_TYPE:
                mImagerLoader.displayImage(mViewHolder.mLogoView, value.logo);
                mViewHolder.mTitleView.setText(value.title);
                mViewHolder.mInfoView.setText(value.info.concat(context.getString(R.string.tian_qian)));
                mViewHolder.mFooterView.setText(value.text);
                mViewHolder.mShareView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShareDialog dialog = new ShareDialog(context, false);
                        dialog.setShareType(Platform.SHARE_VIDEO);
                        dialog.setShareTitle(value.title);
                        dialog.setShareTitleUrl(value.site);
                        dialog.setShareText(value.text);
                        dialog.setShareSite(value.title);
                        dialog.setShareTitle(value.site);
                        dialog.setUrl(value.resource);
                        dialog.show();
                    }
                });
                break;
            case CARD_TYPE_ONE:
                mImagerLoader.displayImage(mViewHolder.mLogoView, value.logo);
                mViewHolder.mTitleView.setText(value.title);
                mViewHolder.mInfoView.setText(value.info.concat(context.getString(R.string.tian_qian)));
                mViewHolder.mFooterView.setText(value.text);
                mViewHolder.mPriceView.setText(value.price);
                mViewHolder.mFromView.setText(value.from);
                mViewHolder.mZanView.setText(context.getString(R.string.dian_zan).concat(value.zan));
                mViewHolder.mProductLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, PhotoViewActivity.class);
                        intent.putStringArrayListExtra(PhotoViewActivity.PHOTO_LIST, value.url);
                        context.startActivity(intent);
                    }
                });

                //记得移除
                mViewHolder.mProductLayout.removeAllViews();

                //动态添加多个imageview
                for (String url : value.url) {
                    mViewHolder.mProductLayout.addView(createImageView(url));
                }


                break;
            case CARD_TYPE_TWO:
                mImagerLoader.displayImage(mViewHolder.mLogoView, value.logo);
                mViewHolder.mTitleView.setText(value.title);
                mViewHolder.mInfoView.setText(value.info.concat(context.getString(R.string.tian_qian)));
                mViewHolder.mFooterView.setText(value.text);
                mViewHolder.mPriceView.setText(value.price);
                mViewHolder.mFromView.setText(value.from);
                mViewHolder.mZanView.setText(context.getString(R.string.dian_zan).concat(value.zan));
                //为单个ImageView加载远程图片
                mImagerLoader.displayImage(mViewHolder.mProductView, value.url.get(0));
                break;
            case CARD_TYPE_THREE:
                break;
        }
    }

    static class ViewHolder{

        //所有Card共有属性
        private CircleImageView mLogoView;
        private TextView mTitleView;
        private TextView mInfoView;
        private TextView mFooterView;
        //Video Card特有属性
        private RelativeLayout mVieoContentLayout;
        private ImageView mShareView;

        //Video Card外所有Card具有属性
        private TextView mPriceView;
        private TextView mFromView;
        private TextView mZanView;
        //Card One特有属性
        private LinearLayout mProductLayout;
        //Card Two特有属性
        private ImageView mProductView;
        //Card Three特有属性
        private ViewPager mViewPager;


    }

    //动态添加ImageView
    private ImageView createImageView(String url) {

        ImageView photoView = new ImageView(context);

        LinearLayout.LayoutParams params = new LinearLayout.
                LayoutParams(Utils.dip2px(context, 100),
                LinearLayout.LayoutParams.MATCH_PARENT);

        params.leftMargin = Utils.dip2px(context, 5);

        photoView.setLayoutParams(params);

        mImagerLoader.displayImage(photoView, url);

        return photoView;
    }

    //自动播放方法
    public void updateAdInScrollView() {
        if (videoAdSlot != null) {
            videoAdSlot.updateAdInScrollView();
        }
    }


}
