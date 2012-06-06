package tempest.game.pogopainter.activities;

import java.util.ArrayList;
import java.util.List;

import tempest.game.pogopainter.graphics.ClassicPanel;
import tempest.game.pogopainter.graphics.Panel;
import tempest.game.pogopainter.player.Player;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.Toast;

public class CanvasActivity extends Activity {
	public static boolean SHOW_RESULTS;

	public Panel panel;
	private Intent gameover;
	private int allPoints;
	private AlertDialog dialog;
	
	private PowerManager pm;
	private PowerManager.WakeLock wl;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "CanvasActivity");
		
		wl.acquire();
		panel = new ClassicPanel(this, this);
		setContentView(panel);
		setResult(666);
		Toast.makeText(this, "Generation: " + PogoPainterActivity.genetic.getGeneration(), Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onPause() {
		wl.release();
		panel.pauseThreads();
		panel.getGame().getMusic().pauseMusic();
		onBackPressed();
		dialog.dismiss();
		
		super.onPause();
	}

	@Override
	protected void onResume() {
		wl.acquire();
		panel.resumeThreads();
		panel.getGame().getMusic().playMusic();
		super.onResume();
	}

	@Override
	public void onBackPressed() {
		panel.stopThreads();
		dialog = new AlertDialog.Builder(this)
		.setTitle("Pause Game")
		.setMessage("The Game is Paused. Do you wanna Resume or return to main menu ?")
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setCancelable(false)
		.setNegativeButton("Resume", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				panel.startThreads();
				dialog.dismiss();
			}
		})
		.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				panel.surfaceDestroyed(panel.getHolder());
				setResult(667);
				finish();
				dialog.dismiss();
			}
		})
		.setNeutralButton("Next run", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface arg0, int arg1) {
				panel.surfaceDestroyed(panel.getHolder());
				setResult(666);
				finish();
				dialog.dismiss();
			}
		})
		.show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		wl.release();
		if (SHOW_RESULTS) {
			//gameover = new Intent(this, GameOver.class);

			List<Player> users = new ArrayList<Player>();
			users.add(panel.getGame().getUser());
			List<Player> AIs = panel.getGame().getAIs();

			collectPlayersPoints(users);
			collectPlayersPoints(AIs);

			gameover.putExtra("ALL_PLAYERS_POINTS", allPoints);

			//this.startActivity(gameover);
			SHOW_RESULTS = false;
		}
		panel.stopThreads();
		panel.clearMemory();
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
			allPoints += p.getPoints();
		}
	}
}