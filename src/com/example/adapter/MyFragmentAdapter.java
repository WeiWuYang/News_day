package com.example.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;


public class MyFragmentAdapter extends PagerAdapter {
	
	FragmentManager fm;
	 
	List<Fragment> fragments;
	
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
    	// TODO Auto-generated method stub
    	Fragment fragment = fragments.get(position);
    	//通过FragmentManager开启事务
        FragmentTransaction bg = fm.beginTransaction();
        if(!fragment.isAdded()){
        	//添加事务
        	bg.add(fragment, fragment.getClass().getSimpleName());
        	//提交事务
        	bg.commit();
        	//立即执行
        	fm.executePendingTransactions();
        }
        //得到布局
        View view = fragment.getView();
        if(view.getParent()==null){
        	container.addView(view);
        }
    	return view;
    }
	public MyFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
		super();
		this.fm = fm;
		this.fragments = fragments;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragments.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}
     @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    	// TODO Auto-generated method stub
    	//super.destroyItem(container, position, object);
    	 container.removeView((View) object);
     }
}
