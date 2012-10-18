package com.johdan.apndemo;
 
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;
 
public class APNDemo extends Activity {
     Cursor cursor_current,cursor_need;
     Uri PREFERRED_APN_URI, APN_TABLE_URI;
     int newCreateAPN_Id;
     String APN_Id;
     TelephonyManager tm;
     WifiManager wifi;
     /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        boolean isCmwap = getCurrentAPN();
        boolean wCheckWapApn = checkHasWapAPN();
        if(!isCmwap){   
            if(!wCheckWapApn){
                newCreateAPN_Id = addCmwapAPN();
                Toast.makeText(APNDemo.this, "APN添加成功！", Toast.LENGTH_LONG).show();
            }
            else{
                setAPN(Integer.parseInt(APN_Id));
                Toast.makeText(APNDemo.this, "APN切换成功！", Toast.LENGTH_LONG).show();
                return;
            }try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        else{
            //don't do anything;
            Toast.makeText(APNDemo.this, "正在使用cmwap网络！", Toast.LENGTH_LONG).show();
 
            wifi = (WifiManager)getSystemService(Context.WIFI_SERVICE);
            if(wifi.isWifiEnabled()){
                wifi.setWifiEnabled(false);
            }
        }
 
    }
 
    //获取当前APN属性
    private boolean getCurrentAPN(){
        PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");
        cursor_current = this.getContentResolver().query(PREFERRED_APN_URI, null, null, null, null);
        if(cursor_current != null && cursor_current.moveToFirst()){
            String proxy = cursor_current.getString(cursor_current.getColumnIndex("proxy"));
            String apn = cursor_current.getString(cursor_current.getColumnIndex("apn"));
            String port = cursor_current.getString(cursor_current.getColumnIndex("port"));
            String current = cursor_current.getString(cursor_current.getColumnIndex("current"));
            if(proxy == null || apn == null || port == null || current == null
                    || proxy.equals("") || port.equals("")){
                return false;
            }
 
            if((proxy.equals("10.0.0.172") || proxy.equals("010.000.000.172")) && port.equals("80") &&
                    apn.equals("cmwap") && current.equals("1")){
                return true;
            }
        }
        return false;       
    }
 
    //检查是否存在cmwap网络
    private boolean checkHasWapAPN(){
        APN_TABLE_URI = Uri.parse("content://telephony/carriers");
        cursor_need = this.getContentResolver().query(APN_TABLE_URI, null, null, null, null);
 
        while(cursor_need != null && cursor_need.moveToNext()){
            String id = cursor_need.getString(cursor_need.getColumnIndex("_id"));      
            String port = cursor_need.getString(cursor_need.getColumnIndex("port"));  
            String proxy = cursor_need.getString(cursor_need.getColumnIndex("proxy"));
            String current = cursor_need.getString(cursor_need.getColumnIndex("current"));
            String mmsc = cursor_need.getString(cursor_need.getColumnIndex("mmsc"));
            if(proxy == null || port == null || current == null){
                continue;
            }
           if((proxy.equals("10.0.0.172") || proxy.equals("010.000.000.172"))
                    && port.equals("80") && current.equals("1")
                    && mmsc == null){
                APN_Id = id;
                return true;
            }
        }
        return false;   
    }
 
    //设置为cmwap网络
    public boolean setAPN(int id){
 
        //如果wifi是打开的，则关闭
        wifi = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        if(wifi.isWifiEnabled()){
            wifi.setWifiEnabled(false);
        }
 
        boolean res = false;
        ContentResolver resolver = this.getContentResolver();
        ContentValues values = new ContentValues();
        values.put("apn_id", id);
        try{
            resolver.update(PREFERRED_APN_URI, values, null, null);
            Cursor c = resolver.query(PREFERRED_APN_URI, new String[]{"name", "apn"}, "_id=" + id, null, null);
            if(c != null){
                res = true;
                c.close();
            }
        }catch(SQLException e){
            Log.e("lhl", e.getMessage());
        }
        return res;
    }
 
    //添加cmwap网络
    private int addCmwapAPN(){
        ContentResolver cr = this.getContentResolver();
        ContentValues cv = new ContentValues();
        cv.put("name", "cmwap");
        cv.put("apn", "cmwap");
        cv.put("proxy", "010.000.000.172");
        cv.put("port", "80");
        cv.put("current", 1);
 
        tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String imsi =tm.getSubscriberId();
        if(imsi != null){
            if(imsi.startsWith("46000")){
                cv.put("numeric", "46000");
                cv.put("mcc", "460");
                cv.put("mnc", "00");
            }
            else if(imsi.startsWith("46002")){
                cv.put("numeric", "46002");
                cv.put("mcc", "460");
                cv.put("mnc", "02");
            }
        }
 
        Cursor c = null;
        try{
            Uri newRow = cr.insert(APN_TABLE_URI, cv);
            if(newRow != null){
                c = cr.query(newRow, null, null, null, null);
                c.moveToFirst();
                String id = c.getString(c.getColumnIndex("_id"));
                setAPN(Integer.parseInt(id));
                return Integer.parseInt(id);
            }
 
        }catch(SQLException e){
            Log.e("lhl", e.getMessage());
        }
        finally{
            if(c != null){
                c.close();
            }
        }   
        return 0;       
    }
 
}
//http://www.johdan.com/tabhost_do_not_-exist-_startactivityforresult.html