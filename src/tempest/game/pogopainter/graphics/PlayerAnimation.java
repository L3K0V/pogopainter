package tempest.game.pogopainter.graphics;

import android.graphics.Rect;
import tempest.game.pogopainter.system.Direction;

public class PlayerAnimation {
	private boolean isMoving;
	private Direction dir;
	private static final int step;
	private int currentStep;
	
	static {
		step = 15; // 15 pixels step for moving
	}
	
	public PlayerAnimation() {
		this.isMoving = false;
		this.dir = Direction.NONE;
		this.currentStep = 0;
	}
	
	public void startMoving(Direction d) {
		this.isMoving = true;
		this.dir = d;
	}
	
	public boolean getMoving() {
		return this.isMoving;
	}
	
	public Rect getCurrentPosition(Rect desireRectangle) {
		int desireLeft   = desireRectangle.left;
		int desireTop    = desireRectangle.top;
		int desireSize   = desireRectangle.width(); // need only 1 because desireRectangle is a square
		currentStep     += step;
		
		if (currentStep > desireSize) {
			currentStep = desireSize;
		}
		int movement = desireSize - currentStep;
		
		switch (this.dir) {
		case LEFT:
			desireRectangle.left   += movement;
			desireRectangle.right  += movement;
			break;
		case RIGHT:
			desireRectangle.left   -= movement;
			desireRectangle.right  -= movement;
			break;
		case UP:
			desireRectangle.top    += movement;
			desireRectangle.bottom += movement;
			break;
		case DOWN:
			desireRectangle.top    -= movement;
			desireRectangle.bottom -= movement;
			break;
		}
		
		if ((desireRectangle.left == desireLeft) && (desireRectangle.top == desireTop)) {
			finishAnimation();
		}
		return desireRectangle;
	}
	
	private void finishAnimation() {
		this.dir = Direction.NONE;
		this.currentStep = 0;
		this.isMoving = false;
	}
}