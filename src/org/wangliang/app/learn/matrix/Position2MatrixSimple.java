package org.wangliang.app.learn.matrix;

import org.wangliang.app.learn.R ;

import android.app.Activity ;
import android.graphics.Bitmap ;
import android.graphics.BitmapFactory ;
import android.graphics.Matrix ;
import android.graphics.PointF ;
import android.os.Bundle ;
import android.support.v4.view.MotionEventCompat ;
import android.util.Log ;
import android.view.MotionEvent ;
import android.view.View ;
import android.view.View.OnClickListener ;
import android.view.View.OnTouchListener ;
import android.widget.Button ;
import android.widget.ImageView ;

public class Position2MatrixSimple extends Activity implements OnClickListener {
	private ImageView imageView;
	Matrix defaultMatrix = new Matrix();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coordinate_matrix_simple);
		imageView = (ImageView) findViewById(R.id.coordinate_myimage_simple);
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.water_lilies);
		imageView.setImageBitmap(bmp);
		defaultMatrix.set(imageView.getImageMatrix());
		imageView.setOnTouchListener(new MyListener());
		Button reset = (Button) findViewById(R.id.coordinate_reset_simple);
		Button enlarge = (Button) findViewById(R.id.coordinate_enlarge_simple);
		Button ensmall = (Button) findViewById(R.id.coordinate_ensmall_simple);

		reset.setOnClickListener(this);
		enlarge.setOnClickListener(this);
		ensmall.setOnClickListener(this);
	}

	private final class MyListener implements OnTouchListener {

		private float x;
		private float y;
		private Matrix startMatrix = new Matrix();
		private Matrix matrix = new Matrix();
		private int type;
		private float startDis;
		private PointF point;
		public boolean onTouch(View v, MotionEvent event) {
			int action = event.getAction() & MotionEvent.ACTION_MASK;
			switch (action) {
				case MotionEvent.ACTION_DOWN:
					type = 1;
					x = event.getX();
					y = event.getY();
					Matrix old = imageView.getImageMatrix();
					Log.e("MyListener", "action down:"+x+" ,"+y);
					System.out.println("old getImageMatrix():\n"
							+ old.toString());
					startMatrix.set(old);
	
					break;
				case MotionEvent.ACTION_MOVE:
					matrix.set(startMatrix);// 必要(当图片已经拖动之后，需要在最初的时刻平移)
					if(type == 2){
						float dis = getDis(event) ;
						float scale = dis / startDis;
						matrix.postScale(scale, scale, point.x, point.y);
						Log.e("MyListener", "ACTION_MOVE type = 2:"+x+" ,"+y+" current dis "+dis +" scale "+scale);
					}else{
						Log.e("MyListener", "ACTION_MOVE type = "+type+" "+x+" ,"+y);
//						System.out.println("matrix01:\n" + matrix.toString());
						matrix.postTranslate(event.getX() - x, event.getY() - y);
//						System.out.println("matrix02:\n" + matrix.toString());
					}
					
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					
					type = 2;
					startMatrix.set(imageView.getImageMatrix());
					startDis  = getDis(event) ;
					Log.e("MyListener", "ACTION_POINTER_DOWN:"+x+" ,"+y+" startDis "+startDis);
					point = getPoint(event);
					break;
			}
			imageView.setImageMatrix(matrix);
			return true;
		}

	}
	
	private float getDis(MotionEvent event) {
		float a = event.getX(1) - event.getX(0);
		float b = event.getY(1) - event.getY(0);
		return  android.util.FloatMath.sqrt(a * a + b * b);
	}

	private PointF getPoint(MotionEvent event) {
		float x = (event.getX(0) + event.getX(1)) / 2;
		float y = (event.getY(0) + event.getY(1)) / 2;
		return new PointF(x, y);
}
	
//	private final class MyListener implements OnTouchListener {
//		private float x;
//		private float y;
//		private Matrix matrix = new Matrix();
//		private Matrix startMatrix = new Matrix();
//		private int type;
//		private float startDis;
//		private PointF point;
//
//		public boolean onTouch(View v, MotionEvent event) {
//			System.out.println(event.getAction());
//			switch (event.getAction()) {
//				case MotionEvent.ACTION_DOWN:
//					type = 1;
//					x = event.getX();
//					y = event.getY();
//					startMatrix.set(imageView.getImageMatrix());
//					break;
//				case MotionEvent.ACTION_MOVE:
//					matrix.set(startMatrix);
//					if (type == 1)
//						matrix.postTranslate(event.getX() - x, event.getY() - y);
//					else {
//						float scale = getDis(event) / startDis;
//						matrix.postScale(scale, scale, point.x, point.y);
//					}
//					break;
//				case MotionEvent.ACTION_POINTER_DOWN:
//					type = 2;
//					startMatrix.set(imageView.getImageMatrix());
//					startDis = getDis(event);
//					point = getPoint(event);
//					break;
//			}
//			imageView.setImageMatrix(matrix);
//			return true;
//		}
//	}



	private Matrix stateMatrix = new Matrix();
	private Bitmap bmp;

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.coordinate_reset_simple:
			// setContentView(R.layout.coordinate_matrix_simple);
			imageView.setImageMatrix(defaultMatrix);
			break;
		case R.id.coordinate_enlarge_simple:
			stateMatrix.set(imageView.getImageMatrix());
			System.out.println("01 imageView.getImageMatrix():\n"
					+ stateMatrix.toString());
			// stateMatrix.postScale(0.9f, 0.9f, 0f, 0f);
			stateMatrix.postScale(1.05f, 1.05f);
			System.out.println("02 imageView.getImageMatrix():\n"
					+ stateMatrix.toString());
			imageView.setImageMatrix(stateMatrix);
			break;
		case R.id.coordinate_ensmall_simple:
			stateMatrix.set(imageView.getImageMatrix());
			System.out.println("01 imageView.getImageMatrix():\n"
					+ stateMatrix.toString());
			// stateMatrix.postScale(0.9f, 0.9f, 0f, 0f);
			
			float center[] = getCenter();
			stateMatrix.postScale(0.95f, 0.95f,center[0],center[1]);
//			stateMatrix.postScale(0.95f, 0.95f);
			System.out.println("02 imageView.getImageMatrix():\n"
					+ stateMatrix.toString());
			imageView.setImageMatrix(stateMatrix);
			break;

		}

	}

	private float[] getCenter() {
		
		Matrix s = imageView.getImageMatrix();
		float values [] = new float[9];
		s.getValues(values);
		float x = values[Matrix.MTRANS_X];
		float y = values[Matrix.MTRANS_Y];
		int bmW = bmp.getWidth();
		int bmH = bmp.getHeight();
		
		float sx =values[Matrix.MSCALE_X];
		float sy =values[Matrix.MSCALE_Y];
		 float []r = new float[2];
		r[0] = x + sx*bmW /2 ;
		r[1] = y + sy*bmH /2 ;
		System.out.println("getCenter x:"+x+" y:"+y+" w:"+sx*bmW+" h:"+sx*bmH);
		return r;
	}
	
	

}
