package org.wangliang.app.learn.indexer;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.wangliang.app.learn.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ContactsIndexer extends Activity{
		//最大可选择的联系人数目
	public static final int maxCheckCount = 20;
	//数据容器
    ArrayList<Person> personArray = null;
	//设置adapter
    MyCheckBoxAdapter adapter = null;
    //发送推荐信息的时候弹出进度条
    private ProgressDialog recommendDialog = null;
    //发送推荐信息正确
    public static final int HTTP_REQUEST_OK = 1;
    //EditText中的字符串
    public String editString = "";
    
    //消息控制器，用来更新界面，因为在普通线程是无法用来更新界面的
    private Handler handler = new Handler() {
    	
    	public void handleMessage(Message msg) {
    		switch (msg.what) {
    		case HTTP_REQUEST_OK:
    			if((recommendDialog != null)||(recommendDialog.isShowing())){
    				recommendDialog.dismiss();
    				recommendDialog = null;
    			}
    			Toast.makeText(ContactsIndexer.this, "发送成功", Toast.LENGTH_SHORT).show();
    			break;
			default:
				break;
    		}
    	}
    };
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_indexer);
        
        //设置默认情况下不弹出输入框
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        
        //填充数据
        personArray = this.getList();

        //设置adapter
        adapter = new MyCheckBoxAdapter(this, personArray);
        
        //设置ListView
        final ListView lv = (ListView)findViewById(R.id.contactsList);
        lv.setAdapter(adapter);
        lv.setTextFilterEnabled(true);
        
      //设置EditText的变化监听事件
        EditText et = (EditText)findViewById(R.id.manual);
        et.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String mString = s.toString().trim();
				editString = mString;
				//用过滤器进行过滤
				adapter.getFilter().filter(mString);
				
				//如果搜索框中有字符，则快速滚动失效
				if(!mString.equals(""))
					lv.setFastScrollEnabled(false);
				//如果搜索框中没有字符，则重新值为有效
				else
					lv.setFastScrollEnabled(true);
			}
		});
        
        //设置推荐按钮
        Button button = (Button)findViewById(R.id.recommendButton);
        button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//存放选中的联系人的电话号码
		        StringBuffer sb = new StringBuffer();
				for(int i=0;i<personArray.size();i++){
					if(personArray.get(i).isSelected()){
						sb.append(personArray.get(i).getPhoneNum());
						personArray.get(i).setSelected(false);
					}
				}
				sb.append(editString);
				String phoneNumMsg = sb.toString();
				System.out.println(phoneNumMsg);
				
				//发送完成
				if(recommendContacts(phoneNumMsg)){
					resetListView();
				}
			}
        });
        
        
    }
    
    /**
     * 测试
     * @return
     *
     * Created by Liuzhao
     * 2011-7-8 上午10:03:16
     */
	private ArrayList<Person> getListTemp() {
    	ArrayList<Person> list = new ArrayList<Person>();
        for(int i=0;i<2000;i++)
        {
        	Person man = new Person();
        	man.setId(i);
        	man.setName("Man"+i);
        	man.setPhoneNum("PnoneNum"+i);
        	list.add(man);
        }
		return list;
	}

	/**
     * 获得手机联系人列表
     * @return
     */
    public ArrayList<Person> getList(){
    	//联系人信息容器
    	ArrayList<Person> list = new ArrayList<Person>();
    	//要查询的表
    	Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    	//查询的字段:ID,DISPLAY_NAME,NUMBER
    	String[] projection = new String[] {
    			ContactsContract.CommonDataKinds.Phone._ID,
    			ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
    			ContactsContract.CommonDataKinds.Phone.NUMBER
        };
    	//按名字排序显示
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
    	//查询，获得查询结果
        Cursor cursor = managedQuery(uri, projection, null, null, sortOrder);
		//读取查询结果
    	while (cursor.moveToNext()) {
    		//联系人ID
    		String id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
            //联系人姓名
    		String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            //联系人电话号码
    		String phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            //联系人实体类
    		Person man = new Person();
            man.setId(Integer.parseInt(id.trim()));
            man.setName(name);
            man.setPhoneNum(phoneNum+";");
            //把联系人加入容器
            list.add(man);
    	}
    	cursor.close();
    	return list;
    }
    
    /**
     * 发送推荐人电话号码
     * @param msg 电话号码，用;隔开
     * @return
     */
    public boolean recommendContacts(String msg){
		try {
			//如果没有正在发送，则弹出dialog并发送推荐信息
			if((recommendDialog == null) || (!recommendDialog.isShowing())){
				recommendDialog = new ProgressDialog(this);
				recommendDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				recommendDialog.setMessage("正在发送请稍后……");
				recommendDialog.setCancelable(false);
				recommendDialog.show();
				//SendRecommedThread srThread = new SendRecommedThread(Constant.RecommendURL,msg);
				//srThread.start();
				Log.i("Contacts", msg);
				this.handler.sendEmptyMessage(HTTP_REQUEST_OK);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
    
    /**
     * 发送推荐信息的线程
     * @author Liuzhao
     *
     */
    class SendRecommedThread extends Thread{
    	
    	private String urlString = null;
    	private String msg = null;
    	
    	public SendRecommedThread(String urlString, String msg){
    		super();
    		this.urlString = urlString;
    		this.msg = msg;
    	}
    	
    	@Override
		public void run() {
    		Log.d("Recommend","");
    		// 创建HttpPost连接对象
			HttpPost httpRequest = new HttpPost(urlString);
			// 使用NameValuePair来保存要传递的Post参数
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// 添加要传递的参数
			params.add(new BasicNameValuePair("phoneno",msg));
			//params.add(new BasicNameValuePair("imsi",tykmAndroid.imsi));
			params.add(new BasicNameValuePair("terminalType","1"));
			try {
				// 设置字符集
				HttpEntity httpentity = new UrlEncodedFormEntity(params,"UTF-8");
				// 请求httpRequest
				httpRequest.setEntity(httpentity);
				// 取的默认的HttpClient
				HttpClient httpclient = new DefaultHttpClient();
				// 取的HttpResponse
				HttpResponse httpResponse = httpclient.execute(httpRequest);
				// HttpStatus.SC_OK表示连接成功
				if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					ContactsIndexer.this.handler.sendEmptyMessage(HTTP_REQUEST_OK);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    }
    
    /**
     * 重置CheckBox
     */
    public void resetListView(){
    	adapter.setCheckedCount(0);
    	adapter.notifyDataSetChanged();
    }
}