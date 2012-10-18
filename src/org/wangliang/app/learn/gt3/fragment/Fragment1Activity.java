package org.wangliang.app.learn.gt3.fragment;

import org.wangliang.app.learn.R ;

import android.os.Bundle ;
import android.support.v4.app.FragmentActivity ;
import android.support.v4.app.FragmentManager ;
import android.support.v4.app.FragmentTransaction ;
import android.view.LayoutInflater ;
import android.view.View ;
import android.view.ViewGroup ;
import android.widget.Button ;


public class Fragment1Activity extends FragmentActivity {
    public static FragmentManager fm;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_acitivity_1);
        fm = getSupportFragmentManager();
        // 只當容器，主要內容已Fragment呈現
        initFragment(new Fragment1());
    }
    // 切換Fragment
    public static void changeFragment(android.support.v4.app.Fragment f){
        changeFragment(f, false);
    }
    // 初始化Fragment(FragmentActivity中呼叫)
    public static void initFragment(android.support.v4.app.Fragment f){
        changeFragment(f, true);
    }
    private static void changeFragment(android.support.v4.app.Fragment f, boolean init){
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.simple_fragment, f);
        if(!init){
            ft.addToBackStack(null);
        }
        ft.commit();
    }
}

class Fragment1 extends android.support.v4.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment1, container, false);
        Button tv = (Button)v.findViewById(R.id.button2);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 直接呼叫FragmentActivity1的靜態方法來做切換
            	Fragment1Activity.changeFragment(new Fragment2());
            }
        });
        return v;
    }
}

class Fragment2 extends android.support.v4.app.Fragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState) ;
	}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment1, container, false);
        Button tv = (Button)v.findViewById(R.id.button2);
        tv.setText("Fragment2");
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 直接呼叫FragmentActivity1的靜態方法來做切換
            	Fragment1Activity.changeFragment(new Fragment1());
            }
        });
        return v;
    }
}