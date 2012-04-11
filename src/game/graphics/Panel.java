package game.graphics;

import java.io.BufferedInputStream;

import game.bonuses.Arrow;
import game.bonuses.BonusObject;
import game.bonuses.Bonuses;
import game.gametypes.Game;
import game.gametypes.GameThread;
import game.player.Player;
import game.pogopainter.R;
import game.system.Cell;
import game.system.Direction;
import game.system.Metrics;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class Panel extends SurfaceView implements SurfaceHolder.Callback {
	protected CanvasThread _panelThread;
	protected GameThread _gameThread;
	protected Game game;
	protected boolean gamePaused = false;

	protected int cellNumber;
	protected int cellSize;
	protected int controlStartX;
	protected int boardStartX;
	protected int counterY;
	protected Rect controlRect;
	protected boolean leftControlns;
	protected Rect up;
	protected Rect down;
	protected Rect left;
	protected Rect right;
	protected Direction currentDir = Direction.NONE;
	protected int padding;
	protected Rect counterRect;
	protected int screenWidth;
	protected int screenHeigth;
	protected Activity holder;

	public Panel(Context context, Activity owner) {
		super(context);
		holder = owner;
		getHolder().addCallback(this);
		_panelThread = new CanvasThread(getHolder(), this);
		_gameThread = new GameThread(this);
		setFocusable(true);
		initFields();
	}
	
	public Activity getOwner() {
		return holder;
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
		if (!_panelThread.isAlive() || !gamePaused) {
			startThreads();
		} else {
			resumeThreads();
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		if (_panelThread.isAlive()) {
			stopThreads();
		}
	}

	public void pauseThreads() {
		_panelThread.setRunning(false);
		_gameThread.setRunning(false);
		gamePaused = true;
	}

	public void startThreads() {
		_panelThread = new CanvasThread(getHolder(), this);
		_gameThread = new GameThread(this);
		resumeThreads();
		_panelThread.start();
		_gameThread.start();
	}

	public void resumeThreads() {
		_panelThread.setRunning(true);
		_gameThread.setRunning(true);
	}

	public void stopThreads() {
		_panelThread.setRunning(false);
		_panelThread.interrupt();
		_gameThread.setRunning(false);
		_gameThread.interrupt();
	}

	protected void initFields() {
		Metrics m = new Metrics();
		screenWidth = m.getScreenWidth();
		screenHeigth = m.getScreenHeight();
		leftControlns = m.isLeftControls();
		cellNumber = game.getBoard().getBoardSize();
		cellSize = (screenHeigth / cellNumber ) - 1;	
		padding = cellSize / 5;
		if (leftControlns) {
			controlStartX = 0;
			boardStartX = (screenWidth - (cellNumber * cellSize)) - 1;
			Rect control = new Rect(controlStartX + padding / 2, (screenHeigth / 2),
					boardStartX - padding / 2, screenHeigth - padding / 2);

			fixControlRect(control);
			counterRect = new Rect(controlStartX + padding / 2, padding / 2,
					boardStartX - padding / 2, screenHeigth / 2 - padding / 2);
		} else {
			controlStartX = cellNumber * cellSize;
			boardStartX = 0;
			Rect control = new Rect(controlStartX + padding / 2 , (screenHeigth / 2),
					screenWidth - padding / 2 , screenHeigth - padding / 2);
			fixControlRect(control);
			counterRect = new Rect(controlStartX + padding / 2, padding / 2,
					screenWidth - padding / 2, (screenHeigth / 2) - padding / 2);
		}
		defineTouchRect();
	}

	protected void fixControlRect(Rect control) {
		int square = 0;
		if (control.width() > control.height()) {
			square = control.height() - padding;
		} else {
			square = control.width() - padding;
		}

		controlRect = new Rect(control.centerX() - square/2, control.centerY() - square/2,
				control.centerX() + square/2, control.centerY() + square/2);
	}

	protected void defineTouchRect() {
		int centerX = controlRect.centerX();
		int centerY = controlRect.centerY();
		int width = controlRect.height();
		int height = controlRect.height();
		int paddingForTouchRect = padding * 3 + padding / 2;

		down = new Rect(centerX - (width / 4), centerY + paddingForTouchRect,
				centerX + (width / 4), controlRect.bottom);

		up = new Rect(centerX - (width / 4), controlRect.top,
				centerX + (width / 4), centerY - paddingForTouchRect);

		left = new Rect(controlRect.left, centerY - (height / 4),
				centerX - paddingForTouchRect, centerY + (height / 4));

		right = new Rect(centerX + paddingForTouchRect, centerY - (height / 4),
				controlRect.right, centerY + (height / 4));
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		checkControls(x, y);
		return true;
	}

	protected void checkControls(int x, int y) {
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
		counterY = counterRect.top;
		canvas.drawColor(Color.WHITE);
		Bitmap bg = BitmapFactory.decodeResource(getResources(), R.drawable.background);
		canvas.drawBitmap(bg, null, new Rect(0,0,getWidth(),getHeight()), null);
		drawBoard(canvas);
		drawUsers(canvas);
		drawNav(canvas);
		//drawAIs(canvas);
		drawTimer(canvas);
		drawPointCounters(canvas);
		drawControls(canvas);
		drawDirection(canvas);
	}
	
	public void clreadCanvas(Canvas canvas) {
		canvas.drawColor(0,PorterDuff.Mode.CLEAR);
	}

	protected void drawDirection(Canvas canvas) {
		if (getDirection() != Direction.NONE) {
			Bitmap clicked = getRotatedBitmap(getDirection(), R.drawable.joystick_clicked);
			canvas.drawBitmap(clicked, null, controlRect, null);
		}
	}

	protected void drawTimer(Canvas canvas) {
		counterY += counterRect.height() / 5;
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setAntiAlias(true);
		paint.setTextSize(counterRect.height() / 7);
		paint.setColor(Color.GRAY);
		String timeLeft = "Time :" + Integer.toString(game.getTime());
		canvas.drawText(timeLeft, counterRect.centerX() - counterRect.width()/3, counterY, paint);
	}

	protected void drawControls(Canvas canvas) {
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.MAGENTA);
		Bitmap controls = getRotatedBitmap(Direction.RIGHT, R.drawable.joystick);
		canvas.drawBitmap(controls, null, controlRect, null);
	}

	protected void drawPointCounters(Canvas canvas) {
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setAntiAlias(true);
		for (Player player: game.getPlayers()) {
			drawCounter(player.getColor(), paint, canvas, player.getPoints());
		}
	}

	protected void drawCounter(int color, Paint paint, Canvas canvas, int points) {
		paint.setColor(color);
		paint.setTextSize(counterRect.height() / 6);
		String spoints = Integer.toString(points);
		Bitmap bitmap = getPlayerColor(color);

		int counterPadding = counterRect.height() / 5;
		int bitmapStartX = counterRect.left + padding;
		Rect rect = new Rect(bitmapStartX , counterY, 
				bitmapStartX  + counterPadding, counterY + counterPadding);

		canvas.drawBitmap(bitmap, null, rect, null);
		canvas.drawText(spoints, rect.right + padding * 2, counterY + counterPadding - padding, paint);
		counterY += counterPadding;
	}

	protected void drawUsers(Canvas canvas) {
		for (Player user: game.getPlayers()) {
			int width = (user.getX() * cellSize) + boardStartX;
			int height = user.getY() * cellSize;
			int color = user.getColor();

			Rect rect = new Rect(width, height, width + cellSize, height + cellSize);
			Bitmap bitmap = getPlayerColor(color);

			canvas.drawBitmap(bitmap, null, rect, null);
		}
	}

	protected void drawBoard(Canvas canvas) {
		Paint paint = new Paint();
		for (int y = 0; y < cellNumber; y++) {
			for (int x = 0; x < cellNumber; x++) {

				Cell cell = game.getBoard().getCellAt(x, y);
				int color  = cell.getColor();
				int width = (cell.getX() * cellSize ) + boardStartX;
				int height = cell.getY() * cellSize;

				Rect rect = new Rect(width, height, width + cellSize, height + cellSize);
				Bitmap bitmap = getCellColor(color);
				canvas.drawBitmap(bitmap, null, rect, null);

				paint.setStyle(Paint.Style.STROKE);
				paint.setColor(Color.WHITE);
				canvas.drawRect(rect, paint);

				if (cell.getBonus() != null) {
					drawBonus(canvas, rect, cell.getBonus());
				}
			}
		}
	}

	protected void drawBonus(Canvas canvas, Rect rect, BonusObject bonus) {
		Bitmap bitmap = null;
		Bonuses type = Bonuses.NONE;
		if (bonus != null) {
			type = bonus.getType();
		}
		switch (type) {
		case CHECKPOINT:
			bitmap = getRotatedBitmap(Direction.RIGHT, R.drawable.bonus_checkpoint);
			break;
		case ARROW:
			switch (((Arrow) bonus).getState()) {
			case 1:
				bitmap = getRotatedBitmap(Direction.RIGHT, R.drawable.bonus_arrow);
				break;
			case 2:
				bitmap = getRotatedBitmap(Direction.DOWN, R.drawable.bonus_arrow);
				break;
			case 3:
				bitmap = getRotatedBitmap(Direction.LEFT, R.drawable.bonus_arrow);
				break;
			case 4:
				bitmap = getRotatedBitmap(Direction.UP, R.drawable.bonus_arrow);
				break;
			}
		case NONE:
			break;
		}
		canvas.drawBitmap(bitmap, null, rect, null);	
	}

	protected void drawNav(Canvas canvas) {
		if (game.checkDir(Direction.RIGHT, game.getUser(), game.getBoard().getBoardSize())) {
			drawArrow(Direction.RIGHT, canvas);
		}

		if (game.checkDir(Direction.LEFT, game.getUser(), game.getBoard().getBoardSize())) {
			drawArrow(Direction.LEFT, canvas);
		}

		if (game.checkDir(Direction.UP, game.getUser(), game.getBoard().getBoardSize())) {
			drawArrow(Direction.UP, canvas);
		}

		if (game.checkDir(Direction.DOWN, game.getUser(), game.getBoard().getBoardSize())) {
			drawArrow(Direction.DOWN, canvas);
		}

	}

	protected void drawArrow(Direction dir, Canvas canvas) {
		int width = 0;
		int height = 0;
		Bitmap arrow = getRotatedBitmap(dir, R.drawable.arrow);
		switch(dir) {
		case RIGHT:
			width = ((game.getUser().getX() + 1) * cellSize) + boardStartX;
			height = game.getUser().getY() * cellSize;
			break;
		case LEFT:
			width = ((game.getUser().getX() - 1) * cellSize ) + boardStartX;
			height = game.getUser().getY() * cellSize;
			break;
		case UP:
			width = (game.getUser().getX() * cellSize) + boardStartX;
			height = (game.getUser().getY() - 1) * cellSize;
			break;
		case DOWN:
			width = (game.getUser().getX() * cellSize) + boardStartX;
			height = (game.getUser().getY() + 1) * cellSize;
			break;
		}

		Rect rect = new Rect(width, height, width + cellSize, height + cellSize);
		canvas.drawBitmap(arrow, null, rect, null);

	}

	protected Bitmap getPlayerColor(int color) {
		Bitmap bitmap = null;
		switch (color) {
		case Color.RED:
			bitmap =BitmapFactory.decodeResource(getResources(), R.drawable.player_red); break;
		case Color.BLUE:
			bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player_blue); break;
		case Color.GREEN:
			bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player_green); break;
		case Color.YELLOW:
			bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player_yellow); break;
		}
		return bitmap;
	}

	protected Bitmap getCellColor(int color) {
		Bitmap bitmap = null;
		switch (color) {
		case Color.RED:
			bitmap =BitmapFactory.decodeResource(getResources(), R.drawable.cell_red); break;
		case Color.BLUE:
			bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cell_blue); break;
		case Color.GREEN:
			bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cell_green); break;
		case Color.YELLOW:
			bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cell_yellow); break;
		default : 
			bitmap = BitmapFactory.decodeStream(new BufferedInputStream(getResources().openRawResource(R.drawable.cell_empty))); break;
		}
		return bitmap;
	}

	protected Bitmap getRotatedBitmap(Direction dir, int resID) {
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

	public Direction getDirection() {
		return currentDir;
	}

	public Game getGame() {
		return game;
	}
}