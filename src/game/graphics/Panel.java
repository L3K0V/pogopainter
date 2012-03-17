package game.graphics;

import game.gametypes.ClassicGameType;
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
	private ClassicGameType game = new ClassicGameType();
	private String tag = "Canvas";
	
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
		drawBoard(canvas);
		drawPlayers(canvas);
		_thread.setRunning(false);
	}

	private void drawPlayers(Canvas canvas) {
		Paint paint = new Paint();
		
	}

	private void drawBoard(Canvas canvas) {
		Paint paint = new Paint();
		
		int cellNumber = game.getBoard().getBoardSize();
		int cellSize = (canvas.getHeight() / cellNumber ) - 1;
		
		for (int y = 0; y < cellNumber; y++) {
			for (int x = 0; x < cellNumber; x++) {
				
				int color  = game.getBoard().getCellAt(x, y).getColor();
				int width  = game.getBoard().getCellAt(x, y).getX();
				int height = game.getBoard().getCellAt(x, y).getY();
				Log.d(tag, "line" + x + " " + y);
				
				paint.setStyle(Paint.Style.FILL);
				paint.setColor(color);
				Rect rect = new Rect((width * cellSize), (height * cellSize), (width * cellSize) + cellSize, (height * cellSize) + cellSize);
				canvas.drawRect(rect, paint);
				
				paint.setStyle(Paint.Style.STROKE);
				paint.setColor(Color.WHITE);
				canvas.drawRect(rect, paint);
			}
		}
	}
}
