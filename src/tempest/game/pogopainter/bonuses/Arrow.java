package tempest.game.pogopainter.bonuses;

import java.util.List;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import tempest.game.pogopainter.player.Player;
import tempest.game.pogopainter.system.Board;
import tempest.game.pogopainter.system.Direction;

public class Arrow extends BonusObject {
	private int state = 0;
	private int rotationDeg = 0;
	private boolean rotated;

	public Arrow() {
		changeState();
		rotated = true;
		type = Bonuses.ARROW;
	}

	@Override
	public void setBonusEffect(Player player, Board board) {
		int playerX = player.getX();
		int playerY = player.getY();
		int bSize = board.getBoardSize();

		switch (state) {
		case 1: //RIGHT
			for (; playerX < bSize; playerX++) {
				board.getCellAt(playerX, playerY).setColor(player.getColor());
			}
			break;
		case 2: //DOWN
			for (; playerY < bSize; playerY++) {
				board.getCellAt(playerX, playerY).setColor(player.getColor());
			}
			break;
		case 3: //LEFT
			for (; playerX >= 0; playerX--) {
				board.getCellAt(playerX, playerY).setColor(player.getColor());
			}
			break;
		case 4: //UP
			for (; playerY >= 0; playerY--) {
				board.getCellAt(playerX, playerY).setColor(player.getColor());
			}
			break;
		}
	}

	@Override
	public void draw(Canvas canvas, Paint paint, Rect bonusRectangle) {
		Matrix mat = new Matrix();

		// Bitmap is a square so we need only the size of the walls
		float fullScaledSize = ((float) bonusRectangle.width()) / bonusBitmap.getWidth();       
		float left = bonusRectangle.exactCenterX() - ((scaleSize * bonusBitmap.getWidth()) / 2);
		float top = bonusRectangle.exactCenterY() - ((scaleSize * bonusBitmap.getHeight()) / 2);

		if (scaleSize < fullScaledSize) {
			scaleSize += 0.05f;
		} else {
			ifScaled = true;
		}

		int degree = (state - 1) * 90;
		if (degree == 0) {
			degree = 360;
		}

		if (!rotated) {
			if (rotationDeg != degree) {
				rotationDeg += 30;
			}
		} else {
			rotationDeg = degree;
		}

		mat.preRotate(rotationDeg, bonusBitmap.getWidth() / 2, bonusBitmap.getHeight() / 2);
		mat.postScale(scaleSize, scaleSize);
		mat.postTranslate(left, top);

		canvas.drawBitmap(bonusBitmap, mat, paint);
	};

	public void changeState() {
		this.state++;
		rotated = false;
		rotationDeg = ((state - 1) * 90) - 90;
		if (rotationDeg == -90) {
			rotationDeg = 270;
		}
		if (state == 5) state = 1;
	}

	public int getState() {
		return this.state;
	}

	public void setState(Direction dir) {
		switch (dir) {
		case RIGHT:
			this.state = 1;
			break;
		case LEFT:
			this.state = 3;
			break;
		case UP:
			this.state = 4;
			break;
		case DOWN:
			this.state = 2;
			break;
		}
	}

	@Override
	public void setBonusEffect(List<Player> players, Board board) {}

}
