package tempest.game.pogopainter.graphics;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class CanvasThread extends Thread {
	private SurfaceHolder surfaceHolder;
	private Panel panel;
	private boolean run = false;
	private final static int FRAME;
	private long startTime;
	private long timeBeforeUpdate;

	static {
		FRAME = 1000;
	}
	public CanvasThread(SurfaceHolder holder, Panel panel) {
		this.surfaceHolder = holder;
		this.panel = panel;
		this.timeBeforeUpdate = FRAME;
		setName("CanvasThread");
	}
	
	public void setRunning(boolean run) {
		this.run = run;
		if (this.run == false) {
			timeBeforeUpdate = FRAME;
		}
	}
	
	public void stopThread() {
		setRunning(false);
		Thread.currentThread().interrupt();
	}
	
	@Override
	public void run() {
		Canvas canvas;
		while(run) {
			startTime = System.nanoTime();
			canvas = null;
			try {
				canvas = surfaceHolder.lockCanvas(null);
				synchronized (surfaceHolder) {
					if (timeBeforeUpdate <= 0 ) {
						timeBeforeUpdate = FRAME;
						panel.getGame().update();
					}
					panel.onDraw(canvas);
					timeBeforeUpdate -= ((System.nanoTime() - startTime)/1000000L);
				}
			} finally {
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}
}
