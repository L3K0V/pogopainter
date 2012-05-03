package tempest.game.pogopainter.graphics;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class CanvasThread extends Thread {
	private SurfaceHolder _surfaceHolder;
	private Panel _panel;
	private boolean _run = false;
	private final static int FRAME;
	private long startTime;
	private long timeBeforeUpdate;

	static {
		FRAME = 1000;
	}
	public CanvasThread(SurfaceHolder holder, Panel panel) {
		_surfaceHolder = holder;
		_panel = panel;
		setName("CanvasThread");
		timeBeforeUpdate = FRAME;
	}
	
	public void setRunning(boolean run) {
		_run = run;
		if (run == false) {
			timeBeforeUpdate = FRAME;
		}
	}
	
	public void stopThisShit() {
		setRunning(false);
		Thread.currentThread().interrupt();
	}
	
	@Override
	public void run() {
		Canvas canvas;
		while(_run) {
			startTime = System.nanoTime();
			canvas = null;
			try {
				canvas = _surfaceHolder.lockCanvas(null);
				synchronized (_surfaceHolder) {
					if (timeBeforeUpdate <= 0 ) {
						timeBeforeUpdate = FRAME;
						_panel.getGame().update();
					}
					_panel.onDraw(canvas);
					timeBeforeUpdate -= ((System.nanoTime()-startTime)/1000000L);
				}
			} finally {
				if (canvas != null) {
					_surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}
}
