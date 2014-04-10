package info.androidhive.slidingmenu;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class HomeFragment extends ListFragment {

	public HomeFragment() {
	}

	static final String[] COMMUNITY_RADIO_MENU = new String[] { "Song", "News",
			"Jokes", "UGC", "Know your region" };

	static String[] NEWS_RESULT;
	static String selectedCity;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setListAdapter(new MenuAdapter(getActivity(), COMMUNITY_RADIO_MENU));

		// selectedCity = getIntent().getStringExtra("Selected_City");
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// do something with the data
		Toast.makeText(getActivity(),"Content Added",Toast.LENGTH_SHORT).show();

	}

}
