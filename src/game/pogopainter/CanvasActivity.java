package game.pogopainter;

import game.pogopainter.R;
import game.system.Metrics;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;

public class CanvasActivity extends Activity {
	private String tag = "Canvas";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Metrics m = new Metrics();
		Log.i(tag, Boolean.toString(m.isLeftControls()));
		if (m.isLeftControls() == true) {
			String pos = "LEFT CONTROLS";
			Log.d(tag, "Load " + pos);
			setContentView(R.layout.canvas_left_controls);
		} else if (m.isLeftControls() == false) {
			String pos = "RIGHT CONTROLS";
			Log.d(tag, "Load " + pos);
			setContentView(R.layout.canvas_right_controls);
		}
		View canvasPanel = findViewById(R.id.canvasPanel);
		LayoutParams params = new LayoutParams(m.getScreenHeight(), m.getScreenHeight());
		canvasPanel.setLayoutParams(params);
		
	}
}