package tempest.game.pogopainter.activities;

import java.util.ArrayList;
import java.util.HashMap;

import tempeset.game.pogopainter.R;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

public class ScoreSystem extends ListActivity {

	ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_score_help);


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
		HashMap<String,String> score = new HashMap<String,String>();
		score.put("title", "One square");
		score.put("content", "1 point");
		list.add(score);
		HashMap<String,String> score1 = new HashMap<String,String>();
		score1.put("title", "One column");
		score1.put("content", "10 (8 + 2 bonus)");
		score1.put("comment", "");
		list.add(score1);
		HashMap<String,String> score2 = new HashMap<String,String>();
		score2.put("title", "One row");
		score2.put("content", "10 (8 + 2 bonus)");
		score2.put("comment", "");
		list.add(score2);
		HashMap<String,String> score3 = new HashMap<String,String>();
		score3.put("title", "Two neighbours columns");
		score3.put("content", "25 (2 * One col + 5 bonus) points");
		score3.put("comment", "");
		list.add(score3);
		HashMap<String,String> score4 = new HashMap<String,String>();
		score4.put("title", "Two neighbours rows");
		score4.put("content", "25 (2 * One row + 5 bonus) points");
		score4.put("comment", "");
		list.add(score4);
	}

}
