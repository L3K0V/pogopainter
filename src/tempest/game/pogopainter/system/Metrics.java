package tempest.game.pogopainter.system;

import tempest.game.pogopainter.activities.PogoPainterActivity;
import tempest.game.pogopainter.player.Difficulty;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

public class Metrics {
	private Context context = PogoPainterActivity.getAppContext();
	private String tag = "Metrics ";

	private int WIDTH;
	private int HEIGHT;

	private int CLASSIC_BOARD_SIZE;
	private int CLASSIC_GAME_TIME;
	private Difficulty CLASSIC_GAME_DIFFICULTY;

	private int COOP_BOARD_SIZE;
	private int COOP_GAME_TIME;
	private Difficulty COOP_GAME_DIFFICULTY;

	private int DEATHMATCH_BOARD_SIZE;
	private int DEATHMATCH_ROUND_POINTS;

	private int playerColor;

	private boolean LeftControls;

	public Metrics() {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(displayMetrics);

		WIDTH  = displayMetrics.widthPixels;
		Log.d(tag + " width ", new Integer(WIDTH).toString());
		HEIGHT = displayMetrics.heightPixels;
		Log.d(tag + " height ", new Integer(HEIGHT).toString());

		classic(pref);
		coop(pref);
		deathmatch(pref);
		general(pref);

		context = null;
	}

	private void general(SharedPreferences pref) {
		String color = pref.getString("PLAYER_COLOR", "red");
		if (color.equals("red")) {
			playerColor = Color.RED;
			Log.d(tag + "player color ", color);
		} else if (color.equals("blue")) {
			playerColor = Color.BLUE;
			Log.d(tag + "player color ", color);
		} else if (color.equals("green")) {
			playerColor = Color.GREEN;
			Log.d(tag + "player color ", color);
		} else if (color.equals("yellow")) {
			playerColor = Color.YELLOW;
			Log.d(tag + "player color ", color);
		}

		LeftControls = pref.getBoolean("CONTROLS", true);
	}

	private void deathmatch(SharedPreferences pref) {
		DEATHMATCH_BOARD_SIZE = Integer.parseInt(pref.getString("DEATHMATCH_BOARD_SIZE", "6"));
		Log.d(tag + "deathmatch board size ", Integer.toString(DEATHMATCH_BOARD_SIZE));

		DEATHMATCH_ROUND_POINTS = Integer.parseInt(pref.getString("DEATHMATCH_POINTS", "100"));
		Log.d(tag + "deathmatch round points ", Integer.toString(DEATHMATCH_ROUND_POINTS));
	}

	private void coop(SharedPreferences pref) {
		String diff;
		COOP_BOARD_SIZE = Integer.parseInt(pref.getString("COOP_BOARD_SIZE", "9"));
		Log.d(tag + "coop board size ", Integer.toString(COOP_BOARD_SIZE));

		COOP_GAME_TIME = Integer.parseInt(pref.getString("COOP_GAME_TIME", "90"));
		Log.d(tag + "coop game time ", Integer.toString(COOP_GAME_TIME));

		diff = pref.getString("COOP_GAME_DIFFICULTY", "normal");
		if (diff.equals("easy")) {
			COOP_GAME_DIFFICULTY = Difficulty.EASY;
		} else if (diff.equals("normal")) {
			COOP_GAME_DIFFICULTY = Difficulty.NORMAL;
		} else if (diff.equals("hard")) {
			COOP_GAME_DIFFICULTY = Difficulty.HARD;
		}
	}

	private void classic(SharedPreferences pref) {
		String diff;
		CLASSIC_BOARD_SIZE = Integer.parseInt(pref.getString("CLASSIC_BOARD_SIZE", "8"));
		Log.d(tag + " classic board size ", Integer.toString(CLASSIC_BOARD_SIZE));

		CLASSIC_GAME_TIME = Integer.parseInt(pref.getString("CLASSIC_GAME_TIME", "90"));
		Log.d(tag + "classic game time ", Integer.toString(CLASSIC_GAME_TIME));

		diff = pref.getString("CLASSIC_GAME_DIFFICULTY", "easy");
		if (diff.equals("easy")) {
			CLASSIC_GAME_DIFFICULTY = Difficulty.EASY;
		} else if (diff.equals("normal")) {
			CLASSIC_GAME_DIFFICULTY = Difficulty.NORMAL;
		} else if (diff.equals("hard")) {
			CLASSIC_GAME_DIFFICULTY = Difficulty.HARD;
		}
	}

	public int getClassicCellNumber() {
		return this.CLASSIC_BOARD_SIZE;
	}

	public int getClassicGameTime() {
		return this.CLASSIC_GAME_TIME;
	}

	public Difficulty getClassicGameDifficulty() {
		return this.CLASSIC_GAME_DIFFICULTY;
	}

	public int getCoopCellNumber() {
		return this.COOP_BOARD_SIZE;
	}

	public int getCoopGameTime() {
		return this.COOP_GAME_TIME;
	}

	public Difficulty getCoopGameDifficulty() {
		return this.COOP_GAME_DIFFICULTY;
	}

	public int getDeathmatchCellNumber() {
		return this.DEATHMATCH_BOARD_SIZE;
	}

	public int getDeathmatchRoundPoints() {
		return this.DEATHMATCH_ROUND_POINTS;
	}

	public int getScreenWidth() {
		return this.WIDTH;
	}

	public int getScreenHeight() {
		return this.HEIGHT;
	}

	public int getPlayerColor() {
		return this.playerColor;
	}

	public boolean isLeftControls() {
		return LeftControls;
	}
}
