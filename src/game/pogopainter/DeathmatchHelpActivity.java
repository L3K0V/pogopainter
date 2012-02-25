package game.pogopainter;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

public class DeathmatchHelpActivity extends ListActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_list_view);
		final ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>(); 

		populateList(list);
		SimpleAdapter adapter = new SimpleAdapter(
				this, list,
				R.layout.custom_row_view,
				new String[] {"title","content","comment"},
				new int[] {R.id.text1,R.id.text2, R.id.text3}
				);
		setListAdapter(adapter);
	}
	
	private void populateList(ArrayList<HashMap<String, String>> list) {
		HashMap<String,String> temp = new HashMap<String,String>();
		temp.put("title","Game purpose");
		temp.put("content", "Zero the oponent points");
		temp.put("comment", "Hint: More points you get, more fast will zero the oponent points");
		list.add(temp);
		HashMap<String,String> temp1 = new HashMap<String,String>();
		temp1.put("title","Number of players");
		temp1.put("content", "2 players");
		temp1.put("comment", "Two players every with unique color");
		list.add(temp1);
		HashMap<String,String> temp2 = new HashMap<String,String>();
		temp2.put("title","Number of teams");
		temp2.put("content", "Two teams of One player each");
		temp2.put("comment", "One for one - to the death!");
		list.add(temp2);
		HashMap<String,String> temp3 = new HashMap<String,String>();
		temp3.put("title","Bonuses");
		temp3.put("content", "Speed, Arrow, Mark, Random, GetPoint, HealPoints");
		temp3.put("comment", "Hint: Use bonuses to get advantage over other players");
		list.add(temp3);
	}
}