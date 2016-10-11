package com.lcwang.androidviews;

import java.util.ArrayList;
import java.util.List;

import com.dsw.viewindicator.R;
import com.lcwang.androidviews.ViewPagerBarnner.ViewPagerClick;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		ViewPagerBarnner viewPagerBarnner = (ViewPagerBarnner) findViewById(R.id.viewPager);
		List<String> url = new ArrayList<String>();
		url.add("http://img2.3lian.com/2014/f7/5/d/22.jpg");
		url.add("http://img4.ph.126.net/lxVFFix1f-a9g4epCoOIkg==/1051309038031652744.jpg");
		url.add("http://pic1.nipic.com/2009-02-20/2009220135032130_2.jpg");
		url.add("http://pic18.nipic.com/20111226/8827553_194814293383_2.jpg");
		url.add("http://pic16.nipic.com/20110906/128199_105212458000_2.jpg");
		url.add("http://s1.dwstatic.com/group1/M00/9A/0D/710b5a81052a7617a0be5b6e6eafc2a6.jpg");
		url.add("http://img4.ph.126.net/DhhyirIMqtvd-Uvx7dLMRA==/1017250565849577544.jpg");
		url.add("http://img1.ph.126.net/JNoKBdmil8XoNOHer-phzw==/3902369077217060260.jpg");
		url.add("http://pic.duowan.com/tx2/1205/201189276629/201189504837.jpg");
		url.add("http://img1.ph.126.net/zS3zi53eUOKeRQ8wcgkJKQ==/867505878322422403.jpg");
		viewPagerBarnner.addImageUrls(url);
		viewPagerBarnner.setViewPagerClick(new ViewPagerClick() {
			
			@Override
			public void viewPagerOnClick(View view) {
				Toast.makeText(MainActivity.this, view.getTag().toString(), Toast.LENGTH_SHORT).show();
			}
		});
	}
}
