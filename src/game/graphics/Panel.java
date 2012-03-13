package game.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Panel extends SurfaceView implements SurfaceHolder.Callback {
	private CanvasThread _thread;
	
	public Panel(Context context, AttributeSet attrs) {
		super(context, attrs);
		getHolder().addCallback(this);
		_thread = new CanvasThread(getHolder(), this);
		setFocusable(true);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
		_thread.setRunning(true);
		_thread.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;

		_thread.setRunning(false);
		while(retry){
			try {
				_thread.join();
				retry = false;
			} catch(InterruptedException e) {
			}
		}
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		Paint paint = new Paint();
		
		paint.setColor(Color.RED);
		paint.setAntiAlias(true);
		canvas.drawCircle(100, 100, 100, paint);
	}
}
