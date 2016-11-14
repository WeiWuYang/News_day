package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.CommonAdapter;
import com.example.adapter.ImgAdapter;
import com.example.model.News;
import com.example.news_day.MainActivity;
import com.example.news_day.R;
import com.example.news_day.ShowNewsActivity;
import com.google.gson.Gson;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentImg extends Fragment implements OnItemClickListener {
	private ListView img_listview;

	private List<News> mList;

	private ImgAdapter adapter;

	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stubl
		super.onCreate(savedInstanceState);
	}

@Override
public View onCreateView(LayoutInflater inflater,
		@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	
	
	View view = inflater.inflate(R.layout.fragment_img, null);
	img_listview = (ListView) view.findViewById(R.id.img_listview);
	img_listview.setOnItemClickListener(this);
	
//	mList = new ArrayList<News>();
	
	// FragmentNews fragmentnews = activity.fragmentnews;
	//List<News> mList = fragmentnews.mList;
	MainActivity activity = (MainActivity) getActivity();
	mList = activity.allnews;
	adapter = new ImgAdapter(mList, getActivity());
	img_listview.setAdapter(adapter);
	return view;

}
		
	

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		News news = (News) adapter.getItem(arg2);
		String json = new Gson().toJson(news);
		Intent intent = new Intent(getActivity(), ShowNewsActivity.class);
		intent.putExtra("json", json);
		startActivity(intent);
	}

}
