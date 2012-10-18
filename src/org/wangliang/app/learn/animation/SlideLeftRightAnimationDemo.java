package org.wangliang.app.learn.animation ;

import java.util.ArrayList ;
import java.util.List ;

import org.wangliang.app.learn.R ;

import android.app.Activity ;
import android.content.Context ;
import android.content.DialogInterface ;
import android.content.res.Configuration ;
import android.graphics.drawable.Drawable ;
import android.os.Bundle ;
import android.os.Handler ;
import android.view.KeyEvent ;
import android.view.Menu ;
import android.view.MenuItem ;
import android.view.View ;
import android.view.View.OnClickListener ;
import android.view.ViewGroup ;
import android.view.animation.Animation ;
import android.view.animation.AnimationUtils ;
import android.webkit.WebSettings ;
import android.webkit.WebView ;
import android.widget.ImageView ;

public class SlideLeftRightAnimationDemo extends Activity implements OnClickListener{
	private MenuDialog dialog = null ;
	private boolean c = false ;
	private boolean show = false ;
	private Context e ;
	private List<MyMenuItem> items = new ArrayList<MyMenuItem>() ;
	private ImageView draw ;
	private View board ;
	
	Animation animIn ;
	Animation animOut ;

	
	//网易
//	   View localView = localLayoutInflater.inflate(2130903157, null);
//	      this.themeSettingsHelper.setViewBackgroudColor(this, localView, 2131230761);
//	      float f = StringUtil.getPercent(Integer.parseInt(((Map)this.itemList.get(j)).get("num").toString()), this.vb.getAllVoteNum());
//	      ((TextView)localView.findViewById(2131493228)).setText(j + 1 + ". ");
//	      ((TextView)localView.findViewById(2131493229)).setText(((Map)this.itemList.get(j)).get("name").toString());
//	      if ((this.flag != 1) && (this.radios[j].isChecked()) && (this.is_success))
//	      {
//	        this.addone = ((TextView)localView.findViewById(2131493230));
//	        this.addone.startAnimation(this.bottomin);
//	      }
//	      this.voteItems[j] = ((TextView)localView.findViewById(2131493231));
//	      this.voteItems[j].setWidth((int)(f * this.percentLength));
//	      this.voteItems[j].setHeight(10);
//	      this.voteItems[j].setBackgroundDrawable(getBGDrawable(j));
//	      this.voteItems[j].startAnimation(this.vote_see);
//	      TextView localTextView = (TextView)localView.findViewById(2131493232);
//	      int k = (int)(100.0F * f);
//	      i += k;
//	      if (j == -1 + this.itemList.size())
//	        k += 100 - i;
//	      localTextView.setText(k + "%");
//	      this.vote_item_list_lay.addView(localView, localLayoutParams);
//
//
//
//	 WebView.enablePlatformNotifications();
//	
//	vote_see
//	<?xml version="1.0" encoding="utf-8"?>
//	<set xmlns:android="http://schemas.android.com/apk/res/android" >
//
//	    <scale
//	        android:duration="800"
//	        android:fillAfter="false"
//	        android:fromXScale="0.0"
//	        android:fromYScale="1.0"
//	        android:interpolator="@android:anim/accelerate_decelerate_interpolator"
//	        android:pivotX="0.0"
//	        android:pivotY="0.0"
//	        android:toXScale="1.0"
//	        android:toYScale="1.0" />
//
//	</set>
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState) ;
		setupView();
	}

	private void setupView() {
		setContentView(R.layout.anitmaion_browser) ;
		getWindow().setSoftInputMode(16) ;

		WebView wv = (WebView) findViewById(R.id.webview) ;
		wv.loadUrl("http://www.google.com.hk") ;
		wv.requestFocus() ;

		WebSettings localWebSettings = wv.getSettings() ;
		localWebSettings.setBuiltInZoomControls(true) ;
		localWebSettings.setSupportZoom(false) ;
		localWebSettings.setJavaScriptEnabled(true) ;

		draw = ((ImageView) findViewById(R.id.draw)) ;
		draw.setOnClickListener(this) ;
		board = findViewById(R.id.board) ;
		View statusbar = findViewById(R.id.statusbar) ; //statusbar
		animIn = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.slide_out_left) ;//<public type="anim" name="slide_out_left" id="0x7f040004" />
		//<?xml version="1.0" encoding="utf-8"?>
		//		    <set
		//		    xmlns:android="http://schemas.android.com/apk/res/android">
		//		      <translate android:duration="@android:integer/config_mediumAnimTime" android:fromXDelta="100.0%p" android:toXDelta="0.0" />
		//		      <alpha android:duration="@android:integer/config_mediumAnimTime" android:fromAlpha="0.0" android:toAlpha="1.0" />
		//  </set>

		animIn.setDuration(500L) ;

		animOut = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.slide_out_right) ;////public static final int slide_out_right = 17432579;

		//<set xmlns:android="http://schemas.android.com/apk/res/android">
		//			<translate android:fromXDelta="0" android:toXDelta="50%p"
		//		            android:duration="@android:integer/config_mediumAnimTime"/>
		//			<alpha android:fromAlpha="1.0" android:toAlpha="0.0"
		//		            android:duration="@android:integer/config_mediumAnimTime" />
		//</set>

		animOut.setDuration(500L) ;
		//WebView.enablePlatformNotifications() ;
		//Caused by: java.lang.SecurityException: ConnectivityService: Neither user 10055 nor current process has android.permission.ACCESS_NETWORK_STATE.

	}
	
	
	@Override
	public void onClick(View v) {
//		if (!"stop".equals(this.i.getTag())) {
		this.board.clearAnimation();
			if (!"collapse".equals(this.draw.getTag())) {
				this.draw.setTag("collapse") ;
				this.draw.setImageDrawable(getResources().getDrawable(
						R.drawable.btn_browser_draw)) ;// <public type="drawable" name="btn_browser_draw" id="0x7f020021" />
				((ViewGroup) this.board.getParent()).getLayoutParams().width = -1 ;//设置宽度为fill——parent

				this.board.setVisibility(0) ;
				this.board.startAnimation(this.animIn) ;
				reset(3000) ;
			} else {
				this.runnable.run() ;
				this.handler.removeCallbacks(this.runnable) ;
			}
//		} else
//			a.stopLoading() ;

	}

	private Handler handler = new Handler() ;
	private Runnable runnable = new RunTask(this) ;

	private void reset(int delay) {
		this.handler.removeCallbacks(this.runnable) ;
		this.handler.postDelayed(this.runnable, delay) ;
	}
	
	protected ImageView getDrawView(){
		return draw ;
	}
	protected View getBoardView(){
		return board ;
	}
	
	
	private void createOriginalMenu(Menu paramMenu) {
		getMenuInflater().inflate(R.menu.defaultmenu, paramMenu) ;
	}

	private void createMenu() {
		this.dialog = new MenuDialog(this, items) ;
		this.dialog.setOnKeyListener(new KeyListener(this)) ;
	}

	private void showMenu() {
		if (this.dialog != null) this.dialog.show() ;
	}

	public void d(boolean paramBoolean) {
		this.c = paramBoolean ;
	}

	public void setShowing(boolean show) {
		this.show = show ;
	}

	//	public boolean onCreateOptionsMenu(Menu menu) {
	//		//getMenuInflater().inflate(R.anim.webview_menu_list,  menu) ;
	//		//super.d(true) ;
	//		return super.onCreateOptionsMenu( menu) ;
	//	}

	public boolean onCreateOptionsMenu(Menu menu) {
		if ((this.dialog == null) && (this.show == true)) {
			createMenu() ;
		}
		return true ;
	}

	public boolean onPrepareOptionsMenu(Menu paramMenu) {
		if (this.show == true) {
			createOriginalMenu(paramMenu) ;
			this.show = false ;
		}
		return super.onPrepareOptionsMenu(paramMenu) ;
	}

	public boolean onMenuOpened(int paramInt, Menu paramMenu) {
		//		int i ;
		//		if ((this.c != true) || (this.dialog != null)) {
		//			this.items.clear() ;
		//			i = 0 ;
		//		}
		//		while (true)
		//			if (i >= paramMenu.size()) {
		//				if (this.items.size() != 0) {
		//					showMenu() ;
		//					this.dialog.a() ;
		//					i = 0 ;
		//				} else {
		//					this.dialog = null ;
		//					i = 1 ;
		//				}
		//			} else {
		//				if (paramMenu.getItem(i).getTitle() != null)
		//					this.items.add(new MyMenuItem(this, paramMenu.getItem(i))) ;
		//				i++ ;
		//				continue ;
		//				i = 1 ;
		//			}
		//		return i ;
		int size = paramMenu.size() ;
		for(int i=0;i<size;i++){
			this.items.add(new MyMenuItem(this, paramMenu.getItem(i))) ;
		}
		
		return false ;
	}

	public void onConfigurationChanged(Configuration paramConfiguration) {
		int i = 0 ;
		if ((this.dialog != null) && (this.dialog.isShowing())) {
			this.dialog.dismiss() ;
			i = 1 ;
		}
		super.onConfigurationChanged(paramConfiguration) ;
		if (i != 0) openOptionsMenu() ;
	}


	
}


class RunTask implements Runnable {
	SlideLeftRightAnimationDemo activity ;

	Animation.AnimationListener animationListener = new Animation.AnimationListener() {
		public void onAnimationEnd(Animation paramAnimation) {
			View boardView = activity.getBoardView() ;
			((ViewGroup) boardView.getParent()).getLayoutParams().width = -2 ;
			boardView.setVisibility(8) ;
		}

		public void onAnimationRepeat(Animation paramAnimation) {
		}

		public void onAnimationStart(Animation paramAnimation) {
		}
	} ;
	
	RunTask(SlideLeftRightAnimationDemo activity) {
		this.activity = activity ;
	}

	public void run() {
		if ("collapse".equals(activity.getDrawView().getTag())) {
			activity.getDrawView().setTag(null) ;
			activity.getDrawView().setImageDrawable(activity.getResources().getDrawable(R.drawable.btn_browser_draw_collapse)) ;
		}
		activity.animOut.setAnimationListener(animationListener) ;
		activity.getBoardView().startAnimation(activity.animOut) ;
	}
}




class KeyListener implements DialogInterface.OnKeyListener {
	KeyListener(Activity paramQqMenuActivity) {
	}

	public boolean onKey(DialogInterface paramDialogInterface, int paramInt,
			KeyEvent paramKeyEvent) {
		int i ;
		if (paramInt != 84) {
			if (paramInt == 82) paramDialogInterface.dismiss() ;
			i = 0 ;
		} else {
			i = 1 ;
		}
		return i == 1 ;
	}
}

class MyMenuItem {
	private MenuItem item ;

	public MyMenuItem(Activity paramQqMenuActivity, MenuItem item) {
		this.item = item ;
	}

	public String getTitle() {
		return this.item.getTitle().toString() ;
	}

	public Drawable getIcon() {
		return this.item.getIcon() ;
	}

	public MenuItem getItem() {
		return this.item ;
	}
}