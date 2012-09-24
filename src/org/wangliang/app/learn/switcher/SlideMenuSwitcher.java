package org.wangliang.app.learn.switcher;

import java.util.ArrayList;

import org.wangliang.app.learn.R;
import org.wangliang.app.learn.switcher.MenuData.DataItem;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.ViewSwitcher;

public class SlideMenuSwitcher extends ViewSwitcher{ 
    private MenuData mMenuData; 
    private int mCurrentScreen; 
    private Context mContext; 
     
    public SlideMenuSwitcher(Context context, AttributeSet attrs) { 
        super(context, attrs); 
        setFactory(new SlideViewFactory(context)); 
//      setAnimateFirstView(false); 
        mContext = context; 
    } 
     
    /**通过该方法将数据赋值进去，并且将初始的屏显示出来*/ 
    public void setData(ArrayList<DataItem> dataItems) { 
        mMenuData = new MenuData(); 
        mMenuData.setMenuItems(dataItems); 
        mCurrentScreen = mMenuData.getScreenNumber() / 2; 
         
        GridView listView = (GridView) getCurrentView(); 
        OneScreenListAdapter adapter = new OneScreenListAdapter(mContext); 
        adapter.setScreenData(mMenuData.getScreen(mCurrentScreen)); 
        listView.setAdapter(adapter); 
    } 
     
    /**该方法用于显示下一屏*/ 
    public void showNextScreen() { 
        if (mCurrentScreen < mMenuData.getScreenNumber() - 1) { 
            mCurrentScreen++; 
            setInAnimation(mContext, R.anim.slide_right_in); 
            setOutAnimation(mContext, R.anim.slide_left_out); 
        } else { 
            return; 
        } 
         
        setViewData(mCurrentScreen); 
        showNext(); 
    } 
     
    /**该方法用于显示上一屏*/ 
    public void showPreviousScreen() { 
        if (mCurrentScreen > 0) { 
            mCurrentScreen--; 
            setInAnimation(mContext, R.anim.slide_left_in); 
            setOutAnimation(mContext, R.anim.slide_right_out); 
        } else { 
            return; 
        } 
         
        setViewData(mCurrentScreen); 
        showPrevious(); 
    } 
     
    private void setViewData(int index) { 
        GridView listView = (GridView) getNextView(); 
        OneScreenListAdapter adapter = new OneScreenListAdapter(mContext); 
        adapter.setScreenData(mMenuData.getScreen(index)); 
        listView.setAdapter(adapter); 
        
    } 
} 