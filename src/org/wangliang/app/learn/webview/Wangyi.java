package org.wangliang.app.learn.webview;

import java.io.BufferedReader ;
import java.io.IOException ;
import java.io.InputStreamReader ;
import java.net.URLEncoder ;
import java.util.ArrayList ;
import java.util.List ;

import org.apache.http.HttpResponse ;
import org.apache.http.NameValuePair ;
import org.apache.http.client.ClientProtocolException ;
import org.apache.http.client.HttpClient ;
import org.apache.http.client.entity.UrlEncodedFormEntity ;
import org.apache.http.client.methods.HttpPost ;
import org.apache.http.impl.client.DefaultHttpClient ;
import org.apache.http.message.BasicNameValuePair ;
import org.wangliang.app.learn.R ;

import android.annotation.SuppressLint ;
import android.app.Activity ;
import android.os.Bundle ;
import android.view.Menu ;
import android.view.MenuItem ;
import android.view.View ;
import android.view.View.OnClickListener ;
import android.webkit.WebSettings ;
import android.webkit.WebView ;
import android.widget.Toast ;

//mDefaultScale
//10 WebView WebViewCore
//16 ZoomManager WebViewCore WebViewClassic


public class Wangyi extends Activity {
	private WebView browser;
	String newsHtml = "file:///android_asset/shownews.html";
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.geo);
		browser = (WebView) findViewById(R.id.webkit);
		View zoomBar = findViewById(R.id.zoom_bar);
		zoomBar.setVisibility(View.VISIBLE);
		zoomBar.findViewById(R.id.zoom_in).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				browser.zoomIn() ;
				//boolean canZoomIn = browser.canZoomIn() ;
				float scale = browser.getScale() ;
				//Toast.makeText(getApplicationContext(), "canZoomIn "+canZoomIn+" scale "+scale, 0).show();
			}
		});
		zoomBar.findViewById(R.id.zoom_out).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				browser.zoomOut() ;
				boolean canZoomOut = browser.canZoomOut();
				float scale = browser.getScale() ;
				
				Toast.makeText(getApplicationContext(), "canZoomOut "+canZoomOut +" scale "+scale, 0).show();
				
			}
		});
		browser.getSettings().setJavaScriptEnabled(true);
		//browser.canZoomIn();
		//browser.capturePicture();
		browser.addJavascriptInterface(new JavaScriptInterface(), "news");
		
		WebSettings webSettings = browser.getSettings();
		webSettings.setBuiltInZoomControls(true);
	
		webSettings.setSupportZoom(true);
		webSettings.setUseWideViewPort(true);
		//webSettings.setDisplayZoomControls(true);
		browser.loadUrl(newsHtml);
		
		//browser.setDownloadListener(listener)
//		browser.setOnDragListener(new OnDragListener() {
//			
//			@Override
//			public boolean onDrag(View v, DragEvent event) {
//				Toast.makeText(Wangyi.this, "onDrag", 1).show();
//				return false ;
//			}
//		});
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

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "loadDataWithBaseURL");
		menu.add(0, 1, 1, "loadData");
		menu.add(0, 2, 2, "loadData(Image)");
		menu.add(0, 3, 3, "load(taobao)");
		menu.add(0, 4, 4, "load(Sina)");
		return super.onCreateOptionsMenu(menu) ;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case 0:
				localHtmlImage();
				break ;

			case 1:
				localHtmlZh();
				break ;
			case 2:
				localImage();
				break ;
			case 3:
				loadTapbao();
				break ;
			case 4:
				loadSina();
				break ;
		}
		return super.onOptionsItemSelected(item) ;
	}
	
	 private void localHtmlImage() {  
	        try {  
	            String data = "测试本地图片和文字混合显示,这是<IMG src='\"file:///android_asset/ic_launcher.png\"/'>APK里的图片";  
	            // SDK1.5本地文件处理(不能显示图片)  
	            // webView.loadData(URLEncoder.encode(data, encoding), mimeType,  
	            // encoding);  
	            // SDK1.6及以后版本  
	            // webView.loadData(data, mimeType, encoding);  
	            // 本地文件处理(能显示图片)  
	            browser.loadDataWithBaseURL("about:blank", data, "text/html",  
	                    "utf-8", "");  
	        } catch (Exception ex) {  
	            ex.printStackTrace();  
	        }  
	    }  
	 
	 //http://a.m.taobao.com/i3407345347.htm
	 //http://a.m.taobao.com/i13098923282.htm
	 private void loadTapbao() {  
		 browser.loadUrl("file:///android_asset/taobao.html");
	 }  
	 boolean local = false; 
	 private void loadSina() {  
		 if(!local){
			 Toast.makeText(getApplicationContext(), "Server", 0).show();
			 browser.loadUrl("https://api.weibo.com/oauth2/authorize?redirect_uri=http%3A//www.guoku.com/sina/auth&response_type=code&client_id=1459383851&display=default");
		 }else{
			 browser.loadUrl("file:///android_asset/authorize.html");
		 }
		 local = !local;
		 
		 
		 
	 }  
	 
	  private void localImage() {  
	        try {  
	            // 本地文件处理(如果文件名中有空格需要用+来替代)  
	        	browser.loadUrl("file:///android_asset/ic_launcher.png");  
	        } catch (Exception ex) {  
	            ex.printStackTrace();  
	        }  
	    }  
	  
	   private void localHtmlZh() {  
	        try {  
	        	
	        	String s = "<!DOCTYPE html><html><head><meta content='text/html; charset=utf-8' http-equiv='Content-Type'/></head><body>hello world!!</body></html>";
	        	
	     
	            //String data = "<!--l encoding='utf-8' version='1'.-->测试含有  中文的Html数据";  
	            // utf-8编码处理(在SDK1.5模拟器和真实设备上都将出现乱码,SDK1.6上能正常显示)  
	            //webView.loadData(data, mimeType, encoding);  
	            // 对数据进行编码处理(SDK1.5版本)  
	             browser.loadData(URLEncoder.encode(s, "utf-8"), "text/html",  
	             "utf-8");  //WebView1.loadData(content, "text/html; charset=utf-8", "UTF-8");
	        } catch (Exception ex) {  
	            ex.printStackTrace();  
	        }  
	    }  
	   
//http://stackoverflow.com/questions/8049343/how-to-control-zoom-in-webview	   
	private void loadString() {
		BufferedReader bufferedReader = null ;
		HttpClient httpClient = new DefaultHttpClient() ;
		HttpPost request = new HttpPost("http://search.yahoo.com/search") ;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>() ;
		postParameters.add(new BasicNameValuePair("p", "Android")) ;

		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
					postParameters) ;
			request.setEntity(entity) ;

			HttpResponse response = httpClient.execute(request) ;

			bufferedReader = new BufferedReader(new InputStreamReader(response
					.getEntity().getContent())) ;
			StringBuffer stringBuffer = new StringBuffer("") ;
			String line = "" ;
			String LineSeparator = System.getProperty("line.separator") ;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line + LineSeparator) ;
			}
			bufferedReader.close() ;

			String webData = stringBuffer.toString() ;

			webData = webData.replace("#", "%23") ;
			webData = webData.replace("%", "%25") ;
			webData = webData.replace("\\", "%27") ;
			webData = webData.replace("?", "%3f") ;

			browser.loadData(webData, "text/html", "UTF-8") ;

		} catch (ClientProtocolException e) {
			e.printStackTrace() ;
		} catch (IOException e) {
			e.printStackTrace() ;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close() ;
				} catch (IOException e) {
					e.printStackTrace() ;
				}

			}
		}
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
