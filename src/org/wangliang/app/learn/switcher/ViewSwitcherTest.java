package org.wangliang.app.learn.switcher;

import java.util.ArrayList;

import org.wangliang.app.learn.R;
import org.wangliang.app.learn.switcher.MenuData.DataItem;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ViewSwitcherTest extends Activity {
	 SlideMenuSwitcher switcher; 
	    @Override 
	    public void onCreate(Bundle savedInstanceState) { 
	        super.onCreate(savedInstanceState); 
	        setContentView(R.layout.viewswitcher_main); 
	        switcher = (SlideMenuSwitcher) findViewById(R.id.slide_view); 
	        switcher.setData(makeItems());    //将24个应用程序赋值到swithcer中。 
	         
	        findViewById(R.id.button_next).setOnClickListener(new OnClickListener() { 
	             
	            public void onClick(View v) { 
	                switcher.showNextScreen();  //点击右边按钮，显示下一屏，当然可以采用手势 
	            } 
	        }); 
	         
	        findViewById(R.id.button_prev).setOnClickListener(new OnClickListener() { 
	             
	            public void onClick(View v) { 
	                switcher.showPreviousScreen();  //点击左边按钮，显示上一屏，当然可以采用手势 
	 
	            } 
	        }); 
	    } 
	     
	    /**模拟24个应用程序*/ 
	    private ArrayList<DataItem> makeItems() { 
	        ArrayList<DataItem> items = new ArrayList<DataItem>(); 
	        for (int i = 0; i < 24; i++) { 
	            String label = "" + i; 
	            Drawable drawable = getResources().getDrawable(R.drawable.ic_launcher); 
	            DataItem item = new DataItem(); 
	            item.dataName = label; 
	            item.drawable = drawable; 
	            items.add(item); 
	        } 
	         
	        return items; 
	    } 
}