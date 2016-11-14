package com.example.adapter;

import java.util.List;

import com.example.model.User;
import com.example.news_day.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UserinfoAdapter extends BaseAdapter {
  List<User> list;
  LayoutInflater layout;
	public UserinfoAdapter(List<User> list, LayoutInflater layout) {
	super();
	this.list = list;
	this.layout = layout;
}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View v, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View view = layout.inflate(R.layout.infos_item, null);
	TextView	tv_down=(TextView) view.findViewById(R.id.tv_down);
	String user = list.get(arg0).getUser_name();
	tv_down.setText(user);
		return v;
	}

}
