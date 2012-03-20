package game.graphics;

import game.gametypes.ClassicGameType;
import game.player.Player;
import game.pogopainter.CanvasActivity;
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
	private ClassicGameType game;
	private int cellNumber;
	private int cellSize;
	private String tag = "Canvas";

	public Panel(Context context, AttributeSet attrs) {
		super(context, attrs);
		getHolder().addCallback(this);
		_thread = new CanvasThread(getHolder(), this);
		game = new ClassicGameType();
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
		drawNav(canvas);
		drawAIs(canvas);
	}
	
	public void update() {
		Direction dir = CanvasActivity.getDir();
		game.update(dir);
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

	private void drawNav(Canvas canvas) {
		if (game.getActions().checkDir(Direction.RIGHT, game.getUser().get(0), game.getBoard().getBoardSize())) {
			drawArrow(Direction.RIGHT, canvas);
		}
		
		if (game.getActions().checkDir(Direction.LEFT, game.getUser().get(0), game.getBoard().getBoardSize())) {
			drawArrow(Direction.LEFT, canvas);
		}
		
		if (game.getActions().checkDir(Direction.UP, game.getUser().get(0), game.getBoard().getBoardSize())) {
			drawArrow(Direction.UP, canvas);
		}
		
		if (game.getActions().checkDir(Direction.DOWN, game.getUser().get(0), game.getBoard().getBoardSize())) {
			drawArrow(Direction.DOWN, canvas);
		}
		
	}
	
	private void drawArrow(Direction dir, Canvas canvas) {
		int width = 0;
		int height = 0;
		Bitmap arrow = getArrow(dir);
		switch(dir) {
		case RIGHT:
			width = (game.getUser().get(0).getX() + 1) * cellSize;
			height = game.getUser().get(0).getY() * cellSize;
			break;
		case LEFT:
			width = (game.getUser().get(0).getX() - 1) * cellSize;
			height = game.getUser().get(0).getY() * cellSize;
			break;
		case UP:
			width = game.getUser().get(0).getX() * cellSize;
			height = (game.getUser().get(0).getY() - 1) * cellSize;
			break;
		case DOWN:
			width = game.getUser().get(0).getX() * cellSize;
			height = (game.getUser().get(0).getY() + 1) * cellSize;
			break;
		}
		
		Rect rect = new Rect(width, height, width + cellSize, height + cellSize);
		canvas.drawBitmap(arrow, null, rect, null);

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
