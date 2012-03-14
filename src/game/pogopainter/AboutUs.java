package game.pogopainter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class AboutUs extends Activity {
	private String tag = "About";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_us);
		Log.d(tag, "us");
	}
};