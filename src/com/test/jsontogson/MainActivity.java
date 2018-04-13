package com.test.jsontogson;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.google.gson.Gson;

public class MainActivity extends Activity implements OnClickListener {

	DataAdapter dataAdapter;

	Data data;
	List<Data> list;

	private Button btn1;
	private EditText edt;
	private ListView listView;
	
	public static StringBuffer sBuffer = new StringBuffer();

	public String hash, name, time;
	public int currentPosition = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn1 = (Button) findViewById(R.id.btn1);
		btn1.setOnClickListener(this);
		edt = (EditText) findViewById(R.id.btn2);

		final View view1 = LayoutInflater.from(MainActivity.this).inflate(
				R.layout.extra_view, null);

		listView = (ListView) findViewById(R.id.listview);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@SuppressLint("NewApi")
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				LinearLayout layout = (LinearLayout) ((LinearLayout) view
						.getParent()).getParent();

				int positionPressed = listView.getPositionForView(layout);

				if (positionPressed == position) {
					layout.removeView(view1);
					positionPressed = -1;
				} else {
					if ((LinearLayout) view1.getParent() != null) {
						((LinearLayout) view1.getParent()).removeView(view1);
					}
					position = positionPressed;
					layout.setOrientation(LinearLayout.VERTICAL);
					layout.addView(view1, new LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT));

				}

				currentPosition = position;
				Log.v("oncreate", currentPosition + "//");
				/**
				 * 不支持此方法 listView.addView(listView, currentPosition);
				 */

				dataAdapter.notifyDataSetChanged();
			}
		});

	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn1:
			new Thread(runnable).start();
			break;
		}

	}

	Runnable runnable = new Runnable() {

		public void run() {

			Looper.prepare();
			Parameters parameters = new Parameters();

			String s = edt.getText().toString();
			if (TextUtils.isEmpty(s)) {

				Toast.makeText(MainActivity.this, "输入格式错误", Toast.LENGTH_SHORT)
						.show();
				Looper.loop();
			} else {
				String infomation = getPinyin(s);

				parameters.put("s", infomation);
			}

			ApiStoreSDK.execute("http://apis.baidu.com/geekery/music/query",
					ApiStoreSDK.GET, parameters, new ApiCallBack() {

						@Override
						public void onSuccess(int status, String responseString) {
							
							super.onSuccess(status, responseString);
							setGsonData(responseString);
						}

						@Override
						public void onComplete() {
							super.onComplete();
						}

						@Override
						public void onError(int status, String responseString,
								Exception e) {
							super.onError(status, responseString, e);
							e.printStackTrace();
						}

					});
			Looper.loop();

		}
	};

	/**
	 * Gson解析Json
	 * 
	 * @param response获取的Json数据
	 */
	public void setGsonData(String response) {
		try {
			JSONObject jsonObject = new JSONObject(response);
			JSONObject totalData = (JSONObject) jsonObject.get("data");
			JSONArray totalArray = (JSONArray) totalData.get("data");

			list = new ArrayList<Data>();

			for (int i = 0; i < totalArray.length(); i++) {
				JSONObject eachData = (JSONObject) totalArray.get(i);
				// Log.i(TAG, eachData.toString());
				Gson gson = new Gson();
				Data data = gson.fromJson(eachData.toString(), Data.class);

				list.add(data);

				dataAdapter = new DataAdapter(MainActivity.this,
						R.layout.eachitem, list);
				listView.setAdapter(dataAdapter);

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 汉字转换成拼音
	 * 
	 * @param chinese
	 *            汉字
	 * @return
	 */
	public static String getPinyin(String chinese) {

		sBuffer.setLength(0);
		char[] nameChar = chinese.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			if (nameChar[i] > 128) {
				try {
					sBuffer.append(PinyinHelper.toHanyuPinyinStringArray(
							nameChar[i], defaultFormat)[0]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				sBuffer.append(nameChar[i]);
			}

		}

		return sBuffer.toString();
	}

	public void showDialog(final String name, final String hash,
			final String time) {
		AlertDialog.Builder adBuilder = new AlertDialog.Builder(
				MainActivity.this);
		adBuilder.setTitle("播放歌曲").setMessage("是否要播放" + name)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(MainActivity.this,
								Download.class);
						intent.putExtra("hash", hash);
						intent.putExtra("name", name);
						intent.putExtra("time", time);

						Log.i("hash--name--time-->", hash + "--" + name + "--"
								+ time);

						startActivity(intent);
					}
				}).setNegativeButton(R.string.cancel, null);

		adBuilder.create().show();
	}

	public class DataAdapter extends ArrayAdapter<Data> implements
			OnClickListener {

		private int resourceId;

		public DataAdapter(Context context, int resource, List<Data> list) {
			super(context, resource, list);
			this.resourceId = resource;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			Data data = (Data) getItem(position);

			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(
						resourceId, null);
				convertView.setTag(position);
			} else {
				convertView.setTag(position);
			}
			TextView filename = (TextView) convertView
					.findViewById(R.id.filename);
			TextView extname = (TextView) convertView
					.findViewById(R.id.extname);
			TextView m4afilesize = (TextView) convertView
					.findViewById(R.id.m4afilesize);
			TextView filesize = (TextView) convertView
					.findViewById(R.id.filesize);
			TextView bitrate = (TextView) convertView
					.findViewById(R.id.bitrate);
			TextView isnew = (TextView) convertView.findViewById(R.id.isnew);
			TextView duration = (TextView) convertView
					.findViewById(R.id.duration);
			TextView albumname = (TextView) convertView
					.findViewById(R.id.albumname);
			TextView singername = (TextView) convertView
					.findViewById(R.id.singername);
			TextView hash = (TextView) convertView.findViewById(R.id.hash);

			LinearLayout extraView = (LinearLayout) convertView
					.findViewById(R.id.extraView);

			TextView addView = (TextView) convertView.findViewById(R.id.add);
			TextView downView = (TextView) convertView
					.findViewById(R.id.download);
			TextView shareView = (TextView) convertView
					.findViewById(R.id.share);
			TextView playView = (TextView) convertView.findViewById(R.id.play);

			filename.setText(data.getFilenameString());
			extname.setText(data.getExtnameString());
			m4afilesize.setText(data.getM4afilesize());
			filesize.setText(data.getFilesize());
			bitrate.setText(data.getBitrate());
			isnew.setText(data.getIsnew());
			duration.setText(data.getDuration());
			albumname.setText(data.getAlbumnameString());
			singername.setText(data.getSingernameString());
			hash.setText(data.getHashString());

			addView.setOnClickListener(this);
			downView.setOnClickListener(this);
			shareView.setOnClickListener(this);
			playView.setOnClickListener(this);

			// if (currentPosition == position) {
			// extraView.setVisibility(View.VISIBIL);
			// // currentPosition = -1;
			// } else {
			// // currentPosition = position;
			// extraView.setVisibility(View.GONE);
			// }

			return convertView;
		}

		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.add:

				currentPosition = -1;
				break;

			case R.id.download:

				currentPosition = -1;
				break;

			case R.id.share:

				currentPosition = -1;
				break;

			case R.id.play:
				data = list.get(currentPosition);
				name = data.getFilenameString();
				hash = data.getHashString();
				time = data.getDuration();
				showDialog(name, hash, time);

				currentPosition = -1;
				break;
			}

		}

	}

}
