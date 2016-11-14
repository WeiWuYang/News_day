package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.adapter.CommonAdapter;
import com.example.model.CollectionNews;
import com.example.model.News;
import com.example.news_day.MainActivity;
import com.example.news_day.R;
import com.example.news_day.ShowNewsActivity;
import com.example.utils.Config;
import com.example.utils.MyDialog;
import com.example.utils.Requestuitls;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentNews extends Fragment implements OnItemClickListener{

	Handler mhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				MyDialog.dismiss();
				mList.clear();
				List<News> list = (List<News>) msg.obj;
				mList.addAll(list);
				MainActivity ma = (MainActivity) getActivity();
				ma.allnews = mList;
				adapter.notifyDataSetChanged();
				break;

			default:
				break;
			}
		};
	};
	private CommonAdapter<News> adapter;
	private ListView news_listview;
	public List<News> mList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		  
		View view = inflater.inflate(R.layout.fragment_news, null);
		news_listview = (ListView) view.findViewById(R.id.news_listview);
		news_listview.setOnItemClickListener(this);
		mList = new ArrayList<News>();
		adapter = new CommonAdapter<News>(getActivity(), mList,
				R.layout.news_item) {
			@Override
			public void setViewData(View view, News item) {
				// TODO Auto-generated method stub
				// super.setViewData(currentView, item);
				TextView tv_news_title = CommonAdapter.get(view,
						R.id.tv_news_title);
				TextView tv_new_context = CommonAdapter.get(view,
						R.id.tv_new_context);
				
				ImageView img_newsinfo= CommonAdapter.get(view, R.id.img_newsinfo);
				String icon = item.getIcon();
				DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true)
				.cacheOnDisc(true)
				.build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
				.threadPoolSize(3)	// default
				.threadPriority(Thread.NORM_PRIORITY - 1)	// default
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.memoryCacheSizePercentage(13) 	// default
				.defaultDisplayImageOptions(defaultOptions)
				.writeDebugLogs() 	// Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
				ImageLoader.getInstance().displayImage(item.getIcon(), img_newsinfo);
			
				tv_news_title.setText(item.getTitle());
				tv_new_context.setText(item.getSummary().trim());
//				if (item.getTitle().length() >= 15) {
//					tv_news_title.setText(item.getTitle().substring(0, 15)
//							+ "...");
//				} else {
//					tv_news_title.setText(item.getTitle());
//				}
//				if (item.getSummary().length() >= 18) {
//					tv_new_context.setText(item.getSummary().trim().substring(0, 18)
//							+ "...");
//				} else { 
//					tv_new_context.setText(item.getSummary().trim());
//				}
			}
		};
		news_listview.setAdapter(adapter);
		MyDialog.showDialog(getActivity());
		RequestParams params = new RequestParams();
		// ver=版本号&subid=分类名&dir=1&nid=新闻id&stamp=20140321&cnt=20
		params.put("ver", "0000000");
		params.put("subid", "1");
		params.put("dir", "1");
		params.put("nid", "1");
		params.put("stamp", "201609211");
	Requestuitls.getRequestuitls().getRequestArraydata(Config.new_list, params, mhandler, 1, new TypeToken<List<News>>() {});
	/*	AsyncHttpClient ac = new AsyncHttpClient();
		ac.get(Config.ip + Config.new_list, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						String str = new String(arg2);
						Gson gson = new Gson();
						try {
							JSONObject js = new JSONObject(str);
							String stuid = js.getString("status");
							JSONArray data = js.getJSONArray("data");
							List<News> mList = gson.fromJson(
									String.valueOf(data),
									new TypeToken<List<News>>() {
									}.getType());
							Message message = mhandler.obtainMessage();
							message.obj = mList;
							message.what = 1;
							mhandler.sendMessage(message);

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// news_listview.setAdapter(newsinfo);

					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub

					}
				});*/
		return view;

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
			News news = mList.get(arg2);
			String json = new Gson().toJson(news);
			
			Intent intent=new Intent(getActivity(),ShowNewsActivity.class);
			intent.putExtra("json", json);
	        startActivity(intent);
		}
		
	}

