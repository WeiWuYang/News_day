package com.example.utils;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Requestuitls<T> {
	private AsyncHttpClient ac;
	private Gson gson;
	static Requestuitls request = null;

	public static Requestuitls getRequestuitls() {
		if (request == null) {
			return request = new Requestuitls();
		} else {
			return request;
		}
	}

	private Requestuitls() {
		ac = new AsyncHttpClient();
		gson = new Gson();
	}

	public void getRequestdata(String url, RequestParams params,
			final Handler mhandler, final int requestCode, final Class c) {
		ac.get(Config.ip + url, params, new AsyncHttpResponseHandler() {

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				try {
					JSONObject josn = new JSONObject(new String(arg2));
					int status = josn.getInt("status");
					if (status == 0) {
						JSONObject data = josn.getJSONObject("data");

						Object json = gson.fromJson(data.toString(), c);
						Message message = mhandler.obtainMessage();
						message.obj = json;
						message.what = requestCode;
						mhandler.sendMessage(message);
					} else {
						mhandler.sendEmptyMessage(status);
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

	public void postRequestdata(String url, RequestParams params,
			final Handler mhandler, final int requestCode, final Class c) {
		ac.post(Config.ip + url, params, new AsyncHttpResponseHandler() {
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				try {
					JSONObject josn = new JSONObject(new String(arg2));
					int status = josn.getInt("status");
					if (status == 0) {
						JSONObject data = josn.getJSONObject("data");
						Object json = new Gson().fromJson(data.toString(), c);
						Message message = mhandler.obtainMessage();
						message.obj = json;
						message.what = requestCode;
						mhandler.sendMessage(message);
					} else {
						mhandler.sendEmptyMessage(status);
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

	public void getRequestArraydata(String url, RequestParams params,
			final Handler mhandler, final int requestCode, final TypeToken<T> c) {
		ac.get(Config.ip + url, params, new AsyncHttpResponseHandler() {
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				try {
					JSONObject josn = new JSONObject(new String(arg2));
					int status = josn.getInt("status");
					if (status == 0) {
						JSONArray jsonArray = josn.getJSONArray("data");
						Object json = new Gson().fromJson(jsonArray.toString(),
								c.getType());
						Message message = mhandler.obtainMessage();
						message.obj = json;
						message.what = requestCode;
						mhandler.sendMessage(message);
					} else {
						mhandler.sendEmptyMessage(status);
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

	public void postRequestArraydata(String url, RequestParams params,
			final Handler mhandler, final int requestCode, final TypeToken<T> c) {
		ac.post(Config.ip + url, params, new AsyncHttpResponseHandler() {
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				try {
					JSONObject josn = new JSONObject(new String(arg2));
					int status = josn.getInt("status");
					if (status == 0) {
						JSONArray jsonArray = josn.getJSONArray("data");
						Object json = new Gson().fromJson(jsonArray.toString(),
								c.getType());
						Message message = mhandler.obtainMessage();
						message.obj = json;
						message.what = requestCode;
						mhandler.sendMessage(message);
					} else {
						mhandler.sendEmptyMessage(status);
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
}
