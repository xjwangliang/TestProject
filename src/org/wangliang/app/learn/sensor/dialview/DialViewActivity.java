package org.wangliang.app.learn.sensor.dialview;

import org.wangliang.app.learn.R ;

import android.app.Activity ;
import android.os.Bundle ;
import android.widget.TextView ;

public class DialViewActivity extends Activity implements DialModel.Listener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dial_view);
        
        DialView dial = (DialView) findViewById(R.id.dial);
        dial.getModel().addListener(this);
    }

	@Override
	public void onDialPositionChanged(DialModel sender, int nicksChanged) {
		TextView text = (TextView) findViewById(R.id.text);
		text.setText(sender.getCurrentNick() + "");
	}
}