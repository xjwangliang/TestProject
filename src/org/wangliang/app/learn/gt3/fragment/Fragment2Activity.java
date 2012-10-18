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


public class Fragment2Activity extends FragmentActivity {
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

