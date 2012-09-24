package org.wangliang.app.learn.email;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.wangliang.app.learn.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

public class Email extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.email);
	}

	public void way1(View v) {

		Intent intent = new Intent(Intent.ACTION_SEND,
				Uri.parse("hello@126.com"));
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		String type = getMimeTypeByName(this, "1.mp3");
		intent.setType(type);
		// intent.putExtra(Intent.EXTRA_STREAM,
		// Uri.parse("file:///sdcard/1.mp3"));
		intent.putExtra(Intent.EXTRA_STREAM,
				Uri.parse("file:///android_asset/ic_launcher.png"));
		startActivity(intent);

	}

	public void way2(View v) {
		
		
		 ScreenshotTools.takeScreenShotToEmail(this, this); 
	}
	public void way3(View v) {
		final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.setType("text/html");
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "主题");
//		TextUtils.htmlEncode(body)
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, getContent());
		startActivity(Intent.createChooser(emailIntent, "Email:"));
	}
	
	
	public void way4(View v) {
		String mailId="yourmail@gmail.com";
		Intent emailIntent = new Intent(Intent.ACTION_SENDTO, 
		                                Uri.fromParts("mailto",mailId, null)); 
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject text here"); 
		// you can use simple text like this
		// emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,"Body text here"); 
		// or get fancy with HTML like this
		emailIntent.putExtra(
		         Intent.EXTRA_TEXT,
		         Html.fromHtml(new StringBuilder()
		             .append("<p><b>Some Content</b></p>")
		             .append("<a>http://www.google.com</a>")
		             .append("<small><p>More content</p></small>")
		             .toString())
		         );
		startActivity(Intent.createChooser(emailIntent, "Send email..."));
	}
	
	

	
	private Spanned getContent() {
		
		 return Html.fromHtml("<br/><font color=\"#0f00ff\">http://weibo.com/guoku </font><br/><br/> Hello world <br/><img src='"+R.drawable.ic_launcher+"'/><br/>wwww.guoku.com", new MyImageGetter(), null);
	}
	
	class MyImageGetter implements ImageGetter {


		@Override
		public Drawable getDrawable(String source) {
			Drawable d = getResources().getDrawable(R.drawable.ic_launcher);
			d.setBounds(0, 0, d.getIntrinsicWidth() / 2,
					d.getIntrinsicHeight() / 2);

			return d;
		}

	}
	
	private String getMimeTypeByName(Context context, String name) {
		if (null == name || 0 == name.length())
			return null;
		String extension = null;
		int dot = name.lastIndexOf(".");
		if (dot >= 0) {
			extension = name.substring(dot + 1);
		}
		String sys = null;
		if (extension != null) {
			sys = MimeTypeMap.getSingleton()
					.getMimeTypeFromExtension(extension);
		}
		return sys;

	}
}

class ScreenshotTools {

	// http://johnson008.blog.51cto.com/4000361/729440
	public static long minSizeSDcard = 50;
	public static String filePath = Environment.getExternalStorageDirectory()
			+ "/FJBICache";
	public static String fileName = "chart.png";
	public static String detailPath = filePath + File.separator + fileName;
	public static final int SEND_EMAIL = 1;

	// public static String detailPath="/sdcard/FjbiCache/chart.png";

	/**
	 * 调用系统程序发送邮件
	 * 
	 * @author Johnson
	 * 
	 * */

	private static void sendEmail(Context context, String[] to, String subject,
			String body, String path) {

		Intent email = new Intent(android.content.Intent.ACTION_SEND);

		if (to != null) {
			email.putExtra(android.content.Intent.EXTRA_EMAIL, to);
		}
		if (subject != null) {
			email.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
		}
		if (body != null) {
			email.putExtra(android.content.Intent.EXTRA_TEXT, body);
		}
		if (path != null) {

			/*
			 * 用email.setType("image/png");或者email.setType(
			 * "application/octet-stream"); 都不会影响邮件的发送
			 * 为什么email.setType("image/png"
			 * );而不用email.setType("application/octet-stream"); ?
			 * 因为在开发中发现setType("image/png")，系统会同时给你调用彩信，邮件，等.....
			 */

			File file = new File(path);
			email.putExtra(android.content.Intent.EXTRA_STREAM,
					Uri.fromFile(file));
			email.setType("image/png");
		}
		context.startActivity(Intent.createChooser(email, "请选择发送软件"));

	}
	
	/**
	View viewConverted;  
	  
	Bitmap viewBitmap = Bitmap.createBitmap(viewConverted.getWidth(), viewConverted.getHeight(),Bitmap.Config.ARGB_8888);  
	Canvas canvas = new Canvas(viewBitmap);  
	  
	viewConverted.draw(canvas); 
	**/
	/**
	 * 获取指定Activity的截屏，保存到png文件
	 * 
	 * @author Johnson
	 * **/

	private static Bitmap takeScreenShot(Activity activity) {
		// View是你需要截图的View
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap b1 = view.getDrawingCache();

		// 获取状态栏高度
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		System.out.println(statusBarHeight);

		// 获取屏幕长和高
		int width = activity.getWindowManager().getDefaultDisplay().getWidth();
		int height = activity.getWindowManager().getDefaultDisplay()
				.getHeight();
		// 去掉标题栏
		// Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
		Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
				- statusBarHeight);
		view.destroyDrawingCache();
		return b;
	}

	/**
	 * 截图保存
	 * 
	 * @author Johnson
	 * **/
	private static void savePic(Bitmap b, String filePath, String fileName) {

		File f = new File(filePath);

		if (!f.exists()) {
			f.mkdir();
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filePath + File.separator + fileName);
			if (null != fos) {
				b.compress(Bitmap.CompressFormat.PNG, 90, fos);
				fos.flush();
				fos.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 截屏并发送邮件
	 * 
	 * @author Johnson
	 * **/
	public static void takeScreenShotToEmail(Context context, Activity a) {

		if (getAvailableSDcard(context)) {
			savePic(takeScreenShot(a), filePath, fileName);

			// selectDialog(context);
			sendEmail(context, null, null, null, detailPath);
		}

	}

	/***
	 * Sd判断SD卡是否可用
	 * 
	 * @author Johnson minSizeSDcard>50kb
	 * */

	public static boolean getAvailableSDcard(Context context) {

		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在

		System.out.println("+++" + sdCardExist);
		if (sdCardExist) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getAvailableBlocks();
			long sdCardSize = (availableBlocks * blockSize) / 1024;// KB值

			if (sdCardSize > minSizeSDcard) {
				System.out.println("SDcardSize:::" + minSizeSDcard + "KB");
				return true;
			} else {
				Toast.makeText(context, "SD卡空间不足", Toast.LENGTH_SHORT).show();
			}

		} else {
			Toast.makeText(context, "请在使用转发功能之前插入SD卡", Toast.LENGTH_SHORT)
					.show();

		}
		return false;
	}

}
