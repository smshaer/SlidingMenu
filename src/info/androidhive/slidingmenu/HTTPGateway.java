package info.androidhive.slidingmenu;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class HTTPGateway {

	public static final String BASE_NEWS_URL = "http://202.164.208.68/newssourceapp/default.aspx?region=";
	//public static final String BASE_SONG_URL = "http://remote.ebsbd.com/2001/song.foo?d=";
	public static final String BASE_SONG_URL = "http://10.10.110.70/streamtest/list.txt?d=";
	public static final String BASE_JOKES_URL = "http://remote.ebsbd.com/2001/humor.foo?d=";

	// public static final String BASE_UGC_URL =
	// "http://202.164.208.59/songsourceapp/default.aspx?region=";
	// public static final String BASE_TOPIC_URL =
	// "http://202.164.208.59/humorsourceappLocal/default.aspx?region=";

	public static final String BASE_UGC_URL = "http://remote.ebsbd.com/2001/ugc.foo?d=";
	public static final String BASE_TOPIC_URL = "http://remote.ebsbd.com/2001/topic.foo?d=";

	private static ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

	// Methods for sending request through GET
	public static String getHttpRetrievedString(String url) {
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

	public static String getHttpResponseString(String selectedCity,
			String selectedMenuItem) {

		String staticURL = null;
		if (selectedMenuItem.equalsIgnoreCase("News") == true) {
			staticURL = BASE_NEWS_URL;
		} else if (selectedMenuItem.equalsIgnoreCase("Song") == true) {
			staticURL = BASE_SONG_URL;
		} else if (selectedMenuItem.equalsIgnoreCase("Jokes") == true) {
			staticURL = BASE_JOKES_URL;
		} else if (selectedMenuItem.equalsIgnoreCase("UGC") == true) {
			staticURL = BASE_UGC_URL;
		} else if (selectedMenuItem.equalsIgnoreCase("Know your region") == true) {
			staticURL = BASE_TOPIC_URL;
		}

		String url = staticURL + selectedCity;
		String response = getHttpRetrievedString(url);
		if (response != null && response != "^;") {
			return response;
		} else {
			return null;
		}
	}

	public static ArrayList<HashMap<String, String>> getDynamicItemLists(
			String selected_region, String s) {

		String[] param = s.split("\n");

		for (int i = 0; i < param.length; i++) {
			String[] p = param[i].split(",");
			HashMap<String, String> song = new HashMap<String, String>();

			song.put(p[0], p[1]);

			songsList.add(song);
		}

		return songsList;
	}

	/**
	 * Function to read all mp3 files from sdcard and store the details in
	 * ArrayList
	 * */
	public ArrayList<HashMap<String, String>> getPlayList() {

		// String s = httpRetrieve("http://202.164.208.68/b.html");

		String s = httpRetrieveFromUrl("http://192.168.110.16/a.html");

		String[] param = s.split("\n");

		for (int i = 0; i < param.length; i++) {
			String[] p = param[i].split(",");
			HashMap<String, String> song = new HashMap<String, String>();
			song.put("songTitle", p[0]);
			song.put("songPath", p[1]);
			songsList.add(song);
		}

		// return songs list array
		return songsList;
	}

	public String httpRetrieveFromUrl(String url) { // static
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

	/**
	 * Function to convert milliseconds time to Timer Format
	 * Hours:Minutes:Seconds
	 * */
	public String milliSecondsToTimer(long milliseconds) {
		String finalTimerString = "";
		String secondsString = "";

		// Convert total duration into time
		int hours = (int) (milliseconds / (1000 * 60 * 60));
		int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
		int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
		// Add hours if there
		if (hours > 0) {
			finalTimerString = hours + ":";
		}

		// Prepending 0 to seconds if it is one digit
		if (seconds < 10) {
			secondsString = "0" + seconds;
		} else {
			secondsString = "" + seconds;
		}

		finalTimerString = finalTimerString + minutes + ":" + secondsString;

		// return timer string
		return finalTimerString;
	}

	/**
	 * Function to get Progress percentage
	 * 
	 * @param currentDuration
	 * @param totalDuration
	 * */
	public int getProgressPercentage(long currentDuration, long totalDuration) {
		Double percentage = (double) 0;

		long currentSeconds = (int) (currentDuration / 1000);
		long totalSeconds = (int) (totalDuration / 1000);

		// calculating percentage
		percentage = (((double) currentSeconds) / totalSeconds) * 100;

		// return percentage
		return percentage.intValue();
	}

	/**
	 * Function to change progress to timer
	 * 
	 * @param progress
	 *            -
	 * @param totalDuration
	 *            returns current duration in milliseconds
	 * */
	public int progressToTimer(int progress, int totalDuration) {
		int currentDuration = 0;
		totalDuration = (int) (totalDuration / 1000);
		currentDuration = (int) ((((double) progress) / 100) * totalDuration);

		// return current duration in milliseconds
		return currentDuration * 1000;
	}

	// Methods For connectivity checking
	public static boolean isConnected(Context context) {
		ConnectivityManager conmgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conmgr.getActiveNetworkInfo();

		if (netInfo != null && netInfo.isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isConnectionCheck(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// test for connection
		if (cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isAvailable()
				&& cm.getActiveNetworkInfo().isConnected()) {
			return true;
		} else {
			// Log.v(TAG, "Internet Connection Not Present");
			return false;
		}
	}

}
