package org.wangliang.app.learn.ui.grid;

import java.util.ArrayList ;
import java.util.HashMap ;
import java.util.List ;
import java.util.Set ;

import org.wangliang.app.learn.R ;

import android.app.Activity ;
import android.content.Context ;
import android.graphics.Color ;
import android.os.Bundle ;
import android.util.AttributeSet ;
import android.view.Gravity ;
import android.view.MotionEvent ;
import android.view.View ;
import android.view.View.OnTouchListener ;
import android.view.ViewGroup ;
import android.widget.AdapterView ;
import android.widget.AdapterView.OnItemClickListener ;
import android.widget.ArrayAdapter ;
import android.widget.GridView ;
import android.widget.LinearLayout ;
import android.widget.LinearLayout.LayoutParams ;
import android.widget.TextView ;
import android.widget.Toast ;

public class GridViewTest extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.gridview);
		setupView();
		
	}

	private void setupView() {
		LinearLayout layout = (LinearLayout) findViewById(R.id.gridView_layout);
		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
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
		list01.add("01_j");
		list01.add("01_k");
		list01.add("01_k");
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
		list02.add("02_n");
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
		list03.add("03_p");
		map.put("number03", list03);
		
		Set<String> keySet = map.keySet();
		MyAdapter adapter ;
		for(String key:keySet){
			GridView gv = new MyGridView(GridViewTest.this);
			gv.setNumColumns(3);
			//gv.setHorizontalSpacing(3);
			//gv.setVerticalSpacing(3);
			gv.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
			gv.setGravity(Gravity.CENTER_HORIZONTAL);
			TextView tv = new TextView(GridViewTest.this);
			tv.setGravity(Gravity.CENTER);
			tv.setBackgroundColor(Color.rgb(0x66, 0x66, 0x66));
			LinearLayout.LayoutParams prarm = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
			layout.addView(tv, prarm);
			tv.setText(key);
			LinearLayout.LayoutParams prarm2 = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
		
			layout.addView(gv, prarm2);
			ArrayList<String> arrayList = map.get(key);
			adapter = new MyAdapter(GridViewTest.this, R.layout.gridview_item, R.id.textview,arrayList);
			
			gv.setAdapter(adapter);
			gv.setTag(key);
			//gv.setTouchDelegate(delegate)
			gv.setOnItemClickListener(new MyItemListener());
//			gv.setOnTouchListener(new OnTouchListener() {
//				 
//				  public boolean onTouch(View v, MotionEvent event) {
//					  if (event.getAction() == MotionEvent.ACTION_MOVE) {
//						  scrollView.requestDisallowInterceptTouchEvent(true);
//					    }
//					    return false;
//				  }
//			});			
			System.out.println("adapter.getCount():"+adapter.getCount());
		}
			
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
	class MyAdapter extends ArrayAdapter<String>{

		public MyAdapter(Context context, int resource, int textViewResourceId,
				List<String> objects) {
			super(context, resource, textViewResourceId, objects);
		}
		
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return super.getView(position, convertView, parent) ;
		}
		
	}
	  
	public class MyGridView extends GridView {   
	  
	    public MyGridView(Context context, AttributeSet attrs) {   
	        super(context, attrs);   
	    }   
	  
	    public MyGridView(Context context) {   
	        super(context);   
	    }   
	  
	    public MyGridView(Context context, AttributeSet attrs, int defStyle) {   
	        super(context, attrs, defStyle);   
	    }   
	    @Override   
	    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {   
	        int expandSpec = MeasureSpec.makeMeasureSpec(   
	                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);   
	        super.onMeasure(widthMeasureSpec, expandSpec);   
	    }   
	} 

}
