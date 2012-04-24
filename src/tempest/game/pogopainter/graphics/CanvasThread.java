package tempest.game.pogopainter.graphics;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class CanvasThread extends Thread {
	private SurfaceHolder _surfaceHolder;
	private Panel _panel;
	private boolean _run = false;

	public CanvasThread(SurfaceHolder holder, Panel panel) {
		_surfaceHolder = holder;
		_panel = panel;
		setName("CanvasThread");
	}
	
	public void setRunning(boolean run) {
		_run = run;
	}
	
	public void stopThisShit() {
//		boolean retry = true;
//		while (retry) {
//			try {
				setRunning(false);
//				Thread.currentThread().join();
//				retry = false;
//			} catch (InterruptedException e) {}
//		}
		Thread.currentThread().interrupt();
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
					}
				}
			} finally {
				if (canvas != null) {
					_surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}
}
