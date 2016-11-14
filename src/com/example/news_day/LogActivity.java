package com.example.news_day;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.adapter.CommonAdapter;

import com.example.model.Popuwindow_bean;
import com.example.model.User;
import com.example.utils.Config;
import com.example.utils.MyDialog;
import com.example.utils.MyShareperference;
import com.example.utils.UserDb;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.media.JetPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

//µÇÂ½Ò³Ãæ
public class LogActivity extends Activity implements OnClickListener {
	Handler mhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				MyDialog.dismiss();

				Intent intent = new Intent(LogActivity.this,
						UserInfoActivity.class);
				startActivity(intent);
				finish();
				break;
			case 1:
				MyDialog.dismiss();
				Toast.makeText(LogActivity.this, "ÕËºÅ»òÃÜÂë´íÎó", 1).show();

				break;
			case 2:
				MyDialog.dismiss();

				Toast.makeText(LogActivity.this, explain, 1).show();

				state = 2;
				ll_regist.setVisibility(View.GONE);
				ll_log.setVisibility(View.VISIBLE);
				ll_back_pwd.setVisibility(View.GONE);
				head_text.setText("µÇÂ¼");
				break;
			case 3:
				MyDialog.dismiss();
				Toast.makeText(LogActivity.this, explain, 1).show();
				state = 2;
				ll_regist.setVisibility(View.GONE);
				ll_log.setVisibility(View.VISIBLE);
				ll_back_pwd.setVisibility(View.GONE);
				head_text.setText("µÇÂ¼");
				break;
			default:
				break;
			}
		};
	};

	private EditText username;
	private EditText pwd;
	private Button bt_zc;
	private View ll_log;
	private View ll_regist;
	// ×¢²á 0 Íü¼ÇÃÜÂë 1 µÇÂ½ 2
	int state = 0;
	private TextView head_text;
	private EditText regist_username;
	private EditText regist_pwd;
	private CheckBox log_cb;
	private EditText email;
	private String explain;
	private EditText back_yx;
	private View ll_back_pwd;

	private ImageView img_down;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		((MyApp) getApplication()).Addactivity(this);
		setContentView(R.layout.log_activity);
		initHead();
		initView();
	}

	private void initView() {
		bt_zc = (Button) findViewById(R.id.bt_zc);
		bt_zc.setOnClickListener(this);
		ll_log = findViewById(R.id.ll_log);
		ll_regist = findViewById(R.id.ll_regist);
		email = (EditText) findViewById(R.id.regist_yx);
		username = (EditText) findViewById(R.id.et_username);
		pwd = (EditText) findViewById(R.id.et_pwd);
		img_down = (ImageView) findViewById(R.id.img_down);
		img_down.setOnClickListener(this);
		regist_username = (EditText) findViewById(R.id.regist_username);
		regist_pwd = (EditText) findViewById(R.id.regist_pwd);
		log_cb = (CheckBox) findViewById(R.id.log_cb);
		back_yx = (EditText) findViewById(R.id.back_yx);
		ll_back_pwd = findViewById(R.id.ll_back_pwd);
		findViewById(R.id.back_pwd).setOnClickListener(this);
		findViewById(R.id.forget_pwd).setOnClickListener(this);
		findViewById(R.id.regist).setOnClickListener(this);
		findViewById(R.id.bt_login).setOnClickListener(this);
		findViewById(R.id.log_cb).setOnClickListener(this);

	}

	private void initHead() {
		// TODO Auto-generated method stub
		ImageView head_left = (ImageView) findViewById(R.id.head_left);
		head_left.setVisibility(View.VISIBLE);
		head_text = (TextView) findViewById(R.id.head_text);
		head_text.setText("µÇÂ½");
		ImageView head_right = (ImageView) findViewById(R.id.head_right);
		head_right.setVisibility(View.GONE);
		head_left.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.head_left) {
			if (state == 0 || state == 1) {
				state = 2;
				ll_back_pwd.setVisibility(View.GONE);
				ll_regist.setVisibility(View.GONE);
				ll_log.setVisibility(View.VISIBLE);
				head_text.setText("µÇÂ¼");
			} else if (state == 2) {
				finish();
			}

		} else if (v.getId() == R.id.bt_login) {

			log();
		} else if (v.getId() == R.id.bt_zc) {
			state = 0;
			ll_back_pwd.setVisibility(View.GONE);
			ll_regist.setVisibility(View.VISIBLE);
			ll_log.setVisibility(View.GONE);
			head_text.setText("×¢²á");
		} else if (v.getId() == R.id.regist) {
			regist();
		} else if (v.getId() == R.id.forget_pwd) {
			state = 1;
			ll_regist.setVisibility(View.GONE);
			ll_log.setVisibility(View.GONE);
			ll_back_pwd.setVisibility(View.VISIBLE);
			head_text.setText("ÕÒ»ØÃÜÂë");
		} else if (v.getId() == R.id.back_pwd) {
			ForgetPassword();
		} else if (v.getId() == R.id.img_down) {
			// µÇÂ½ÏÂÀ­¿ò
			Dropbox();
		}
	}

	int index = 0;

	private CommonAdapter<Popuwindow_bean> adapter;

	private PopupWindow pw;

	private List<Popuwindow_bean> select;

	private void Dropbox() {
		// TODO Auto-generated method stub
		if (index==0) {
			int index = 1;
			img_down.setImageResource(R.drawable.arrow_up);
			showpopuwidow();
		}else {
			index=0;
			img_down.setImageResource(R.drawable.arrow_down);
			pw.dismiss();
		}
		
		
	}

	private void showpopuwidow() {
		// TODO Auto-generated method stub
		ListView lv= new ListView(this);
		UserDb db = new UserDb(this);
		select = db.select(Popuwindow_bean.class);
		adapter = new CommonAdapter<Popuwindow_bean>(this, select,
				R.layout.show_popuwidow) {
			@Override
			public void setViewData(View v, Popuwindow_bean item) {
				// TODO Auto-generated method stub
				super.setViewData(v, item);
				ImageView img_popu = CommonAdapter.get(v, R.id.img_popu);
				TextView tv_popu = CommonAdapter.get(v, R.id.tv_popu);
				Picasso.with(LogActivity.this).load(item.getIcon())
						.into(img_popu);
				tv_popu.setText(item.getName());
  
  
			}
		};
		lv.setAdapter(adapter);
		pw = new PopupWindow(lv,username .getWidth(),300);
		//ÉèÖÃ±ßÔµ¹Ø±Õ
		pw.setOutsideTouchable(true);
		//ÉèÖÃ½¹µã
		pw.setFocusable(true);
		//ÉèÖÃ±³¾°Í¼Æ¬ 
		pw.setBackgroundDrawable(getResources().getDrawable(R.drawable.aaaaaaaaa));
//	ÉèÖÃ×ø±ê
		pw.showAsDropDown(username, 5, -5);
		//ListViewµÄ¼àÌý
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Popuwindow_bean pp=new Popuwindow_bean();
				Popuwindow_bean bean = select.get(position);
				username.setText(bean.getName());
				pwd.setText(bean.getPwd());
				index=0;
				img_down.setImageResource(R.drawable.arrow_down);
				pw.dismiss();
			}
			
		});
		pw.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				index=0;
				img_down.setImageResource(R.drawable.arrow_down);
				pw.dismiss();
			}
		});
	}
   
	private void ForgetPassword() {
		// TODO Auto-generated method stub
		String email = back_yx.getText().toString();
		if (email.length() <= 0) {
			Toast.makeText(this, "ÓÊÏä²»ÄÜÎª¿Õ", 1).show();
			return;
		}
		MyDialog.showDialog(this);
		AsyncHttpClient ac = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ver", 1);
		params.put("email", email);
		ac.get(Config.ip + Config.user_forgetpass, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						String str = new String(arg2);
						try {
							JSONObject obj = new JSONObject(str);
							int status = obj.getInt("status");
							if (status == 0) {
								JSONObject jsonObject = obj
										.getJSONObject("data");
								explain = jsonObject.getString("explain");
								int result = jsonObject.getInt("result");
								if (result == 0) {
									mhandler.sendEmptyMessage(3);
								} else {
									Toast.makeText(LogActivity.this, explain, 1)
											.show();
									MyDialog.dismiss();
								}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub

					}
				});
	}

	private void regist() {
		// TODO Auto-generated method stub
		boolean checked = log_cb.isChecked();
		if (!checked) {
			Toast.makeText(this, "·þÎñÌõ¿î±ØÐë±»Ñ¡ÖÐ", 1).show();
		}
		String Email = email.getText().toString();
		final String user = regist_username.getText().toString();
		final String pwd = regist_pwd.getText().toString();
		if (user.length() <= 0 || pwd.length() <= 0 || Email.length() <= 0) {
			Toast.makeText(this, "ÓÊÏäÕËºÅÃÜÂë²»ÄÜÎª¿Õ", 1).show();
			return;
		}
		MyDialog.showDialog(this);
		AsyncHttpClient ac = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("uid", user);
		params.put("pwd", pwd);
		params.put("email", Email);
		params.put("ver", "1");
		ac.get(Config.ip + Config.user_register, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						String str = new String(arg2);
						try {
							JSONObject jsonobj = new JSONObject(str);
							int status = jsonobj.getInt("status");
							if (status == 0) {
								JSONObject obj = jsonobj.getJSONObject("data");
								explain = obj.getString("explain");
								int Result = obj.getInt("result");
								if (Result == 0) {
									mhandler.sendEmptyMessage(2);
								} else {
									Toast.makeText(LogActivity.this, explain, 1)
											.show();
									MyDialog.dismiss();
								}
								// User u=new User();
								// u.setToken(token);
								// u.setUser_name(user);
								// u.setPwd(pwd);
								// MyApp application=(MyApp)
								// LogActivity.this.getApplication();
								// application.user=u;

								// Message message = mhandler.obtainMessage();
								// message.obj=explain;
								// message.what=2;
								// mhandler.sendMessage(message);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub

					}
				});
	}

	private void log() {
		// TODO Auto-generated method stub
		// Toast.makeText(this, "ÔËÐÐµ½´Ë´¦1", 1).show();
		final String user = username.getText().toString();
		final String password = pwd.getText().toString();
		if (user.length() <= 0 || password.length() <= 0) {
			return;
		} else {
			MyDialog.showDialog(this);
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
									String token = jsonObject
											.getString("token");
									User u = new User();
									u.setToken(token);
									u.setUser_name(user);
									u.setPwd(password);
									MyApp application = (MyApp) LogActivity.this
											.getApplication();
									application.user = u;
									MyShareperference sp = new MyShareperference(
											LogActivity.this);
									sp.setusername(user);
									sp.setuserpwd(password);
									sp.setisoutapp(true);
									mhandler.sendEmptyMessage(0);
								} else if (i == -1) {

									mhandler.sendEmptyMessage(1);
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
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == 4) {
			if (state == 0 || state == 1) {
				state = 2;
				ll_back_pwd.setVisibility(View.GONE);
				ll_regist.setVisibility(View.GONE);
				ll_log.setVisibility(View.VISIBLE);
				head_text.setText("µÇÂ¼");
			} else if (state == 2) {
				finish();
			}
		}
		return true;

	}
}
