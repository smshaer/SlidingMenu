package info.androidhive.slidingmenu;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import android.os.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

public class SongsManager {
	// SDCard Path
	final String MEDIA_PATH = Environment.getExternalStorageDirectory()
			.getPath();
	private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

	public static ArrayList<HashMap<String, String>> _songsList = new ArrayList<HashMap<String, String>>();

	// Constructor
	public SongsManager() {

	}

	/**
	 * Function to read all mp3 files from sdcard and store the details in
	 * ArrayList
	 * */
	public ArrayList<HashMap<String, String>> getPlayList() {
		/*
		 * File home = new File(MEDIA_PATH);
		 * 
		 * if (home.listFiles(new FileExtensionFilter()).length > 0) { for (File
		 * file : home.listFiles(new FileExtensionFilter())) { HashMap<String,
		 * String> song = new HashMap<String, String>(); song.put("songTitle",
		 * file.getName().substring(0, (file.getName().length() - 4)));
		 * song.put("songPath", file.getPath());
		 * 
		 * // Adding each song to SongList songsList.add(song); } }
		 */

		/*
		 * HashMap<String, String> song = new HashMap<String, String>();
		 * song.put("songTitle", "track 1"); song.put("songPath",
		 * "http://202.164.208.68/a.mp3"); songsList.add(song);
		 * 
		 * song = new HashMap<String, String>(); song.put("songTitle",
		 * "track 2"); song.put("songPath",
		 * "http://202.164.208.68/James Morrison One Life.mp3");
		 * songsList.add(song);
		 */

		/*
		 * //String s = httpRetrieve("http://202.164.208.68/b.html");
		 * 
		 * String s = httpRetrieve("http://10.10.110.62/a.html");
		 * 
		 * 
		 * String[] param = s.split("\n");
		 * 
		 * 
		 * for(int i = 0; i < param.length; i++) { String[] p =
		 * param[i].split(","); HashMap<String, String> song = new
		 * HashMap<String, String>(); song.put("songTitle", p[0]);
		 * song.put("songPath", p[1]); songsList.add(song); }
		 */

		// return songs list array
		return _songsList;
	}

	public static String httpRetrieve(String url) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(url);

		try {
			HttpResponse response = client.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			String responseText = EntityUtils.toString(entity);
			return responseText;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Class to filter files which are having .mp3 extension
	 * */
	class FileExtensionFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			return (name.endsWith(".mp3") || name.endsWith(".MP3"));
		}
	}
}
