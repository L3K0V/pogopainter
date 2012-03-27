package game.graphics;

import game.bonuses.BonusObject;
import game.bonuses.Bonuses;
import game.gametypes.ClassicGameType;
import game.player.Player;
import game.pogopainter.R;
import game.system.Cell;
import game.system.Direction;
import game.system.Metrics;
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
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class Panel extends SurfaceView implements SurfaceHolder.Callback {
	private CanvasThread _thread;
	private ClassicGameType game;

	private int cellNumber;
	private int cellSize;
	private int controlStartX;
	private int boardStartX;
	private int counterY;
	private Rect controlRect;
	private boolean leftControlns;
	private String tag = "Canvas";
	
	private Rect up;
	private Rect down;
	private Rect left;
	private Rect right;
	private Direction currentDir = Direction.NONE;

	public Panel(Context context, AttributeSet attrs) {
		super(context, attrs);
		getHolder().addCallback(this);
		_thread = new CanvasThread(getHolder(), this);
		setFocusable(true);
		initFields();
	}

	private void initFields() {
		game = new ClassicGameType(this);
		Metrics m = new Metrics();
		leftControlns = m.isLeftControls();
		cellNumber = game.getBoard().getBoardSize();
		cellSize = (m.getScreenHeight() / cellNumber ) - 1;	
		if (leftControlns) {
			controlStartX = 0;
			boardStartX = (m.getScreenWidth() - (cellNumber * cellSize)) - 1;
			controlRect = new Rect(controlStartX + (cellSize / 5), 3 * cellSize, m.getScreenWidth() - (m.getScreenWidth() - boardStartX) - (cellSize / 4), m.getScreenHeight());
		} else {
			controlStartX = cellNumber * cellSize;
			boardStartX = 0;
			controlRect = new Rect(controlStartX + (cellSize / 5), 3 * cellSize, m.getScreenWidth(), m.getScreenHeight());
		}
		
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
		_thread.interrupt();
		while(retry){
			try {
				_thread.join();
				retry = false;
			} catch(InterruptedException e) {
			}
		}
		game.stopThread();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		checkControls(x, y);
		return true;
	}
	
	private void checkControls(int x, int y) {
		if (up.contains(x, y)) {
			currentDir = Direction.UP;
		} else if (right.contains(x, y)) {
			currentDir = Direction.RIGHT;
		} else if (down.contains(x, y)) {
			currentDir = Direction.DOWN;
		} else if (left.contains(x, y)) {
			currentDir = Direction.LEFT;
		}
	}

	@Override
	public void onDraw(Canvas canvas) {
		counterY = cellSize / 4;
		canvas.drawColor(Color.BLACK);
		drawBoard(canvas);
		drawUsers(canvas);
		drawNav(canvas);
		drawAIs(canvas);
		drawPointCounters(canvas);
		drawControls(canvas);
		drawDirection(canvas);
	}
	
	public CanvasThread getThread() {
		return _thread;
	}
	
	public ClassicGameType getGame() {
		return game;
	}
	
	private void drawDirection(Canvas canvas) {
		if (getDirection() != Direction.NONE) {
			Bitmap clicked = getRotatedBitmap(getDirection(), R.drawable.joystick_clicked);
			canvas.drawBitmap(clicked, null, controlRect, null);
		}
	}

	public Direction getDirection() {
		return currentDir;
	}

	private void drawControls(Canvas canvas) {
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.MAGENTA);
		int centerX = controlRect.centerX();
		int centerY = controlRect.centerY();
		int width = controlRect.width();
		int height = controlRect.height();
		Bitmap controls = getRotatedBitmap(Direction.RIGHT, R.drawable.joystick);
		canvas.drawBitmap(controls, null, controlRect, null);

		down = new Rect(centerX - (width / 4), centerY + (height / 4) - (cellSize / 2), centerX + (width / 4), controlRect.bottom);
		up = new Rect(centerX - (width / 4), controlRect.top, centerX + (width / 4), centerY - (height / 4) + (cellSize / 2));
		left = new Rect(controlRect.left, centerY - (height / 4), centerX - (width / 4) + (cellSize / 2), centerY + (height / 4));
		right = new Rect(centerX + (width / 4) - (cellSize / 2), centerY - (height / 4), controlRect.right, centerY + (height / 4));
		canvas.drawRect(up, paint);
		canvas.drawRect(down, paint);
		canvas.drawRect(left, paint);
		canvas.drawRect(right, paint);	
	}

	private void drawPointCounters(Canvas canvas) {
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setAntiAlias(true);
		paint.setTextSize(cellSize / 2);
		drawCounter(Color.RED, paint, canvas, game.getUser().get(0).getPoints());
		drawCounter(Color.BLUE, paint, canvas, game.getAIs().get(0).getPoints());
		drawCounter(Color.GREEN, paint, canvas, game.getAIs().get(1).getPoints());
		drawCounter(Color.YELLOW, paint, canvas, game.getAIs().get(2).getPoints());
	}

	private void drawCounter(int color, Paint paint, Canvas canvas, int points) {
		paint.setColor(color);
		String spoints = Integer.toString(points);
		Bitmap bitmap = getPlayerColor(color);
		Rect rect = new Rect(controlStartX + cellSize, counterY, (controlStartX + cellSize) + ((cellSize / 3) * 2), counterY + ((cellSize / 3) * 2));
		canvas.drawBitmap(bitmap, null, rect, null);
		canvas.drawText(spoints, controlStartX + (cellSize * 2) , counterY + (cellSize / 2), paint);
		counterY += (cellSize / 3) * 2;
	
	}

	private void drawBoard(Canvas canvas) {
		Paint paint = new Paint();
		for (int y = 0; y < cellNumber; y++) {
			for (int x = 0; x < cellNumber; x++) {

				Cell cell = game.getBoard().getCellAt(x, y);
				int color  = cell.getColor();
				int width = (cell.getX() * cellSize ) + boardStartX;
				int height = cell.getY() * cellSize;

				paint.setStyle(Paint.Style.FILL);
				paint.setColor(color);
				Rect rect = new Rect(width, height, width + cellSize, height + cellSize);
				canvas.drawRect(rect, paint);

				paint.setStyle(Paint.Style.STROKE);
				paint.setColor(Color.WHITE);
				canvas.drawRect(rect, paint);
				
				if (cell.getBonus() != null) {
					drawBonus(canvas, rect, cell.getBonus().getType());
				}
			}
		}
	}

	private void drawUsers(Canvas canvas) {
		for (Player user: game.getUser()) {
			int width = (user.getX() * cellSize) + boardStartX;
			int height = user.getY() * cellSize;
			int color = user.getColor();

			Rect rect = new Rect(width, height, width + cellSize, height + cellSize);
			Bitmap bitmap = getPlayerColor(color);

			canvas.drawBitmap(bitmap, null, rect, null);
		}
	}
	
	private void drawBonus(Canvas canvas, Rect rect, Bonuses type) {
		Bitmap bitmap = null;
		switch (type) {
		case CHECKPOINT:
			bitmap = getRotatedBitmap(Direction.RIGHT, R.drawable.checkpoint);
			break;
		}
		canvas.drawBitmap(bitmap, null, rect, null);	
	}
	
	private void drawAIs(Canvas canvas) {
		for (Player ai: game.getAIs()) {
			int width = (ai.getX() * cellSize) + boardStartX;
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
		Bitmap arrow = getRotatedBitmap(dir, R.drawable.arrow);
		switch(dir) {
		case RIGHT:
			width = ((game.getUser().get(0).getX() + 1) * cellSize) + boardStartX;
			height = game.getUser().get(0).getY() * cellSize;
			break;
		case LEFT:
			width = ((game.getUser().get(0).getX() - 1) * cellSize ) + boardStartX;
			height = game.getUser().get(0).getY() * cellSize;
			break;
		case UP:
			width = (game.getUser().get(0).getX() * cellSize) + boardStartX;
			height = (game.getUser().get(0).getY() - 1) * cellSize;
			break;
		case DOWN:
			width = (game.getUser().get(0).getX() * cellSize) + boardStartX;
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

	private Bitmap getRotatedBitmap(Direction dir, int resID) {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resID);
		Bitmap rotatedBitmap = null;
		Matrix mat = new Matrix();

		switch (dir) {
		case LEFT:
			mat.postRotate(180);
			rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true);
			break;
		case RIGHT:
			rotatedBitmap = bitmap;
			break;
		case UP:
			mat.postRotate(270);
			rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true);
			break;
		case DOWN:
			mat.postRotate(90);
			rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true);
			break;
		}
		return rotatedBitmap;
	}
}