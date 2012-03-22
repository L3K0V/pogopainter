package game.pogopainter;

import android.app.Activity;
import android.os.Bundle;


public class CanvasActivity extends Activity {
	private String tag = "Canvas";


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.canvas);
	}
}