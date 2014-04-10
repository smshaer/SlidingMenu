package info.androidhive.slidingmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuAdapter extends ArrayAdapter<String> {

	private final Context context;
	private final String[] values;

	public MenuAdapter(Context context, String[] values) {
		super(context, R.layout.static_menu_layout2, values);
		

		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.static_menu_layout2, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		textView.setText(values[position]);

		// Change icon based on name
		String s = values[position];

		/*if (s.equals("Song")) {
			imageView.setImageResource(R.drawable.ic_menu_song);
		} else if (s.equals("News")) {
			imageView.setImageResource(R.drawable.ic_menu_news);
		} else if (s.equals("Jokes")) {
			imageView.setImageResource(R.drawable.ic_menu_jokes);
		} else if (s.equals("UGC")) {
			imageView.setImageResource(R.drawable.microphone);
		} else if (s.equals("Know your region")) {
			imageView.setImageResource(R.drawable.bangladesh_map);
		}*/ 
		

		return rowView;
	}

	


}