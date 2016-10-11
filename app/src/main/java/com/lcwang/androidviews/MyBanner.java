package com.lcwang.androidviews;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.LinkedList;

public class MyBanner extends RelativeLayout implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;

    private LinearLayout indicatorView;
    private Context context;

    private LinkedList<String> imgUrls = new LinkedList<String>();
    private LinkedList<ImageView> imgViews = new LinkedList<ImageView>();



    public MyBanner(Context context) {
        super(context);

    }

    public MyBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
