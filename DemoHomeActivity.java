package com.fanvil.eden;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.TextView;

/**
 * @author Eden
 *	2011.03.21
 */
public class DemoHomeActivity extends Activity {
	
	private static final String TAG = "DemoHomeActivity";
	
	private SlidingDrawer mDrawer;
	private TextView input_tv;
	private EndClosedMyDrawer mClosedDrawer;
	private ListView test_lv;
	
	private String [] test = new String[]{"010668971455","020132456","01058585896","113456","4896325","1372160485","195369871"};
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        init();
        
		mClosedDrawer = new EndClosedMyDrawer(5000,1000);
		new UpdateListTask().execute(getTextNumber());
    }
    
    
    
	protected void onRestart() {
		new UpdateListTask().execute(getTextNumber());
		super.onRestart();
	}



	public void init()
    {
    	mDrawer=(SlidingDrawer)findViewById(R.id.slidingdrawer);
		input_tv = (TextView)findViewById(R.id.input_dialpad_tv);
		
		test_lv = (ListView)findViewById(R.id.test_lv);
		
		input_tv.addTextChangedListener(watcher);
		
    }
    
    private TextWatcher watcher = new TextWatcher() {
		
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
		}
		
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			
		}
		
		public void afterTextChanged(Editable s) {
			new UpdateListTask().execute(getTextNumber());
		}
	};
    
	
    private void keyPressed ( int keyCode )
    {
 		mDrawer.open();
 		try{
 			mClosedDrawer.cancel();
 		}catch (Exception e) {
 			Log.i(TAG, "This is the first run updatelist Countdown!");
 		}
 		mClosedDrawer.start();
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event)
	{
	    switch (keyCode)
	    {
	        case KeyEvent.KEYCODE_0 :
	        {
	            keyPressed(KeyEvent.KEYCODE_0);
				input_tv.append("0");
	        }
	        break;

	        case KeyEvent.KEYCODE_1 :
	        {
                keyPressed(KeyEvent.KEYCODE_1);
	            input_tv.append("1");
	        }
	        break;

	        case KeyEvent.KEYCODE_2 :
	        {
	            keyPressed(KeyEvent.KEYCODE_2);
				input_tv.append("2");
	        }
	        break;

	        case KeyEvent.KEYCODE_3 :
	        {
	            keyPressed(KeyEvent.KEYCODE_3);
				input_tv.append("3");
	        }
	        break;

	        case KeyEvent.KEYCODE_4 :
	        {
	            keyPressed(KeyEvent.KEYCODE_4);
				input_tv.append("4");
	        }
	        break;

	        case KeyEvent.KEYCODE_5 :
	        {
	            keyPressed(KeyEvent.KEYCODE_5);
				input_tv.append("5");
	        }
	        break;

	        case KeyEvent.KEYCODE_6 :
	        {
	            keyPressed(KeyEvent.KEYCODE_6);
				input_tv.append("6");
	        }
	        break;

	        case KeyEvent.KEYCODE_7 :
	        {
	            keyPressed(KeyEvent.KEYCODE_7);
				input_tv.append("7");
	        }
	        break;

	        case KeyEvent.KEYCODE_8 :
	        {
	            keyPressed(KeyEvent.KEYCODE_8);
				input_tv.append("8");
	        }
	        break;

	        case KeyEvent.KEYCODE_9 :
	        {
	            keyPressed(KeyEvent.KEYCODE_9);
				input_tv.append("9");
	        }
	        break;

	        case KeyEvent.KEYCODE_STAR :
	        {
	            keyPressed(KeyEvent.KEYCODE_STAR);
				input_tv.append("*");
	        }
	        break;

	        case KeyEvent.KEYCODE_POUND :
	        {
	            keyPressed(KeyEvent.KEYCODE_POUND);
				input_tv.append("#");
	        }
	        break;
	        
	        case KeyEvent.KEYCODE_DEL:
	        {
	            keyPressed(KeyEvent.KEYCODE_DEL);
				CharSequence str = "";
				str = input_tv.getText();
				if(str != null && str.length() > 0){
					String temp = str.toString();
					if(temp.length() == 1){
						input_tv.setText("");
					}else if(temp.length() > 1){
						input_tv.setText(temp.subSequence(0, temp.length()-1));
					}
				}
	        }
	        break;
	        default:
				return super.onKeyDown(keyCode, event);
	    }
        return true;
	}
    
    
    /**
	 * Customize Adapter, so that realize highlighted keywords
	 */
	private  class hightKeywordsAdapter extends BaseAdapter
	{
		private List list;
		private Context context;
		private String[] from;
		private int[] to;
		private int layoutid;
		private HashMap info;
		LayoutInflater myInflater;
		HashMap item; 

		public hightKeywordsAdapter(Context context, List list, int layoutid, String[] from,int[] to)
		{
			this.context = context;
			this.list = list;
			this.from = from;
			this.to = to;
			this.layoutid = layoutid;
		}
		
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}
		
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			myInflater = LayoutInflater.from(context);
			try
			{
				item = (HashMap) list.get(position);
				convertView = myInflater.inflate(layoutid, null);
				convertView.setTag(item);
			}catch (Exception e) {
				Log.e(TAG, "Hight Key Error! ");
			}	
			
			View number = convertView.findViewById(to[0]);
			
			if (number instanceof TextView) {
				TextView number_tv = (TextView) number;
				number_tv = (TextView) number;
				String number_temp = "";
				number_temp = item.get(from[0]).toString();
				
				String input = input_tv.getText().toString();
				
				if(number_temp.contains(input)){
					
					//Methods 1
					int index = number_temp.indexOf(input);
					int len = input.length();
					Spanned temp = Html.fromHtml(number_temp.substring(0, index)
					         + "<u><font color=#5DB43B>" + number_temp.substring(index, index + len)
					         + "</font></u>" + number_temp.substring(index + len, number_temp.length()));
					
					//Methods 2
					/*int start = number.indexOf(input);   
					SpannableStringBuilder style=new SpannableStringBuilder(number);   
					style.setSpan(new Tex(Color.RED), start, start + input.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
					*/
					number_tv.setText(temp);
				}
			}
			return convertView;
		}		
	}
	
	private synchronized List updateNumberslist(String input)
	{
		List list = new ArrayList<HashMap<String, String>>();
		if(input == null)
		{
			for(int i=0;i<test.length;i++){
				HashMap<String, String> number_info = new HashMap<String, String>();
				number_info.put("number", test[i]);
				list.add(number_info);
			}
		}
		else
		{
			for(int i=0;i<test.length;i++){
				
				if(test[i].contains(input)){
					HashMap<String, String> number_info = new HashMap<String, String>();
					number_info.put("number", test[i]);
					list.add(number_info);
				}
			}
		}
		return list;
	}
	
	private String getTextNumber(){
		if(input_tv.getText() != null && input_tv.getText().length() > 0){
			return input_tv.getText().toString();
		}else{
			return null;
		}
	}
	
	private class UpdateListTask extends AsyncTask<String, Integer, BaseAdapter>
	{
		protected BaseAdapter doInBackground(String... params) {
			
			BaseAdapter listAdapter = new hightKeywordsAdapter(DemoHomeActivity.this, 
					updateNumberslist(getTextNumber()),
					android.R.layout.simple_list_item_1,
					new String[] {"number" },
					new int[] {android.R.id.text1 });
			
			return listAdapter;
		}

		protected void onPostExecute(BaseAdapter result) {
				test_lv.setAdapter(result);
		}
	}
	
    //end close the mdrawer
    private class EndClosedMyDrawer extends CountDownTimer {
	    public EndClosedMyDrawer(long millisInFuture, long countDownInterval){
	      super(millisInFuture, countDownInterval);
	    }    
	    
	    public void onFinish(){
	    	//Log.i(TAG, "Start asyncTask to updateList !");
	    	mDrawer.close();
	    }
	    
	    public void onTick(long millisUntilFinished){
	    	
		}  	    
	}
}