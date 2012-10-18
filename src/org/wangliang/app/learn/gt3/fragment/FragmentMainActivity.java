package org.wangliang.app.learn.gt3.fragment;


import org.wangliang.app.learn.R ;

import android.app.Activity ;
import android.os.Bundle ;
import android.support.v4.app.FragmentActivity ;
import android.view.Menu ;
import android.widget.ImageView ;
import android.widget.RadioGroup ;
import android.widget.RadioGroup.OnCheckedChangeListener ;
import android.widget.RelativeLayout ;
import android.widget.TabHost ;

import com.and.netease.utils.MoveBg ;

//http://stackoverflow.com/questions/8059378/android-how-to-interact-with-fragment-tabs
public class FragmentMainActivity extends FragmentActivity {

	RadioGroup radioGroup;
	ImageView img;
	TabHost tabHost;
	int startLeft;
	RelativeLayout bottom_layout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tabHost = (TabHost) findViewById(R.id.tabhost);
		bottom_layout = (RelativeLayout) findViewById(R.id.layout_bottom);

		radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
		radioGroup.setOnCheckedChangeListener(checkedChangeListener);

		img = new ImageView(this);
		img.setImageResource(R.drawable.tab_front_bg);
		bottom_layout.addView(img);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	// 当Tab发生变化时，改变tab的标签的显示图片
	private OnCheckedChangeListener checkedChangeListener = new OnCheckedChangeListener() {
		//Android Compatibility Package doesn't include Activity.getFragmentManager()
		//You will need to extend FragmentActivity instead of the normal Activity. Then you will be able to call getSupportFragmentManager() which works the same way as getFragmentManager().
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			Contentfragment fragment = (Contentfragment) getSupportFragmentManager().findFragmentById(R.id.content_fragment);
			TopBarFragment fragmentTopic = (TopBarFragment) getSupportFragmentManager()
					.findFragmentById(R.id.content_fragment_top);

			switch (checkedId) {

			case R.id.radio_news:
				fragment.changeContent(0);
				fragmentTopic.changeContent(0);

				tabHost.setCurrentTabByTag("news");
				MoveBg.moveFrontBg(img, startLeft, 0, 0, 0);
				startLeft = 0;
				break;
			case R.id.radio_topic:
				fragment.changeContent(1);
				fragmentTopic.changeContent(1);
				
				tabHost.setCurrentTabByTag("topic");
				MoveBg.moveFrontBg(img, startLeft, img.getWidth(), 0, 0);
				startLeft = img.getWidth();
				break;
			case R.id.radio_pic:
				fragment.changeContent(2);
				fragmentTopic.changeContent(2);
				
				tabHost.setCurrentTabByTag("picture");
				MoveBg.moveFrontBg(img, startLeft, img.getWidth() * 2, 0, 0);
				startLeft = img.getWidth() * 2;
				break;
			case R.id.radio_follow:
				fragment.changeContent(3);
				fragmentTopic.changeContent(3);
				
				tabHost.setCurrentTabByTag("follow");
				MoveBg.moveFrontBg(img, startLeft, img.getWidth() * 3, 0, 0);
				startLeft = img.getWidth() * 3;
				break;
			case R.id.radio_vote:
				fragment.changeContent(4);
				fragmentTopic.changeContent(4);
				
				tabHost.setCurrentTabByTag("vote");
				MoveBg.moveFrontBg(img, startLeft, img.getWidth() * 4, 0, 0);
				startLeft = img.getWidth() * 4;
				break;

			default:
				break;
			}
		}
	};
}
