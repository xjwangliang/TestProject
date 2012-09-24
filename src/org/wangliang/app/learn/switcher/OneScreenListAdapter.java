package org.wangliang.app.learn.switcher;
import org.wangliang.app.learn.R;
import org.wangliang.app.learn.switcher.MenuData.MenuDataOneScreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OneScreenListAdapter extends BaseAdapter{ 
    private MenuDataOneScreen mScreen; 
    private LayoutInflater mInflater; 
     
    public OneScreenListAdapter(Context context) { 
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
    } 
     
    /**这里将数据赋予Adapter*/ 
    public void setScreenData(MenuDataOneScreen screenData) { 
        mScreen = screenData; 
    } 
 
    public int getCount() { 
        return mScreen.mDataItems.size(); 
    } 
 
    public Object getItem(int position) { 
        return mScreen.mDataItems.get(position); 
    } 
 
    public long getItemId(int position) { 
        return position; 
    } 
 
    /**该函数中将数据和View进行关联*/ 
    public View getView(int position, View convertView, ViewGroup parent) { 
        View view = convertView; 
        if (convertView == null) { 
            view = mInflater.inflate(R.layout.viewswitcher_item, null); 
        }  
     
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview); 
        TextView textView = (TextView) view.findViewById(R.id.textview); 
        imageView.setImageDrawable(mScreen.mDataItems.get(position).drawable); 
        textView.setText(mScreen.mDataItems.get(position).dataName); 
         
        return view; 
    } 
 
} 