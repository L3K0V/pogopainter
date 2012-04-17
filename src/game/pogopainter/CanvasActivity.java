package game.pogopainter;

import java.util.ArrayList;
import java.util.List;

import game.graphics.ClassicPanel;
import game.player.Player;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

public class CanvasActivity extends Activity {
	public ClassicPanel panel;
	private Intent gameover;
	private int AllPoints;
	public static boolean showResults;
	private AlertDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		panel = new ClassicPanel(this, this);
		setContentView(panel);
	}

	@Override
	protected void onPause() {
		panel.pauseThreads();
		onBackPressed();
		dialog.dismiss();
		super.onPause();
	}

	@Override
	protected void onResume() {
		panel.resumeThreads();
		super.onResume();
	}

	@Override
	public void onBackPressed() {
		panel.stopThreads();
		dialog = new AlertDialog.Builder(this)
		.setTitle(getString(R.string.exit))
		.setMessage(getString(R.string.exit_question))
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				panel.startThreads();
				dialog.dismiss();
			}
		})
		.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				panel.surfaceDestroyed(panel.getHolder());
				finish();
				dialog.dismiss();
			}
		})
		.show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (showResults) {
			gameover = new Intent(this, GameOver.class);

			List<Player> users = new ArrayList<Player>();
			users.add(panel.getGame().getUser());
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