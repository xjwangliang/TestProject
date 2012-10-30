package com.androidengineer.antbuild;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

	private final static String TAG = "MainActivity";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        if (Config.LOGGING)
        {
    		Log.d(TAG, "[onCreate] Success");
        }
    }
}