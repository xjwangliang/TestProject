package org.wangliang.app.learn.animation ;

import java.util.List ;

import org.wangliang.app.learn.R ;

import android.app.AlertDialog ;
import android.content.Context ;
import android.os.Bundle ;
import android.text.SpannableString ;
import android.text.style.TextAppearanceSpan ;
import android.view.LayoutInflater ;
import android.view.MenuItem ;
import android.view.MotionEvent ;
import android.view.View ;
import android.view.ViewGroup ;
import android.view.WindowManager ;
import android.widget.AdapterView ;
import android.widget.BaseAdapter ;
import android.widget.GridView ;
import android.widget.ImageView ;
import android.widget.TextView ;

public class MenuDialog extends AlertDialog {
	GridView gridview ;
	private MenuAdapter menuAdapter = null ;
	private List<MyMenuItem> data ;
	private Context context = null ;

	protected MenuDialog( Context context,List<MyMenuItem> data) {
		super(context) ;
		this.context = context ;
		this.data = data ;
	}
	
	protected List<MyMenuItem> getData(){
		return data ;
	}

	private void a(MenuItem paramMenuItem) {
//		Intent localIntent ;
//		switch (paramMenuItem.getItemId()) {
//			case 2131559018:
//				localIntent = new Intent(this.context, SettingManagerActivity.class) ;
//				this.b.startActivity(localIntent) ;
//				break ;
//			case 2131559019:
//				UICore.f().w() ;
//				break ;
//			case 2131559020:
//				localIntent = new Intent(this.context, HelpActivity.class) ;
//				this.b.startActivity(localIntent) ;
//				break ;
//			case 2131559021:
//				UICore.f().x() ;
//		}
	}

	private boolean a(MotionEvent paramMotionEvent) {
		int k = (int) paramMotionEvent.getX() ;
		int j = (int) paramMotionEvent.getY() ;
		View localView = getWindow().getDecorView() ;
		localView.invalidate() ;
		int i ;
		if ((k >= 0) && (j >= 0) && (k <= 0 + localView.getWidth())
				&& (j <= 0 + localView.getHeight()))
			i = 0 ;
		else
			i = 1 ;
		return i==1 ;
	}

	public void a() {
		this.gridview.setAdapter(this.menuAdapter) ;
	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle) ;
		this.gridview = ((GridView) LayoutInflater.from(this.context).inflate(R.layout.qqgridview_menu,null)) ;
		getWindow().setContentView(this.gridview) ;
		getWindow().setLayout(-1, -2) ;
		WindowManager.LayoutParams params = new WindowManager.LayoutParams(-1, -2, 0, 0, 2, 0, -3) ;
		params.gravity = 81 ;
		getWindow().setAttributes(params) ;
		getWindow().getAttributes().windowAnimations = 2131427356 ;
		setCanceledOnTouchOutside(true) ;
		
		this.menuAdapter = new MenuAdapter(this) ;
		this.gridview.setFocusable(true) ;
		this.gridview.setOnItemClickListener(new MenuItemClickListener(this)) ;
		this.gridview.setNumColumns(4) ;
		this.gridview.setSelector(R.drawable.menu_selectitem) ;
	}

	public boolean onTouchEvent(MotionEvent paramMotionEvent) {
		int i ;
		if ((paramMotionEvent.getAction() != 0) || (!a(paramMotionEvent))) {
			i = 0 ;
		} else {
			cancel() ;
			i = 1 ;
		}
		return i==1 ;
	}
}

class MenuAdapter extends BaseAdapter {
	private Context context ;
	private MenuDialog dialog ;
	MenuAdapter(MenuDialog paramMenuDialog) {
		context = paramMenuDialog.getContext() ;
		dialog = paramMenuDialog ;
	}

	public int getCount() {
		return dialog.getData().size() ;
	}

	public Object getItem(int paramInt) {
		return null ;
	}

	public long getItemId(int paramInt) {
		return 0L ;
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		View localView ;
		if (paramView != null)
			localView = paramView ;
		else{
			List<MyMenuItem> data = dialog.getData() ;
			MyMenuItem itemData = data.get(paramInt) ;
			localView = LayoutInflater.from(context).inflate(R.layout.item_menu, null) ;
			ImageView iv = ((ImageView) localView.findViewById(R.id.item_image)) ;
			iv.setImageDrawable(itemData.getIcon()) ;
			
			
			int i = context.getResources().getIdentifier("menuTextStyle", "style",context.getPackageName()) ;
			
			SpannableString localSpannableString = new SpannableString(itemData.getTitle()) ;
			localSpannableString.setSpan(new TextAppearanceSpan(context, i), 0,itemData.getTitle().length(),33) ;
//			<style name="menuTextStyle" parent="@android:style/TextAppearance.Small">
//		        <item name="android:textSize">12.0sp</item>
//		        <item name="android:textColor">@color/qqmenu_text_color</item>
//		    </style>
			((TextView) localView.findViewById(R.id.item_text)).setText(localSpannableString) ;
			if (!itemData.getItem().isEnabled()){
				iv.setAlpha(77) ;
			}
		
			
		}
			
		
				
		
		
		
		
		return localView ;
	}
}

class MenuItemClickListener implements AdapterView.OnItemClickListener {
	MenuItemClickListener(MenuDialog paramMenuDialog) {
	}

	public void onItemClick(AdapterView paramAdapterView, View paramView,
			int paramInt, long paramLong) {
		//  this.a.cancel();
		//  if (((hs)QqMenuActivity.a(this.a.b).get(paramInt)).c().isEnabled())
		//  {
		//    this.a.b.onOptionsItemSelected(((hs)QqMenuActivity.a(this.a.b).get(paramInt)).c());
		//    if (!UICore.q().aj())
		//      UICore.q().m(true);
		//    else
		//      QqMenuActivity.MenuDialog.a(this.a, ((hs)QqMenuActivity.a(this.a.b).get(paramInt)).c());
		//  }
	}
}