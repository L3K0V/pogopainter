package tempest.game.pogopainter.graphics;

import android.graphics.Rect;
import tempest.game.pogopainter.player.Player;
import tempest.game.pogopainter.system.Board;
import tempest.game.pogopainter.system.Direction;

public class PlayerAnimation {
	private static final int STEP;
	private boolean isMoving;
	private Direction dir;
	private int currentStep;
	private Player player;
	private Board board;
	
	static {
		STEP = 15; // 15 pixels step for moving
	}
	
	public PlayerAnimation(Player pl) {
		this.isMoving = false;
		this.dir = Direction.NONE;
		this.currentStep = 0;
		this.player = pl;
	}
	
	public void startMoving(Direction d, Board b) {
		this.isMoving = true;
		this.dir = d;
		this.board = b;
	}
	
	public boolean getMoving() {
		return this.isMoving;
	}
	
	public Rect getCurrentPosition(Rect desireRectangle) {
		int desireLeft   = desireRectangle.left;
		int desireTop    = desireRectangle.top;
		int desireSize   = desireRectangle.width(); 
		// need only 1 because desireRectangle is a square
		
		currentStep     += STEP;
		
		if (currentStep > desireSize) {
			currentStep = desireSize;
		}
		int movement = desireSize - currentStep;
		changeRect(desireRectangle, movement);
	
		if ((desireRectangle.left == desireLeft) && (desireRectangle.top == desireTop)) {
			finishAnimation();
		}
		return desireRectangle;
	}
	
	private void changeRect(Rect rect, int movement) {
		switch (this.dir) {
		case LEFT:
			rect.left   += movement;
			rect.right  += movement;
			break;
		case RIGHT:
			rect.left   -= movement;
			rect.right  -= movement;
			break;
		case UP:
			rect.top    += movement;
			rect.bottom += movement;
			break;
		case DOWN:
			rect.top    -= movement;
			rect.bottom -= movement;
			break;
		}
	}
	
	private void finishAnimation() {
		this.dir = Direction.NONE;
		this.currentStep = 0;
		this.isMoving = false;
		this.board.setPlayerColorOnCell(player);
	}
}
