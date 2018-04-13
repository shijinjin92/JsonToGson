package com.test.jsontogson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

@SuppressLint("SetJavaScriptEnabled")
public class Download extends Activity {

	private TextView showLyric;
	private WebView webView;
	private ProgressBar progressBar;

	private static String hashString;
	private static String timeString;
	private static String nameString;
	private static String lyric;
	private static String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_down);
		showLyric = (TextView) findViewById(R.id.text_lyric);
		webView = (WebView) findViewById(R.id.web_music);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);

		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);

		Bundle bundle = this.getIntent().getExtras();
		hashString = bundle.getString("hash");
		timeString = bundle.getString("time");
		nameString = bundle.getString("name");

		Log.i("hash--name--time-->", hashString + "--" + nameString + "--"
				+ timeString);

		hashString = encode(hashString);
		timeString = encode(timeString);
		nameString = encode(nameString);
		Log.i("hash--name--time-->", hashString + "--" + nameString + "--"
				+ timeString);

		new Thread(loadMusic).start();
		// new Thread(downloadLyric).start();

		if (lyric == null) {
			showLyric.setText("暂无歌词");
		} else {
			showLyric.setText(lyric);
		}

		/**
		 * url 为空， Log打印发现先打印if里面的url,在打印runnable里面的url
		 */
		if (url != null) {
			webView.loadUrl(url);
			Log.e("url", url);
		} else {
			webView.loadUrl("http://baidu.com");
			Log.e("url", "null");
		}

		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				return true;
			}
		});

		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				super.onProgressChanged(view, newProgress);
				if (newProgress == 100) {
					webView.setBackgroundColor(Color.WHITE);
					Log.v("progressBar", newProgress + ".");
				} else {
					progressBar.setProgress(newProgress);
					Log.v("progressBar", newProgress + "/");
				}
			}
		});

	}

	/**
	 * url编码
	 * 
	 * @param str
	 * @return
	 */
	public String encode(String str) {

		try {
			str = URLEncoder.encode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return str;
	}

	/**
	 * 下载歌词 需要歌曲信息：hash time name
	 * 
	 * 问题：httpArg有问题（网址无法连接）
	 */
	Runnable downloadLyric = new Runnable() {

		public void run() {
			// TODO Auto-generated method stub
			Looper.prepare();
			String httpUrl = "http://apis.baidu.com/geekery/music/krc";
			BufferedReader reader = null;
			StringBuffer sbf = new StringBuffer();
			String httpArg = "name=" + nameString + "&hash=" + hashString
					+ "&time=" + timeString;

			String urlString = httpUrl + "?" + httpArg;

			try {
				URL url = new URL(urlString);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setRequestMethod("GET");
				connection.setRequestProperty("apikey",
						"0742a0b1236b4aec12573bea8054fc29");
				connection.connect();
				InputStream is = connection.getInputStream();
				reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				String strRead = null;
				while ((strRead = reader.readLine()) != null) {
					sbf.append(strRead);
					sbf.append("\r\n");

				}

				Log.i("sbf", sbf.toString());
				reader.close();

				JSONObject jsonObject = new JSONObject(sbf.toString());
				lyric = jsonObject.getString("data");

			} catch (Exception e) {
				e.printStackTrace();
			}

			Looper.loop();
		}
	};

	/**
	 * 播放歌曲 需要歌曲信息：hash
	 */
	Runnable loadMusic = new Runnable() {

		public void run() {
			// TODO Auto-generated method stub
			Looper.prepare();

			String httpUrl = "http://apis.baidu.com/geekery/music/playinfo";
			BufferedReader reader = null;
			StringBuffer sbf = new StringBuffer();
			String urlString = httpUrl + "?" + "hash=" + hashString;

			try {
				URL url1 = new URL(urlString);
				HttpURLConnection connection = (HttpURLConnection) url1
						.openConnection();
				connection.setRequestMethod("GET");
				connection.setRequestProperty("apikey",
						"0742a0b1236b4aec12573bea8054fc29");
				connection.connect();
				InputStream is = connection.getInputStream();
				reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				String strRead = null;
				while ((strRead = reader.readLine()) != null) {
					sbf.append(strRead);
					sbf.append("\r\n");
				}
				Log.v("getURL", sbf.toString());
				JSONObject jsonObject = new JSONObject(sbf.toString());
				JSONObject dataJsonObject = jsonObject.optJSONObject("data");
				url = dataJsonObject.getString("url");

				Log.v("URL", url);

				reader.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

			Looper.loop();
		}
	};

}
