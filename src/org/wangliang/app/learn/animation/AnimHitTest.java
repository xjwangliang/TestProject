
package org.wangliang.app.learn.animation;

import android.app.Activity ;
import android.content.Context ;
import android.graphics.Color ;
import android.graphics.Matrix ;
import android.graphics.Rect ;
import android.os.Bundle ;
import android.util.AttributeSet ;
import android.util.Log ;
import android.view.Gravity ;
import android.view.MotionEvent ;
import android.view.View ;
import android.view.animation.AlphaAnimation ;
import android.view.animation.Animation ;
import android.view.animation.AnimationSet ;
import android.view.animation.RotateAnimation ;
import android.view.animation.ScaleAnimation ;
import android.view.animation.Transformation ;
import android.view.animation.TranslateAnimation ;
import android.widget.ImageView ;
import android.widget.RelativeLayout ;

//http://blog.csdn.net/seker_xinjian/article/details/7236945
public class AnimHitTest extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ParentView(this));
    }
}

class ParentView extends RelativeLayout {
    public ParentView(Context context) {
        super(context);
        init(context);
    }
    
    public ParentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    
    public ParentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }
    
    private void init(Context context) {
        final int duration = 5 * 1000;
        AnimationSet anim_set = new AnimationSet(true);
        
        ScaleAnimation anim_scale = new ScaleAnimation(1.0f, 3f, 1.0f, 5f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        anim_scale.setDuration(duration);
        anim_scale.setRepeatMode(Animation.REVERSE);
        anim_scale.setRepeatCount(Animation.INFINITE);
        anim_set.addAnimation(anim_scale);
        
        TranslateAnimation anim_translate = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.5f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.5f);
        anim_translate.setDuration(duration);
        anim_translate.setRepeatMode(Animation.REVERSE);
        anim_translate.setRepeatCount(Animation.INFINITE);
        anim_set.addAnimation(anim_translate);
        
        AlphaAnimation anim_alpha = new AlphaAnimation(1.0f, 0.5f);
        anim_alpha.setDuration(duration);
        anim_alpha.setRepeatMode(Animation.REVERSE);
        anim_alpha.setRepeatCount(Animation.INFINITE);
        anim_set.addAnimation(anim_alpha);
        
        RotateAnimation anim_rotate = new RotateAnimation(0.0f, 90.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        anim_rotate.setDuration(duration);
        anim_rotate.setRepeatMode(Animation.REVERSE);
        anim_rotate.setRepeatCount(Animation.INFINITE);
        anim_set.addAnimation(anim_rotate);
        
        ImageView imageview = new ImageView(context);
        imageview.setBackgroundColor(Color.RED);
        addView(imageview, new LayoutParams(50, 50));
        imageview.setAnimation(anim_set);
        
        setGravity(Gravity.CENTER);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                if (containPoint(getChildAt(0), x, y)) {
                    Log.d("seker", "Hit suceess.");
                    return true;
                } else {
                    Log.d("seker", "Hit faild.");
                    return false;
                }
        }
        return super.onTouchEvent(event);
    }
    
    private boolean containPoint(View view, float x, float y) {
        Transformation trans = new Transformation();
        Animation anim = view.getAnimation();
        anim.getTransformation(view.getDrawingTime(), trans);
        Matrix matrix = trans.getMatrix();
        
        int dx = view.getLeft();
        int dy = view.getTop();
        
        x -= dx;
        y -= dy;
        
        // Invert Matrix
        Matrix mat = new Matrix();
        if (matrix.invert(mat)) {
            float[] pointsSrc = new float[] { x, y };
            float[] pointsEnd = new float[] { 0, 0 };
            
            // Get the point in inverted matrix.
            mat.mapPoints(pointsEnd, pointsSrc);
            
            // Offset the point because we translate matrix which dx and dy before.
            x = pointsEnd[0] + dx;
            y = pointsEnd[1] + dy;
        }
        
        Rect rect = new Rect();
        view.getHitRect(rect);
        return rect.contains((int) x, (int) y);
    }
}

/**
动画中的View的点击判断

在开发Android应用过程中，我曾遇到过下面的问题：

   假设有一个View，它在做一系列复杂的、组合的Tween动画（平移动画、旋转动画、缩放动画、Alpha动画）。在动画的过程中，用户会去点击这个View。如何去判断这个View被点击中了没有呢？

   为此，我曾专门在CSDN上发布了一条悬赏100分的技术贴：
       http://topic.csdn.net/u/20111125/14/79debf30-c6ea-4945-ab1b-456e17259a2c.html。
   以求得其解。然而，终究没有得到相应的答案。最终，得益于从前的两位同事，找到了解决方案，特书此文，以供路人指教。

一、动画的原理    
   很多人看到我的帖子的时候，不懂我在说些什么，不知道问题点在哪里。他们可能觉得点击事件只要注册了“事件监听器”不久OK了么？

   事实上不是这样子的。正如我在上述的技术贴中提到的：

   View做在做动画的时候，它并没有真正的移动它的位置。而是根据动画时间的插值，计算出一个Matrix，然后不停的invalidate，在onDraw中的Canvas上使用这个计算出来的Matrix去draw这个View的内容。
   换句话说，View在做动画的时候，它的位置根本没有变化，只是画它的时候进行了Matrix处理，使得它看起来变化了。那么，动画中的View点击事件的判断区域，应该是它“看起来”的那片区域，而不是它layout的那片区域。
   
   我相信很多人还是不明白。所以特地找到了一个大牛人写的另外一个博文《Android 动画框架详解》，供大家搞明白Android中的动画原理。明白了Android补间动画的原理之后，然后再读下去。
   http://www.ibm.com/developerworks/cn/opensource/os-cn-android-anmt1/index.html

二、问题重述
   比方说：一个矩形的View，它的的layout区域是（l，t，r，b），自然它的点击事件的判断区域也就是（l，t，r，b）
   
   当它做一个动画（平移动画、旋转动画、缩放动画）时，它的的layout区域依然是（l，t，r，b），但是它的显示区域却可是另外一片区域，比如是下图（红色区域）：


   
    这时候如果还是以（l，t，r，b）区域来点击事件，自然就不可能正确了。

三、问题分析
   《Android 动画框架详解》所讲的最核心的一点就是：Android 动画就是通过 ParentView 来不断调整 ChildView 的画布坐标系来实现的。
   严格来讲，上述的“Android 动画”应该限为：补间动画的1）、平移动画，2）、旋转动画，3）、缩放动画。
   
   动画的产生过程涉及到两个重要的类型，Animation 和 Transformation，这两个类是实现动画的主要的类。

   Animation 中主要定义了动画的一些属性比如开始时间、持续时间、是否重复播放等。这个类主要有两个重要的函数：getTransformation 和 applyTransformation，在 getTransformation 中 Animation 会根据动画的属性来产生一系列的差值点，然后将这些差值点传给 applyTransformation，这个函数将根据这些点来生成不同的 Transformation。

   Transformation 中包含一个矩阵和 alpha 值，矩阵是用来做平移、旋转和缩放动画的，而 alpha 值是用来做 alpha 动画的（简单理解的话，alpha 动画相当于不断变换透明度或颜色来实现动画）。这正好对应着Transformation.TYPE_ALPHA和Transformation.TYPE_MATRIX这两种类型。

四、解决方案
   到此，可以看到如果一个View如果在做补间动画中的平移、旋转、缩放动画，那么它的点击事件一定要进行的矩阵处理。
   
   具体做法就是：
       1、在ParentView中重写onTouchEvent(MotionEvent event)，拦截点击点击事件的x、y坐标。注意（x，y）是相对于ParentView坐标系的。
       2、根据（x，y）坐标算得“点击”点相对于View坐标系的坐标点（x - view.getLeft()， y - view.getTop()）。
       3、获得View的动画的时间，从而获得Transformation，进而获得Matrix。然后求的Matrix的逆矩阵Matrix'。
       4、使用Matrix'将坐标点（x - view.getLeft()， y - view.getTop()）求对应的映射坐标（x'，y'）。
       5、（x'，y'）再还原成ParentView坐标系中的点（x' + view.getLeft()， y' + view.getTop()）。
       6、使用View.getHitRect(rect)，获得“点击判断矩形”，再Rect.contains(int x, int y)判断改点是否在View的区域范围内。

五、代码
   为了解决这个问题，我曾写过一段测试代码，也上传到CSDN上来了。因为是自己原创，为了告慰为此而阵亡的脑细胞，因而该资源不是免费的。
   下载地址：http://download.csdn.net/detail/seker_xinjian/4047390

分享到： 
上一篇：纪念为“第九维”而死的脑细胞
下一篇：实现MapFragment（一）
顶
0
踩
0

查看评论
2楼 leehong2005 2012-03-02 10:33发表 [回复]

楼主好，我看了你的文章，写得不错。
但我有一个问题：如果作动画的View是一个复合View，它里面的child如果还需要响应touch事件，怎么办？
我看了你上面列出的6个步骤，感觉像上述的问题，实现不了。
所以，我认为，本质上是要重写viewgroup的事件分发，也就是dirpatchTouchEvent。
Re: seker_xinjian 2012-03-02 13:17发表 [回复]

回复leehong2005：你说的很对，我写的demo，只解决View本身的事件响应。

复合View做动画的时候，如果它的孩子View想要响应点击事件，本质上来讲，复合View应该需要重写dirpatchTouchEvent()，将Touch点的坐标做相应的矩阵运算后，再dispatch给它的孩子View。

有兴趣的可以看看我参与开发的游戏《在哪之第九维》 Version1.1.0及以后版本中的“囧吧”功能，用到了复合View的用例。那个地方虽然没有使用Animation，但是直接使用了Matrix运算。
Re: leehong2005 2012-03-03 16:12发表 [回复]

回复seker_xinjian：关于这个问题，其实还是一种解决方法：
就是重写顶层viewgroup的dispatchTouchEvent，当然这里面可能要用到反射来方法一些私有变量。
在分发事件时判断是到在child的bound时，去把touch的点作matrix的逆运算，从而得到一个新的点。再用这个新的点去判断是否在child的bound内。
Re: seker_xinjian 2012-03-03 21:24发表 [回复]

回复leehong2005：高见
1楼 gundumw100 2012-02-16 23:09发表 [回复]

该问题我也困扰了很久，我当初需要的效果是：当动画移动一个button到另一个地方后，发现button表面上是移动了，但实际的点击位置还在原处，我就觉得这是google动画的问题造成的。看了你的这篇文章，那我明白了。
Re: seker_xinjian 2012-02-18 12:59发表 [回复]

回复gundumw100： 呵呵，这帖子总算起到了点作用了。
**/