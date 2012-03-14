package game.pogopainter;

import game.pogopainter.R;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

public class CanvasActivity extends Activity {
	private  int numberOfCells;
	private int gameTime;
	private static String tag = "Canvas";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.canvas);
		
		Context context = getApplicationContext();
		
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		numberOfCells = Integer.parseInt(pref.getString("CLASSIC_BOARD_SIZE", "8"));
		Log.d(tag + " board size", pref.getString("CLASSIC_BOARD_SIZE", ""));
		gameTime = Integer.parseInt(pref.getString("CLASSIC_GAME_TIME", "90"));
		Log.d(tag + " game time", pref.getString("CLASSIC_GAME_TIME", ""));
	}
	
	public int getNumberOfCells() {
		return this.numberOfCells;
	}
	
	public int getGameTime() {
		return this.gameTime;
	}
		
//	public static Context getAppContext() {
//        return CanvasActivity.context;
//    }
}