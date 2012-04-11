package game.pogopainter;

import java.util.List;

import game.graphics.ClassicPanel;
import game.player.Player;

import com.bugsense.trace.BugSenseHandler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;


public class CanvasActivity extends Activity {
	public ClassicPanel panel;
	private Intent gameover;
	private int AllPoints;
	public static boolean showResults;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		panel = new ClassicPanel(this, this);
		setContentView(panel);
		BugSenseHandler.setup(this, "318415b5");;
	}

	@Override
	protected void onPause() {
		super.onPause();
		panel.pauseThreads();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		panel.pauseThreads();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (showResults) {
			gameover = new Intent(this, GameOver.class);

			List<Player> users = panel.getGame().getUser();
			List<Player> AIs = panel.getGame().getAIs();

			collectPlayersPoints(users);
			collectPlayersPoints(AIs);

			gameover.putExtra("ALL_PLAYERS_POINTS", AllPoints);

			this.startActivity(gameover);
			showResults = false;
		}
		panel.stopThreads();	
	}

	private void collectPlayersPoints(List<Player> players) {
		for (Player p : players) {
			switch (p.getColor()) {
			case Color.RED:
				gameover.putExtra("RED_PLAYER_POINTS", p.getPoints());
				break;
			case Color.BLUE:
				gameover.putExtra("BLUE_PLAYER_POINTS", p.getPoints());
				break;
			case Color.GREEN:
				gameover.putExtra("GREEN_PLAYER_POINTS", p.getPoints());
				break;
			case Color.YELLOW:
				gameover.putExtra("YELLOW_PLAYER_POINTS", p.getPoints());
				break;

			}
			AllPoints += p.getPoints();
		}
	}
}