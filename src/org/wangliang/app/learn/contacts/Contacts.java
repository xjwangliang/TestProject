package org.wangliang.app.learn.contacts;

import java.util.ArrayList;
import java.util.List;

import org.wangliang.app.learn.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People.Phones;
import android.provider.ContactsContract;
import android.view.View;

public class Contacts extends Activity {

	// 来源：http://blog.csdn.net/laoyao_moyan/article/details/7328995
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts);
	}

	// <2
	public void way1(View view) {
		try {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_PICK);
			// android.provider.Contacts;android.provider.ContactsContract.Contacts;
			intent.setData(android.provider.Contacts.People.CONTENT_URI);// ContactsContract.Contacts没有People
			startActivityForResult(intent, 1);
		} catch (Exception e) {
		}
		
	}

	// <2
	public void way2(View view) {
		try {
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType("Contacts.People.CONTENT_TYPE");// vnd.android.cursor.dir/person
			startActivityForResult(intent, 1);
		} catch (Exception e) {
		}
		
	}

	public void way3(View view) {
		try {
			Intent intent = new Intent(Intent.ACTION_PICK,
					ContactsContract.Contacts.CONTENT_URI);
			startActivityForResult(intent, 3);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	public void way4(View view) {
		try {
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType(ContactsContract.Contacts.CONTENT_TYPE);// vnd.android.cursor.dir/contact
			startActivityForResult(intent, 4);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		Cursor cursor = null ;
		
		switch (requestCode) {
		case 1:
			if (data == null) {
				return;
			}
			Uri uri = data.getData();
			cursor = getContentResolver().query(uri, null, null, null,
					null);
			cursor.moveToFirst();
			// android.provider.Contacts.Phones;android.provider.Contacts.People.Phones;
			// 两个都是"number"
			String number = cursor.getString(cursor
					.getColumnIndexOrThrow(Phones.NUMBER));
			// mContactText.setText(number);
			// mContactText.setSelection(number.length());
			break;

		default:
			if (resultCode == Activity.RESULT_OK) {
				Uri contactData = data.getData();
				cursor = managedQuery(contactData, null, null, null, null);
				if (cursor.moveToFirst()) {
					String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
					String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
					//String hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
					//if (hasPhone.equalsIgnoreCase("1")) {
					//	hasPhone = "true";
					//} else {
					//	hasPhone = "false";
					//}
					
					Cursor emails = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,null,ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId,null, null);
					Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID+ " = " + contactId, null, null);
					
					List<String> ls = new ArrayList<String>();
				    while (emails.moveToNext()) {
				        ls.add(emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)));
				    }
				    List<String> ls2 = new ArrayList<String>();
				    while (phones.moveToNext()) {
				    	ls2.add(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
					}
				    
				    emails.close();
				    phones.close();
				}
				
			}
			break;
		}
	}

}

// ------------------------------------------------------------------------------------------
// ------------------------------------------------------------------------------------------

