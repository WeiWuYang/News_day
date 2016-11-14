package com.example.news_day;

import java.util.List;

import com.example.model.CollectionNews;
import com.example.model.News;
import com.example.model.User;
import com.example.utils.LiteDb;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowNewsActivity extends Activity implements OnClickListener {
  private WebView webview;
private News news;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	((MyApp)getApplication()).Addactivity(this);
	setContentView(R.layout.show_news_activity);
	iniview();
	initHead();
	String json = getIntent().getStringExtra("json");
	Gson gson = new Gson();
    news = gson.fromJson(json, News.class);
    webview.loadUrl(news.getLink());
    //自适应屏幕
    webview.getSettings().setUseWideViewPort(true);
    webview.getSettings().setLoadWithOverviewMode(true);
	
}
  private void iniview() {
	webview = (WebView) findViewById(R.id.webview_news);
}
private void initHead() {
		// TODO Auto-generated method stub
		ImageView head_left = (ImageView) findViewById(R.id.head_left);
		head_left.setVisibility(View.VISIBLE);
		TextView	head_text = (TextView) findViewById(R.id.head_text);
		head_text.setText("咨询");
		ImageView head_right = (ImageView) findViewById(R.id.head_right);
		head_right.setVisibility(View.VISIBLE);
		head_right.setImageResource(R.drawable.plus);
		head_left.setOnClickListener(this);
		head_right.setOnClickListener(this);
	}
@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	if(v.getId()==R.id.head_left){
		this.finish();
	}else if(v.getId()==R.id.head_right){
		CollectionNews ns=new CollectionNews();
	   ns.setIcon(news.getIcon());
	   ns.setLink(news.getLink());
	   ns.setNid(news.getNid());
	   ns.setStamp(news.getStamp());
	   ns.setSummary(news.getSummary());
	   ns.setTitle(news.getTitle());
	   ns.setType(news.getType());
	MyApp app=   (MyApp) this.getApplication();
	User user = app.user;
	if(user==null){
		Toast.makeText(this, "没登录不能收藏", 1).show();
		return;
	}else{
	   ns.setToken(user.getToken());
	   LiteDb litedb=new LiteDb(this);
       List<CollectionNews>	list=    litedb.getQueryByWhere(CollectionNews.class, "nid=? and token = ?", new String[]{news.getNid()+"",app.user.getToken()});
	   if(list!=null&&list.size()>0){
		Toast.makeText(this, "已收藏  不能重复收藏", 1).show();
	   }else{
		   litedb.Add(ns);
		Toast.makeText(this, "收藏成功", 1).show();
	}
	}
	}
}
}
