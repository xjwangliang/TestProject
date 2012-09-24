package org.wangliang.app.learn;

import java.util.HashMap;

import org.wangliang.app.learn.contacts.Contacts;
import org.wangliang.app.learn.email.Email;
import org.wangliang.app.learn.webview.Geo;
import org.wangliang.app.learn.webview.Wangyi;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Main extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice, Items));

		final ListView listView = getListView();

		listView.setItemsCanFocus(false);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		initIntent();
	}

	private static final String[] Items = new String[] { "Contacts","Wangyi","Geo","Email","Span","Pic","Switcher","Switcher2","Contacts Indexer","Service","AChart"};
	HashMap<Integer, Intent> intents = null ;
	private void initIntent(){
		intents = new HashMap<Integer, Intent>();//Use new SparseArray<Intent>(...) instead for better performance
		intents.put(0, new Intent(this, Contacts.class));//android.provider.Contacts;android.provider.ContactsContract.Contacts;
		intents.put(1, new Intent(this, Wangyi.class));
		intents.put(2, new Intent(this, Geo.class));
		intents.put(3, new Intent(this, Email.class));//android.provider.ContactsContract.CommonDataKinds.Email
		intents.put(4, new Intent(this, org.wangliang.app.learn.span.Span.class));//android.provider.ContactsContract.CommonDataKinds.Email
		intents.put(5, new Intent(this, org.wangliang.app.learn.webview.Pic.class));//android.provider.ContactsContract.CommonDataKinds.Email
		intents.put(6, new Intent(this, org.wangliang.app.learn.switcher.ViewSwitcherTest.class));
		intents.put(7, new Intent(this, org.wangliang.app.learn.switcher2.Switcher2Main.class));
		intents.put(8, new Intent(this, org.wangliang.app.learn.indexer.ContactsIndexer.class));
		intents.put(9, new Intent(this, org.wangliang.app.learn.service.ServiceActivity.class));
		intents.put(10, new Intent(this, org.wangliang.app.learn.graph.ChartDemo.class));
	}
	

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = intents.get(position);
		startActivity(intent);
	}

}