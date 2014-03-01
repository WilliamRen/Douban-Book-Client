package com.bluesunshine.doubanbook.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.bluesunshine.doubanbook.R;
import com.bluesunshine.doubanbook.sys.Defs;
import com.bluesunshine.doubanbook.utils.HandlePreference;
import com.dongxuexidu.douban4j.constants.DefaultConfigs;
import com.dongxuexidu.douban4j.model.app.RequestGrantScope;
import com.dongxuexidu.douban4j.provider.OAuthDoubanProvider;
import com.dongxuexidu.douban4j.provider.WebViewLauncher;


/**
 * 
 * @author wenhao  <wenhao@leochin.com>
 * 
 * Mar 1, 2014  9:51:35 AM
 *
 */
public class LoginActivity extends Activity {
	
	private Button button;
	private OAuthDoubanProvider mOauth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		checkLoginStatus();
		
		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				initDoubanOauth();
			}
		});
	}
	

	private void checkLoginStatus(){
		
		HandlePreference hp = new HandlePreference(this, Defs.USER_SHARED_PREFERENCE);
		boolean flag = hp.containKey(Defs.ACCESS_TOKEN_JSON_KEY);
		
		if(flag){
			String response = hp.getString(Defs.ACCESS_TOKEN_JSON_KEY);
			
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			intent.putExtra(Defs.ACCESS_TOKEN_JSON_KEY, response);
			startActivity(intent);
			finish();
		}		
	}


	private void initDoubanOauth() {

		mOauth = new OAuthDoubanProvider();
		mOauth.setApiKey(DefaultConfigs.API_KEY);
		mOauth.setSecretKey(DefaultConfigs.SECRET_KEY);
		mOauth.setRedirectUrl(DefaultConfigs.ACCESS_TOKEN_REDIRECT_URL);
		mOauth.addScope(RequestGrantScope.BASIC_COMMON_SCOPE);

		String redirectUrl = mOauth.getGetCodeRedirectUrl();
		Intent intent = new Intent(this, WebViewLauncher.class);
		intent.putExtra(WebViewLauncher.INTENT_EXTRA_URL, redirectUrl);
		startActivityForResult(intent, WebViewLauncher.REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		String response = data
				.getStringExtra(WebViewLauncher.INTENT_EXTRA_TOKEN);
		
		HandlePreference hp = new HandlePreference(this, Defs.USER_SHARED_PREFERENCE);
		hp.putString(Defs.ACCESS_TOKEN_JSON_KEY, response);

		Intent intent = new Intent(LoginActivity.this, MainActivity.class);
		intent.putExtra(Defs.ACCESS_TOKEN_JSON_KEY, response);
		startActivity(intent);
		finish();

	}
}
