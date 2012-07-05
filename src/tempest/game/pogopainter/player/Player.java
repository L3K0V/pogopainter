package tempest.game.pogopainter.player;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import tempest.game.pogopainter.bonuses.BonusObject;
import tempest.game.pogopainter.gametypes.Game;
import tempest.game.pogopainter.graphics.PlayerAnimation;
import tempest.game.pogopainter.system.Board;
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
	private int speed;

	public Player(int x, int y, int color,int points, Behavior control, int speed) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.behaviour = control;
		this.points = points;
		this.playerState = PlayerState.NORMAL;
		this.behaviour.setPlayer(this);
		this.animation = new PlayerAnimation(this);
		this.speed = speed;
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

	public void setBonus(BonusObject bonus) {
		this.bonus = bonus;
	}

	public void setState(PlayerState st) {
		this.playerState = st;
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

	public int getPoints() {
		return this.points;
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
	
	public PlayerAnimation getAnimation() {
		return this.animation;
	}

	public void changeScore(int score) {
		this.points += score;
	}

	public void startMoving(Direction dir, Board board) {
		this.animation.startMoving(dir, board);
	}

	public void draw(Canvas canvas, Paint paint, Rect rectangle) {
		canvas.drawBitmap(bitmap, null, rectangle, paint);
	}
	
	public int update(Game action) {
		action.move(this, behaviour.getNextDirection());
		return speed * 100;
	}
}
