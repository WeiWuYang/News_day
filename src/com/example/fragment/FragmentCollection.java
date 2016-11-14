package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.CommonAdapter;
import com.example.model.CollectionNews;
import com.example.model.News;
import com.example.news_day.MyApp;
import com.example.news_day.R;
import com.example.news_day.ShowNewsActivity;
import com.example.utils.LiteDb;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.internal.widget.AdapterViewCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 收藏
 * @author Administrator
 *
 */
public class FragmentCollection extends Fragment implements android.widget.AdapterView.OnItemLongClickListener,OnItemClickListener{
     @Override
    public void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    }
  
      private ListView collection_listview;
      private MyApp app;
	private List<CollectionNews> select;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	  View view = inflater.inflate(R.layout.fragment_collection, null);
    	  collection_listview = (ListView) view.findViewById(R.id.collection_listview);
    	  collection_listview.setOnItemLongClickListener(this);
    	  collection_listview.setOnItemClickListener(this);
    	  select = new ArrayList<CollectionNews>();
    	  app = (MyApp) getActivity().getApplication();
    	  // TODO Auto-generated method stub
    	 
    	return view;
    
      } 
      @Override
    public void onResume() {
    	// TODO Auto-generated method stub
    	  super.onResume();
    	  notifydata();
    	 
}
     private void notifydata() {
		// TODO Auto-generated method stub
       litedb = new LiteDb(getActivity());
       
       if(app.user==null){
    	   Toast.makeText(getActivity(), "登陆才能查看收藏", Toast.LENGTH_SHORT).show();
    	   return;
    	   }
//   	  select = litedb.select(CollectionNews.class);
   	select = litedb.getQueryByWhere(CollectionNews.class, "token=?", new String[]{app.user.getToken()});
   	  adapter = new CommonAdapter<CollectionNews>(getActivity(), select,
 				R.layout.news_item) {
 			@Override
 			public void setViewData(View view, CollectionNews item) {
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
   	
 			}
   };
   collection_listview.setAdapter(adapter);
	}

	int index=-1;
	private LiteDb litedb;
	private CommonAdapter<CollectionNews> adapter;
	
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		index=arg2;
		// TODO Auto-generated method stub
		Builder dialog = new AlertDialog.Builder(getActivity());
		dialog.setTitle("是否删除条目？");
		dialog.setPositiveButton("确定", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				CollectionNews news = select.get(index);
				
				litedb.deleteWhere(CollectionNews.class, "nid=?and token=?", new String []{news.getNid()+"",app.user.getToken()});
				adapter.notifyDataSetChanged();
				notifydata();
			}
		});
		dialog.setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		AlertDialog create = dialog.create();
		create.show();
		return false;
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		News news = select.get(arg2);
		String json = new Gson().toJson(news);
		Intent intent=new Intent(getActivity(),ShowNewsActivity.class);
		intent.putExtra("json", json);
        startActivity(intent);
	}
}