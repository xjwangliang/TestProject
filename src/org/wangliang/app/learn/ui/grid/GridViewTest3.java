package org.wangliang.app.learn.ui.grid ;

import java.util.ArrayList ;
import java.util.HashMap ;
import java.util.List ;
import java.util.Map ;
import java.util.Set ;

import android.app.Activity ;
import android.graphics.Color ;
import android.graphics.drawable.ColorDrawable ;
import android.os.Bundle ;
import android.view.Gravity ;
import android.view.View ;
import android.view.ViewGroup ;
import android.widget.AbsListView ;
import android.widget.AdapterView ;
import android.widget.AdapterView.OnItemClickListener ;
import android.widget.BaseAdapter ;
import android.widget.GridView ;
import android.widget.LinearLayout ;
import android.widget.TextView ;
import android.widget.Toast ;

public class GridViewTest3 extends Activity {
	private GridView gridview ;
	private HashMap<Integer, ArrayList<String>> map ;

	//	<?xml version="1.0" encoding="utf-8"?>
	//	<GridView xmlns:android="http://schemas.android.com/apk/res/android"
	//	    android:id="@+id/main_GridView"
	//	    android:horizontalSpacing="1dp"
	//	    android:verticalSpacing="1dp"
	//	    android:stretchMode="columnWidth"
	//	    android:numColumns="3"
	//	    android:gravity="center"
	//	    android:listSelector="@null"
	//	    android:background="#DCDCDC"
	//	    android:layout_width="fill_parent"
	//	    android:layout_height="fill_parent">
	//
	//	</GridView>

	//	
	//	package dyingbleed.iteye;
	//
	//	import android.app.Activity;
	//	import android.content.Context;
	//	import android.graphics.Color;
	//	import android.os.Bundle;
	//	import android.view.Gravity;
	//	import android.view.View;
	//	import android.view.ViewGroup;
	//	import android.view.ViewGroup.LayoutParams;
	//	import android.widget.AbsListView;
	//	import android.widget.BaseAdapter;
	//	import android.widget.GridView;
	//	import android.widget.TextView;
	//
	//	public class Main extends Activity {
	//		
	//		private GridView grid;
	//		
	//	    @Override
	//	    public void onCreate(Bundle savedInstanceState) {
	//	        super.onCreate(savedInstanceState);
	//	        setContentView(R.layout.main);
	//	        
	//	        grid = (GridView) findViewById(R.id.main_GridView);
	//	        grid.setAdapter(new GridViewAdapter(this));
	//	    }
	//	    
	//	    private class GridViewAdapter extends BaseAdapter {
	//	    	
	//	    	private Context context;
	//	    	
	//	    	public GridViewAdapter(Context context) {
	//	    		this.context = context;
	//	    	}
	//	    	
	//	    	int count = 100;
	//
	//			@Override
	//			public int getCount() {
	//				return count;
	//			}
	//
	//			@Override
	//			public Object getItem(int position) {
	//				return position;
	//			}
	//
	//			@Override
	//			public long getItemId(int position) {
	//				return position;
	//			}
	//
	//			@Override
	//			public View getView(int position, View convertView, ViewGroup parent) {
	//				TextView result = new TextView(context);
	//				result.setText("Item "+position);
	//				result.setTextColor(Color.BLACK);
	//				result.setTextSize(24);
	//				result.setLayoutParams(new AbsListView.LayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT)));
	//				result.setGravity(Gravity.CENTER);
	//				result.setBackgroundColor(Color.WHITE); //设置背景颜色
	//				return result;
	//			}
	//	    	
	//	    }
	//	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState) ;

		//setContentView(R.layout.gridview);

		LinearLayout linearLayout = new LinearLayout(this) ;
		LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT) ;
		linearLayout.setLayoutParams(p) ;

		gridview = new GridView(this) ;
		gridview.setLayoutParams(new AdapterView.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT)) ;
		gridview.setNumColumns(ITEM_EACH_ROW);
		gridview.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		gridview.setSelected(false);
		gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gridview.setScrollContainer(false);
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(myAdapter.getItemViewType(position) == M.TYPE_ITME){
					int section = positionMap.get(position).section;
					int p = positionMap.get(position).position - ITEM_EACH_ROW;
					String s = null ;
					s = map.get(section).get(p);
					Toast.makeText(GridViewTest3.this, "hello "+s, 0).show();
				}
				
			}
		});
		linearLayout.addView(gridview) ;
		setContentView(linearLayout) ;

		setupView() ;

	}

	private void setupView() {

		map = new HashMap<Integer, ArrayList<String>>() ;
		ArrayList<String> list01 = new ArrayList<String>() ;
		list01.add("01_a") ;
		list01.add("01_l") ;//12
		list01.add("01_l") ;//12
		list01.add("01_l") ;//12
		list01.add("01_l") ;//12
		list01.add("01_l") ;//12
		list01.add("01_l") ;//12
		list01.add("01_l") ;//12
		list01.add("01_l") ;//12
		map.put(0, list01) ;
		ArrayList<String> list02 = new ArrayList<String>() ;
		list02.add("02_a") ;
		list02.add("02_n") ;//14
		list02.add("02_n") ;//14
		list02.add("02_n") ;//14
		list02.add("02_n") ;//14
		list02.add("02_n") ;//14
		list02.add("02_n") ;//14
		list02.add("02_n") ;//14
		list02.add("02_n") ;//14
		list02.add("02_n") ;//14
		list02.add("02_n") ;//14
		list02.add("02_n") ;//14
		list02.add("02_n") ;//14
		list02.add("02_n") ;//14
		list02.add("02_n") ;//14
		list02.add("02_n") ;//14
		list02.add("02_n") ;//14
		list02.add("02_n") ;//14
		list02.add("02_n") ;//14
		list02.add("02_n") ;//14
		list02.add("02_n") ;//14
		list02.add("02_n") ;//14
		list02.add("02_n") ;//14
		map.put(1, list02) ;
		ArrayList<String> list03 = new ArrayList<String>() ;
		list03.add("03_a") ;
		list03.add("03_a") ;
		list03.add("03_a") ;
		list03.add("03_a") ;
		list03.add("03_a") ;
		list03.add("03_a") ;
		list03.add("03_a") ;
		list03.add("03_a") ;
		list03.add("03_a") ;
		list03.add("03_a") ;
		list03.add("03_a") ;
		list03.add("03_a") ;
		list03.add("03_a") ;
		list03.add("03_a") ;
		list03.add("03_a") ;
		list03.add("03_a") ;
		list03.add("03_a") ;
		list03.add("03_a") ;
		list03.add("03_a") ;
		list03.add("03_a") ;
		list03.add("03_a") ;
		list03.add("03_a") ;
		list03.add("03_a") ;
		map.put(2, list03) ;

		computeItemtCount() ;
		myAdapter = new MyAdapter() ;
		gridview.setAdapter(myAdapter) ;
		
		


		//
	}

	class MyItemListener implements OnItemClickListener {

		MyItemListener(int id, String tag) {

		}

		MyItemListener() {

		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			String key = (String) parent.getTag() ;
			Toast.makeText(getApplicationContext(), key + " " + position, 1)
					.show() ;

		}

	}

	class M {
		static final int TYPE_TAG = 0 ;
		static final int TYPE_ITME = 1 ;
		static final int TYPE_FILL = 2 ;

		public M(int section, int position, int type) {
			this.section = section ;
			this.position = position ;
			this.type = type ;
		}

		int position ;
		int section ;
		int type ;
	}

	int ITEM_EACH_ROW = 3 ;
	int LIST_COUNT = 0 ;
	Map<Integer, M> positionMap = new HashMap<Integer, M>() ;//
	//Map<Integer, String> tagMap = new HashMap<Integer, String>() ;
	private MyAdapter myAdapter ;

	private void computeItemtCount() {
		Set<Integer> keySet = map.keySet() ;
		int i = 0, j = 0;//section = 0 ;
		for (Integer key : keySet) {
			int dataSize = ((List<String>) map.get(key)).size() ;
			if (dataSize > 0) {
				j = i ;
				i += (dataSize + ITEM_EACH_ROW) ;
				int x = 0;
				for (x = j; x < j + ITEM_EACH_ROW; x++) {
					positionMap.put(x, new M(key, x-j, M.TYPE_TAG)) ;
				}
				for (x = j + ITEM_EACH_ROW ; x < i; x++) {
					positionMap.put(x, new M(key, x-j, M.TYPE_ITME)) ;
				}
				
				int r = dataSize - (dataSize / ITEM_EACH_ROW) * ITEM_EACH_ROW ;
				if (r != 0) {
					for (x = i; x < i+(ITEM_EACH_ROW - r) ; x++) {
						positionMap.put(x, new M(key, x-j, M.TYPE_FILL)) ;
					}
					i += (ITEM_EACH_ROW - r) ;
				}
				//section++ ;
			}

		}

		LIST_COUNT = i ;

		System.out.println("positionMap " + positionMap) ;
		//System.out.println("tagMap " + tagMap) ;

	}
	
	private String getSectionString(Integer key){
		switch (key) {
			case 0:
				return ">> section 1";

			case 1:
				return ">> section 2";
			case 2:
				return ">> section 3";
			default:
					throw new RuntimeException();
		}
	}

	class MyAdapter extends BaseAdapter {
//		static final int TYPE_SEPARATOR = 0 ;
//		static final int TYPE_ITEM = 1 ;
//		static final int TYPE_FILL = 2 ;

		MyAdapter() {

		}

		@Override
		public int getCount() {
			return LIST_COUNT ;
		}

		@Override
		public Object getItem(int position) {
			return null ;
		}

		@Override
		public long getItemId(int position) {
			return position ;
		}

		@Override
		public int getViewTypeCount() {
			return 3 ;
		}

		@Override
		public int getItemViewType(int position) {
			return positionMap.get(position).type ;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			int type = getItemViewType(position) ;
			switch (type) {
				case M.TYPE_TAG:
					if (convertView == null) {
						TextView tv = new TextView(GridViewTest3.this) ;
						tv.setGravity(Gravity.CENTER_VERTICAL |Gravity.RIGHT);
						tv.setBackgroundColor(Color.rgb(0x66, 0x66, 0x66));
						convertView = tv ;
						//tv.setClickable(false);
					}
					if(positionMap.get(position).position == 0){
						((TextView) convertView).setText(">> "+positionMap.get(position).section+" ") ;
					}else {
						((TextView) convertView).setText("") ;
					}
					break ;

				case M.TYPE_ITME:
					if (convertView == null) {
						convertView = newView() ;
					}
					bindView(convertView, position) ;
					break ;
				case M.TYPE_FILL:
					if (convertView == null) {
						convertView = newView() ;
					}
					break;
			}
			return convertView ;
		}

		private View newView() {
			TextView convertView = new TextView(GridViewTest3.this) ;
			convertView.setGravity(Gravity.CENTER) ;
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
					LinearLayout.LayoutParams.FILL_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT, 1) ;
			
			convertView.setLayoutParams(lp) ;
			convertView.setPadding(20, 30, 20, 30);
			convertView.setBackgroundColor(Color.argb(0x8d, 0x98, 0x98, 0x98));
			return convertView ;
		}

		private void bindView(View view, int position) {
			TextView tv = (TextView) view ;
			int section = positionMap.get(position).section;
			int p = positionMap.get(position).position - ITEM_EACH_ROW;
			System.out.println("position "+position +" section "+section+" p "+p) ;
			String s = null ;
			s = map.get(section).get(p);
			tv.setText(s );

		}

	}

	//	public class MyGridView2 extends ExpandableListView {   
	//	  
	//	    public MyGridView2(Context context, AttributeSet attrs) {   
	//	        super(context, attrs);   
	//	    }   
	//	  
	//	    public MyGridView2(Context context) {   
	//	        super(context);   
	//	    }   
	//	  
	//	    public MyGridView2(Context context, AttributeSet attrs, int defStyle) {   
	//	        super(context, attrs, defStyle);   
	//	    }   
	//	    @Override   
	//	    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {   
	//	        int expandSpec = MeasureSpec.makeMeasureSpec(   
	//	                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);   
	//	        super.onMeasure(widthMeasureSpec, expandSpec);   
	//	    }   
	//	} 

}
