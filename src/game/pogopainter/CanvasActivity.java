package game.pogopainter;

import game.pogopainter.R;
import game.system.Direction;
import game.system.Metrics;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ToggleButton;
import android.widget.FrameLayout.LayoutParams;

public class CanvasActivity extends Activity implements OnClickListener {
	private String tag = "Canvas";
	private static ToggleButton up; 
	private static ToggleButton left;
	private static ToggleButton right;
	private static ToggleButton down;

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

		up = (ToggleButton) findViewById(R.id.buttonUp);
		up.setOnClickListener(this);
		
		left = (ToggleButton)findViewById(R.id.buttonLeft);
		left.setOnClickListener(this);

		right = (ToggleButton)findViewById(R.id.buttonRight);
		right.setOnClickListener(this);	

		down = (ToggleButton)findViewById(R.id.buttonDown);
		down.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonUp:
			up.setChecked(true);
			down.setChecked(false);
			left.setChecked(false);
			right.setChecked(false);
			break;
		case R.id.buttonDown:
			down.setChecked(true);
			up.setChecked(false);
			left.setChecked(false);
			right.setChecked(false);
			break;
		case R.id.buttonLeft:
			left.setChecked(true);
			up.setChecked(false);
			down.setChecked(false);
			right.setChecked(false);
			break;
		case R.id.buttonRight:
			right.setChecked(true);
			up.setChecked(false);
			down.setChecked(false);
			left.setChecked(false);
			break;
		case R.id.buttonAction:
			break;

		}
	}
	
	public static Direction getDir() {
		Direction dir = Direction.NONE;
		if (up.isChecked()) {
			dir = Direction.UP;
		} else if (right.isChecked()) {
			dir = Direction.RIGHT;
		} else if (down.isChecked()) {
			dir = Direction.DOWN;
		} else if (left.isChecked()) {
			dir = Direction.LEFT;
		}
		return dir; 
	}
}