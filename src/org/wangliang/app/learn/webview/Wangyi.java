package org.wangliang.app.learn.webview;

import org.wangliang.app.learn.R;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

public class Wangyi extends Activity {
	private WebView browser;
	String newsHtml = "file:///android_asset/shownews.html";
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.geo);
		browser = (WebView) findViewById(R.id.webkit);

		browser.getSettings().setJavaScriptEnabled(true);
		browser.addJavascriptInterface(new JavaScriptInterface(), "news");
		browser.loadUrl(newsHtml);
		
		//loadUrl("javascript:getMore()");
		//loadUrl("javascript:showBigSize()");
		
		//str2 = str2.replace(str2.substring(k, k + str1.length()), 
		
		
//		<div style="background-image:url(detailpage_image_bk.png);
//					width:115px;height:105px;
//					float:right;
//					clear:right;
//					overflow:hidden;
//					margin-top:10px;"> 
//			<a href="javascript:void(0)"
//					id="imgcontent" + i " 
//					onclick="zoomOut(i);"
//					style="background-image:url('url');display:block;width:100px;height:100px;margin-left:12px;margin-right:1px;margin-top:0px;margin-bottom:2px" />
//			<img src="add.png" style="border:none;margin-top:-16px;"/>
//		</div>
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	
	
	private class JavaScriptInterface {
		private JavaScriptInterface() {}

		public String getBody() {
			return "Body............这是一个很古老的故事";
		}

		public void getMore() {
			new Thread() {
				public void run() {
				}
			}.start();
		}

		public String getMoreBody() {
			Toast.makeText(getApplicationContext(), "getMoreBody", 1).show();
			return "getMoreBodyWithImg()";
		}

		public String getReplyBoard() {
			return "replyBoard";
		}

		public int getReplyCount() {
			return 10;
		}

		public String getSource() {
			return ("来源");
		}

		public String getTime() {
			return "Time";
		}

		public String getTitle() {
			return "Title";
		}

		public int hasNext() {
			int i = 0;
			return i;
		}

		public void showBigPic(int paramInt) {
			Toast.makeText(getApplicationContext(), "Big picture", 1).show();
		}

		public void toLink(int paramInt) {
			Toast.makeText(getApplicationContext(), "To Link", 1).show();
		}

		public void triggerFullScreen() {
			Toast.makeText(getApplicationContext(), "Full Screen", 1).show();
		}
	}

}
