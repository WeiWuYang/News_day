package com.example.utils;

import com.example.news_day.R;
import com.example.photoview.PhotoViewAttacher.OnPhotoTapListener;
import com.example.photoview.PhotoViewAttacher.OnViewTapListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MyDialog {
        private static Dialog dialog;
		private static ImageView img_load;
		private static Dialog dg;

		public static void showDialog(Context context){
        	LayoutInflater layout=LayoutInflater.from(context);
        	View view = layout.inflate(R.layout.load_dialog_rotate, null);
        	Animation animation = AnimationUtils.loadAnimation(context, R.anim.dialog_rotate);
        	dialog = new Dialog(context, R.style.my_dialog_style);
        	img_load = (ImageView) view.findViewById(R.id.img_load);
        	img_load.startAnimation(animation);
        	
        	
        	dialog.setContentView(view);
        	
        	dialog.show();
        }
		public static void dismiss() {
			if (dialog != null) {
				dialog.dismiss();
				dialog = null;
			}

		}
		public static void showimageDialog(Context context ,String uri){
			LayoutInflater layout=LayoutInflater.from(context);
        	View view = layout.inflate(R.layout.image_item, null);
        	//view.findViewById(R.id.ll_img).setOnClickListener(context);
        	
        	com.example.photoview.PhotoView	img_login_item= 	(com.example.photoview.PhotoView) view.findViewById(R.id.img_login_item);
        	img_login_item.setOnViewTapListener(new OnViewTapListener() {
				
				@Override
				public void onViewTap(View view, float x, float y) {
					// TODO Auto-generated method stub
					dg.dismiss();
				}
			});
        ImageLoader.getInstance().displayImage(uri, img_login_item);
        dg = new Dialog(context,R.style.my_dialog_style);
		Window window = dg.getWindow();
		WindowManager.LayoutParams LayoutParams=new LayoutParams();
		LayoutParams.width=WindowManager.LayoutParams.MATCH_PARENT;
		LayoutParams.height=WindowManager.LayoutParams.MATCH_PARENT;

		
		LayoutParams.gravity=Gravity.CENTER;
		dg.setContentView(view, LayoutParams);
		dg.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		dg.show();
		}
	
}
