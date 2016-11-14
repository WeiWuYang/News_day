package com.exmple.Service;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.model.User;
import com.example.model.mhandler_bean;
import com.example.news_day.MainActivity;
import com.example.news_day.MyApp;
import com.example.news_day.StartActivity;
import com.example.utils.Config;
import com.example.utils.MyShareperference;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import de.greenrobot.event.EventBus;

public class LogService {
	/**
	 * 1注册监听EventBus.getDefault().register(this);
	 * 2移除监听EventBus.getDefault().unregister(this);
	 * 3重写onEvenMainThread();
	 * 4在回调的地方post 进行回调EventBus.getDefault();
	 */
	
		public static void Log(final Activity activity){
			MyShareperference sp=new MyShareperference(activity);
			final String username = sp.getusername();
			final String userpwd = sp.getuserpwd();
			boolean getisoutapp = sp.getisoutapp();
			
			if(username.length()>0&&userpwd.length()>0&&getisoutapp==true){
				AsyncHttpClient ac=new AsyncHttpClient();
				RequestParams params=new RequestParams();
				params.put("ver", "1");
				params.put("device", "0");
				params.put("uid", username);
				params.put("pwd", userpwd);
				ac.get(Config.ip+Config.user_login, params, new AsyncHttpResponseHandler() {
					
					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						
						// TODO Auto-generated method stub
						String str=new String(arg2);
						try {
							JSONObject jsonobject=new JSONObject(str);
							int status = jsonobject.getInt("status");
							if(status==0){
								JSONObject data = jsonobject.getJSONObject("data");
								String token = data.getString("token");
								User u=new User();
								u.setToken(token);
								u.setUser_name(username);
								u.setPwd(userpwd);
								
								
								MyApp app=(MyApp)activity.getApplication();
								app.user=u;
								
								mhandler_bean event=new mhandler_bean();
								event.obj="登录成功";
								event.what=0;
								EventBus.getDefault().post(event);
							}else if(status==-1){
								mhandler_bean event=new mhandler_bean();
								event.obj="登录失败";
								event.what=1;
								EventBus.getDefault().post(event);
							}
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		}

	}

