package com.bluesunshine.doubanbook.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class HandlePreference {

	private Context mContext;
	private SharedPreferences sp;
	
	public HandlePreference(Context context,String name) {

		this.mContext = context;
		sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
	}
	
	public void putString(String key, String value){
		
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public String getString(String key){
		return sp.getString(key, null);
	}
	
	public boolean containKey(String key){
				
		String value = getString(key);
		if(value != null){
			return true;
		}
		return false;
	}

}
