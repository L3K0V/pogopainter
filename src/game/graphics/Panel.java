package game.graphics;

import game.gametypes.ClassicGameType;
import game.player.Player;
import game.pogopainter.R;
import game.system.Direction;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class Panel extends SurfaceView implements SurfaceHolder.Callback {
	private CanvasThread _thread;
	private ClassicGameType game = new ClassicGameType();
	private int cellNumber;
	private int cellSize;
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
		cellNumber = game.getBoard().getBoardSize();
		cellSize = (canvas.getHeight() / cellNumber ) - 1;

		canvas.drawColor(Color.BLACK);
		drawBoard(canvas);
		drawUsers(canvas);
		drawArrows(canvas);
		drawAIs(canvas);
		_thread.setRunning(false);
	}

	private void drawBoard(Canvas canvas) {
		Paint paint = new Paint();

		for (int y = 0; y < cellNumber; y++) {
			for (int x = 0; x < cellNumber; x++) {

				int color  = game.getBoard().getCellAt(x, y).getColor();
				int width = game.getBoard().getCellAt(x, y).getX() * cellSize;
				int height = game.getBoard().getCellAt(x, y).getY() * cellSize;

				paint.setStyle(Paint.Style.FILL);
				paint.setColor(color);
				Rect rect = new Rect(width, height, width + cellSize, height + cellSize);
				canvas.drawRect(rect, paint);

				paint.setStyle(Paint.Style.STROKE);
				paint.setColor(Color.WHITE);
				canvas.drawRect(rect, paint);
			}
		}
	}

	private void drawUsers(Canvas canvas) {
		for (Player user: game.getUser()) {
			int width = user.getX() * cellSize;
			int height = user.getY() * cellSize;
			int color = user.getColor();

			Rect rect = new Rect(width, height, width + cellSize, height + cellSize);
			Bitmap bitmap = getPlayerColor(color);

			canvas.drawBitmap(bitmap, null, rect, null);
		}
	}

	private void drawAIs(Canvas canvas) {
		for (Player ai: game.getAIs()) {
			int width = ai.getX() * cellSize;
			int height = ai.getY() * cellSize;
			int color = ai.getColor();

			Rect rect = new Rect(width, height, width + cellSize, height + cellSize);
			Bitmap bitmap = getPlayerColor(color);

			canvas.drawBitmap(bitmap, null, rect, null);
		}
	}

	private void drawArrows(Canvas canvas) {
		int width = game.getUser().get(0).getX() +1 * cellSize;
		int height = game.getUser().get(0).getY() * cellSize;

		int width2 = game.getUser().get(0).getX() * cellSize;
		int height2 = (game.getUser().get(0).getY() -1) * cellSize;

		Bitmap RIGHT = getArrow(Direction.RIGHT);
		Bitmap UP = getArrow(Direction.UP);
		Rect arrow = new Rect(width, height, width + cellSize, height + cellSize);
		Rect arrow2 = new Rect(width2, height2, width2 + cellSize, height2 + cellSize);

		canvas.drawBitmap(RIGHT, null, arrow, null);
		canvas.drawBitmap(UP, null, arrow2, null);
	}

	private Bitmap getPlayerColor(int color) {
		Bitmap bitmap = null;
		switch (color) {
		case Color.RED:
			bitmap =BitmapFactory.decodeResource(getResources(), R.drawable.red); break;
		case Color.BLUE:
			bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blue); break;
		case Color.GREEN:
			bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.green); break;
		case Color.YELLOW:
			bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.yellow); break;
		}
		return bitmap;
	}

	private Bitmap getArrow(Direction dir) {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.arrow);
		Bitmap rotatedBitmap = null;
		Matrix mat;

		switch (dir) {
		case LEFT:
			mat = new Matrix();
			mat.postRotate(180);
			rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true);
			break;
		case RIGHT:
			rotatedBitmap = bitmap;
			break;
		case UP:
			mat = new Matrix();
			mat.postRotate(270);
			rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true);
			break;
		case DOWN:
			mat = new Matrix();
			mat.postRotate(90);
			rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true);
			break;

		}

		return rotatedBitmap;
	}
}
