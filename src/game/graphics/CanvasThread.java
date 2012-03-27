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
					_panel.onDraw(canvas);
					synchronized (_panel) {
						_panel.notify();
						_panel.wait(400);
					}
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
