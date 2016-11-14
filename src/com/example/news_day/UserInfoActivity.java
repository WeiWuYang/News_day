package com.example.news_day;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.View.CircularImage;
import com.example.adapter.CommonAdapter;
import com.example.model.LoginLogbean;
import com.example.model.Popuwindow_bean;
import com.example.news_day.R.layout;
import com.example.utils.Config;
import com.example.utils.MyDialog;
import com.example.utils.MyShareperference;
import com.example.utils.Requestuitls;
import com.example.utils.UserDb;
import com.example.utils.bitmap.CropImageActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.Window;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
 * 用户界面
 * @author Administrator
 *
 */
public class UserInfoActivity extends Activity implements OnClickListener ,OnLongClickListener{
	Handler mhandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				MyDialog.dismiss();
				home_username.setText(uid);
				home_jf.setText(integration+"");
				home_comnum.setText("跟贴数统计:"+commun);
				Popuwindow_bean pw=new Popuwindow_bean();
				pw.setName(((MyApp)getApplication()).user.getUser_name());
				pw.setPwd(((MyApp)getApplication()).user.getPwd());
				pw.setToken(((MyApp)getApplication()).user.getToken());
				pw.setIcon(portrait);
				UserDb ud=new UserDb(UserInfoActivity.this);
				List<Popuwindow_bean> where = ud.getQueryByWhere(Popuwindow_bean.class, "token=?", new String[]{((MyApp)getApplication()).user.getToken()});
				if (where.size()==0) {
					ud.Add(pw);
					
				}
				
				
				mlist.clear();
				List<LoginLogbean> list=(List<LoginLogbean>) msg.obj;
				mlist.addAll(list);
			
				adapter.notifyDataSetChanged();
				break;
			case 2:
				
				Toast.makeText(UserInfoActivity.this, "上传成功", 1).show();
				MyDialog.dismiss();
				Popuwindow_bean pw1=new Popuwindow_bean();
				pw1.setName(((MyApp)getApplication()).user.getUser_name());
				pw1.setPwd(((MyApp)getApplication()).user.getPwd());
				pw1.setToken(((MyApp)getApplication()).user.getToken());
				pw1.setIcon(portrait);
				UserDb ud1=new UserDb(UserInfoActivity.this);
				ud1.Add(pw1);
				String path=(String) msg.obj;
				Bitmap b = BitmapFactory.decodeFile(path);
				img_tx2.setImageBitmap(b);
				break;
			default:
				break;
			}
		};
	};
	private String explain;
	public static final String IMAGE_PATH = "Jokeep";
	private static String localTempImageFileName = "";
	private static final int FLAG_CHOOSE_IMG = 5;
	private static final int FLAG_CHOOSE_PHONE = 6;
	private static final int FLAG_MODIFY_FINISH = 7;
	public static final File FILE_SDCARD = Environment
			.getExternalStorageDirectory();
	public static final File FILE_LOCAL = new File(FILE_SDCARD, IMAGE_PATH);
	public static final File FILE_PIC_SCREENSHOT = new File(FILE_LOCAL,
			"images/screenshots");

	private String portrait;
	private String uid;
	private int integration;
	private int commun;
	private TextView home_username;
	private TextView home_jf;
	private TextView home_comnum;
	private List<LoginLogbean> mlist;
	private ListView user_listview;
	private CommonAdapter<LoginLogbean> adapter;
	private ImageView img_tx;
	private CircularImage img_tx2;
	private Dialog dialog;
      @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	((MyApp)getApplication()).Addactivity(this);
    	setContentView(R.layout.user_info_activity);
    
    	initHead();
    	initView();
    	
    	  
    }
      private void initView() {
		home_username = (TextView) findViewById(R.id.home_username);
    	home_jf = (TextView) findViewById(R.id.home_jf);
    	home_comnum = (TextView) findViewById(R.id.home_comnum);
    	user_listview = (ListView) findViewById(R.id.user_listview);
    	img_tx2 = (CircularImage) findViewById(R.id.img_tx);
    	img_tx2.setOnClickListener(this);
    	img_tx2.setOnLongClickListener(this);
    	
    	findViewById(R.id.out_app).setOnClickListener(this);
    	UserHome();
	}
	private void UserHome() {
		mlist=new ArrayList<LoginLogbean>();
		adapter = new CommonAdapter<LoginLogbean>(this, mlist, R.layout.loginlogbean){
			@Override
			public void setViewData(View currentView, LoginLogbean item) {
				TextView view = CommonAdapter.get(currentView, R.id.tv_adress);
				view.setText(item.getAdress()+"-"+item.getTime());
			};
		};
		user_listview.setAdapter(adapter);
		MyDialog.showDialog(this);
		AsyncHttpClient ac=new AsyncHttpClient();
		RequestParams params =new RequestParams();
		//ver=版本号&imei=手机标识符&token =用户令牌
		TelephonyManager Imei1 = ((TelephonyManager) this.getSystemService(TELEPHONY_SERVICE));
       String Imei=Imei1.getDeviceId();
       MyApp APP = (MyApp) UserInfoActivity.this.getApplication();
       String token = APP.user.getToken();
		params.put("ver", "1");
		params.put("imei", Imei);
		params.put("token", token);
			ac.get(Config.ip+Config.user_home, params, new AsyncHttpResponseHandler() {
			
		

			

			

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				String str=new String(arg2);
				try {
					JSONObject obj=new JSONObject(str);
					int i = obj.getInt("status");
					if(i==0){
						JSONObject jsonObject = obj.getJSONObject("data");
						//用户名字
						uid = jsonObject.getString("uid");
						//积分
						integration = jsonObject.getInt("integration");
						//跟贴数
						commun = jsonObject.getInt("comnum");
						portrait = jsonObject.getString("portrait");
						ImageLoader.getInstance().displayImage(portrait, img_tx2);
						
						//Picasso.with(UserInfoActivity.this).load(portrait).into(img_tx);

						//登录时间地点
						
						JSONArray list = jsonObject.getJSONArray("loginlog");
					//gson解析
						Gson gson=new Gson();
					 
						List<LoginLogbean> mlist = gson.fromJson(String.valueOf(list), new TypeToken<List<LoginLogbean>> (){}.getType());
					
						Message message = mhandler.obtainMessage();
						message.obj=mlist;
						message.what=1;
						mhandler.sendMessage(message);
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
	private void initHead() {
  		// TODO Auto-generated method stub
  		ImageView head_left = (ImageView) findViewById(R.id.head_left);
  		head_left.setVisibility(View.VISIBLE);
  		TextView	head_text = (TextView) findViewById(R.id.head_text);
  		head_text.setText("个人中心");
  		ImageView head_right = (ImageView) findViewById(R.id.head_right);
  		head_right.setVisibility(View.GONE);
  		head_left.setOnClickListener(this);

  	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.head_left){
			finish();
		}else if(v.getId()==R.id.out_app){
			MyApp application = (MyApp) getApplication();
			application.user=null;
			MyShareperference sp=new MyShareperference(UserInfoActivity.this);
			sp.setisoutapp(false);
			this.finish();
		}else if(v.getId()==R.id.img_tx){
			MyDialog.showimageDialog(this, portrait);
		
		}else if(v.getId()==R.id.bt_photoshop){
			dialog.dismiss();
			OppenCamera();
		}else if(v.getId()==R.id.bt_album){
			dialog.dismiss();
			OppenPhoto();
		}else if(v.getId()==R.id.bt_cannel){
			dialog.dismiss();
		}
	}
	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.img_tx){
			selectimg(this);
		}
		return true;
	}
	void selectimg(Context context){
		
		View v = getLayoutInflater().inflate(R.layout.imgs_select, null);
		v.findViewById(R.id.bt_photoshop).setOnClickListener(this);
    	v.findViewById(R.id.bt_album).setOnClickListener(this);
    	v.findViewById(R.id.bt_cannel).setOnClickListener(this);
		dialog = new Dialog(context,R.style.my_dialog_style);
		Window window = dialog.getWindow();
		WindowManager.LayoutParams LayoutParams=new LayoutParams();
		LayoutParams.width=WindowManager.LayoutParams.MATCH_PARENT;
		LayoutParams.height=WindowManager.LayoutParams.MATCH_PARENT;
		LayoutParams.gravity=Gravity.CENTER;
		dialog.setContentView(v, LayoutParams);
		dialog.show();
	}
	// 打开相机
		public void OppenCamera() {
			String status = Environment.getExternalStorageState();
			if (status.equals(Environment.MEDIA_MOUNTED)) {

				try {
					localTempImageFileName = "";
					localTempImageFileName = String.valueOf((new Date()).getTime())
							+ ".png";
					File filePath = FILE_PIC_SCREENSHOT;
					if (!filePath.exists()) {
						filePath.mkdirs();
					}
					Intent intent = new Intent(
							android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
					File f = new File(filePath, localTempImageFileName);
					// localTempImgDir和localTempImageFileName是自己定义的名字
					Uri u = Uri.fromFile(f);
					intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
					startActivityForResult(intent, FLAG_CHOOSE_PHONE);

				} catch (Exception e) {
					//
				}
			}

		}
		// 打开相册
		public void OppenPhoto() {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_PICK);
			intent.setType("image/*");
			startActivityForResult(intent, FLAG_CHOOSE_IMG);

		}

	
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {

			if (requestCode == FLAG_CHOOSE_IMG && resultCode == RESULT_OK) {

				if (data != null) {
					Uri uri = data.getData();
					if (!TextUtils.isEmpty(uri.getAuthority())) {
						Cursor cursor = getContentResolver().query(uri,
								new String[] { MediaStore.Images.Media.DATA },
								null, null, null);
						if (null == cursor) {

							return;
						}
						cursor.moveToFirst();
						String path = cursor.getString(cursor
								.getColumnIndex(MediaStore.Images.Media.DATA));
						cursor.close();

						Intent intent = new Intent(this, CropImageActivity.class);
						intent.putExtra("path", path);
						startActivityForResult(intent, FLAG_MODIFY_FINISH);
					} else {

						Intent intent = new Intent(this, CropImageActivity.class);
						intent.putExtra("path", uri.getPath());
						startActivityForResult(intent, FLAG_MODIFY_FINISH);
					}
				}
			} else if (requestCode == FLAG_CHOOSE_PHONE && resultCode == RESULT_OK) {
				File f = new File(FILE_PIC_SCREENSHOT, localTempImageFileName);

				Intent intent = new Intent(this, CropImageActivity.class);
				intent.putExtra("path", f.getAbsolutePath());
				startActivityForResult(intent, FLAG_MODIFY_FINISH);
			} else if (requestCode == FLAG_MODIFY_FINISH && resultCode == RESULT_OK) {
				if (data != null) {
					final String path = data.getStringExtra("path");

					Bitmap b = BitmapFactory.decodeFile(path);
				
					MyApp app=(MyApp) getApplication();
					AsyncHttpClient ac=new AsyncHttpClient();
					RequestParams params=new RequestParams();
					params.put("token", app.user.getToken());
					try {
						params.put("portrait",new File(path) );
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ac.post(Config.ip+Config.user_image, params, new AsyncHttpResponseHandler() {
						
				//		private String explain;

						@Override
						public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
							// TODO Auto-generated method stub
							String str=new String(arg2);
							try {
								JSONObject jsonobj=new JSONObject(str);
							     int i = jsonobj.getInt("status");
							     if(i==0){
							    	 JSONObject object = jsonobj.getJSONObject("data");
							    	 int result = object.getInt("result");
							    	 explain = object.getString("explain");
							    	 if(result==0){
							    		 Message message = mhandler.obtainMessage();
							    		 message.obj=path;
							    		 message.what=2;
							    		 mhandler.sendMessage(message);
							    		 
							    	 }else {
							    		 Toast.makeText(UserInfoActivity.this, explain, 1).show();
							    	 }
							    	 
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
				//	img_tx2.setImageBitmap(b);

				}
			}
		}

}
