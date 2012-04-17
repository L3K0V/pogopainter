package game.pogopainter;

import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

public class GameOver extends Activity implements OnClickListener {

	protected ProgressBar redPlayer;
	protected ProgressBar bluePlayer;
	protected ProgressBar greenPlayer;
	protected ProgressBar yellowPlayer;
	protected Intent gameIntent;
	protected boolean incrementRunning = false;

	protected int red;
	protected int blue;
	protected int green;
	protected int yellow;
	private ValueComparator pointsComparator;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_over);
		gameIntent = getIntent();

		initPlayersProgress();
		setProgressMaxPoints();
		setPlayersProgressState();
		setPlayersPointsIndicators();
		
		getWinner();

		View play = findViewById(R.id.play_again);
		play.setOnClickListener(this);

		View menu = findViewById(R.id.go_to_menu);
		menu.setOnClickListener(this);

	}

	protected void setPlayersPointsIndicators() {
		TextView redPoints = (TextView) findViewById(R.id.red_player_points);
		redPoints.setText("Points: " + Integer.toString(gameIntent.getIntExtra("RED_PLAYER_POINTS", 0)));

		TextView bluePoints = (TextView) findViewById(R.id.blue_player_points);
		bluePoints.setText("Points: " + Integer.toString(gameIntent.getIntExtra("BLUE_PLAYER_POINTS", 0)));

		TextView greenPlayer = (TextView) findViewById(R.id.green_player_points);
		greenPlayer.setText("Points: " + Integer.toString(gameIntent.getIntExtra("GREEN_PLAYER_POINTS", 0)));

		TextView yellowPlayer = (TextView) findViewById(R.id.yellow_player_points);
		yellowPlayer.setText("Points: " + Integer.toString(gameIntent.getIntExtra("YELLOW_PLAYER_POINTS", 0)));
	}

	protected void setPlayersProgressState() {
		redPlayer.setProgress(0);
		bluePlayer.setProgress(0);
		greenPlayer.setProgress(0);
		yellowPlayer.setProgress(0);

		red = gameIntent.getIntExtra("RED_PLAYER_POINTS", 0);
		blue = gameIntent.getIntExtra("BLUE_PLAYER_POINTS", 0);
		green = gameIntent.getIntExtra("GREEN_PLAYER_POINTS", 0);
		yellow = gameIntent.getIntExtra("YELLOW_PLAYER_POINTS", 0);

		Thread updateProgress =new Thread(new Runnable() {

			public void run() {

				try {
					int max = gameIntent.getIntExtra("ALL_PLAYERS_POINTS", 0);
					for (int i=0;i<max && incrementRunning;i++) {
						if (redPlayer.getProgress() <= red) {
							redPlayer.setProgress(i);
						}
						if (bluePlayer.getProgress() <= blue) {
							bluePlayer.setProgress(i);
						}
						if (greenPlayer.getProgress() <= green) {
							greenPlayer.setProgress(i);
						}
						if (yellowPlayer.getProgress() <= yellow) {
							yellowPlayer.setProgress(i);
						}
						if (max < 50) {
							Thread.sleep(250);
						} else if (max > 50 && max < 100) {
							Thread.sleep(100);
						} else {
							Thread.sleep(50);
						}

					}

				}

				catch (Throwable t) {

				}     
			}     
		});
		incrementRunning = true;
		updateProgress.start();

	}

	public void onStop() {
		super.onStop();
		incrementRunning = false;
	}

	protected void initPlayersProgress() {
		redPlayer    = (ProgressBar) findViewById(R.id.red_player_progress);
		bluePlayer   = (ProgressBar) findViewById(R.id.blue_player_progress);
		greenPlayer  = (ProgressBar) findViewById(R.id.green_player_progress);
		yellowPlayer = (ProgressBar) findViewById(R.id.yellow_player_progress);

	}

	protected void setProgressMaxPoints() {
		redPlayer.setMax(gameIntent.getIntExtra("ALL_PLAYERS_POINTS", 0));
		bluePlayer.setMax(gameIntent.getIntExtra("ALL_PLAYERS_POINTS", 0));
		greenPlayer.setMax(gameIntent.getIntExtra("ALL_PLAYERS_POINTS", 0));
		yellowPlayer.setMax(gameIntent.getIntExtra("ALL_PLAYERS_POINTS", 0));
	}

	protected void getWinner() {
		TextView winner = (TextView) findViewById(R.id.player_winner_text);
		TextView draw   = (TextView) findViewById(R.id.winners); 
		winner.setText("Nobody");
		
		Map<String, Integer> playersResults = new Hashtable<String, Integer>();
		pointsComparator = new ValueComparator(playersResults);
		TreeMap<String, Integer> sortedPlayersByPoints = new TreeMap<String, Integer>(pointsComparator);
		
		playersResults.put("Red", red);
		playersResults.put("Blue", blue);
		playersResults.put("Green", green);
		playersResults.put("Yellow", yellow);
		
		sortedPlayersByPoints.putAll(playersResults);
		
		winnersNumber(winner, draw, sortedPlayersByPoints);
	}

	private void winnersNumber(TextView winner, TextView draw,
			TreeMap<String, Integer> sortedPlayersByPoints) {
		int firstValue = sortedPlayersByPoints.get(sortedPlayersByPoints.firstKey());
		String firstKey = sortedPlayersByPoints.firstKey();
		
		if (sortedPlayersByPoints.get(sortedPlayersByPoints.firstKey()) != 0) {
			if (sortedPlayersByPoints.size() > 1 && firstValue+1 == firstValue) {
				if (sortedPlayersByPoints.size() > 2 && firstValue+2 == firstValue) {
					if (sortedPlayersByPoints.size() > 3 && firstValue+3 == firstValue && firstValue != 0) {
						winner.setText(firstKey + " and " + firstKey+1 + " and " + firstKey+2 + " and " + firstKey+3);
						draw.setText("are winners");
					} else {
						winner.setText(firstKey + " and " + firstKey+1 + " and " + firstKey+2);
						draw.setText("are winners");
					}
				} else {
					winner.setText(firstKey + " and " + firstKey+1);
					draw.setText("are winners");
				}
				
			} else {
				winner.setText(firstKey);
			}	
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.play_again:
			Intent play = new Intent(this, CanvasActivity.class);
			startActivity(play);
			finish();
			break;
		case R.id.go_to_menu:
			this.finish();
			break;
		}
	}
}

class ValueComparator implements Comparator<Object> {

	  Map<String, Integer> base;
	  public ValueComparator(Map<String, Integer> base) {
	      this.base = base;
	  }

	  public int compare(Object a, Object b) {

	    if((Integer)base.get(a) < (Integer)base.get(b)) {
	      return 1;
	    } else if((Integer)base.get(a) == (Integer)base.get(b)) {
	      return 0;
	    } else {
	      return -1;
	    }
	  }
	}