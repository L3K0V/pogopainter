package game.graphics;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class CanvasThread extends Thread {
	private SurfaceHolder _surfaceHolder;
	private Panel _panel;
	private boolean _run = false;

	public CanvasThread(SurfaceHolder holder, Panel panel) {
		_surfaceHolder = holder;
		_panel = panel;
	}
	
	public void setRunning(boolean run) {
		_run = run;
	}
	
	@Override
	public void run() {
		Canvas canvas;
		while(_run) {
			canvas = null;
			try {
				canvas = _surfaceHolder.lockCanvas(null);
				synchronized (_surfaceHolder) {
					_panel.update();
					_panel.onDraw(canvas);
					Thread.sleep(300);
				}
			} catch (InterruptedException e) {
			} finally {
				if (canvas != null) {
					_surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}
}
