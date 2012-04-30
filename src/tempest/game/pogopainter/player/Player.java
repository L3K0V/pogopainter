package tempest.game.pogopainter.player;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import tempest.game.pogopainter.bonuses.BonusObject;
import tempest.game.pogopainter.system.Direction;

public class Player {
	private int x;
	private int y;
	private int color;
	private BonusObject bonus;
	private int speed;
	private Behavior behaviour;
	private int points;
	private Bitmap bitmap = null;
	private State playerState;
	private boolean animationFinished = false;
	private float step = 0.25f;


	public Player(int x, int y, int color,int points, Behavior control) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.speed = 1;
		this.behaviour = control;
		this.points = points;
		playerState = State.JUMP;
		behaviour.setPlayer(this);
	}

	public void draw(Canvas canvas, Paint paint, Rect newRectangle) {
		Matrix m = new Matrix();
		Rect rectangle = getLastRectangle(newRectangle);
		
		float fullScaledSize = ((float) rectangle.width()) / bitmap.getWidth();	
		m.postScale(fullScaledSize, fullScaledSize);
		
		float left   = rectangle.left;
		float right  = rectangle.right;
		float top    = rectangle.top;
		float bottom = rectangle.bottom;

		Direction dir = behaviour.getPreviousDirection();

		if (!animationFinished) {
			step += playerState.getStateInt() * 5f;

			switch (dir) {
			case LEFT:
				if ((left - step) <= (left - rectangle.width())) {
					animationFinished = true;
				} else {
					m.postTranslate(left - step, top);
				}
				break;
			case RIGHT:
				if ((left + step) >= right) {
					animationFinished = true;
				} else {
					m.postTranslate(left + step, top);
				}
				break;
			case UP:
				if ((top - step) <= (top - rectangle.height())) {
					animationFinished = true;
				} else {
					m.postTranslate(left, top - step);
				}
				break;
			case DOWN:
				if ((top + step) >= bottom) {
					animationFinished = true;
				} else {
					m.postTranslate(left, top + step);
				}
				break;
			case NONE:
				m.postTranslate(left, top);
				break;

			}
		} else {
			step = 0;
			
			switch (dir) {
			case LEFT:
				m.postTranslate(left - rectangle.width(), top);
				break;
			case RIGHT:
				m.postTranslate(right, top);
				break;
			case UP:
				m.postTranslate(left, top - rectangle.height());
				break;
			case DOWN:
				m.postTranslate(left, bottom);
				break;
			case NONE:
				m.postTranslate(left, top);
				break;
			}
		}
		canvas.drawBitmap(bitmap, m, paint);
	}

	private Rect getLastRectangle(Rect newRectangle) {
		Rect lastRectangle = null;

		switch (behaviour.getPreviousDirection()) {
		case LEFT:
			lastRectangle = new Rect(newRectangle.right, newRectangle.top, 
					newRectangle.right + newRectangle.width(), newRectangle.bottom);
			break;
		case RIGHT:
			lastRectangle = new Rect(newRectangle.left - newRectangle.width(), newRectangle.top, 
					newRectangle.left, newRectangle.bottom);
			break;
		case UP:
			lastRectangle = new Rect(newRectangle.left, newRectangle.bottom, 
					newRectangle.right, newRectangle.bottom + newRectangle.height());
			break;
		case DOWN:
			lastRectangle = new Rect(newRectangle.left, newRectangle.top - newRectangle.height(),
					newRectangle.right, newRectangle.top);
			break;
		case NONE:
			lastRectangle = newRectangle;
			break;
		}

		return lastRectangle;
	}
	
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	public void isMoved(boolean res) {
		this.animationFinished = res;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getColor() {
		return this.color;
	}

	public int getSpeed() {
		return this.speed;
	}

	public void changeScore(int score) {
		this.points += score;
	}

	public int getPoints() {
		return this.points;
	}

	public void setBonus(BonusObject b) {
		bonus = b;
	}

	public BonusObject getBonus() {
		return bonus;
	}

	public Behavior getBehaviour() {
		return behaviour;
	}
}
