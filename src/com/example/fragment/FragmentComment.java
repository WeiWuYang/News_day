package com.example.fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.adapter.CommonAdapter;
import com.example.model.CommentInfo;
import com.example.model.News;
import com.example.news_day.R;
import com.example.utils.Config;
import com.example.utils.Requestuitls;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentComment extends Fragment {
	Handler mhandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 9:
				list.clear();
				List<CommentInfo> ls=(List<CommentInfo>) msg.obj;
				list.addAll(ls);
				adapter.notifyDataSetChanged();
				break;

			default:
				break;
			}
		};
	};
	private CommonAdapter<CommentInfo> adapter;
	private List<CommentInfo> list;
      @Override
    public void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    }
      @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	  View v = inflater.inflate(R.layout.fragment_comment, null);
    	  ListView lv_comment=(ListView) v.findViewById(R.id.lv_comment);
    	  list = new  ArrayList<CommentInfo>();
    	  adapter = new CommonAdapter<CommentInfo>(getActivity(), list, R.layout.news_item){
    		  @Override
    		public void setViewData(View currentView, CommentInfo item) {
    			// TODO Auto-generated method stub
    			super.setViewData(currentView, item);
    			ImageView img_newsinf=CommonAdapter.get(currentView, R.id.img_newsinfo);
    			TextView tv_news_title=CommonAdapter.get(currentView, R.id.tv_news_title);
    			TextView tv_new_context=CommonAdapter.get(currentView, R.id.tv_new_context);
//    			ImageLoader.getInstance().displayImage(item.getPortrait(), img_newsinf);
    			Picasso.with(getActivity()).load(item.getPortrait()).into(img_newsinf);
    			tv_news_title.setText(item.getUid());
    			tv_new_context.setText(item.getStamp());
    		}
    	  };
    	  lv_comment.setAdapter(adapter);
    	  RequestParams params=new RequestParams();
    	  params.put("ver", 1);
    	  params.put("nid", 20);
    	  params.put("type", 1);
    	  params.put("stamp", 20160617);
    	  params.put("dir", 0);
    	  params.put("cid", 111);
    	  Requestuitls.getRequestuitls().getRequestArraydata(Config.cmt_list, params, mhandler, 9, new TypeToken<List<CommentInfo>>(){});
    	/*  AsyncHttpClient ac=new AsyncHttpClient();
    	  RequestParams params=new RequestParams();
//    	  ver=1&nid=20&type=1&stamp=20160617&dir=0&cid=111
    	  params.put("ver", 1);
    	  params.put("nid", 20);
    	  params.put("type", 1);
    	  params.put("stamp", 20160617);
    	  params.put("dir", 0);
    	  params.put("cid", 111);
    	  ac.get(Config.ip+Config.cmt_list, params, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				try {
					JSONObject json=new JSONObject(new String(arg2));
					int status = json.getInt("status");
					if (status==0) {
						JSONArray jsonArray = json.getJSONArray("data");
						Type type= new TypeToken<List<CommentInfo>>(){}.getType();
						List<CommentInfo> list=new Gson().fromJson(jsonArray.toString(), type);
						Message message = mhandler.obtainMessage();
						message.obj=list;
						message.what=9;
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
		});*/
    	return v;
    
      }
}
