package org.wangliang.app.learn.ui.grid ;
//package com.guoku.activity;
//
//import java.util.ArrayList ;
//import java.util.Comparator ;
//import java.util.HashMap ;
//import java.util.Iterator ;
//import java.util.List ;
//import java.util.Set ;
//import java.util.TreeMap ;
//
//import org.json.JSONException ;
//import org.json.JSONObject ;
//import org.wangliang.app.learn.R ;
//
//import android.app.Activity ;
//import android.content.Intent ;
//import android.graphics.Color ;
//import android.graphics.drawable.ColorDrawable ;
//import android.os.AsyncTask ;
//import android.os.Bundle ;
//import android.text.format.DateFormat ;
//import android.util.Log ;
//import android.view.Gravity ;
//import android.view.View ;
//import android.view.View.OnClickListener ;
//import android.widget.Adapter ;
//import android.widget.AdapterView ;
//import android.widget.AdapterView.OnItemClickListener ;
//import android.widget.BaseAdapter ;
//import android.widget.GridView ;
//import android.widget.ImageButton ;
//import android.widget.LinearLayout ;
//import android.widget.LinearLayout.LayoutParams ;
//import android.widget.TextView ;
//import android.widget.Toast ;
//
///**
// * should check if there is a logged user before start this activity.
// * 
// * 
// * @author yanzhao
// * 
// */
//public class Favour extends Activity implements OnItemClickListener {
//	private GridView mGridView;
//	private CardAdapter adapter;
//
//	private OnRefreshListener mOnRefreshListener = new OnRefreshListener() {
//		@Override
//		public void onRefresh() {
//			// Do work to refresh the list here.
//			new RefreshDataTask().execute();
//		}
//	};
//
//	private OnClickListener mOnClickListener = new OnClickListener() {
//		public void onClick(View arg0) {
//			mPullToRefreshGridView.refreshing();
//			new RefreshDataTask().execute();
//		};
//	};
//	private HashMap<String, Adapter> adapterMap;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.favour);
//
//
//	}
//
//	
//	private void populateCard(List<Card> result){
//		Log.e("populateCard", "populateCard执行了"+(++i)+"次");
//		map = new TreeMap<String, ArrayList<Card>>(
//				new Comparator<String>() {
//
//					@Override
//					public int compare(String lhs, String rhs) {
//						// TODO Auto-generated method stub
//						return -lhs.compareTo(rhs);
//
//					}
//				});
//
//		java.text.DateFormat dateFormat = DateFormat
//				.getDateFormat(getApplicationContext());
//		for (int i = 0; i < result.size(); i++) {
//			String dateStr = dateFormat.format(result.get(i)
//					.getLiked_time());
//			System.out.println(dateStr);
//			if (!map.containsKey(dateStr)) {
//				ArrayList<Card> list = new ArrayList<Card>();
//				list.add(result.get(i));
//				map.put(dateStr, list);
//			} else {
//				map.get(dateStr).add(result.get(i));
//			}
//			// Date likeDate = new
//			// Date(result.get(i).getLiked_time());
//			// String format = formatter.format(likeDate);
//			// Log.e(TAG, "like time:"+format);
//		}
//		int size = map.size();
//		Set<String> keySet = map.keySet();
//		Iterator<String> it = keySet.iterator();
//		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//				LayoutParams.FILL_PARENT,
//				LayoutParams.WRAP_CONTENT);
//		params.topMargin = 5;
//		params.bottomMargin = 5;
//		adapterMap = new HashMap<String, Adapter>();
//		
//		while (it.hasNext()) {
//			String key = it.next();
//			GridView gv = new MyGridView(Favour.this);
//			gv.setSelector(new ColorDrawable(Color.TRANSPARENT));
//			gv.setNumColumns(3);
//			gv.setHorizontalSpacing(3);
//			gv.setVerticalSpacing(3);
//			TextView tv = new TextView(Favour.this);
//			tv.setGravity(Gravity.CENTER);
//			tv.setBackgroundColor(Color.rgb(0x45, 0x56, 0x56));
//
//			mPullToRefreshGridView.addView(tv, params);
//			tv.setText(key);
//			mPullToRefreshGridView.addView(gv, params);
//			ArrayList<Card> arrayList = map.get(key);
//			
//			adapter = new FavourCardAdapter(Favour.this, 0,
//					arrayList, Favour.this, key);
//			adapterMap.put(key, adapter);
//			// GuokuService service =
//			// GuokuService.getInstance(this);
//			// service.getLikeCards(service.getGuokuUserFromLocal());
//			gv.setAdapter(adapter);
//			gv.setOnItemClickListener(Favour.this);
//			System.out.println("adapter.getCount():"
//					+ adapter.getCount());
//			// mGridView.setAdapter(adapter);
//			// mGridView.setOnItemClickListener(Favour.this);
//		}
//
//	}
//
//
//	@Override
//	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//		// TODO Auto-generated method stub
//
//		// Card item = (Card) mGridView.getItemAtPosition(arg2);
//		// Intent in = new Intent(this, CardDetail.class);
//		// in.putExtras(item.toBundle());
//		// startActivity(in);
//		//
//		// recordStatic(item);
//		String key = (String) ((ViewHolder) arg1.getTag()).iv.getTag();
//
//		final Card card = map.get(key).get(arg2);
//		Intent in = new Intent(this, CardDetail.class);
//		in.putExtras(card.toBundle());
//		
//		
//		in.putExtra("liked", true);
//		in.putExtra("dateKey", key);
//		MyApplication.singleCard = card ;
//		int REQUESTCODE =100;
//		startActivityForResult(in,REQUESTCODE );
//		
//		
//		new Thread(){
//			public void run() {
//				recordStatic(card);
//			};
//		};
//	}
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if(resultCode == Activity.RESULT_OK){
//			boolean changed = data.getBooleanExtra("changed", false);
//			
//			if(changed){
//				String dateKey = data.getStringExtra("dateKey");
//				int likedCount = MyApplication.singleCard.getLiked_count();
//				switch (data.getIntExtra("add", 1)) {
//					case 1:
////						MyApplication.likedCards.add(MyApplication.singleCard);
////						MyApplication.likedIds.add(MyApplication.singleCard.getCard_id());
////						likedCount++;
//						Toast.makeText(this, "出错了。", Toast.LENGTH_LONG).show();
//						break;
//	
//					case -1:
//						MyApplication.likedCards.remove(MyApplication.singleCard);
//						MyApplication.likedIds.remove(MyApplication.singleCard.getCard_id());
//						likedCount--;
//						map.get(dateKey).remove(MyApplication.singleCard);
//						((BaseAdapter)adapterMap.get(dateKey)).notifyDataSetChanged();
//						break;
//				};
//				
//				MyApplication.singleCard.setLiked_count(likedCount);
////				adapter.notifyDataSetChanged();
//			}
//			
//		}
//		
//		super.onActivityResult(requestCode, resultCode, data);
//	}
////	private void refreshCard(ArrayList<Card> result){
////		map = new TreeMap<String, ArrayList<Card>>(
////				new Comparator<String>() {
////
////					@Override
////					public int compare(String lhs, String rhs) {
////						// TODO Auto-generated method stub
////						return -lhs.compareTo(rhs);
////
////					}
////				});
////
////		java.text.DateFormat dateFormat = DateFormat
////				.getDateFormat(getApplicationContext());
////		for (int i = 0; i < result.size(); i++) {
////			String dateStr = dateFormat.format(result.get(i)
////					.getLiked_time());
////			System.out.println(dateStr);
////			if (!map.containsKey(dateStr)) {
////				ArrayList<Card> list = new ArrayList<Card>();
////				list.add(result.get(i));
////				map.put(dateStr, list);
////			} else {
////				map.get(dateStr).add(result.get(i));
////			}
////			if(adapterMap !=null){
////				Iterator<String> it = adapterMap.keySet().iterator();
////				while (it.hasNext()) {
////					BaseAdapter adapter = (BaseAdapter) adapterMap.get( it.next() );
////					adapter.notifyDataSetChanged();
////					
////				}
////			}
////		}
////	}
//
//	
//
//}
