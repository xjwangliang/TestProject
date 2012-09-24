package org.wangliang.app.learn.switcher2;

import org.wangliang.app.learn.R;

import android.app.ActivityGroup;
import android.os.Bundle;
import android.view.View;

public class Switcher2Main extends ActivityGroup implements
		View.OnClickListener {

	//
	public static String tag1 = "我是第一个button对应页面,触摸也可以切换";
	public static String tag2 = "我是第2个button对应页面，触摸也可以切换";
	public static String tag3 = "我是第3个button对应页面，触摸也可以切换";
	public static String tag4 = "我是第4个button对应页面，触摸也可以切换";
	public static String tag5 = "我是第5个button对应页面，触摸也可以切换";
	
	private int mCurId = R.id.btn1;
	private MainViewManager mViewManager;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewswitcher2_main);
		mViewManager = MainViewManager.getInstance();
		mViewManager.setupViews(this);
	}

	@Override
	public void onClick(View v) {
		final int id = v.getId();
		if (mCurId == id) {
			return;
		}
		mCurId = id;
		mViewManager.setCurBtnPos(id);
		mViewManager.processViews(this);
		mViewManager.onRotateAnimation(mViewManager.getCurBtnIndex(id));
	}
}
