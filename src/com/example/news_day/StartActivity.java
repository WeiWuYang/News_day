package com.example.news_day;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.adapter.StartViewPagerAdapter;
import com.example.model.Popuwindow_bean;
import com.example.model.User;
import com.example.utils.Config;
import com.example.utils.MyDialog;
import com.example.utils.MyShareperference;
import com.example.utils.UserDb;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class StartActivity extends Activity {
	Handler mhandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				MyDialog.dismiss();
				Intent intent = new Intent(StartActivity.this,
						MainActivity.class);
				startActivity(intent);
//				�������
//				UserDb usd = new UserDb(StartActivity.this);
//				usd.deleteWhere(Popuwindow_bean.class, "token=?", new String[]{token});

				StartActivity.this.finish();
				break;

			default:
				break;
			}
		};
	};
	private SharedPreferences sp;
	ViewPager vp;
	private ImageView start_img;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		((MyApp)getApplication()).Addactivity(this);
		setContentView(R.layout.startactivity);
		vp = (ViewPager) findViewById(R.id.start_vp);
		start_img = (ImageView) findViewById(R.id.start_img);
		// �������ݿ� ���� Ȩ��
		sp = this.getSharedPreferences("sp", Context.MODE_PRIVATE);
         
		initView();

	}
	private String token;


	private void initView() {
		// TODO Auto-generated method stub
		// �ж��ǲ��ǵ�һ�ν���
		int getisstart = getisstart();
		// �ǵ�һ�ν���
		if (getisstart == 0) {
			List<View> loaddata = loaddata();
			vp.setVisibility(View.VISIBLE);
			start_img.setVisibility(View.GONE);

			StartViewPagerAdapter dapter = new StartViewPagerAdapter(loaddata);
			vp.setAdapter(dapter);
			// ����ViewPager���һҳ��ת
			View view = views.get(res.length - 1);
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mhandler.sendEmptyMessage(1);
				}
			});
			// ���ݿ������ݿ⸳ֵ
			upData();
			// ���ǵ�һ�ν��룬ֱ����ת��mhandler����Ϣ
		} else {
			
			MyDialog.showDialog(this);
			MyShareperference sp=new MyShareperference(StartActivity.this);		
			if(sp.getisoutapp()){
				final String user = sp.getusername();
			    final String password = 	sp.getuserpwd();
			    if(user == null){
			    	Intent it = new Intent(StartActivity.this,MainActivity.class);
			    	startActivity(it);
			    	return;
			    	//�߼��д�
			    }
				AsyncHttpClient ac = new AsyncHttpClient();
				RequestParams params = new RequestParams();
				params.put("ver", "1");
				params.put("device", "0");
				params.put("uid", user);
				params.put("pwd", password);
				ac.get(Config.ip + Config.user_login, params,
						new AsyncHttpResponseHandler() {

							
							@Override
							public void onSuccess(int arg0, Header[] arg1,
									byte[] arg2) {
								// TODO Auto-generated method stub
								String str = new String(arg2);
								try {
									JSONObject obj = new JSONObject(str);
									int i = obj.getInt("status");
									if (i == 0) {
										JSONObject jsonObject = obj
												.getJSONObject("data");
										token = jsonObject
												.getString("token");
										User u = new User();
										u.setToken(token);
										u.setUser_name(user);
										u.setPwd(password);
										MyApp application = (MyApp) StartActivity.this
												.getApplication();
										application.user = u;
										mhandler.sendEmptyMessage(1);
										
									} else if (i == -1) {

										
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

							@Override
							public void onFailure(int arg0, Header[] arg1,
									byte[] arg2, Throwable arg3) {
								// TODO Auto-generated method stub

							}
						});
			}else {
				mhandler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						mhandler.sendEmptyMessage(1);
					}
				}, 2000);
				mhandler.sendEmptyMessage(1);
			}

			}
			
	}

	// ����ViewPagerͼƬ
	int[] res = new int[] { R.drawable.small, R.drawable.welcome,
			R.drawable.bd, R.drawable.wy };
	private List<View> views;

	public List<View> loaddata() {
		views = new ArrayList<View>();
		for (int i = 0; i < res.length; i++) {
			ImageView img = new ImageView(this);
			// ����img����
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			img.setLayoutParams(params);
			img.setScaleType(ScaleType.FIT_XY);
			// img��ֵ
			img.setImageResource(res[i]);
			// vies���
			views.add(img);
		}
		return views;
	}

	public int getisstart() {
		return sp.getInt("isstart", 0);
	}

	public void upData() {
		Editor edit = sp.edit();
		edit.putInt("isstart", 1);
		edit.commit();
	}
}
