package org.wangliang.app.learn.webview;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wangliang.app.learn.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

//button上加上图片Spanned span = Html.fromHtml(dbutton.toString(), imgGetter, null);button1.setText(span);
//http://www.devdiv.com/thread-46160-1-1.html
public class Pic extends Activity {
	private WebView browser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.pic);
		browser = (WebView) findViewById(R.id.webkit);

		browser.getSettings().setJavaScriptEnabled(true);
		way1( null );
	}
	final String str = "这个阶段的典型特征是容易被新技术的华丽外表所迷惑。当在网上看到一种新技术的介绍或者心得，立即产生了大量肾上腺素的分泌，干什么都想用一用，如果这这个阶段的典型特征是容易被新技术的华丽外表所迷惑。当在网上看到一种新技术的介绍或者心得，立即产生了大量肾上腺素的分泌，干什么都想用一用，如果这这个阶段的典型特征是容易被新技术的华丽外表所迷惑。当在网上看到一种新技术的介绍或者心得，立即产生了大量肾上腺素的分泌，干什么都想用一用，如果这这个阶段的典型特征是容易被新技术的华丽外表所迷惑。当在网上看到一种新技术的介绍或者心得，立即产生了大量肾上腺素的分泌，干什么都想用一用，如果这这个阶段的典型特征是容易被新技术的华丽外表所迷惑。当在网上看到一种新技术的介绍或者心得，立即产生了大量肾上腺素的分泌，干什么都想用一用，如果这这个阶段的典型特征是容易被新技术的华丽外表所迷惑。当在网上看到一种新技术的介绍或者心得，立即产生了大量肾上腺素的分泌，干什么都想用一用，如果这这个阶段的典型特征是容易被新技术的华丽外表所迷惑。当在网上看到一种新技术的介绍或者心得，立即产生了大量肾上腺素的分泌，干什么都想用一用，如果这这个阶段的典型特征是容易被新技术的华丽外表所迷惑。当在网上看到一种新技术的介绍或者心得，立即产生了大量肾上腺素的分泌，干什么都想用一用，如果这这个阶段的典型特征是容易被新技术的华丽外表所迷惑。当在网上看到一种新技术的介绍或者心得，立即产生了大量肾上腺素的分泌，干什么都想用一用，如果这这个阶段的典型特征是容易被新技术的华丽外表所迷惑。当在网上看到一种新技术的介绍或者心得，立即产生了大量肾上腺素的分泌，干什么都想用一用，如果这这个阶段的典型特征是容易被新技术的华丽外表所迷惑。当在网上看到一种新技术的介绍或者心得，立即产生了大量肾上腺素的分泌，干什么都想用一用，如果这这个阶段的典型特征是容易被新技术的华丽外表所迷惑。当在网上看到一种新技术的介绍或者心得，立即产生了大量肾上腺素的分泌，干什么都想用一用，如果这这个阶段的典型特征是容易被新技术的华丽外表所迷惑。当在网上看到一种新技术的介绍或者心得，立即产生了大量肾上腺素的分泌，干什么都想用一用，如果这这个阶段的典型特征是容易被新技术的华丽外表所迷惑。当在网上看到一种新技术的介绍或者心得，立即产生了大量肾上腺素的分泌，干什么都想用一用，如果这";
	public void way1(View v) {

		

		// 我们用它来完成我们的异步调用 ,当我new这个对象出来，它会绑定到当前
		// activity消息队列里去，如果发现它里面新建了Runnable，它就会新建一个线程去。。。
		Handler handler = new Handler();

		final WebView webView = browser;

		// 支持javaScript
		webView.getSettings().setJavaScriptEnabled(true);
		// 不保存表单数据
		webView.getSettings().setSaveFormData(false);
		// 不保存密码
		webView.getSettings().setSavePassword(false);
		// 不支持页面放大功能
		webView.getSettings().setSupportZoom(false);
		// 把我们的页面显示出来
		webView.loadUrl("file:///android_asset/pic.html");
		webView.addJavascriptInterface(new JavaScriptInterface() , "pic");
//		webView.loadUrl("javascript:load_title('" + "My Title" + "')");
//		webView.loadUrl("javascript:load_content('" + str + "')");
//		webView.loadUrl("javascript:load_img('" + "file:///android_asset/ic_launcher,png"
//				+ "')");
//		handler.post(new Runnable() {
//			@Override
//			public void run() {
//				webView.loadUrl("javascript:load_title('" + "My Title" + "')");
//				webView.loadUrl("javascript:load_content('" + str + "')");
//				webView.loadUrl("javascript:load_img('" + "file:///android_asset/ic_launcher,png"
//						+ "')");
//			}
//		});

	}
	
	private class JavaScriptInterface {

		public String getTitle() {
			return "Body............这是一个很古老的故事";
		}

		public String getContent() {
			return str ;
		}

		public String getImage() {
			return "file:///android_asset/ic_launcher.png";
		}

		
	}

	public void way2(View v) {
		if(true)return ;
		final WebView webView = browser;
		// 支持javaScript
		webView.getSettings().setJavaScriptEnabled(true);
		// 不保存表单数据
		webView.getSettings().setSaveFormData(false);
		// 不保存密码
		webView.getSettings().setSavePassword(false);
		// 不支持页面放大功能
		webView.getSettings().setSupportZoom(false);
		webView.addJavascriptInterface(new Partner4javaJavaScript(),
				"partner4java");
		// 把我们的页面显示出来
		webView.loadUrl("file:///android_asset/index.html");

		new Handler().post(new Runnable() {
			@Override
			public void run() {
				// // 可以通过访问SQLite访问数据库获取
				List<Contact> contacts = new ArrayList<Contact>();
				contacts.add(new Contact(12, "王昌龙", "18701445755"));
				contacts.add(new Contact(13, "小妾", "18701445755"));
				webView.loadUrl("javascript:show('" + buildJson(contacts)
						+ "')");
			}

		});
	}

	public void way3(View v) {
		try {
			final WebView webView = browser;
			String data = "<HTML><p>标题很长很长很长很长很长很长很长很长很长很长很长很长<p>在模拟器 2.1 上测试,这是<IMG src=\"file:///android_asset/ic_launcher.png\" style=\"float:left;\" clear:none;/>APK里的图片 文字环绕图片文字环绕图片文字环绕图片文字环绕图片文字环绕图片文字环绕图片文字环绕图片文字环绕图片文字环绕图片文字环绕图片文字环绕图片文字环绕图片文字环绕图片" +
					"" +
					"<IMG src=\"http://image.gsfc.nasa.gov/image/image_launch_a5.jpg\"/>";
			// SDK1.5本地文件处理(不能显示图片)
			// myWebView.loadData(URLEncoder.encode(data, encoding), mimeType,
			// encoding);
			// SDK1.6及以后版本
			// myWebView.loadData(data, mimeType, encoding);
			// 本地文件处理(能显示图片)
			// webView.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
			webView.loadDataWithBaseURL(null, data, "text/html", "UTF-8", null);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	class Contact {
		private long id;
		private String name;
		private String phone;

		public Contact(long id, String name, String phone) {
			super();
			this.id = id;
			this.name = name;
			this.phone = phone;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

	}

	private String buildJson(List<Contact> contacts) {
		try {
			JSONArray jsonArray = new JSONArray();
			for (Contact contact : contacts) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", contact.getId());
				jsonObject.put("name", contact.getName());
				jsonObject.put("phone", contact.getPhone());
				jsonArray.put(jsonObject);
			}
			return jsonArray.toString();

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	private final class Partner4javaJavaScript {

		public void call(final String phone) {

			new Handler().post(new Runnable() {
				@Override
				public void run() {
					new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
				}
			});

		}
	}

	// public void getHtml(NewsContentBean content, int isAllowPic) {
	// List<ImageFile> imgfile = new ArrayList<NewsContentBean.ImageFile>();
	// String image = "";
	// imgfile = content.getImagefile();
	// if (isAllowPic == 2) {// 有图
	// if (imgfile != null && imgfile.size() > 0) {
	// Log.i("NewsDetail+有图片文件：：", imgfile.toString());
	// String fileurl = "";
	// String originalurl = "";
	// for (int i = 0; i < imgfile.size(); i++) {
	// fileurl = imgfile.get(i).getFileUrl();
	// originalurl = imgfile.get(i).getOriginalUrl();
	// image += "<span class='photo'><a href ='" + originalurl
	// + "'>" + "<img class='photo_box' src='" + fileurl
	// + "'/></a></span>";
	// }
	// String html = "<html>" + "<head><style type='text/css'>"
	// + style + "</style></head>" + "<body>"
	// + "<div id='title_section'>" + "<div id='title'>"
	// + content.getTitle() + "</div>" + "<div id='subtitle'>"
	// + content.getAddTime() + "   来源：" + content.getSource()
	// + "</div>" + "</div>" + "<hr/>"
	// + "<div id='body_section'>" + image
	// + TTStringUtils.getHtmlString(content.getContent())
	// + "<p>　　(完)</p></div> " + "</body></html>";
	// webView.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
	// webView.setWebViewClient(new CustomWebViewClient());
	// System.out.println(html);
	// } else {
	// NoImage(content);
	// }
	// } else {// 无图
	// NoImage(content);
	// }
	// }

	// private void NoImage(NewsContentBean content) {
	// String html = "<html>" + "<head><style type='text/css'>" + style
	// + "</style></head>" + "<body>" + "<div id='title_section'>"
	// + "<div id='title'>" + content.getTitle() + "</div>"
	// + "<div id='subtitle'>" + content.getAddTime()
	// + "    来源：<p style='text-indent : 2em;'>"
	// + content.getSource()
	// + "</div>" + "</div>" + "<hr/>" + "<div id='body_section'>"
	// + TTStringUtils.getHtmlString(content.getContent())
	// + "</p><p>　　(完)</p></div> "
	// + "</body></html>";
	// webView.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
	// }
}
