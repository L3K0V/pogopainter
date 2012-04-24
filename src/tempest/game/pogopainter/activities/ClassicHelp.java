package tempest.game.pogopainter.activities;

import java.util.ArrayList;
import java.util.HashMap;

import tempeset.game.pogopainter.R;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

public class ClassicHelp extends ListActivity {

	final ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_list_view);

		populateList();

		SimpleAdapter adapter = new SimpleAdapter(
				this, list,
				R.layout.custom_row_view,
				new String[] {"title","content","comment"},
				new int[] {R.id.text1,R.id.text2, R.id.text3}
				) {
			public boolean areAllItemsEnabled() 
			{ 
				return false; 
			} 
			public boolean isEnabled(int position) 
			{ 
				return false; 
			} 
		};



		setListAdapter(adapter);
	}

	private void populateList() {
		HashMap<String,String> temp = new HashMap<String,String>();
		temp.put("title","Game purpose");
		temp.put("content", "Get most points!");
		temp.put("comment", "Hint: color most cells you can and then get checkpoint");
		list.add(temp);
		HashMap<String,String> temp1 = new HashMap<String,String>();
		temp1.put("title","Number of players");
		temp1.put("content", "4 players");
		temp1.put("comment", "Four players every with unique color");
		list.add(temp1);
		HashMap<String,String> temp2 = new HashMap<String,String>();
		temp2.put("title","Number of teams");
		temp2.put("content", "Four teams of one player each");
		temp2.put("comment", "One for one");
		list.add(temp2);
		HashMap<String,String> temp3 = new HashMap<String,String>();
		temp3.put("title","Bonuses");
		temp3.put("content", "Speed, Arrow, Mark, Random");
		temp3.put("comment", "Hint: Use bonuses to get advantage over other players");
		list.add(temp3);

	}
}