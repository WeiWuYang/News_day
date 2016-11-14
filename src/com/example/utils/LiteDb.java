
package com.example.utils;


import java.util.List;

import android.content.Context;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.WhereBuilder;
import com.litesuits.orm.db.model.ConflictAlgorithm;


public class LiteDb {
	private  DataBase newInstance;

	public LiteDb(Context context) {
		newInstance = LiteOrm.newInstance(context, "letedb");
	}
	//娣诲姞
	public <T> void Add(T t) {
		newInstance.save(t);
	}
	//鏌ユ壘鎵�鏈�
	public <T> List<T> select(Class<T> t) {
		return newInstance.query(t);
	}
	//鏍规嵁鏉′欢鏌ユ壘
	public <T> List<T> getQueryByWhere(Class<T> cla, String field,
			String[] value) {
		return newInstance.query(new QueryBuilder(cla).where(field,
				value));
	}
	//鏍规嵁鏉′欢鍒犻櫎
	public <T> void deleteWhere(Class<T> cla, String field, String[] value) {
		newInstance.delete(cla, WhereBuilder.create()
				.where(field , value));
	}

	
	// 浠呭湪浠ュ瓨鍦ㄦ椂淇敼
	 
	public <T> void update(T t) {
		newInstance.update(t, ConflictAlgorithm.Replace);
	}
}
