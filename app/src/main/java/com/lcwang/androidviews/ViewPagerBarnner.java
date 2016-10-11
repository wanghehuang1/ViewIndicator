package com.lcwang.androidviews;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dsw.viewindicator.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ViewPagerBarnner extends RelativeLayout implements OnPageChangeListener {
	/**
	 * ViewPager对象
	 */
	private ViewPager viewPager;
	/**
	 * 指示器
	 */
	private LinearLayout indicatorView;
	private Context context;
	/**
	 * 存放Url地址
	 */
	private LinkedList<String> imageUrls = new LinkedList<String>();
	/**
	 * 存放ImageView
	 */
	private LinkedList<ImageView> imageViews = new LinkedList<ImageView>();
	/**
	 * ViewPager的点击事件
	 */
	private ViewPagerClick viewPagerClick;
	/**
	 * 指示器的大小
	 */
	private float indicatorSize = 15;
	/**
	 * 指示器的背景
	 */
	private int idBackgroud;
	/**
	 * 指示器间隔
	 */
	private float indicatorMargin = 20;
	/**
	 * 容器的高度
	 */
	private float containerHeight = 20;
	/**
	 * 选中的位置
	 */
	private int currentPostion;
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			viewPager.setCurrentItem(currentPostion + 1);
			//轮播间隔时间3s
			mHandler.sendEmptyMessageDelayed(0, 3000);
		}
	};
	
	public ViewPagerBarnner(Context context) {
		this(context,null);
	}

	public ViewPagerBarnner(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initViews();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ViewPager);
        indicatorSize = typedArray.getDimension(R.styleable.ViewPager_indicatorSize, 10);
        indicatorMargin = typedArray.getDimension(R.styleable.ViewPager_indicatorMargin, 15);
        idBackgroud = typedArray.getResourceId(R.styleable.ViewPager_indicatorBackgroud, 0);
        containerHeight = typedArray.getDimension(R.styleable.ViewPager_containerHeight, 20);
        typedArray.recycle();
	}
	
	/**
	 * 初始化View的视图
	 */
	private void initViews(){
		viewPager = new ViewPager(context);
		LayoutParams viewPagerParams = new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 
												RelativeLayout.LayoutParams.MATCH_PARENT);
		viewPager.setLayoutParams(viewPagerParams);
		viewPager.setAdapter(viewPagerAdapter);
		viewPager.setOnPageChangeListener(this);
		
		indicatorView = new LinearLayout(context);
		indicatorView.setOrientation(LinearLayout.HORIZONTAL);
		LayoutParams layoutParams = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, (int)containerHeight);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		indicatorView.setLayoutParams(layoutParams);
		addView(viewPager);
		addView(indicatorView);
		mHandler.sendEmptyMessageDelayed(0, 3000);
	}
	
	/**
	 * ViewPager的适配器
	 */
	private PagerAdapter viewPagerAdapter = new PagerAdapter() {
		
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
		
		@Override
		public int getCount() {
			return imageUrls == null ? 0 : imageUrls.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public void finishUpdate(ViewGroup container) {
		}

		@Override
		public int getItemPosition(Object object) {
			return super.getItemPosition(object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView imageView = null;
			if(imageViews != null && imageViews.size() > 0){
				imageView = imageViews.get(position);
				imageView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View view) {
						if(viewPagerClick != null){
							viewPagerClick.viewPagerOnClick(view);
						}
					}
				});
			}
			container.addView(imageView);
			return imageView;
		}
	};
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int location) {
		if(location == this.imageUrls.size() -1){
			location = 1;
			viewPager.setCurrentItem(location,false);
		}else if(location == 0){
			location = this.imageUrls.size() -2;
			viewPager.setCurrentItem(location,false);
		}
		currentPostion = location;
		setSelectPage(location - 1);
	}
	
	
	/** 
	 * 从url地址创建imageview对象，同时初始化指示器
	 */
	private void createImageView(List<String> imageUrlList){
		if(imageUrlList != null && imageUrlList.size() > 0){
			ImageView imageView;
			View pointView;
			//清除头尾
			if(imageViews.size() > 1){
				imageViews.removeFirst();
				imageViews.removeLast();
			}
			for(String url : imageUrlList){
				imageView = new ImageView(context);
				imageView.setScaleType(ScaleType.FIT_XY);
				ImageLoader.getInstance().displayImage(url, imageView);
				imageView.setTag(url);
				imageViews.add(imageView);
				
				pointView = new View(context);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)(indicatorSize),(int)(indicatorSize));
				params.rightMargin = (int)indicatorMargin;
				pointView.setLayoutParams(params);
				pointView.setBackgroundDrawable(context.getResources().getDrawable(idBackgroud));
				pointView.setEnabled(false);
				indicatorView.addView(pointView);
			}
			//增加头尾
			if(imageViews.size() > 1){
				ImageView ivFirst = new ImageView(context);
				ImageView ivLast = new ImageView(context);
				ImageLoader.getInstance().displayImage(imageUrls.getLast(),ivLast);
				ImageLoader.getInstance().displayImage(imageUrls.getFirst(),ivFirst);
				imageViews.addFirst(ivFirst);
				imageViews.addLast(ivLast);
			}
			viewPager.setCurrentItem(1);
		}
	}
	
	/**
	 * 设置当前选中页面
	 * @param position
	 */
	private void setSelectPage(int position){
		for(int index=0; index < indicatorView.getChildCount();index++){
			if(position == index){
				indicatorView.getChildAt(index).setEnabled(true);
			}else{
				indicatorView.getChildAt(index).setEnabled(false);
			}
		}
	}
	
	/**
	 * 设置图片的地址，从网络加载图片
	 * @param imageUrls
	 */
	public void addImageUrls(List<String> imageUrls) {
		if(this.imageUrls.size() > 1){//清除头尾
			this.imageUrls.removeFirst();
			this.imageUrls.removeLast();
		}
		this.imageUrls.addAll(imageUrls);
		if(this.imageUrls.size() >1){//增加头尾
			String first = this.imageUrls.getFirst();
			this.imageUrls.addFirst(this.imageUrls.getLast());
			this.imageUrls.addLast(first);
		}
		createImageView(imageUrls);
		viewPagerAdapter.notifyDataSetChanged();
	}

	/**
	 * 获取ViewPager对象
	 * @return
	 */
	public ViewPager getViewPager() {
		return viewPager;
	}

	/**
	 * 获取指示器
	 * @return
	 */
	public LinearLayout getIndicatorView() {
		return indicatorView;
	}

	/**
	 * 设置点击ViewPager中的ImageView的监听
	 * @param viewPagerClick
	 */
	public void setViewPagerClick(ViewPagerClick viewPagerClick) {
		this.viewPagerClick = viewPagerClick;
	}

	public interface ViewPagerClick{
		public void viewPagerOnClick(View view);
	}
}
