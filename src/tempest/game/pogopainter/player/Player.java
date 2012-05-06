package tempest.game.pogopainter.player;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import tempest.game.pogopainter.bonuses.BonusObject;
import tempest.game.pogopainter.graphics.PlayerAnimation;
import tempest.game.pogopainter.system.Direction;

public class Player {
	private int x;
	private int y;
	private int color;
	private BonusObject bonus;
	private Behavior behaviour;
	private int points;
	private Bitmap bitmap = null;
	private PlayerState playerState;
	private PlayerAnimation animation;

	public Player(int x, int y, int color,int points, Behavior control) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.behaviour = control;
		this.points = points;
		this.playerState = PlayerState.NORMAL;
		this.behaviour.setPlayer(this);
		this.animation = new PlayerAnimation();
	}

	public void draw(Canvas canvas, Paint paint, Rect rectangle) {
		canvas.drawBitmap(bitmap, null, rectangle, paint);
	}
	
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
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
	
	public PlayerState getState() {
		return playerState;
	}
	
	public void setState(PlayerState st) {
		this.playerState = st;
	}
	
	public void startMoving(Direction dir) {
		this.animation.startMoving(dir);
	}
	
	public PlayerAnimation getAnimation() {
		return this.animation;
	}
}
