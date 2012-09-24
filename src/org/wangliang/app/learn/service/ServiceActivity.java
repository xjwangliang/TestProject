package org.wangliang.app.learn.service;

import java.util.Random ;

import org.wangliang.app.learn.R ;

import android.app.Activity ;
import android.content.ComponentName ;
import android.content.Context ;
import android.content.Intent ;
import android.content.ServiceConnection ;
import android.os.Bundle ;
import android.os.IBinder ;
import android.util.Log ;
import android.view.View ;
import android.view.View.OnClickListener ;
import android.widget.Button ;
import android.widget.Toast ;

public class ServiceActivity extends Activity {
	//为日志工具设置标签  
    private static String TAG = "MusicService";  
      
    /** Called when the activity is first created. */  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.service_layout);  
          
        //输出Toast消息和日志记录  
        Toast.makeText(this, "MusicServiceActivity",  Toast.LENGTH_SHORT).show();  
        Log.e(TAG, "MusicServiceActivity");  
          
        initlizeViews();  
    }  
      
    private void initlizeViews(){  
        Button btnStart = (Button)findViewById(R.id.startMusic);  
        Button btnStop = (Button)findViewById(R.id.stopMusic);  
        Button btnBind = (Button)findViewById(R.id.bindMusic);  
        Button btnUnbind = (Button)findViewById(R.id.unbindMusic);  
          
        //定义点击监听器  
        OnClickListener ocl = new OnClickListener() {  
              
            @Override  
            public void onClick(View v) {  
            	boolean nextBoolean = random.nextBoolean() ;
                //显示指定  intent所指的对象是个   service  
                Intent intent = new Intent(ServiceActivity.this,BackService.class);  
                intent.putExtra("data", 1000);
                Intent intent1 = new Intent(ServiceActivity.this,BackService.class);  
                intent1.putExtra("data", 2000);
                switch(v.getId()){  
                case R.id.startMusic:  
                	if(nextBoolean){
                		startedService = startService(intent) ;
					}else{
						startedService = startService(intent1) ;
					}
                    //开始服务  
					//startedService = startService(intent) ;
					if(startedService != null){
						Toast.makeText(ServiceActivity.this, "startService "  +startedService.getClassName()
                                , Toast.LENGTH_SHORT).show();  
                    	 Log.e(TAG, "startService "  +startedService.getClassName());  
					}else{
						Toast.makeText(ServiceActivity.this, "startService failured" 
                                , Toast.LENGTH_SHORT).show();  
                    	 Log.e(TAG, "startService failured"  );  
					}
                    break;  
                case R.id.stopMusic:  
                    //停止服务  
						
						boolean stopped = false ;
						if(nextBoolean){
							stopped = stopService(intent) ; 
						}else{
							stopped = stopService(intent1) ; 
						}
						 
						if(stopped){
							Toast.makeText(ServiceActivity.this, "stopService success " 
	                                , Toast.LENGTH_SHORT).show();  
	                    	 Log.e(TAG, "stopService success " );  
						}else{
							Toast.makeText(ServiceActivity.this, "stopService failured" 
	                                , Toast.LENGTH_SHORT).show();  
	                    	 Log.e(TAG, "stopService failured"  );  
						}
                    break;  
                case R.id.bindMusic:  
                	if(nextBoolean){
                		serviceBound = bindService(intent, conn, Context.BIND_AUTO_CREATE); 
					}else{
						serviceBound = bindService(intent, conn, Context.BIND_AUTO_CREATE); 
					}
                    //绑定服务  
                	 
                    break;  
                case R.id.unbindMusic:  
                	if(nextBoolean){
                		 //解绑服务  
                        if(serviceBound){
                        	unbindService(conn);  //没有绑定、绑定但是conn不同都会失败
                        	serviceBound = false ;
                        }else {
                        	Toast.makeText(ServiceActivity.this, "没有绑定，不能解除绑定"  
                                    , Toast.LENGTH_SHORT).show();  
                        	 Log.e(TAG, "没有绑定，不能解除绑定");  
                        }
					}else{
						 //解绑服务  
	                    if(serviceBound){
	                    	unbindService(conn);  //没有bind此时
	                    	serviceBound = false ;
	                    }else {
	                    	Toast.makeText(ServiceActivity.this, "没有绑定，不能解除绑定"  
	                                , Toast.LENGTH_SHORT).show();  
	                    	 Log.e(TAG, "没有绑定，不能解除绑定");  
	                    }
					}
                   
                    break;  
                }  
            }  
        };  
        
         //绑定点击监听  
        btnStart.setOnClickListener(ocl);  
        btnStop.setOnClickListener(ocl);  
        btnBind.setOnClickListener(ocl);  
        btnUnbind.setOnClickListener(ocl);  
    }  
    Random random = new Random() ;
    private boolean serviceBound = false ;
    ComponentName startedService = null ;
    //定义服务链接对象  
    final ServiceConnection conn = new ServiceConnection() {  
          
        @Override  
        public void onServiceDisconnected(ComponentName name) {  
            Toast.makeText(ServiceActivity.this, "MusicServiceActivity onSeviceDisconnected"  
                    , Toast.LENGTH_SHORT).show();  
            Log.e(TAG, "MusicServiceActivity onSeviceDisconnected");  
        }  
          
        @Override  
        public void onServiceConnected(ComponentName name, IBinder service) {  
            Toast.makeText(ServiceActivity.this, "MusicServiceActivity onServiceConnected"  
                    ,Toast.LENGTH_SHORT).show();  
            Log.e(TAG, "MusicServiceActivity onServiceConnected");  
        }  
    };  
    final ServiceConnection conn1 = new ServiceConnection() {  
    	
    	@Override  
    	public void onServiceDisconnected(ComponentName name) {  
    		Toast.makeText(ServiceActivity.this, "MusicServiceActivity onSeviceDisconnected"  
    				, Toast.LENGTH_SHORT).show();  
    		Log.e(TAG, "MusicServiceActivity onSeviceDisconnected ...1");  
    	}  
    	
    	@Override  
    	public void onServiceConnected(ComponentName name, IBinder service) {  
    		Toast.makeText(ServiceActivity.this, "MusicServiceActivity onServiceConnected"  
    				,Toast.LENGTH_SHORT).show();  
    		Log.e(TAG, "MusicServiceActivity onServiceConnected...1");  
    	}  
    };  
}
