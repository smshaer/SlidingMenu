package info.androidhive.slidingmenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.app.Fragment;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class FindPeopleFragment extends ListFragment {

	public FindPeopleFragment() {
	}

	ProgressDialog pd;
	String response;
	String selectedCity;
	String selectedMenuItem;
	static String[] itemsTitleArray;

	public ArrayList<HashMap<String, String>> urlArrayList;
	public HashMap<String, String> urlWithTitleHashMap = new HashMap<String, String>();

	static String[] NEWS_TITLE = new String[] {};
	static String Selected_City = null;

	/*
	 * @Override public View onCreateView(LayoutInflater inflater, ViewGroup
	 * container, Bundle savedInstanceState) { ArrayAdapter<String> adapter =
	 * new ArrayAdapter<String>( inflater.getContext(),
	 * android.R.layout.simple_list_item_1, numbers_text);
	 * setListAdapter(adapter); return super.onCreateView(inflater, container,
	 * savedInstanceState); }
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		System.out.println(" clicked on onCreate Starting....>");
		
		//setListAdapter(new DynamicMenuAdapter(getActivity(), itemsTitleArray));

		selectedCity = "dhaka";
		selectedMenuItem = "song";
		//selectedCity = getIntent().getStringExtra("Selected_City");
		//selectedMenuItem = getIntent().getStringExtra("Selected_Menu");

		System.out.println("SELECTED CITY on LAST : >" + selectedCity + " <");

		try {

			
			pd = ProgressDialog.show(getActivity(), "Please Wait",
					"Loading....");
			// pd = ProgressDialog.show(FindPeopleFragment.this,
			// "Please Wait","Loading....");

			RequestThread reqThread = new RequestThread();
			
			System.out.println(" before clicked on thread > ....>");
			
			
			reqThread.start();
			
			

		} catch (Exception ex) {

			Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT)
					.show();
		}

	}

	public void onListItemClick(ListView l, View v, int position, long id) {

		// get selected items
		String selectedValue = (String) getListAdapter().getItem(position);

		if (selectedValue != null && selectedValue.length() > 0) {
			String urlOfSelectedSong = urlWithTitleHashMap.get(selectedValue);
			Toast.makeText(getActivity(),
			// urlOfSelectedSong,
					"Content added to Play List", Toast.LENGTH_SHORT).show();

			HashMap<String, String> song = new HashMap<String, String>();
			song.put("songTitle", selectedValue);
			song.put("songPath", urlOfSelectedSong);
			SongsManager._songsList.add(song);

		}

		/*
		 * Toast.makeText(getApplicationContext(), selectedValue,
		 * Toast.LENGTH_SHORT).show();
		 */

	}

	public class RequestThread extends Thread {
		public void run() {

			synchronized (this) {
				
				System.out.println(" after clicked on tread....>");
				
				response = HTTPGateway.getHttpResponseString(selectedCity,
						selectedMenuItem);
				//response = "Naow Bhiraow Naow Bhiraow- Shopna,http://remote.ebsbd.com/2001/song/dhaka/Naow Bhiraow Naow Bhiraow- Shopna.mp3";
				//System.out.println("GateWay Response  > " + response + " <");
			}
			handler.sendEmptyMessage(0);
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			pd.dismiss();
			if (response != null) {

				urlArrayList = HTTPGateway.getDynamicItemLists(selectedCity,
						response);

				List<String> title_list = new ArrayList<String>();
				for (int i = 0; i < urlArrayList.size(); i++) {
					HashMap<String, String> tmpData = (HashMap<String, String>) urlArrayList
							.get(i);
					Set<String> key = tmpData.keySet();
					Iterator it = key.iterator();
					while (it.hasNext()) {
						String hmKey = (String) it.next();
						String hmValue = (String) tmpData.get(hmKey);
						title_list.add(hmKey);
						// title_list.add(hmValue);

						urlWithTitleHashMap.put(hmKey, hmValue);
						it.remove();

					}
				}
				itemsTitleArray = title_list.toArray(new String[] {});

				ListAdapter adapter = new DynamicMenuAdapter(getActivity(),
						itemsTitleArray);

				setListAdapter(adapter);

			} else {
				Toast.makeText(getActivity(), "Communication Error",
						Toast.LENGTH_LONG).show();
			}
		}

	};

}
