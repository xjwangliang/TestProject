package org.wangliang.app.learn.matrix;

import org.wangliang.app.learn.R ;

import android.app.Activity ;
import android.content.Context ;
import android.graphics.Bitmap ;
import android.graphics.BitmapFactory ;
import android.graphics.Canvas ;
import android.graphics.ColorMatrixColorFilter ;
import android.graphics.Paint ;
import android.os.Bundle ;
import android.util.AttributeSet ;
import android.view.View ;
import android.widget.Button ;
import android.widget.EditText ;

public class ColorMatrix extends Activity { 

	private Button applyBt; 
	private Button cancleBt; 
	private EditText [] et=new EditText[20]; 
	private float []carray=new float[20]; 
	private static final float [] defaultColor ={1 , 0, 0, 0, 0, 
												 0 , 1, 0, 0, 0, 
												 0 , 0, 1, 0, 0, 
												 0 , 0, 0, 1, 0};
	private ColorView sv; 
	@Override 
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.color_matrix); 
	
		applyBt=(Button)findViewById(R.id.coordinate_apply); 
		cancleBt = (Button) findViewById(R.id.coordinate_cancle);
		sv=(ColorView)findViewById(R.id.coordinate_myimage); 
	
		for(int i=0;i<20;i++){ 
			et[i]=(EditText)findViewById(R.id.color01+i); 
			//carray[i]=Float.valueOf(et[i].getText().toString()); 
			et[i].setText(""+defaultColor[i]);
		} 
		applyBt.setOnClickListener(myListener); 
		cancleBt.setOnClickListener(myListener);
	} 

	private Button.OnClickListener myListener = new Button.OnClickListener(){ 
		public void onClick(View view) { 
			switch (view.getId()) {
			case R.id.coordinate_apply:
				getValues(); 
				sv.setValues(carray); 
				sv.invalidate(); 
				break;

			case R.id.coordinate_cancle:
				for(int i=0;i<20;i++){ 
					et[i].setText(""+defaultColor[i]);
				} 
				sv.setValues(defaultColor); 
				sv.invalidate(); 
				break;
			}
			
		} 
	}; 
	
	public void getValues(){ 
		for(int i=0;i<20;i++){ 
			carray[i]=Float.valueOf(et[i].getText().toString()); 
		} 
	}
	
}
 class ColorView extends View { 
	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG); 
	private Bitmap mBitmap ; 
	private float [] array= {1 , 0, 0, 0, 0, 
							 0 , 1, 0, 0, 0, 
							 0 , 0, 1, 0, 0, 
							 0 , 0, 0, 1, 0};

	private float mAngle; 

	public ColorView(Context context,AttributeSet attrs) { 
		super(context,attrs); 
		mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.water_lilies); 
		//invalidate(); 
		System.out.println("MyImage的构造方法执行了。");
	} 


	public void setValues(float [] a){ 
		for(int i=0;i<20;i++){ 
			array[i]=a[i]; 
			System.out.println("MyImage的setValues方法执行了。");
		} 
	} 

	@Override 
	protected void onDraw(Canvas canvas) { 
		Paint paint = mPaint; 
		paint.setColorFilter(null); 
		//canvas.drawBitmap(mBitmap, 0, 0, paint); 
		android.graphics.ColorMatrix cm = new android.graphics.ColorMatrix(); 
		//设置颜色矩阵 
		cm.set(array); 
		//颜色滤镜，将颜色矩阵应用于图片 
		paint.setColorFilter(new ColorMatrixColorFilter(cm)); 
		
		//绘图 
		canvas.drawBitmap(mBitmap, 0, 0, paint); 
		System.out.println("CMatrix--------->onDraw");
		printArr(array);
		
	} 
	private void printArr(float[] arr){
		System.out.println("---------------");
		for(int i=0;i<arr.length;i++){
			System.out.print(arr[i]+"  ");
			if((i+1)%5 == 0)
				System.out.println("---------------");
		}
		System.out.println("---------------");
		
	}
}
