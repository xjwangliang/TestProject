package org.wangliang.app.learn.matrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.wangliang.app.learn.R ;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PositionMatrix extends Activity { 

	private Button applyChangeBt; 
	private Button cancleBt; 
	private EditText [] et=new EditText[9]; 
	private float []carray=new float[9]; 
	private static final float [] defaultCoordinate ={1 , 0, 0,   
												 	0 , 1, 0,   
												 	0 , 0, 1 };
	
	private PositionView sv;
	/** Called when the activity is first created. */ 
	@Override 
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.coordinate_matrix); 
	
		applyChangeBt=(Button)findViewById(R.id.coordinate_apply); 
		cancleBt=(Button)findViewById(R.id.coordinate_cancle); 
		sv=(PositionView)findViewById(R.id.coordinate_myimage); 
		for(int i=0;i<9;i++){ 
			et[i]=(EditText)findViewById(R.id.coordinate01+i); 
			//carray[i]=Float.valueOf(et[i].getText().toString());
			et[i].setText(""+defaultCoordinate[i]);
		} 
		applyChangeBt.setOnClickListener(myListener); 
		cancleBt.setOnClickListener(myListener);
		sv.setDrawingCacheEnabled(true);
		
	} 

	private Button.OnClickListener myListener=new Button.OnClickListener(){ 
		public void onClick(View view) { 
			switch (view.getId()) {
			case R.id.coordinate_apply:
				getValues(); 
				sv.setValues(carray); 
				sv.invalidate(); 
				break;

			case R.id.coordinate_cancle:
				for(int i=0;i<9;i++){ 
					et[i].setText(""+defaultCoordinate[i]);
				} 
				sv.setValues(defaultCoordinate); 
				sv.invalidate(); 
				break;
			}
		} 
	}; 
	public void getValues(){ 
		for(int i=0;i<9;i++){ 
			carray[i]=Float.valueOf(et[i].getText().toString()); 
		} 
	} 
	
	  @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	    	 menu.add(0, 0, 0, "保存图片").setOnMenuItemClickListener(myMenuClickListener);
	    	 menu.add(0, 1, 1, "方便模式").setOnMenuItemClickListener(myMenuClickListener);
	         return true;
	    	
	    }
	  
	    OnMenuItemClickListener myMenuClickListener = new MenuItem.OnMenuItemClickListener() {
	        public boolean onMenuItemClick(MenuItem item) {
	        	Intent intent = null ;
	        	switch (item.getItemId()) {
				case 0:
					Bitmap bitMap = sv.getDrawingCache(true);
					sv.getDrawingCache();
					 File file=new File("/sdcard/mybookback.jpg");
				        try {
				            FileOutputStream out=new FileOutputStream(file);
				            if(bitMap.compress(Bitmap.CompressFormat.JPEG, 100, out)){
				                out.flush();
				                out.close();
				            }
				        }catch (IOException e) {
				            e.printStackTrace();
				        }
				        Toast.makeText(PositionMatrix.this, "保存成功", 0).show();
					break;

				case 1:
					 intent =new Intent(PositionMatrix.this,Position2MatrixSimple.class);
					startActivity(intent);
					break;
				}
	            return true;
	        }
	    };


} 
//===============
class PositionView extends View { 
	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG); 
	private Bitmap mBitmap; 
	private float [] array= {1 , 0, 0,   
		 					0 , 1, 0,   
		 					0 , 0, 1 };


	public PositionView(Context context,AttributeSet attrs) { 
		super(context,attrs); 
		mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.water_lilies); 
		//invalidate(); 
	} 


	public void setValues(float [] a){ 
		for(int i=0;i<9;i++){ 
			array[i]=a[i]; 
		} 

	} 
	@Override protected void onDraw(Canvas canvas) { 
		Paint paint = mPaint; 
		//canvas.drawBitmap(mBitmap, 0, 0, paint); 
		//new 一个坐标变换矩阵 
		Matrix cm = new Matrix(); 
		//为坐标变换矩阵设置响应的值 
		cm.setValues(array); 
		//按照坐标变换矩阵的描述绘图 
		
		canvas.drawBitmap(mBitmap, cm, paint); 
		
		System.out.println("CMatrix--------->onDraw");
		System.out.println(cm.toString());
	} 

}
 
 
// mBitmap.createBitmap(source, x, y, width, height, m, filter)
// canvas.drawBitmap(mBitmap, cm, paint); 
// imageView.setImageMatrix(stateMatrix);