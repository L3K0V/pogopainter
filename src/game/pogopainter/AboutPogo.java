package game.pogopainter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class AboutPogo extends Activity{

	private String tag = "About";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		Log.d(tag, "Pogo");
	}
};