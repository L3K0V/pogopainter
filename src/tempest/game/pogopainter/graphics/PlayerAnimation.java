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
		Rect currentRect = new Rect(desireRectangle);
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
			currentRect.left   += movement;
			currentRect.right  += movement;
			break;
		case RIGHT:
			currentRect.left   -= movement;
			currentRect.right  -= movement;
			break;
		case UP:
			currentRect.top    += movement;
			currentRect.bottom += movement;
			break;
		case DOWN:
			currentRect.top    -= movement;
			currentRect.bottom -= movement;
			break;
		}
		
		if ((currentRect.left == desireLeft) && (currentRect.top == desireTop)) {
			finishAnimation();
		}
		return currentRect;
	}
	
	private void finishAnimation() {
		this.dir = Direction.NONE;
		this.currentStep = 0;
		this.isMoving = false;
	}
}
