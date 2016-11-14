package com.example.utils;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.content.Context;

public class img_utils {
	 /**
	 * ��ʼ��ͼƬ�������
	 * 
	 * @param context
	 */
	protected static void initImageLoader(Context context) {
		// Create default options which will be used for every
		// displayImage(...) call if no options will be passed to this method
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true)
				.cacheOnDisc(true)
				.build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
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
		/*
		 * url 
		 * 1 ȥLur������ �о�ֱ�ӷ��� û�м���
		 * 2 �洢�����ݿ� url ��ΪΨһ��ʾ �о�ֱ�ӷ���
		 * ��������û�ҵ�  ����
		 * ��Lur �ﱣ��һ��
		 * ͬʱҲ���� �������ݿ��һ��
		 * Lur
		 * ���ݿ� Lur
		 *  Lur
		 * */
	
	}
}
