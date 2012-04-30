package tempest.game.pogopainter.bonuses;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import tempest.game.pogopainter.player.Player;
import tempest.game.pogopainter.system.Board;

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
	public void setBonusEffect(Player p, Board b) {
		int playerX = p.getX();
		int playerY = p.getY();
		int bSize = b.getBoardSize();

		switch (state) {
		case 1: //RIGHT
			for (; playerX < bSize; playerX++) {
				b.getCellAt(playerX, playerY).setColor(p.getColor());
			}
			break;
		case 2: //DOWN
			for (; playerY < bSize; playerY++) {
				b.getCellAt(playerX, playerY).setColor(p.getColor());
			}
			break;
		case 3: //LEFT
			for (; playerX >= 0; playerX--) {
				b.getCellAt(playerX, playerY).setColor(p.getColor());
			}
			break;
		case 4: //UP
			for (; playerY >= 0; playerY--) {
				b.getCellAt(playerX, playerY).setColor(p.getColor());
			}
			break;
		}
	}
	
	@Override
	public void draw(Canvas canvas, Paint paint, Rect bonusRectangle) {
		Matrix mat = new Matrix();
		Rect scaledRect = null;
		
		mat.postScale(scaleX, scaleY);

		if (scaleX <= 1.0f) {
			scaleX += 0.05f;
			scaleY += 0.05f;
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
		
		mat.postRotate(rotationDeg);
		Bitmap bitmap = Bitmap.createBitmap(bonusBitmap, 0, 0, 
				bonusBitmap.getWidth(), bonusBitmap.getHeight(), mat, true);
		
		int top    = bonusRectangle.centerY()-(bitmap.getHeight()/2);
		int left   = bonusRectangle.centerX()-(bitmap.getWidth()/2);
		int bottom = bonusRectangle.centerY()+(bitmap.getHeight()/2);
		int right  = bonusRectangle.centerX()+(bitmap.getWidth()/2);
		
		if(left < bonusRectangle.left) {
			left   = bonusRectangle.left;
			top    = bonusRectangle.top;
			right  = bonusRectangle.right;
			bottom = bonusRectangle.bottom;
		}
		scaledRect = new Rect(left, top, right, bottom);
		
		canvas.drawBitmap(bitmap, null, scaledRect, paint);
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

}
