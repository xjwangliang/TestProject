package org.wangliang.app.learn.gt3.fragment ;

import org.wangliang.app.learn.R ;

import android.app.Activity ;
import android.app.LocalActivityManager ;
import android.content.Intent ;
import android.os.Bundle ;
import android.widget.TabHost ;
//http://www.cnblogs.com/mybkn/archive/2012/05/18/2507789.html
//http://wazai.net/2114

//FragmentActivity1.java : 第一個Tab中用來管理Fragment的FragmentActivity。(2012-05-07更新)經由Jay留言後，小蛙詳細測了一下，發現FragmentActivity主畫面中的Button是沒辦法消失的(因為FragmentActivity的目的關係)，
//因此改成這樣，讓FragmentActivity純粹當成容器，主要的內容還是以Fragment為主。(這個方法不是唯一，但是一個可行的方法，應該也有更好的做法！
public class MainFragmentTab extends Activity {
	private TabHost mHost ;
	// 在Activity中使用Tabhost必須要有LocalActivityManager物件來設定
	LocalActivityManager lam ;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState) ;
		setContentView(R.layout.fragment_acitivity) ;
		// main layout採用預設的Tabhost
		mHost = (TabHost) findViewById(android.R.id.tabhost) ;

		lam = new LocalActivityManager(this, false) ;
		lam.dispatchCreate(savedInstanceState) ;
		//Caused by: java.lang.IllegalStateException: Did you forget to call 'public void setup(LocalActivityManager activityGroup)'?
		mHost.setup(lam) ;
		//mHost.setup();
		mHost.addTab(mHost
				.newTabSpec("Tab1")
				.setIndicator("Tab1")
				.setContent(new Intent(MainFragmentTab.this,Fragment1Activity.class))) ;
		mHost.addTab(mHost
				.newTabSpec("Tab2")
				.setIndicator("Tab2")
				.setContent(
						new Intent(MainFragmentTab.this,
								Fragment2Activity.class))) ;
	}

	@Override
	protected void onPause() {
		// 漏掉這行一定出錯
		lam.dispatchPause(isFinishing()) ;
		super.onPause() ;
	}

	@Override
	protected void onResume() {
		// 漏掉這行一定出錯
		lam.dispatchResume() ;
		super.onResume() ;
	}
}