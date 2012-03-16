package game.system;

import game.pogopainter.PogoPainterActivity;
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

	private int CLASSIC_BOARD_SIZE;
	private int CLASSIC_GAME_TIME;

	private int WIDTH;
	private int HEIGHT;

	private int COOP_BOARD_SIZE;
	private int COOP_GAME_TIME;

	private int D_BOARD_SIZE;
	private int D_ROUND_POINTS;

	private int playerColor;

	private boolean LeftControls = true;

	public Metrics() {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(displayMetrics);

		WIDTH  = displayMetrics.widthPixels;
		Log.d(tag + " width ", new Integer(WIDTH).toString());
		HEIGHT = displayMetrics.heightPixels;
		Log.d(tag + " height ", new Integer(HEIGHT).toString());

		CLASSIC_BOARD_SIZE = Integer.parseInt(pref.getString("CLASSIC_BOARD_SIZE", ""));
		Log.d(tag + " classic board size ", Integer.toString(CLASSIC_BOARD_SIZE));

		CLASSIC_GAME_TIME = Integer.parseInt(pref.getString("CLASSIC_GAME_TIME", "0"));
		Log.d(tag + "classic game time ", Integer.toString(CLASSIC_GAME_TIME));

		COOP_BOARD_SIZE = Integer.parseInt(pref.getString("COOP_BOARD_SIZE", "0"));
		Log.d(tag + "coop board size ", Integer.toString(COOP_BOARD_SIZE));

		COOP_GAME_TIME = Integer.parseInt(pref.getString("COOP_GAME_TIME", "0"));
		Log.d(tag + "coop game time ", Integer.toString(COOP_GAME_TIME));

		D_BOARD_SIZE = Integer.parseInt(pref.getString("DEATHMATCH_BOARD_SIZE", "0"));
		Log.d(tag + "deathmatch board size ", Integer.toString(D_BOARD_SIZE));

		D_ROUND_POINTS = Integer.parseInt(pref.getString("DEATHMATCH_POINTS", "0"));
		Log.d(tag + "deathmatch round points ", Integer.toString(D_ROUND_POINTS));
		
		String color = pref.getString("PLAYER_COLOR", "0");
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
		} else {
			Log.e(color, "is not defined!");
		}
		
		if (pref.getString("PLAYER_CONTROLS", "left").equals("right")) {
			LeftControls = false;
			Log.d(tag + "controls ", pref.getString("PLAYER_CONTROLS", "error"));
		}
		
		context = null;
	}

	public int getClassicCellNumber() {
		return this.CLASSIC_BOARD_SIZE;
	}

	public int getClassicGameTime() {
		return this.CLASSIC_GAME_TIME;
	}


	public int getCoopCellNumber() {
		return this.COOP_BOARD_SIZE;
	}

	public int getCoopGameTime() {
		return this.COOP_GAME_TIME;
	}

	public int getDeathmatchCellNumber() {
		return this.COOP_BOARD_SIZE;
	}

	public int getDeathmatchRoundTime() {
		return this.COOP_GAME_TIME;
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
