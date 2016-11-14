package com.example.adapter;

import java.util.List;
import java.util.zip.Inflater;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.model.News;
import com.example.news_day.MainActivity;
import com.example.news_day.MyApp;
import com.example.news_day.R;
import com.example.utils.Config;
import com.example.utils.MyDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.opengl.Visibility;
import android.text.Editable;
import android.text.Selection;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ImgAdapter extends BaseAdapter {
	List<News> list;
	LayoutInflater layout;
	Context context;
	private ViewHolder vh;
	ImgAdapter adapter;

	public ImgAdapter(List<News> list, Context context) {
		super();
		this.list = list;
		this.context = context;
		layout = LayoutInflater.from(context);
		adapter = this;
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
	public View getView(final int arg0, View v, ViewGroup arg2) {
		vh = null;
		if (v == null) {
			vh = new ViewHolder();
			v = layout.inflate(R.layout.img_list_item, null);
			vh.img_head = (ImageView) v.findViewById(R.id.img_head);

			vh.img_head_text = (TextView) v.findViewById(R.id.img_head_text);
			vh.img_contect = (ImageView) v.findViewById(R.id.img_contect);
			vh.ll_send = (LinearLayout) v.findViewById(R.id.ll_send);
			vh.send_text = (EditText) v.findViewById(R.id.send_text);
			vh.send_contect = (Button) v.findViewById(R.id.send_contect);
			v.setTag(vh);
		} else {
			vh = (ViewHolder) v.getTag();
		}

		Picasso.with(context).load(list.get(arg0).getIcon()).into(vh.img_head);
		vh.img_head_text.setText(list.get(arg0).getTitle());

		vh.img_contect.setOnClickListener(new imgOnClickListener(arg0));
		vh.send_contect.setOnClickListener(new SendOncick(vh.send_text, arg0));
		if (list.get(arg0).isIsshow()) {
			vh.ll_send.setVisibility(View.VISIBLE);
			// 设置可获得焦点
			vh.send_text.setFocusable(true);
			vh.send_text.setFocusableInTouchMode(true);
			// 请求获得焦点
			vh.send_text.requestFocus();
			// 调用系统输入法
			InputMethodManager inputManager = (InputMethodManager) vh.send_text
					.getContext()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			//inputManager.showSoftInput(vh.send_text, 0);
			Editable etext = vh.send_text.getText();
			Selection.setSelection(etext, etext.length());
		} else {
			vh.send_text.setText("");
			vh.ll_send.setVisibility(View.GONE);

		}
		return v;
	}
	static class ViewHolder {
		EditText send_text;
		ImageView img_head;
		TextView img_head_text;
		ImageView img_contect;
		Button send_contect;
		LinearLayout ll_send;

	}
	class imgOnClickListener implements OnClickListener {

		int i;

		public imgOnClickListener(int arg0) {
			// TODO Auto-generated constructor stub
			super();
			this.i = arg0;
		}

		@Override
		public void onClick(View v) {

			// TODO Auto-generated method stub
			for (int a = 0; a < list.size(); a++) {
				if (list.get(a).isIsshow()) {
					list.get(a).setIsshow(false);
				} else {
					if (i == a) {

						list.get(a).setIsshow(true);
					}
				}
			}

			adapter.notifyDataSetChanged();
		}

	}

	

	class SendOncick implements OnClickListener {
		EditText send_text;
		int index;

		public SendOncick(EditText send_text, int index) {
			super();
			this.send_text = send_text;
			this.index = index;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String text = vh.send_text.getText().toString();
			MyDialog.showDialog(context);
			News news = list.get(index);
			MainActivity m = (MainActivity) context;
			MyApp app = (MyApp) m.getApplication();
			
			AsyncHttpClient ac = new AsyncHttpClient();
			RequestParams params = new RequestParams();
			// ver=0nid=token=用户令牌&imei=手机标识符&ctx=评论内容
			params.put("ver", "0");
			params.put("nid", news.getNid() + "");
			params.put("token", app.user.getToken());
			params.put("imei", "123456");
			params.put("ctx", "text");
			System.out.print("token的数值"+app.user.getToken());
			ac.get(Config.ip + Config.cmt_commit, params,
					new AsyncHttpResponseHandler() {

						@Override 
						public void onSuccess(int arg0, Header[] arg1,
								byte[] arg2) {
							// TODO Auto-generated method stub
							try {
								JSONObject json = new JSONObject(new String(
										arg2));
								int status = json.getInt("status");
								
								System.out.print("status的数值"+status);
								if (status == 0) {
									MyDialog.dismiss();
									
									send_text.setText(""); 
									News news = list.get(index);
									news.setIsshow(false);
									adapter.notifyDataSetChanged();
									System.out.print("评论成功");
									Toast.makeText(context, "评论成功", 1).show();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						@Override
						public void onFailure(int arg0, Header[] arg1,
								byte[] arg2, Throwable arg3) {
							// TODO Auto-generated method stub

						}
					});

		}

	}

}
