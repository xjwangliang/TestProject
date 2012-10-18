package org.wangliang.app.learn.matrix;

import java.io.File ;
import java.io.FileInputStream ;
import java.io.FileNotFoundException ;
import java.io.IOException ;

import org.wangliang.app.learn.R ;

import android.app.Activity ;
import android.content.Context ;
import android.content.Intent ;
import android.graphics.Bitmap ;
import android.graphics.BitmapFactory ;
import android.graphics.BitmapFactory.Options ;
import android.graphics.Camera ;
import android.graphics.Canvas ;
import android.graphics.Color ;
import android.graphics.Matrix ;
import android.graphics.Paint ;
import android.graphics.Point ;
import android.graphics.Rect ;
import android.graphics.RectF ;
import android.graphics.drawable.BitmapDrawable ;
import android.os.Bundle ;
import android.util.AttributeSet ;
import android.util.Log ;
import android.view.Menu ;
import android.view.MenuItem ;
import android.view.MenuItem.OnMenuItemClickListener ;
import android.view.MotionEvent ;
import android.view.View ;
import android.view.View.OnClickListener ;
import android.widget.ImageView ;
import android.widget.TextView ;
import android.widget.Toast ;

public class MatrixDemo extends Activity /*implements OnClickListener*/ {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);
		//findViewById(R.id.matrix01).setOnClickListener(this);
		//findViewById(R.id.matrix02).setOnClickListener(this);
		TextView tv = new TextView(this) ;
		tv.setText("点击Menu");
		setContentView(tv);
		
	}

//	@Override
//	public void onClick(View v) {
//		Intent intent ;
//		switch (v.getId()) {
//			case R.id.matrix01:
//				 intent = new Intent(this,MatrixTest01.class);
//				startActivity(intent);
//				break;
//			case R.id.matrix02:
//				 intent = new Intent(this,BitMapActivity.class);
//				startActivity(intent);
//				break;
//	
//			default:
//				break;
//		}
//		
//	}
	
	
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	    	 menu.add(0, 0, 0, "位置变换").setOnMenuItemClickListener(myMenuClickListener);
	    	 menu.add(0, 1, 1, "颜色处理").setOnMenuItemClickListener(myMenuClickListener);
	    	 menu.add(0, 2, 2, "Zoom Control").setOnMenuItemClickListener(myMenuClickListener);
	    	 menu.add(0, 3, 3, "Rotate Control").setOnMenuItemClickListener(myMenuClickListener);
	    	 menu.add(0, 4, 4, "复杂一点").setOnMenuItemClickListener(myMenuClickListener);
	    	 menu.add(0, 5, 5, "很简单").setOnMenuItemClickListener(myMenuClickListener);
	         return true;
	    	
	    }
	    OnMenuItemClickListener myMenuClickListener = new MenuItem.OnMenuItemClickListener() {
	        public boolean onMenuItemClick(MenuItem item) {
	        	Intent intent = null ;
	        	switch (item.getItemId()) {
					case 0:
						intent= new Intent(MatrixDemo.this,PositionMatrix.class );
						startActivity(intent);
						break;
					case 1:
						intent= new Intent(MatrixDemo.this,ColorMatrix.class );
						startActivity(intent);
						break;
					case 2:
						CommonImgEffectView view = new CommonImgEffectView(MatrixDemo.this);
						setContentView(view);
						break;
					case 3:
						FlipImgEffectView view2 = new FlipImgEffectView(MatrixDemo.this);
						setContentView(view2);
						break;
					case 4:
						intent= new Intent(MatrixDemo.this,Position2MatrixSimple.class );
						startActivity(intent);
						break;
					case 5:
						setContentView(R.layout.matrix_test_sample);

						ImageView imageView= (ImageView) findViewById(R.id.src_myimage);

						
						imageView.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								ImageView imageView= (ImageView) findViewById(R.id.myimage);
								Matrix mMatrix = new Matrix();
								Bitmap bmp = ((BitmapDrawable) getResources().getDrawable(
										R.drawable.picture1)).getBitmap();
								// 旋转60度
								//mMatrix.setRotate(60);
								// 倾斜
								//mMatrix.postSkew(0.3f, 0.7f);
								// 放大缩小
								//mMatrix.setScale(0.5f, 2.5f);

								mMatrix.setScale(0.5f, 0.5f);
								mMatrix.postRotate(60);
								Bitmap bm = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(),
										bmp.getHeight(), mMatrix, true);
								System.out.println("mMatrix "+mMatrix) ;
								imageView.setImageBitmap(bm);
								Toast.makeText(getApplicationContext(), "Finish", 0).show() ;
								
							}
						});
						break;
				}
	            return true;
	        }
	    };
	    
	    
	    
	    
//	    或者
//	    @Override
//		public boolean onCreateOptionsMenu(Menu menu) {
//			menu.addSubMenu(0, 0, 0, "one");
//			menu.addSubMenu(0, 1, 1, "two");
//			menu.addSubMenu(0, 2, 2, "three");
//			return super.onCreateOptionsMenu(menu);
//		}
//
//		@Override
//		public boolean onOptionsItemSelected(MenuItem item) {
//			switch (item.getItemId()) {
//			case 0:
//				t1();
//				break;
//			case 1:
//				t2();
//				break;
//			case 2:
//				t3();
//				break;
//			}
//			return super.onOptionsItemSelected(item);
//		}


}


// ================================================================================================================================
// FlipImgEffectView
// ================================================================================================================================
/**
 * 使用矩阵控制图片移动、缩放、旋转
 * 
 * @author 张进
 */
class CommonImgEffectView extends View {

	private Context context;
	private Bitmap mainBmp, controlBmp;
	private int mainBmpWidth, mainBmpHeight, controlBmpWidth,
			controlBmpHeight;
	private Matrix matrix;
	private float[] srcPs, dstPs;
	private RectF srcRect, dstRect;
	private Paint paint, paintRect, paintFrame;
	private float deltaX = 0, deltaY = 0; // 位移值
	private float scaleValue = 1; // 缩放值
	private Point lastPoint;
	private Point prePivot, lastPivot;
	private float preDegree, lastDegree;
	private short currentSelectedPointindex; // 当前操作点击点
	private Point symmetricPoint = new Point(); // 当前操作点对称点

	/**
	 * 图片操作类型
	 */
	public static final int OPER_DEFAULT = -1; // 默认
	public static final int OPER_TRANSLATE = 0; // 移动
	public static final int OPER_SCALE = 1; // 缩放
	public static final int OPER_ROTATE = 2; // 旋转
	public static final int OPER_SELECTED = 3; // 选择
	public int lastOper = OPER_DEFAULT;

	/*
	 * 图片控制点 0---1---2 | | 7 8 3 | | 6---5---4
	 */
	public static final int CTR_NONE = -1;
	public static final int CTR_LEFT_TOP = 0;
	public static final int CTR_MID_TOP = 1;
	public static final int CTR_RIGHT_TOP = 2;
	public static final int CTR_RIGHT_MID = 3;
	public static final int CTR_RIGHT_BOTTOM = 4;
	public static final int CTR_MID_BOTTOM = 5;
	public static final int CTR_LEFT_BOTTOM = 6;
	public static final int CTR_LEFT_MID = 7;
	public static final int CTR_MID_MID = 8;
	public int current_ctr = CTR_NONE;

	public CommonImgEffectView(Context context) {
		super(context);
		this.context = context;
		initData();
	}

	public CommonImgEffectView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initData();
	}

	/**
	 * 初始化数据
	 * 
	 * @author 张进
	 */
	private void initData() {
		mainBmp = BitmapFactory.decodeResource(this.context.getResources(),
				R.drawable.water_lilies);
		controlBmp = BitmapFactory.decodeResource(
				this.context.getResources(), R.drawable.matrix_control);
		// mainBmp =
		// BitmapFactory.decodeResource(this.context.getResources(),
		// R.drawable.flower);
		// controlBmp =
		// BitmapFactory.decodeResource(this.context.getResources(),
		// R.drawable.control);
		mainBmpWidth = mainBmp.getWidth();
		mainBmpHeight = mainBmp.getHeight();
		controlBmpWidth = controlBmp.getWidth();
		controlBmpHeight = controlBmp.getHeight();

		srcPs = new float[] { 0, 0, mainBmpWidth / 2, 0, mainBmpWidth, 0,
				mainBmpWidth, mainBmpHeight / 2, mainBmpWidth,
				mainBmpHeight, mainBmpWidth / 2, mainBmpHeight, 0,
				mainBmpHeight, 0, mainBmpHeight / 2, mainBmpWidth / 2,
				mainBmpHeight / 2 };
		dstPs = srcPs.clone();
		srcRect = new RectF(0, 0, mainBmpWidth, mainBmpHeight);
		dstRect = new RectF();

		matrix = new Matrix();

		prePivot = new Point(mainBmpWidth / 2, mainBmpHeight / 2);
		lastPivot = new Point(mainBmpWidth / 2, mainBmpHeight / 2);

		lastPoint = new Point(0, 0);

		paint = new Paint();

		paintRect = new Paint();
		paintRect.setColor(Color.RED);
		paintRect.setAlpha(100);
		paintRect.setAntiAlias(true);

		paintFrame = new Paint();
		paintFrame.setColor(Color.GREEN);
		paintFrame.setAntiAlias(true);

		setMatrix(OPER_DEFAULT);
	}

	/**
	 * 矩阵变换，达到图形平移的目的
	 * 
	 * @author 张进
	 */
	private void setMatrix(int operationType) {
		switch (operationType) {
		case OPER_TRANSLATE:
			matrix.postTranslate(deltaX, deltaY);
			break;
		case OPER_SCALE:
			matrix.postScale(scaleValue, scaleValue, symmetricPoint.x,
					symmetricPoint.y);
			break;
		case OPER_ROTATE:
			matrix.postRotate(preDegree - lastDegree,
					dstPs[CTR_MID_MID * 2], dstPs[CTR_MID_MID * 2 + 1]);
			break;
		}

		matrix.mapPoints(dstPs, srcPs);
		matrix.mapRect(dstRect, srcRect);
	}

	private boolean isOnPic(int x, int y) {
		if (dstRect.contains(x, y)) {
			return true;
		} else
			return false;
	}

	private int getOperationType(MotionEvent event) {

		int evX = (int) event.getX();
		int evY = (int) event.getY();
		int curOper = lastOper;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			current_ctr = isOnCP(evX, evY);
			Log.i("img", "current_ctr is " + current_ctr);
			if (current_ctr != CTR_NONE || isOnPic(evX, evY)) {
				curOper = OPER_SELECTED;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (current_ctr > CTR_NONE && current_ctr < CTR_MID_MID) {
				curOper = OPER_SCALE;
			} else if (current_ctr == CTR_MID_MID) {
				curOper = OPER_ROTATE;
			} else if (lastOper == OPER_SELECTED) {
				curOper = OPER_TRANSLATE;
			}
			break;
		case MotionEvent.ACTION_UP:
			curOper = OPER_SELECTED;
			break;
		default:
			break;
		}
		Log.d("img", "curOper is " + curOper);
		return curOper;

	}

	/**
	 * 判断点所在的控制点
	 * 
	 * @param evX
	 * @param evY
	 * @return
	 */
	private int isOnCP(int evx, int evy) {
		Rect rect = new Rect(evx - controlBmpWidth / 2, evy
				- controlBmpHeight / 2, evx + controlBmpWidth / 2, evy
				+ controlBmpHeight / 2);
		int res = 0;
		for (int i = 0; i < dstPs.length; i += 2) {
			if (rect.contains((int) dstPs[i], (int) dstPs[i + 1])) {
				return res;
			}
			++res;
		}
		return CTR_NONE;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		int evX = (int) event.getX();
		int evY = (int) event.getY();

		int operType = OPER_DEFAULT;
		operType = getOperationType(event);

		switch (operType) {
		case OPER_TRANSLATE:
			translate(evX, evY);
			break;
		case OPER_SCALE:
			scale(event);
			break;
		case OPER_ROTATE:
			rotate(event);
			break;
		}

		lastPoint.x = evX;
		lastPoint.y = evY;

		lastOper = operType;
		invalidate();// 重绘
		return true;
	}

	/**
	 * 移动
	 * 
	 * @param evx
	 * @param evy
	 * @author zhang_jin1
	 */
	private void translate(int evx, int evy) {

		prePivot.x += evx - lastPoint.x;
		prePivot.y += evy - lastPoint.y;

		deltaX = prePivot.x - lastPivot.x;
		deltaY = prePivot.y - lastPivot.y;

		lastPivot.x = prePivot.x;
		lastPivot.y = prePivot.y;

		setMatrix(OPER_TRANSLATE); // 设置矩阵

	}

	/**
	 * 缩放 0---1---2 | | 7 8 3 | | 6---5---4
	 * 
	 * @param evX
	 * @param evY
	 */
	private void scale(MotionEvent event) {

		int pointIndex = current_ctr * 2;

		float px = dstPs[pointIndex];
		float py = dstPs[pointIndex + 1];

		float evx = event.getX();
		float evy = event.getY();

		float oppositeX = 0;
		float oppositeY = 0;
		if (current_ctr < 4 && current_ctr >= 0) {
			oppositeX = dstPs[pointIndex + 8];
			oppositeY = dstPs[pointIndex + 9];
		} else if (current_ctr >= 4) {
			oppositeX = dstPs[pointIndex - 8];
			oppositeY = dstPs[pointIndex - 7];
		}
		float temp1 = getDistanceOfTwoPoints(px, py, oppositeX, oppositeY);
		float temp2 = getDistanceOfTwoPoints(evx, evy, oppositeX, oppositeY);

		this.scaleValue = temp2 / temp1;
		symmetricPoint.x = (int) oppositeX;
		symmetricPoint.y = (int) oppositeY;

		Log.i("img", "scaleValue is " + scaleValue);
		setMatrix(OPER_SCALE);
	}

	/**
	 * 旋转图片 0---1---2 | | 7 8 3 | | 6---5---4
	 * 
	 * @param evX
	 * @param evY
	 */
	private void rotate(MotionEvent event) {

		if (event.getPointerCount() == 2) {
			preDegree = computeDegree(new Point((int) event.getX(0),
					(int) event.getY(0)), new Point((int) event.getX(1),
					(int) event.getY(1)));
		} else {
			preDegree = computeDegree(new Point((int) event.getX(),
					(int) event.getY()), new Point((int) dstPs[16],
					(int) dstPs[17]));
		}
		setMatrix(OPER_ROTATE);
		lastDegree = preDegree;
	}

	/**
	 * 计算两点与垂直方向夹角
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	public float computeDegree(Point p1, Point p2) {
		float tran_x = p1.x - p2.x;
		float tran_y = p1.y - p2.y;
		float degree = 0.0f;
		float angle = (float) (Math.asin(tran_x
				/ Math.sqrt(tran_x * tran_x + tran_y * tran_y)) * 180 / Math.PI);
		if (!Float.isNaN(angle)) {
			if (tran_x >= 0 && tran_y <= 0) {// 第一象限
				degree = angle;
			} else if (tran_x <= 0 && tran_y <= 0) {// 第二象限
				degree = angle;
			} else if (tran_x <= 0 && tran_y >= 0) {// 第三象限
				degree = -180 - angle;
			} else if (tran_x >= 0 && tran_y >= 0) {// 第四象限
				degree = 180 - angle;
			}
		}
		return degree;
	}

	/**
	 * 计算两个点之间的距离
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	private float getDistanceOfTwoPoints(Point p1, Point p2) {
		return (float) (Math.sqrt((p1.x - p2.x) * (p1.x - p2.x)
				+ (p1.y - p2.y) * (p1.y - p2.y)));
	}

	private float getDistanceOfTwoPoints(float x1, float y1, float x2,
			float y2) {
		return (float) (Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2)
				* (y1 - y2)));
	}

	@Override
	public void onDraw(Canvas canvas) {
		drawBackground(canvas);// 绘制背景,以便测试矩形的映射
		canvas.drawBitmap(mainBmp, matrix, paint);// 绘制主图片
		drawFrame(canvas);// 绘制边框,以便测试点的映射
		drawControlPoints(canvas);// 绘制控制点图片
	}

	private void drawBackground(Canvas canvas) {
		canvas.drawRect(dstRect, paintRect);
	}

	private void drawFrame(Canvas canvas) {
		canvas.drawLine(dstPs[0], dstPs[1], dstPs[4], dstPs[5], paintFrame);
		canvas.drawLine(dstPs[4], dstPs[5], dstPs[8], dstPs[9], paintFrame);
		canvas.drawLine(dstPs[8], dstPs[9], dstPs[12], dstPs[13],
				paintFrame);
		canvas.drawLine(dstPs[0], dstPs[1], dstPs[12], dstPs[13],
				paintFrame);
		canvas.drawPoint(dstPs[16], dstPs[17], paintFrame);
	}

	private void drawControlPoints(Canvas canvas) {

		for (int i = 0; i < dstPs.length; i += 2) {
			canvas.drawBitmap(controlBmp, dstPs[i] - controlBmpWidth / 2,
					dstPs[i + 1] - controlBmpHeight / 2, paint);
		}

	}

}




//================================================================================================================================
	// FlipImgEffectView
	// ================================================================================================================================
	class FlipImgEffectView extends View {

		private Context context;
		private Bitmap showBmp;
		private Matrix matrix; // 作用矩阵
		private Camera camera;
		private int deltaX, deltaY; // 翻转角度差值
		private int centerX, centerY; // 图片中心点

		public FlipImgEffectView(Context context) {
			super(context);
			this.context = context;
			initData();
		}

		private void initData() {
			showBmp = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.water_lilies);
			// showBmp = BitmapFactory.decodeResource(context.getResources(),
			// R.drawable.flower);
			centerX = showBmp.getWidth() / 2;
			centerY = showBmp.getHeight() / 2;
			matrix = new Matrix();
			camera = new Camera();
		}

		int lastMouseX;
		int lastMouseY;

		@Override
		public boolean dispatchTouchEvent(MotionEvent event) {
			int x = (int) event.getX();
			int y = (int) event.getY();
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				lastMouseX = x;
				lastMouseY = y;
				break;
			case MotionEvent.ACTION_MOVE:
				int dx = x - lastMouseX;
				int dy = y - lastMouseY;
				deltaX += dx;
				deltaY += dy;
				break;
			}

			invalidate();
			return true;
		}

		@Override
		protected void onDraw(Canvas canvas) {

			camera.save();
			// 绕X轴翻转
			camera.rotateX(-deltaY);
			// 绕Y轴翻转
			camera.rotateY(deltaX);
			// 设置camera作用矩阵
			camera.getMatrix(matrix);
			camera.restore();
			// 设置翻转中心点
			matrix.preTranslate(-this.centerX, -this.centerY);
			matrix.postTranslate(this.centerX, this.centerY);

			canvas.drawBitmap(showBmp, matrix, null);
		}

	}
	/**
	 * 加载大图片工具类：解决android加载大图片时报OOM异常 解决原理：先设置缩放选项，再读取缩放的图片数据到内存，规避了内存引起的OOM
	 * 
	 * @author: 张进
	 * 
	 * @time:2011/7/28
	 */
	class BitmapUtil {

		public static final int UNCONSTRAINED = -1;

		/*
		 * 获得设置信息
		 */
		public static Options getOptions(String path) {
			Options options = new Options();
			options.inJustDecodeBounds = true;// 只描边，不读取数据
			BitmapFactory.decodeFile(path, options);
			return options;
		}

		/**
		 * 获得图像
		 * 
		 * @param path
		 * @param options
		 * @return
		 * @throws FileNotFoundException
		 */
		public static Bitmap getBitmapByPath(String path, Options options,
				int screenWidth, int screenHeight) throws FileNotFoundException {
			File file = new File(path);
			if (!file.exists()) {
				throw new FileNotFoundException();
			}
			FileInputStream in = null;
			in = new FileInputStream(file);
			if (options != null) {
				Rect r = getScreenRegion(screenWidth, screenHeight);
				int w = r.width();
				int h = r.height();
				int maxSize = w > h ? w : h;
				int inSimpleSize = computeSampleSize(options, maxSize, w * h);
				options.inSampleSize = inSimpleSize; // 设置缩放比例
				options.inJustDecodeBounds = false;
			}
			Bitmap b = BitmapFactory.decodeStream(in, null, options);
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return b;
		}

		private static Rect getScreenRegion(int width, int height) {
			return new Rect(0, 0, width, height);
		}

		/**
		 * 获取需要进行缩放的比例，即options.inSampleSize
		 * 
		 * @param options
		 * @param minSideLength
		 * @param maxNumOfPixels
		 * @return
		 */
		public static int computeSampleSize(BitmapFactory.Options options,
				int minSideLength, int maxNumOfPixels) {
			int initialSize = computeInitialSampleSize(options, minSideLength,
					maxNumOfPixels);

			int roundedSize;
			if (initialSize <= 8) {
				roundedSize = 1;
				while (roundedSize < initialSize) {
					roundedSize <<= 1;
				}
			} else {
				roundedSize = (initialSize + 7) / 8 * 8;
			}

			return roundedSize;
		}

		private static int computeInitialSampleSize(BitmapFactory.Options options,
				int minSideLength, int maxNumOfPixels) {
			double w = options.outWidth;
			double h = options.outHeight;

			int lowerBound = (maxNumOfPixels == UNCONSTRAINED) ? 1 : (int) Math
					.ceil(Math.sqrt(w * h / maxNumOfPixels));
			int upperBound = (minSideLength == UNCONSTRAINED) ? 128 : (int) Math
					.min(Math.floor(w / minSideLength),
							Math.floor(h / minSideLength));

			if (upperBound < lowerBound) {
				// return the larger one when there is no overlapping zone.
				return lowerBound;
			}

			if ((maxNumOfPixels == UNCONSTRAINED)
					&& (minSideLength == UNCONSTRAINED)) {
				return 1;
			} else if (minSideLength == UNCONSTRAINED) {
				return lowerBound;
			} else {
				return upperBound;
			}
		}

	}

