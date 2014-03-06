package com.bluesunshine.doubanbook.view;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.bluesunshine.douban4droid.api.DoubanUsersAPI;
import com.bluesunshine.douban4droid.model.app.AccessToken;
import com.bluesunshine.douban4droid.model.app.DoubanException;
import com.bluesunshine.douban4droid.utils.Converters;
import com.bluesunshine.douban4droid.utils.RequestListener;
import com.bluesunshine.doubanbook.R;
import com.bluesunshine.doubanbook.sys.Defs;


public class MainActivity extends Activity {

	private AccessToken mAccessToken;
	private TextView mTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent intent = getIntent();
		String tokenJson = intent.getStringExtra(Defs.ACCESS_TOKEN_JSON_KEY);
		
		try {		
			mAccessToken = Converters.stringToAccessToken(tokenJson);
		} catch (DoubanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		this.mTextView = (TextView)findViewById(R.id.textView1);

		getUserInfo();
	}

	private void getUserInfo() {

		String accessToken = mAccessToken.getAccessToken();
		DoubanUsersAPI instance = new DoubanUsersAPI(accessToken);


		//instance.getLoggedInUserProfile(new RequestListener() {
		//instance.getUserProfileByUid("70860551",new RequestListener() {
		try {
			instance.searchUserProfile("rain",new RequestListener() {
				@Override
				public void onIOException(IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onError(DoubanException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onComplete(String response) {
					// TODO Auto-generated method stub
					final String res = response;
					runOnUiThread(new Runnable() {
						public void run() {
							mTextView.setText(res);		
						}
					});
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
