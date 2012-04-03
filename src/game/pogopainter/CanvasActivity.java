package game.pogopainter;

import game.graphics.Panel;

import com.bugsense.trace.BugSenseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class CanvasActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.canvas);

		
		BugSenseHandler.setup(this, "318415b5");
	}
}