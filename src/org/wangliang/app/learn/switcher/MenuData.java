package org.wangliang.app.learn.switcher;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

public class MenuData { 
    /**该常量代表每一屏能够容纳的应用程序数目*/ 
    public static final int NUMBER_IN_ONE_SCREEN = 9; 
     
    /**该类代表每个应用程序的数据部分*/ 
    public static class DataItem { 
        public String dataName;   //应用程序名称 
        public Drawable drawable;  //应用程序图标 
    } 
     
    /**该类代表了一个屏的所有应用程序*/ 
    public static class MenuDataOneScreen {  
        ArrayList<DataItem> mDataItems = new ArrayList<DataItem>(); 
    } 
     
    /**该数据时该类的主要部分，所有屏的列表，实际上该类就是代表了所有的屏*/ 
    ArrayList<MenuDataOneScreen> mScreens = new ArrayList<MenuDataOneScreen>(); 
     
    /**对该类进行赋予数据*/ 
    public void setMenuItems(ArrayList<DataItem> dataItems) { 
        int screenNum = dataItems.size() / NUMBER_IN_ONE_SCREEN; 
        int remain = dataItems.size() % NUMBER_IN_ONE_SCREEN; 
        screenNum += remain == 0 ? 0 : 1; 
         
        int pos = 0; 
        for (int i = 0; i < screenNum; i++) { 
            MenuDataOneScreen screen = new MenuDataOneScreen(); 
            for (int j = 0; j < NUMBER_IN_ONE_SCREEN; j++) { 
                if (pos <= dataItems.size() - 1) { 
                    screen.mDataItems.add(dataItems.get(pos)); 
                    pos++; 
                } 
            } 
            mScreens.add(screen); 
        } 
    } 
     
    /**获取屏的数目*/ 
    public int getScreenNumber() { 
        return mScreens.size(); 
    } 
     
    /**根据屏的索引，获取某个屏的数据*/ 
    public MenuDataOneScreen getScreen(int screenIndex) { 
        return mScreens.get(screenIndex); 
    } 
} 