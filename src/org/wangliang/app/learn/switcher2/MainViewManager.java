
package org.wangliang.app.learn.switcher2;

import org.wangliang.app.learn.R;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainViewManager {
    private static final int FLING_MIN_DISTANCE = 120;

    private static final int FLING_MIN_VELOCITY = 200;
    
    private final static int DISTENCE_X = 240, DISTENCE_Y = 0;

    private final static int ROTATE_ANIMATION_DURATION = 300;
    
    private final static int DEFAULT_FLING_ANGLE = 30;
    
    public final static Class<?>[] sActivityClasses = new Class[]{
            Activity1.class, Activity2.class, Activity3.class, Activity4.class, Activity5.class
    };

    public final static int[] sResIds = new int[]{
            R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5
    };

    public final static String[] sActivityIds = new String[]{
            "Activity1", "Activity2", "Activity3", "Activity4", "Activity5"
    };

    private int mPreBtnPos = 0, mCurBtnPos = 0;

    private RelativeLayout mViewContainer;

    private View mPreView;

    private View[] mCurView = new View[sResIds.length];

    private Button[] mBtns = new Button[sResIds.length];

    private static MainViewManager mInstance = new MainViewManager();

    private MainViewManager() {
    }

    public static MainViewManager getInstance() {
        return mInstance;
    }
   
    public int getCurBtnId(int index) {
        return sResIds[index];
    }
    
    public void setCurBtnPos(int rid) {
        mCurBtnPos = getCurBtnIndex(rid);
    }
    
    public View getCurView(int index) {
        return mCurView[index];
    }
    
    public void setupViews(Context context) {
        mViewContainer = (RelativeLayout) ((Activity) context).findViewById(R.id.container);
        final Button[] btns = mBtns;
        for (int i = 0; i < btns.length; i++) {
            btns[i] = (Button) ((Activity) context).findViewById(sResIds[i]);
            btns[i].setOnClickListener((OnClickListener) context);
        }

        // ��һ������ʱ��Ĭ����ת����һ��activity
        mCurView[0] = ((ActivityGroup) context).getLocalActivityManager().startActivity(
                sActivityIds[0],
                new Intent(context, sActivityClasses[0]).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                .getDecorView();
        mViewContainer.addView(mCurView[0]);
        mPreView = mCurView[0];
        mPreBtnPos = 0;
    }

    public int getCurBtnIndex(int rid) {
        final int length = sResIds.length;
        for (int i = 0; i < length; i++) {
            if (rid == sResIds[i]) {
                return i;
            }
        }
        return 0;
    }
    
    public int getCurBtnResid(){
        return sResIds[mCurBtnPos];
    }

    public boolean getMotionState(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
        return e1.getX() - e2.getX() > FLING_MIN_DISTANCE
        && Math.abs(velocityX) > FLING_MIN_VELOCITY
        && Math.abs(Math.toDegrees(Math.atan((e1.getY() - e2.getY())
                / (e1.getX() - e2.getX())))) < DEFAULT_FLING_ANGLE;
    }
    
    public void processViews(Context context) {
        mViewContainer.removeAllViews();
        final Intent intent = new Intent(context, sActivityClasses[mCurBtnPos]);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mCurView[mCurBtnPos] = ((ActivityGroup) context).getLocalActivityManager().startActivity(
                sActivityIds[mCurBtnPos], intent).getDecorView();
    }

    public void onRotateAnimation(int index) {
        if (mPreBtnPos > mCurBtnPos) {
            Rotate3d.rightRotate(mPreView, mCurView[index], DISTENCE_X, DISTENCE_Y,
                    ROTATE_ANIMATION_DURATION, new AnimListener());
        } else {
            Rotate3d.leftRotate(mPreView, mCurView[index], DISTENCE_X, DISTENCE_Y,
                    ROTATE_ANIMATION_DURATION, new AnimListener());
        }

        mPreView = mCurView[index];
        mViewContainer.removeAllViews();
        mViewContainer.addView(mCurView[index]);
        mPreBtnPos = mCurBtnPos;
    }

    private final static class AnimListener implements Animation.AnimationListener {

        public void onAnimationEnd(Animation animation) {
        }

        public void onAnimationRepeat(Animation animation) {

        }

        public void onAnimationStart(Animation animation) {
        }
    }
}
