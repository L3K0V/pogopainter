package game.graphics;

import game.pogopainter.ExtrasActivity;
import game.system.Board;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Panel extends SurfaceView implements SurfaceHolder.Callback {
	private CanvasThread _thread;
	public ExtrasActivity extras = new ExtrasActivity();
	
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
		canvas.drawColor(Color.YELLOW);
		Paint paint = new Paint();
		
		int cellSize = extras.getCellSize();
		
//		Board board = new Board(8);
//		
//		for (int y = 0; y < 8; y++) {
//			for (int x = 0; x < 8; x++) {
//				int color  = board.getCellAt(x, y).getColor();
//				int width  = board.getCellAt(x, y).getX();
//				int height = board.getCellAt(x, y).getY();
//				
//				paint.setColor(color);
//				Rect rect = new Rect((width * cellSize), (height * cellSize), cellSize, cellSize);
//				canvas.drawRect(rect, paint);
//				
//			}
//		}
		
		paint.setColor(Color.BLACK);
		Rect rect = new Rect((1 * cellSize), (1 * cellSize), cellSize, cellSize);
		canvas.drawRect(rect, paint);
		
		paint.setColor(Color.RED);
	}
}
