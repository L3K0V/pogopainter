package tempest.game.pogopainter.graphics;

import java.util.HashMap;
import java.util.Map;
import tempest.game.pogopainter.R;
import tempest.game.pogopainter.bonuses.BonusObject;
import tempest.game.pogopainter.bonuses.Bonuses;
import tempest.game.pogopainter.gametypes.Game;
import tempest.game.pogopainter.player.Player;
import tempest.game.pogopainter.player.PlayerState;
import tempest.game.pogopainter.system.Cell;
import tempest.game.pogopainter.system.Direction;
import tempest.game.pogopainter.system.Metrics;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class Panel extends SurfaceView implements SurfaceHolder.Callback {
	protected Game game;
	
	private CanvasThread panelThread;
	private int cellNumber;
	private int cellSize;
	private int boardStartX;
	private int counterY;
	private Rect controlRect;
	private Rect up;
	private Rect down;
	private Rect left;
	private Rect right;
	private Rect board;
	private Direction currentDir = Direction.NONE;
	private int padding;
	private Rect counterRect;
	private Activity owner;
	private Map<Integer, Bitmap> bitmapCache;
	private Paint bPaint;

	public Panel(Context context, Activity owner) {
		super(context);
		this.owner = owner;
		getHolder().addCallback(this);
		this.panelThread = new CanvasThread(getHolder(), this);
		setFocusable(true);
		initFields();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
		if (!panelThread.isAlive()) {
			startThreads();
		} else {
			resumeThreads();
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		if (panelThread.isAlive()) {
			stopThreads();
		}
	}

	public void pauseThreads() {
		panelThread.setRunning(false);
	}

	public void startThreads() {
		panelThread = new CanvasThread(getHolder(), this);
		resumeThreads();
		panelThread.start();
	}

	public void resumeThreads() {
		panelThread.setRunning(true);
	}

	public void stopThreads() {
		panelThread.stopThread();
	}

	protected void initFields() {
		Metrics m = new Metrics();
		int screenWidth = m.getScreenWidth();
		int screenHeigth = m.getScreenHeight();
		boolean leftControlns = m.isLeftControls();
		cellNumber = game.getBoard().getBoardSize();
		cellSize = (screenHeigth / cellNumber ) - 1;
		padding = cellSize / 5;
		bitmapCache = new HashMap<Integer, Bitmap>();
		fillBitmapCache();
		Rect control = fixLeftOrRightControls(screenWidth, screenHeigth, leftControlns);
		defineTouchRect(control);
		bPaint = new Paint();
		bPaint.setAntiAlias(false);
		bPaint.setFilterBitmap(true);
		for (Player p : game.getPlayers()) {
			p.setBitmap(getPlayerColor(p.getColor()));
		}
	}

	private Rect fixLeftOrRightControls(int screenWidth, int screenHeigth, boolean leftControlns) {
		Rect control = null;
		int controlStartX = 0;
		if (leftControlns) {
			controlStartX = 0;
			boardStartX = (screenWidth - (cellNumber * cellSize)) - 1;
			control = new Rect(controlStartX + padding / 2, (screenHeigth / 2),
					boardStartX - padding / 2, screenHeigth - padding / 2);

			fixControlRect(control);
			counterRect = new Rect(controlStartX + padding / 2, padding / 2,
					boardStartX - padding / 2, screenHeigth / 2 - padding / 2);

			board = new Rect(boardStartX + padding / 2, 0, screenWidth, screenHeigth);
		} else {
			controlStartX = cellNumber * cellSize;
			boardStartX = 0;
			control = new Rect(controlStartX + padding / 2 , (screenHeigth / 2),
					screenWidth - padding / 2 , screenHeigth - padding / 2);
			
			fixControlRect(control);
			counterRect = new Rect(controlStartX + padding / 2, padding / 2,
					screenWidth - padding / 2, (screenHeigth / 2) - padding / 2);

			board = new Rect(boardStartX, 0, controlStartX - padding / 2, screenHeigth);
		}
		return control;
	}

	private void fillBitmapCache() {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inDither         = false;
		options.inPurgeable      = false;
		options.inInputShareable = true;
		options.inTempStorage    = new byte[32 * 1024];
		
		bitmapCache.put(R.drawable.cell_blue,        BitmapFactory.decodeResource(getResources(), R.drawable.cell_blue, options));
		bitmapCache.put(R.drawable.cell_empty,       BitmapFactory.decodeResource(getResources(), R.drawable.cell_empty, options));
		bitmapCache.put(R.drawable.cell_yellow,      BitmapFactory.decodeResource(getResources(), R.drawable.cell_yellow, options));
		bitmapCache.put(R.drawable.cell_green,       BitmapFactory.decodeResource(getResources(), R.drawable.cell_green, options));
		bitmapCache.put(R.drawable.cell_red,         BitmapFactory.decodeResource(getResources(), R.drawable.cell_red, options));
		bitmapCache.put(R.drawable.player_blue,      BitmapFactory.decodeResource(getResources(), R.drawable.player_blue, options));
		bitmapCache.put(R.drawable.player_yellow,    BitmapFactory.decodeResource(getResources(), R.drawable.player_yellow, options));
		bitmapCache.put(R.drawable.player_red,       BitmapFactory.decodeResource(getResources(), R.drawable.player_red, options));
		bitmapCache.put(R.drawable.player_green,     BitmapFactory.decodeResource(getResources(), R.drawable.player_green, options));
		bitmapCache.put(R.drawable.background,       BitmapFactory.decodeResource(getResources(), R.drawable.background, options));
		bitmapCache.put(R.drawable.bonus_arrow,      BitmapFactory.decodeResource(getResources(), R.drawable.bonus_arrow, options));
		bitmapCache.put(R.drawable.bonus_teleport,   BitmapFactory.decodeResource(getResources(), R.drawable.bonus_teleport, options));
		bitmapCache.put(R.drawable.bonus_checkpoint, BitmapFactory.decodeResource(getResources(), R.drawable.bonus_checkpoint, options));
		bitmapCache.put(R.drawable.bonus_cleaner,    BitmapFactory.decodeResource(getResources(), R.drawable.bonus_cleaner, options));
		bitmapCache.put(R.drawable.bonus_stun,       BitmapFactory.decodeResource(getResources(), R.drawable.bonus_stun, options));
		bitmapCache.put(R.drawable.indicators_stun,  BitmapFactory.decodeResource(getResources(), R.drawable.indicators_stun, options));
		bitmapCache.put(R.drawable.joystick,         BitmapFactory.decodeResource(getResources(), R.drawable.joystick, options));
		bitmapCache.put(R.drawable.joystick_clicked, BitmapFactory.decodeResource(getResources(), R.drawable.joystick_clicked, options));
		bitmapCache.put(R.drawable.joystick_action,  BitmapFactory.decodeResource(getResources(), R.drawable.joystick_action, options));
		bitmapCache.put(R.drawable.arrow,            BitmapFactory.decodeResource(getResources(), R.drawable.arrow, options));
	}

	public void clearMemory() {
		for (Bitmap b : bitmapCache.values()) {
			b.recycle();
		}
		bitmapCache.clear();
		bitmapCache = null;
		game.getMusic().releaseSounds();
		System.gc();
	}

	private void fixControlRect(Rect control) {
		int square = 0;
		if (control.width() > control.height()) {
			square = control.height() - padding;
		} else {
			square = control.width() - padding;
		}

		controlRect = new Rect(control.centerX() - square/2, control.centerY() - square/2,
				control.centerX() + square/2, control.centerY() + square/2);
	}

	private void defineTouchRect(Rect masterControls) {
		int centerX = controlRect.centerX();
		int centerY = controlRect.centerY();
		int width = controlRect.width();
		int height = controlRect.height();
		int paddingForTouchRect = padding * 3 + padding / 2;

		down = new Rect(centerX - (width / 4), centerY + paddingForTouchRect,
				centerX + (width / 4), controlRect.bottom);

		up = new Rect(centerX - (width / 4), controlRect.top,
				centerX + (width / 4), centerY - paddingForTouchRect);

		left = new Rect(masterControls.left, centerY - (height / 4),
				centerX - paddingForTouchRect, centerY + (height / 4));

		right = new Rect(centerX + paddingForTouchRect, centerY - (height / 4),
				masterControls.right, centerY + (height / 4));
	}

	private void checkControls(int x, int y) {
		if (board.contains(x, y)) {
			game.triggerBonus(game.getUser(), game.getUser().getBonus());
		} else if (up.contains(x, y)) {
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
	public boolean onTouchEvent(MotionEvent event) { 
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			int x = (int) event.getX();
			int y = (int) event.getY();
			checkControls(x, y);
		}
		return true;
	}

	@Override
	public void onDraw(Canvas canvas) {
		counterY = counterRect.top;
		drawBackground(canvas);
		drawBoard(canvas);
		drawPlayers(canvas);
		//drawNav(canvas);
		drawTimer(canvas);
		drawPointCounters(canvas);
		//drawControls(canvas);
		//drawDirection(canvas);
	}

	private void drawBackground(Canvas canvas) {
		Bitmap bg = bitmapCache.get(R.drawable.background);
		canvas.drawBitmap(bg, null, new Rect(0,0,getWidth(),getHeight()), null);
	}

	private void drawBoard(Canvas canvas) {
		Paint paint = new Paint();
		for (int y = 0; y < cellNumber; y++) {
			for (int x = 0; x < cellNumber; x++) {
	
				Cell cell = game.getBoard().getCellAt(x, y);
				int color  = cell.getColor();
				int posX = (cell.getX() * cellSize ) + boardStartX;
				int posY = cell.getY() * cellSize;
				
				Rect rect = new Rect(posX , posY, posX + cellSize , posY + cellSize);
				Bitmap bitmap = getCellColor(color);
				canvas.drawBitmap(bitmap, null, rect, null);
	
				
				paint.setStyle(Paint.Style.STROKE);
				paint.setColor(Color.WHITE);
				canvas.drawRect(rect, paint);
	
				if (cell.getBonus() != null) {
					drawBonus(canvas, rect,  cell.getBonus());
				}
			}
		}
	}

	private void drawPlayers(Canvas canvas) {
		for (Player player: game.getPlayers()) {
			int posX = (player.getX() * cellSize) + boardStartX;
			int posY = player.getY() * cellSize;
	
			Rect rect = new Rect(posX, posY, posX + cellSize, posY + cellSize);
			if (!player.getAnimation().getMoving()) {
				player.draw(canvas, bPaint, rect);
				if (player.getState() == PlayerState.STUN) {
					canvas.drawBitmap(bitmapCache.get(R.drawable.indicators_stun), 
							null, rect, bPaint);
				}
			} else {
				player.draw(canvas, bPaint, player.getAnimation().getCurrentPosition(rect));
			}
		}
	}

	private void drawNav(Canvas canvas) {
		if (!game.getUser().getAnimation().getMoving()) {
			if (game.checkDir(Direction.RIGHT, game.getUser())) {
				drawArrow(Direction.RIGHT, canvas);
			}
	
			if (game.checkDir(Direction.LEFT, game.getUser())) {
				drawArrow(Direction.LEFT, canvas);
			}
	
			if (game.checkDir(Direction.UP, game.getUser())) {
				drawArrow(Direction.UP, canvas);
			}
	
			if (game.checkDir(Direction.DOWN, game.getUser())) {
				drawArrow(Direction.DOWN, canvas);
			}
		}
	}

	private void drawTimer(Canvas canvas) {
		counterY += counterRect.height() / 5;
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setAntiAlias(true);
		paint.setTextSize(counterRect.height() / 7);
		paint.setColor(Color.GRAY);
		String timeLeft = "Time :" + Integer.toString(game.getTime());
		canvas.drawText(timeLeft, counterRect.centerX() - counterRect.width()/3, counterY, paint);
	}

	private void drawControls(Canvas canvas) {
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.MAGENTA);
		Bitmap controls;
		if (game.getUser().getBonus() != null) {
			controls = getRotatedBitmap(Direction.RIGHT, R.drawable.joystick_action);
		} else {
			controls = getRotatedBitmap(Direction.RIGHT, R.drawable.joystick);
		}
		canvas.drawBitmap(controls, null, controlRect, bPaint);
	}

	private void drawDirection(Canvas canvas) {
		if (getDirection() != Direction.NONE) {
			Bitmap clicked = getRotatedBitmap(getDirection(), R.drawable.joystick_clicked);
			canvas.drawBitmap(clicked, null, controlRect, bPaint);
		}
	}

	private void drawPointCounters(Canvas canvas) {
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setAntiAlias(true);
		for (Player player: game.getPlayers()) {
			drawCounter(player.getColor(), paint, canvas, player.getPoints());
		}
	}

	private void drawCounter(int color, Paint paint, Canvas canvas, int points) {
		paint.setColor(color);
		paint.setTextSize(counterRect.height() / 6);
		String spoints = Integer.toString(points);
		Bitmap bitmap = getPlayerColor(color);

		int counterPadding = counterRect.height() / 5;
		int bitmapStartX = counterRect.left + padding;
		Rect rect = new Rect(bitmapStartX , counterY, 
				bitmapStartX  + counterPadding, counterY + counterPadding);

		canvas.drawBitmap(bitmap, null, rect, bPaint);
		canvas.drawText(spoints, rect.right + padding * 2, 
				counterY + counterPadding - padding, paint);
		counterY += counterPadding;
	}

	private void drawBonus(Canvas canvas, Rect rect, BonusObject bonus) {
		Bonuses type = Bonuses.NONE;
		if (bonus != null)
			type = bonus.getType();
		switch (type) {
		case CHECKPOINT:
			if (!bonus.ifBitmap()) {
				bonus.setBitmap(bitmapCache.get(R.drawable.bonus_checkpoint));
			} break;
		case TELEPORT:
			if (!bonus.ifBitmap()) {
				bonus.setBitmap(bitmapCache.get(R.drawable.bonus_teleport));
			} break;
		case ARROW:
			bonus.setBitmap(bitmapCache.get(R.drawable.bonus_arrow));
			break;
		case CLEANER:
			if (!bonus.ifBitmap()) {
				bonus.setBitmap(bitmapCache.get(R.drawable.bonus_cleaner));
			} break;
		case STUN:
			if (!bonus.ifBitmap()) {
				bonus.setBitmap(bitmapCache.get(R.drawable.bonus_stun));
			} break;
		case NONE:
			break;
		}
		bonus.draw(canvas, bPaint, rect);
	}

	private void drawArrow(Direction dir, Canvas canvas) {
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
		canvas.drawBitmap(arrow, null, rect, bPaint);
	}

	private Bitmap getPlayerColor(int color) {
		Bitmap bitmap = null;
		switch (color) {
		case Color.RED:
			bitmap = bitmapCache.get(R.drawable.player_red); break;
		case Color.BLUE:
			bitmap = bitmapCache.get(R.drawable.player_blue); break;
		case Color.GREEN:
			bitmap = bitmapCache.get(R.drawable.player_green); break;
		case Color.YELLOW:
			bitmap = bitmapCache.get(R.drawable.player_yellow); break;
		}
		return bitmap;
	}

	private Bitmap getCellColor(int color) {
		Bitmap bitmap = null;
		switch (color) {
		case Color.RED:
			bitmap = bitmapCache.get(R.drawable.cell_red); break;
		case Color.BLUE:
			bitmap = bitmapCache.get(R.drawable.cell_blue); break;
		case Color.GREEN:
			bitmap = bitmapCache.get(R.drawable.cell_green); break;
		case Color.YELLOW:
			bitmap = bitmapCache.get(R.drawable.cell_yellow); break;
		default : 
			bitmap = bitmapCache.get(R.drawable.cell_empty); break;
		}
		return bitmap;
	}

	private Bitmap getRotatedBitmap(Direction dir, int resID) {
		Bitmap bitmap = bitmapCache.get(resID);
		Bitmap rotatedBitmap = null;
		Matrix mat = new Matrix();

		switch (dir) {
		case LEFT:
			mat.postRotate(180);
			rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, 
					bitmap.getWidth(), bitmap.getHeight(), mat, true);
			break;
		case RIGHT:
			rotatedBitmap = bitmap;
			break;
		case UP:
			mat.postRotate(270);
			rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, 
					bitmap.getWidth(), bitmap.getHeight(), mat, true);
			break;
		case DOWN:
			mat.postRotate(90);
			rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, 
					bitmap.getWidth(), bitmap.getHeight(), mat, true);
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

	public Activity getOwner() {
		return owner;
	}
}