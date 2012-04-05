package game.pogopainter;

import game.graphics.ClassicPanel;

import com.bugsense.trace.BugSenseHandler;
import android.app.Activity;
import android.os.Bundle;


public class CanvasActivity extends Activity {
	public ClassicPanel panel;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.canvas);
		panel = new ClassicPanel(this);
		setContentView(panel);
		BugSenseHandler.setup(this, "318415b5");
	}
}