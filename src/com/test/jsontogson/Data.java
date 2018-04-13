package com.test.jsontogson;

import android.util.Log;

public class Data {

	private String filename;
	private String extname;
	private String m4afilesize;
	private String filesize;
	private String bitrate;
	private String isnew;
	private String duration;
	private String album_name;
	private String singername;
	private String hash;

	public Data(String filenameString, String extnameString,
			String m4afilesize, String filesize, String bitrate, String isnew,
			String duration, String albumnameString, String singernameString,
			String hashString) {
		super();
		this.filename = filenameString;
		this.extname = extnameString;
		this.m4afilesize = m4afilesize;
		this.filesize = filesize;
		this.bitrate = bitrate;
		this.isnew = isnew;
		this.duration = duration;
		this.album_name = albumnameString;
		this.singername = singernameString;
		this.hash = hashString;
	}

	public String getFilenameString() {

		return filename;
	}

	public void setFilenameString(String filenameString) {
		this.filename = filenameString;
	}

	public String getExtnameString() {
		return extname;
	}

	public void setExtnameString(String extnameString) {
		this.extname = extnameString;
	}

	public String getM4afilesize() {
		return m4afilesize;
	}

	public void setM4afilesize(String m4afilesize) {
		this.m4afilesize = m4afilesize;
	}

	public String getFilesize() {
		return filesize;
	}

	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

	public String getBitrate() {
		return bitrate;
	}

	public void setBitrate(String bitrate) {
		this.bitrate = bitrate;
	}

	public String getIsnew() {
		return isnew;
	}

	public void setIsnew(String isnew) {
		this.isnew = isnew;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getAlbumnameString() {
		return album_name;
	}

	public void setAlbumnameString(String albumnameString) {
		this.album_name = albumnameString;
	}

	public String getSingernameString() {
		return singername;
	}

	public void setSingernameString(String singernameString) {
		this.singername = singernameString;
	}

	public String getHashString() {
		Log.i("Data--->getHashString", hash);
		return hash;
	}

	public void setHashString(String hashString) {
		this.hash = hashString;
	}

	@Override
	public String toString() {
		return "Data [filenameString=" + filename + ", extnameString="
				+ extname + ", m4afilesize=" + m4afilesize + ", filesize="
				+ filesize + ", bitrate=" + bitrate + ", isnew=" + isnew
				+ ", duration=" + duration + ", albumnameString=" + album_name
				+ ", singernameString=" + singername + ", hashString=" + hash
				+ "]";
	}

}
