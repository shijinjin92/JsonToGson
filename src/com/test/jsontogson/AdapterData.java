package com.test.jsontogson;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AdapterData extends BaseAdapter {

	public List<Data> lists;
	public Context context;
	public int currentPosition;

	public AdapterData(List<Data> lists, Context context) {
		super();
		this.lists = lists;
		this.context = context;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lists.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		Data data = lists.get(position);
		currentPosition = position;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.eachitem, parent, false);
			holder = new ViewHolder();
			holder.fileName = (TextView) convertView
					.findViewById(R.id.filename);
			holder.extName = (TextView) convertView.findViewById(R.id.extname);
			holder.m4aFileSize = (TextView) convertView
					.findViewWithTag(R.id.m4afilesize);
			holder.fileSize = (TextView) convertView
					.findViewById(R.id.filesize);
			holder.bitRate = (TextView) convertView.findViewById(R.id.bitrate);
			holder.isNew = (TextView) convertView.findViewById(R.id.isnew);
			holder.duration = (TextView) convertView
					.findViewById(R.id.duration);
			holder.albumName = (TextView) convertView
					.findViewById(R.id.albumname);
			holder.singerName = (TextView) convertView
					.findViewById(R.id.singername);
			holder.hash = (TextView) convertView.findViewById(R.id.hash);
			holder.extraView = (LinearLayout) convertView
					.findViewById(R.id.extraView);
			//
			// holder.addView = (TextView) convertView.findViewById(R.id.add);
			// holder.downView = (TextView) convertView
			// .findViewById(R.id.download);
			// holder.shareView = (TextView)
			// convertView.findViewById(R.id.share);
			// holder.playView = (TextView) convertView.findViewById(R.id.play);

			convertView.setTag(holder);

		} else {
			convertView.setTag(holder);
		}

		holder.fileName.setText(data.getFilenameString());
		holder.extName.setText(data.getExtnameString());
		holder.m4aFileSize.setText(data.getM4afilesize());
		holder.fileSize.setText(data.getFilesize());
		holder.bitRate.setText(data.getBitrate());
		holder.isNew.setText(data.getIsnew());
		holder.duration.setText(data.getDuration());
		holder.albumName.setText(data.getAlbumnameString());
		holder.singerName.setText(data.getSingernameString());
		holder.hash.setText(data.getHashString());
		if (currentPosition == position) {
			currentPosition = -1;
			holder.extraView.setVisibility(View.VISIBLE);
		} else {
			holder.extraView.setVisibility(View.GONE);
		}

		return convertView;
	}

	public class ViewHolder {
		TextView fileName;
		TextView extName;
		TextView m4aFileSize;
		TextView fileSize;
		TextView bitRate;
		TextView isNew;
		TextView duration;
		TextView albumName;
		TextView singerName;
		TextView hash;
		LinearLayout extraView;

		// TextView addView;
		// TextView downView;
		// TextView shareView;
		// TextView playView;
	}

}
