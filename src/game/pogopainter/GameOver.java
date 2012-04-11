package game.pogopainter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

public class GameOver extends Activity implements OnClickListener {

	private ProgressBar redPlayer;
	private ProgressBar bluePlayer;
	private ProgressBar greenPlayer;
	private ProgressBar yellowPlayer;
	private Intent gameIntent;
	private boolean incrementRunning = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_over);
		gameIntent = getIntent();

		initPlayersProgress();
		setProgressMaxPoints();
		setPlayersProgressState();
		setPlayersPointsIndicators();
		
		View play = findViewById(R.id.play_again);
		play.setOnClickListener(this);
		
		View menu = findViewById(R.id.go_to_menu);
		menu.setOnClickListener(this);
		
	}

	private void setPlayersPointsIndicators() {
		TextView redPoints = (TextView) findViewById(R.id.red_player_points);
		redPoints.setText("Points:" + Integer.toString(gameIntent.getIntExtra("RED_PLAYER_POINTS", 0)));

		TextView bluePoints = (TextView) findViewById(R.id.blue_player_points);
		bluePoints.setText("Points:" + Integer.toString(gameIntent.getIntExtra("BLUE_PLAYER_POINTS", 0)));

		TextView greenPlayer = (TextView) findViewById(R.id.green_player_points);
		greenPlayer.setText("Points:" + Integer.toString(gameIntent.getIntExtra("GREEN_PLAYER_POINTS", 0)));

		TextView yellowPlayer = (TextView) findViewById(R.id.yellow_player_points);
		yellowPlayer.setText("Points:" + Integer.toString(gameIntent.getIntExtra("YELLOW_PLAYER_POINTS", 0)));
	}

	private void setPlayersProgressState() {
		redPlayer.setProgress(0);
		bluePlayer.setProgress(0);
		greenPlayer.setProgress(0);
		yellowPlayer.setProgress(0);

		final int red = gameIntent.getIntExtra("RED_PLAYER_POINTS", 0);
		final int blue = gameIntent.getIntExtra("BLUE_PLAYER_POINTS", 0);
		final int green = gameIntent.getIntExtra("GREEN_PLAYER_POINTS", 0);
		final int yellow = gameIntent.getIntExtra("YELLOW_PLAYER_POINTS", 0);

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
						Thread.sleep(50);
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

	private void initPlayersProgress() {
		redPlayer    = (ProgressBar) findViewById(R.id.red_player_progress);
		bluePlayer   = (ProgressBar) findViewById(R.id.blue_player_progress);
		greenPlayer  = (ProgressBar) findViewById(R.id.green_player_progress);
		yellowPlayer = (ProgressBar) findViewById(R.id.yellow_player_progress);

	}

	private void setProgressMaxPoints() {
		redPlayer.setMax(gameIntent.getIntExtra("ALL_PLAYERS_POINTS", 0));
		bluePlayer.setMax(gameIntent.getIntExtra("ALL_PLAYERS_POINTS", 0));
		greenPlayer.setMax(gameIntent.getIntExtra("ALL_PLAYERS_POINTS", 0));
		yellowPlayer.setMax(gameIntent.getIntExtra("ALL_PLAYERS_POINTS", 0));
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
