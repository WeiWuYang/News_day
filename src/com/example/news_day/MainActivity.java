package com.example.news_day;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.MyFragmentAdapter;
import com.example.fragment.FragmentCollection;
import com.example.fragment.FragmentComment;
import com.example.fragment.FragmentImg;
import com.example.fragment.FragmentNews;
import com.example.model.News;

import android.os.Bundle;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements OnClickListener {
	ViewPager vp;
	public FragmentNews fragmentnews;
	public List<News> allnews;
	private ViewPager home_vp;
	private List<Fragment> fragments;
	private RadioGroup home_rg;
	private FragmentManager fm;
	private ImageView head_left;
	private TextView head_text;
	private ImageView head_right;
	String str[] = new String[] { "新闻", "收藏", "跟帖", "图片" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((MyApp)getApplication()).Addactivity(this);
		setContentView(R.layout.activity_main);
		allnews = new ArrayList<News>();
		getFragments();
		initView();
		initHead();
	}

	private void initHead() {
		// TODO Auto-generated method stub
		head_left = (ImageView) findViewById(R.id.head_left);
		head_left.setVisibility(View.GONE);
		head_text = (TextView) findViewById(R.id.head_text);
		head_text.setText(str[0]);
		head_right = (ImageView) findViewById(R.id.head_right);
		head_right.setOnClickListener(this);
		
		fragmentnews = new FragmentNews();

	}

	private void initView() {
		home_vp = (ViewPager) findViewById(R.id.home_vp);
		home_rg = (RadioGroup) findViewById(R.id.home_rg);
		home_rg.check(R.id.rb_news);
		// 监听ViewPager
		home_vp.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int index) {
				// TODO Auto-generated method stub
				if (index == 0) {
					home_rg.check(R.id.rb_news);
				} else if (index == 1) {
					home_rg.check(R.id.rb_collection);
				} else if (index == 2) {
					home_rg.check(R.id.rb_comment);
				} else if (index == 3) {
					home_rg.check(R.id.rb_img);
				}
				head_text.setText(str[index]);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		// 监听RadioGroup
		home_rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (checkedId == R.id.rb_news) {
					home_vp.setCurrentItem(0);
					head_text.setText(str[0]);
				} else if (checkedId == R.id.rb_collection) {
					home_vp.setCurrentItem(1);
					head_text.setText(str[1]);
				} else if (checkedId == R.id.rb_comment) {
					home_vp.setCurrentItem(2);
					head_text.setText(str[2]);
				} else if (checkedId == R.id.rb_img) {
					home_vp.setCurrentItem(3);
					head_text.setText(str[3]);
				}
			}
		});
		fm = getSupportFragmentManager();
		// 调用适配器
		MyFragmentAdapter adapter = new MyFragmentAdapter(fm, fragments);
		home_vp.setAdapter(adapter);
	}

	public List<Fragment> getFragments() {
		
		fragments = new ArrayList<Fragment>();
		fragments.add(new FragmentNews());
		fragments.add(new FragmentCollection());
		fragments.add(new FragmentComment());
		fragments.add(new FragmentImg());
		return fragments;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// 跳转到登陆界面
		if (v.getId() == R.id.head_right) {
			MyApp application = (MyApp) getApplication();
			if(application.user==null){
				jump(LogActivity.class);
			}else{
				jump(UserInfoActivity.class);
			}
			
		}
	}

	public void jump(Class c) {
		Intent intent = new Intent(this, c);
		startActivity(intent);
	}
	long i=0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == 4) {  
			if(System.currentTimeMillis()-i>2000){
				Toast.makeText(this, "再次点击退出", 1).show();
				i=System.currentTimeMillis();
			}else{
				((MyApp)getApplication()).Outactivity();
			}
			
	       
		}
		return true;
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setContentView(R.layout.activity_main);
		allnews = new ArrayList<News>();
		getFragments();
		initView();
		initHead();
	}
}
