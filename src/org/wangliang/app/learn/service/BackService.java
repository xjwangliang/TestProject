package org.wangliang.app.learn.service ;

import java.lang.reflect.Method ;
import java.util.Timer ;
import java.util.TimerTask ;

import org.wangliang.app.learn.R ;

import android.app.Notification ;
import android.app.Service ;
import android.content.Intent ;
import android.content.res.Configuration ;
import android.media.MediaPlayer ;
import android.os.Handler ;
import android.os.IBinder ;
import android.util.Log ;
import android.widget.Toast ;
//http://www.congci.com/item/android-chengxu-qian-houtai-qiehuan
public class BackService extends Service {
	// 为日志工具设置标签
	private static String TAG = "MusicService" ;
	// 定义音乐播放器变量
	// private MediaPlayer mPlayer ;
	Timer timer = null ;
	TimerTask task = null ;

	// boolean running = false ;
	@Override
	public IBinder onBind(Intent intent) {
		Toast.makeText(this, "MusicSevice onBind()", Toast.LENGTH_SHORT).show() ;
		Log.e(TAG,
				"MusicSerice onBind() " + intent.hashCode() + " "
						+ intent.getIntExtra("data", 0)) ;

		// mPlayer.start() ;
		doSomething() ;
		return null ;
	}

	private void doSomething() {
		task = new TimerTask() {
			private int count = 0 ;

			@Override
			public void run() {
				Log.e(TAG, "Do something ..." + count++ + "    Task object:"
						+ this) ;
				// if(!running && timer != null){
				// task.cancel();
				// timer.cancel();
				// timer.purge();
				// }
			}
		} ;
		timer = new Timer() ;
		timer.schedule(task, 20, 5000) ;
	}

	private void cancleSomething() {
		// running = false ;
		if (task != null) task.cancel() ;
		if (timer != null) {
			timer.cancel() ;
			timer.purge() ;
		}

	}

	@Override
	public void onCreate() {
		Toast.makeText(this, "MusicSevice onCreate()", Toast.LENGTH_SHORT)
				.show() ;
		Log.e(TAG, "MusicSerice onCreate()") ;
		// /mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music) ;
		// 设置可以重复播放
		// mPlayer.setLooping(true) ;
		super.onCreate() ;
	}

	boolean started = false ;

	@Override
	public void onStart(Intent intent, int startId) {
		Toast.makeText(this, "MusicSevice onStart()", Toast.LENGTH_SHORT)
				.show() ;
		Log.e(TAG, "MusicSerice onStart() intent " + intent.hashCode() + " "
				+ intent.getExtras() + " startId " + startId) ;
		if (!started) {
			doSomething() ;
			started = true ;
		} else {
			Log.d(TAG,
					"MusicSerice onStart() Already start intent "
							+ intent.hashCode() + " " + intent.getExtras()
							+ " startId " + startId) ;
		}
		// mPlayer.start() ;

		super.onStart(intent, startId) ;
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "MusicSevice onDestroy()", Toast.LENGTH_SHORT)
				.show() ;
		Log.e(TAG, "MusicSerice onDestroy()") ;

		// mPlayer.stop() ;
		cancleSomething() ;
		super.onDestroy() ;
	}

	// 其它对象通过unbindService方法通知该Service时该方法被调用
	@Override
	public boolean onUnbind(Intent intent) {
		Toast.makeText(this, "MusicSevice onUnbind()", Toast.LENGTH_SHORT)
				.show() ;
		Log.e(TAG,
				"MusicSerice onUnbind()" + intent.hashCode() + " "
						+ intent.getIntExtra("data", 0)) ;

		// mPlayer.stop() ;
		cancleSomething() ;
		if (intent.getIntExtra("data", 0) > 0) { return true ; }
		return super.onUnbind(intent) ;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "MusicSevice onUnbind()", Toast.LENGTH_SHORT)
				.show() ;
		Log.e(TAG, "MusicSerice onStartCommand()" + intent.hashCode() + " "
				+ intent.getIntExtra("data", 0) + " flags " + flags
				+ " startId " + startId) ;
		return super.onStartCommand(intent, flags, startId) ;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig) ;
	}

	@Override
	public void onLowMemory() {
		Toast.makeText(this, "MusicSevice onLowMemory()", Toast.LENGTH_SHORT)
				.show() ;
		Log.e(TAG, "MusicSerice onLowMemory()") ;
		super.onLowMemory() ;
	}

	@Override
	public void onRebind(Intent intent) {
		Toast.makeText(this, "MusicSevice onRebind()", Toast.LENGTH_SHORT)
				.show() ;
		Log.e(TAG,
				"MusicSerice onRebind() " + intent.hashCode() + " "
						+ intent.getIntExtra("data", 0)) ;
		super.onRebind(intent) ;
	}

	static Class<?>[] arrayB ;
	static Class<?>[] arrayC ;
	static Class<?>[] arrayD ;
	static {
		Class<?>[] arrayOfClass = new Class[1] ;
		arrayOfClass[0] = Boolean.TYPE ;
		arrayB = arrayOfClass ;

		arrayOfClass = new Class[2] ;
		arrayOfClass[0] = Integer.TYPE ;
		arrayOfClass[1] = Notification.class ;
		arrayC = arrayOfClass ;

		arrayOfClass = new Class[1] ;
		arrayOfClass[0] = Boolean.TYPE ;
		arrayD = arrayOfClass ;
	}

	private void frontOrBack() {
		try {
			Method frontM = getClass().getMethod("startForeground", arrayC) ;
			Method backM = getClass().getMethod("stopForeground", arrayD) ;
			return ;
		} catch (NoSuchMethodException localNoSuchMethodException1) {
			try {
				Method setFrontM = getClass()
						.getMethod("setForeground", arrayB) ;
			} catch (NoSuchMethodException localNoSuchMethodException2) {
			}
		}
	}

}
