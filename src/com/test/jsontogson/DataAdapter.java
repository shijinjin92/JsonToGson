package com.test.jsontogson;


//public class DataAdapter extends ArrayAdapter<Data> {
//
//	public int resourceId;
//	public int currentPosition;
//
//	public DataAdapter(Context context, int resource, List<Data> list) {
//		super(context, resource, list);
//		// TODO Auto-generated constructor stub
//		this.resourceId = resource;
//	}
//
//	public View getView(int position, View convertView, ViewGroup parent) {
//		// TODO Auto-generated method stub
//		Data data = (Data) getItem(position);
//
//		if (convertView == null) {
//			convertView = LayoutInflater.from(getContext()).inflate(resourceId,
//					null);
//			convertView.setTag(position);
//		} else {
//			convertView.setTag(position);
//		}
//		TextView filename = (TextView) convertView.findViewById(R.id.filename);
//		TextView extname = (TextView) convertView.findViewById(R.id.extname);
//		TextView m4afilesize = (TextView) convertView
//				.findViewById(R.id.m4afilesize);
//		TextView filesize = (TextView) convertView.findViewById(R.id.filesize);
//		TextView bitrate = (TextView) convertView.findViewById(R.id.bitrate);
//		TextView isnew = (TextView) convertView.findViewById(R.id.isnew);
//		TextView duration = (TextView) convertView.findViewById(R.id.duration);
//		TextView albumname = (TextView) convertView
//				.findViewById(R.id.albumname);
//		TextView singername = (TextView) convertView
//				.findViewById(R.id.singername);
//		TextView hash = (TextView) convertView.findViewById(R.id.hash);
//
//		LinearLayout extraView = (LinearLayout) convertView
//				.findViewById(R.id.extraView);
//
//		filename.setText(data.getFilenameString());
//		extname.setText(data.getExtnameString());
//		m4afilesize.setText(data.getM4afilesize());
//		filesize.setText(data.getFilesize());
//		bitrate.setText(data.getBitrate());
//		isnew.setText(data.getIsnew());
//		duration.setText(data.getDuration());
//		albumname.setText(data.getAlbumnameString());
//		singername.setText(data.getSingernameString());
//		hash.setText(data.getHashString());
//
//		if (currentPosition == position) {
//			extraView.setVisibility(View.VISIBLE);
//			currentPosition = -1;
//		} else {
//			// currentPosition = position;
//			extraView.setVisibility(View.GONE);
//		}
//
//		return convertView;
//	}
//
// }
