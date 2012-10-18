package org.wangliang.app.learn.ui.grid;

import java.util.ArrayList ;
import java.util.HashMap ;
import java.util.List ;
import java.util.Map ;
import java.util.Set ;

import android.app.Activity ;
import android.os.Bundle ;
import android.view.Gravity ;
import android.view.View ;
import android.view.ViewGroup ;
import android.widget.AdapterView ;
import android.widget.AdapterView.OnItemClickListener ;
import android.widget.BaseAdapter ;
import android.widget.LinearLayout ;
import android.widget.ListView ;
import android.widget.TextView ;
import android.widget.Toast ;

public class GridViewTest2 extends Activity {
	private ListView listview ;
	private HashMap<String, ArrayList<String>> map ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//setContentView(R.layout.gridview);
		
		LinearLayout linearLayout = new LinearLayout(this) ;
		LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT) ;
		linearLayout.setLayoutParams(p);
		
		listview = new ListView(this) ;
		listview.setLayoutParams(new AdapterView.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
		listview.setDivider(null);
		listview.setDividerHeight(0);
		linearLayout.addView(listview);
		setContentView(linearLayout);
		
		
		setupView();
		
	}

	private void setupView() {
		
		
		
		
		map = new HashMap<String, ArrayList<String>>() ;
		ArrayList<String> list01 = new ArrayList<String>();
		list01.add("01_a");
		list01.add("01_b");
		list01.add("01_c");
		list01.add("01_d");
		list01.add("01_e");
		list01.add("01_f");
		list01.add("01_g");
		list01.add("01_h");
		list01.add("01_i");
		list01.add("01_i");
		list01.add("01_i");
		list01.add("01_i");
		list01.add("01_i");
		list01.add("01_i");
		list01.add("01_i");
		list01.add("01_i");
		list01.add("01_i");
		list01.add("01_i");
		list01.add("01_i");
		list01.add("01_i");
		list01.add("01_i");
		list01.add("01_i");
		list01.add("01_i");
		list01.add("01_j");
		list01.add("01_k");
		list01.add("01_l");//12
		map.put("number01", list01);
		ArrayList<String> list02 = new ArrayList<String>();
		list02.add("02_a");
		list02.add("02_b");
		list02.add("02_c");
		list02.add("02_d");
		list02.add("02_e");
		list02.add("02_e");
		list02.add("02_f");
		list02.add("02_g");
		list02.add("02_h");
		list02.add("02_i");
		list02.add("02_g");
		list02.add("02_k");
		list02.add("02_l");
		list02.add("02_m");
		list02.add("02_m");
		list02.add("02_m");
		list02.add("02_m");
		list02.add("02_m");
		list02.add("02_m");
		list02.add("02_m");
		list02.add("02_m");
		list02.add("02_m");
		list02.add("02_m");
		list02.add("02_m");
		list02.add("02_m");
		list02.add("02_m");
		list02.add("02_m");
		list02.add("02_m");
		list02.add("02_m");
		list02.add("02_m");
		list02.add("02_m");
		list02.add("02_m");
		list02.add("02_m");
		list02.add("02_m");
		list02.add("02_m");
		list02.add("02_n");//14
		map.put("number02", list02);
		ArrayList<String> list03 = new ArrayList<String>();
		list03.add("03_a");
		list03.add("03_b");
		list03.add("03_c");
		list03.add("03_d");
		list03.add("03_e");
		list03.add("03_f");
		list03.add("03_g");
		list03.add("03_h");
		list03.add("03_i");
		list03.add("03_j");
		list03.add("03_k");
		list03.add("03_l");
		list03.add("03_m");
		list03.add("03_n");
		list03.add("03_o");
		list03.add("03_o");
		list03.add("03_o");
		list03.add("03_o");
		list03.add("03_o");
		list03.add("03_o");
		list03.add("03_o");
		list03.add("03_o");
		list03.add("03_o");
		list03.add("03_o");
		list03.add("03_o");
		list03.add("03_o");
		list03.add("03_o");
		list03.add("03_o");
		list03.add("03_o");
		list03.add("03_o");
		list03.add("03_o");
		list03.add("03_o");
		list03.add("03_o");
		list03.add("03_o");
		list03.add("03_o");
		list03.add("03_o");
		list03.add("03_o");
		list03.add("03_o");
		list03.add("03_o");
		list03.add("03_o");
		list03.add("03_o");
		list03.add("03_o");
		list03.add("03_o");
		list03.add("03_o");
		list03.add("03_p");//16
		map.put("number03", list03);
		
		computeItemtCount();
		listview.setAdapter(new MyAdapter());
		
		//
	}
	
	
	class MyItemListener implements OnItemClickListener{
		
		MyItemListener(int id ,String tag){
			
		}
		MyItemListener(){
			
		}	
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			String key = (String) parent.getTag();
			Toast.makeText(getApplicationContext(), key +" "+position , 1).show();
			
		}
		
	}
	
	int ITEM_EACH_ROW = 3 ;
	int LIST_COUNT = 0 ;
	Map<Integer,Integer>  positionMap = new HashMap<Integer,Integer>();//
	Map<Integer,String>  tagMap = new HashMap<Integer,String>();
	
	private void computeItemtCount(){
		int i = 0,j = 0 ;
		Set<String> keySet = map.keySet() ;
		for(String key:keySet){
			j = i ;
			int dataSize = ((List<String>)map.get(key)).size() ;
			i += dataSize / ITEM_EACH_ROW +(dataSize % ITEM_EACH_ROW == 0 ?0:1) +1;
			positionMap.put(j, 0);
			tagMap.put(j, key);
			for(int x = j+1;x< i;x++){
				positionMap.put(x, ITEM_EACH_ROW);
			}
			positionMap.put(i, dataSize- (i-j-2)*ITEM_EACH_ROW);
			
		}
		LIST_COUNT = i ;
		
		System.out.println("positionMap "+positionMap) ;
		System.out.println("tagMap "+tagMap) ;
		
		

		

		
		
	}
	
	
	class MyAdapter extends BaseAdapter{
		static final int TYPE_SEPARATOR = 0;
		static final int TYPE_ITEM = 1;
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
			return 2;
		}

		@Override
		public int getItemViewType(int position) {
			if (tagMap.containsKey(position)) {
				return TYPE_SEPARATOR;
			} else {
				return TYPE_ITEM;
			}
		}
		
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			int type = getItemViewType(position) ;
			if(convertView != null)System.out.println("type "+ (type == TYPE_SEPARATOR ? "TYPE_SEPARATOR":"TYPE_ITEM") +"view type "+convertView) ;
			switch (type) {
				case TYPE_SEPARATOR:
					if(convertView == null){
						convertView = new TextView(GridViewTest2.this);
					}
					((TextView)convertView).setText(tagMap.get(position));
					break ;

				case TYPE_ITEM:
					if(convertView == null){
						convertView = newView() ;
					}
					bindView(convertView,position);
					break ;
			}
			return convertView ;
		}
		
		
		private View newView() {
			LinearLayout linearLayout = new LinearLayout(GridViewTest2.this) ;
			linearLayout.setOrientation(LinearLayout.HORIZONTAL);
			linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
			for(int i =0;i<ITEM_EACH_ROW;i++){
				TextView itemTv = new TextView(GridViewTest2.this);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,1) ;
				itemTv.setLayoutParams(lp);
				itemTv.setGravity(Gravity.CENTER);
				linearLayout.addView(itemTv);
			}
			return linearLayout ;
			
		}
		private void bindView(View view ,int position) {
			LinearLayout linearLayout = (LinearLayout) view ;
			int childCount = linearLayout.getChildCount() ;
			Integer size = positionMap.get(position) ;
			for(int i=0;i<size;i++){
				TextView childAt = (TextView) linearLayout.getChildAt(i) ;
				childAt.setText("hello");
			}
			
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
