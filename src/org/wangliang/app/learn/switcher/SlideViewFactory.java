package org.wangliang.app.learn.switcher;
import org.wangliang.app.learn.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ViewSwitcher.ViewFactory;

public class SlideViewFactory implements ViewFactory{ 
    LayoutInflater mInflater; 
    Context context ;
    public SlideViewFactory(Context context) { 
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
        this.context = context ;
    } 
     
    /**这个函数就是得到我们要生成的View，这里实际上直接从布局得到， 
    *我们定义的是一个GridView ，一个GridView用于显示一屏的应用程序*/  
    public View makeView() { 
    	
    	GridView gridView = new GridView(context);
    	gridView.setNumColumns(4);
    	gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
    	return gridView ;
        //return mInflater.inflate(R.layout.slidelistview, null); 
    } 
} 