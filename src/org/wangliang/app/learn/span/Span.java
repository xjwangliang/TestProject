package org.wangliang.app.learn.span;

import java.util.ArrayList;

import org.wangliang.app.learn.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
//http://orgcent.com/android-imagespan-view-html/
public class Span extends Activity{
	
	private ViewGroup content;
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.span);
		content = (ViewGroup) findViewById(R.id.content);
	};
	
	// <2
	public void way1(View view) {
		SpannableString spanStr = new SpannableString("掌声那历史的房间里是副经理撒旦法阿斯顿及福利费是到发顺丰");
		ImageSpan imageSpan = new ImageSpan(this, R.drawable.ic_launcher);
		spanStr.setSpan(imageSpan, 3, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		TextView tv = new TextView(this);
		tv.setText(spanStr);
		if(content.getChildCount() > 0){
			content.removeAllViews();
		}
		content.addView(tv);
	}
	
	public void way2(View view) {
		FloatImageText v = new FloatImageText(this);
		v.setText("电视里发生了房间里是积分拉萨积分拉萨积分拉萨减肥啦空间  撒旦法发大水发撒旦法看完了鸡肉味容积率为热键礼物i经二路文件容量为积分拉萨解放路口上飞机撒离开房间爱水立方法拉圣诞节福禄寿");
		Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		v.setImageBitmap(bm, 30, 30);
		if(content.getChildCount() > 0){
			content.removeAllViews();
		}
		content.addView(v);
	}
	
	
	 class FloatImageText extends View {
	    private Bitmap mBitmap;
	    private final Rect bitmapFrame = new Rect();
	    private final Rect tmp = new Rect();
	    private int mTargetDentity = DisplayMetrics.DENSITY_DEFAULT;
	    
	    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	    private String mText;
	    private ArrayList<TextLine> mTextLines;
	    private final int[] textSize = new int[2];

	    public FloatImageText(Context context, AttributeSet attrs, int defStyle) {
	        super(context, attrs, defStyle);
	        init();
	    }

	    public FloatImageText(Context context, AttributeSet attrs) {
	        super(context, attrs);
	        init();
	    }

	    public FloatImageText(Context context) {
	        super(context);
	        init();
	    }
	    
	    private void init() {
	        mTargetDentity = getResources().getDisplayMetrics().densityDpi;
	        mTextLines = new ArrayList<TextLine>();
	        
	        mPaint.setTextSize(14);
	        mPaint.setColor(Color.RED);
	        
	    }
	    
	    

	    @Override
	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	        int w = 0, h = 0;
	        //图片大小
	        w += bitmapFrame.width();
	        h += bitmapFrame.height();
	        
	        //文本宽度
	        if(null != mText && mText.length() > 0) {
	            mTextLines.clear();
	            int size = resolveSize(Integer.MAX_VALUE, widthMeasureSpec);
	            measureAndSplitText(mPaint, mText, size);
	            final int textWidth = textSize[0], textHeight = textSize[1];
	            w += textWidth; //内容宽度
	            if(h < textHeight) { //内容高度
	                h = (int) textHeight;
	            }
	        }
	        
	        w = Math.max(w, getSuggestedMinimumWidth());
	        h = Math.max(h, getSuggestedMinimumHeight());
	        
	        setMeasuredDimension(
	                resolveSize(w, widthMeasureSpec), 
	                resolveSize(h, heightMeasureSpec));
	    }
	    
	    @Override
	    protected void onDraw(Canvas canvas) {
	        //绘制图片
	        if(null != mBitmap) {
	            canvas.drawBitmap(mBitmap, null, bitmapFrame, null);
	        }
	        
	        //绘制文本
	        TextLine line;
	        final int size = mTextLines.size();
	        for(int i = 0; i < size; i++) {
	            line = mTextLines.get(i);
	            canvas.drawText(line.text, line.x, line.y, mPaint);
	        }
	        System.out.println(mTextLines);
	    }
	    
	    
	    public void setImageBitmap(Bitmap bm) {
	        setImageBitmap(bm, null);
	    }
	    
	    public void setImageBitmap(Bitmap bm, int left, int top) {
	        setImageBitmap(bm, new Rect(left, top, 0, 0));
	    }
	    
	    public void setImageBitmap(Bitmap bm, Rect bitmapFrame) {
	        mBitmap = bm;
	        computeBitmapSize(bitmapFrame);
	        requestLayout();
	        invalidate();
	    }
	    
	    public void setText(String text) {
	        mText = text;
	        requestLayout();
	        invalidate();
	    }
	    
	    private void computeBitmapSize(Rect rect) {
	        if(null != rect) {
	            bitmapFrame.set(rect);
	        }
	        if(null != mBitmap) {
	            if(rect.right == 0 && rect.bottom == 0) {
	                final Rect r = bitmapFrame;
	                r.set(r.left, r.top, 
	                        r.left + mBitmap.getScaledHeight(mTargetDentity), 
	                        r.top + mBitmap.getScaledHeight(mTargetDentity));
	            }
	        } else {
	            bitmapFrame.setEmpty();
	        }
	    }
	    
	    private void measureAndSplitText(Paint p, String content, int maxWidth) {
	        FontMetrics fm = mPaint.getFontMetrics();
	        final int lineHeight = (int) (fm.bottom - fm.top);
	        
	        final Rect r = new Rect(bitmapFrame);
//	        r.inset(-5, -5);
	        
	        final int length = content.length();
	        int start = 0, end = 0, offsetX = 0, offsetY = 0;
	        int availWidth = maxWidth;
	        TextLine line;
	        boolean onFirst = true;
	        boolean newLine = true;
	        while(start < length) {
	            end++;
	            if(end == length) { //剩余的不足一行的文本
	                if(start <= length - 1) {
	                    if(newLine) offsetY += lineHeight;
	                    line = new TextLine();
	                    line.text = content.substring(start, end - 1);
	                    line.x = offsetX;
	                    line.y = offsetY;
	                    mTextLines.add(line);
	                }
	                break;
	            }
	            p.getTextBounds(content, start, end, tmp);
	            if(onFirst) { //确定每个字符串的坐标
	                onFirst = false;
	                final int height = lineHeight + offsetY;
	                if(r.top >= height) { //顶部可以放下一行文字
	                    offsetX = 0;
	                    availWidth = maxWidth;
	                    newLine = true;
	                } else if(newLine && (r.bottom >= height && r.left >= tmp.width())) { //中部左边可以放文字
	                    offsetX = 0;
	                    availWidth = r.left;
	                    newLine = false;
	                } else if(r.bottom >= height && maxWidth - r.right >= tmp.width()) { //中部右边
	                    offsetX = r.right;
	                    availWidth = maxWidth - r.right;
	                    newLine = true;
	                } else { //底部
	                    offsetX = 0;
	                    availWidth = maxWidth;
	                    if(offsetY < r.bottom) offsetY = r.bottom;
	                    newLine = true;
	                }
	            }
	            
	            if(tmp.width() > availWidth) { //保存一行能放置的最大字符串
	                onFirst = true;
	                line = new TextLine();
	                line.text = content.substring(start, end - 1);
	                line.x = offsetX;
	                mTextLines.add(line);
	                if(newLine) {
	                    offsetY += lineHeight;
	                    line.y = offsetY;
	                } else {
	                    line.y = offsetY + lineHeight;
	                }
	                
	                start = end - 1;
	            }
	        }
	        textSize[1] = offsetY;
	    }
	    
	    class TextLine {
	        String text;
	        int x;
	        int y;
	        
	        @Override
	        public String toString() {
	            return "TextLine [text=" + text + ", x=" + x + ", y=" + y + "]";
	        }
	    }
	}
}
