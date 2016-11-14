package com.example.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MyShareperference {
	private SharedPreferences sharedpreference;
	public   MyShareperference (Context context){
	sharedpreference=	context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		
	}
	//±£¥Ê’À∫≈
	public void setusername(String username){
		Editor edit = sharedpreference.edit();
		edit.putString("username",username );
		edit.commit();
	}
	//Ã·»°’À∫≈
	public String getusername(){
		return sharedpreference.getString("username",null);
	}
	//±£¥Ê√‹¬Î
	public void setuserpwd(String userpwd){
		Editor edit = sharedpreference.edit();
		edit.putString("userpwd", userpwd);
		edit.commit();
		
	}
	//ªÒ»°√‹¬Î
	public String getuserpwd(){
	return	sharedpreference.getString("userpwd", null);
	}
	public  void setisoutapp(boolean isoutapp){
		Editor edit = sharedpreference.edit();
		edit.putBoolean("isoutapp", isoutapp);
		edit.commit();
		
	}
	public boolean getisoutapp(){
		return sharedpreference.getBoolean("isoutapp", true);
	}
}
