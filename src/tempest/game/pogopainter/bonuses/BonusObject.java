package tempest.game.pogopainter.bonuses;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import tempest.game.pogopainter.player.Player;
import tempest.game.pogopainter.system.Board;

public abstract class BonusObject {
	protected int x;
	protected int y;
	protected Bonuses type;
	protected Bitmap bonusBitmap = null;
	protected boolean ifScaled = false;

	protected float scaleSize = 0.1f;

	public final int getX() {
		return x;
	}

	public final void setX(int x) {
		this.x = x;
	}

	public final int getY() {
		return y;
	}

	public final void setY(int y) {
		this.y = y;
	}

	public final Bonuses getType() {
		return type;
	}

	public void setBitmap(Bitmap bonusBitmap) {
		this.bonusBitmap = bonusBitmap;
	}

	public void draw(Canvas canvas, Paint paint, Rect bonusRectangle) {
		if (!ifScaled) {
			Matrix mat = new Matrix();
			
			// Bitmap is a square so we need only the size of the walls
			float fullScaledSize = ((float) bonusRectangle.width()) / bonusBitmap.getWidth();
			
			mat.postScale(scaleSize, scaleSize);
			
			float left = bonusRectangle.exactCenterX() - ((scaleSize * bonusBitmap.getWidth()) / 2);
			float top = bonusRectangle.exactCenterY() - ((scaleSize * bonusBitmap.getHeight()) / 2);
			mat.postTranslate(left, top);
			
			canvas.drawBitmap(bonusBitmap, mat, paint);
			
			if (scaleSize < fullScaledSize) {
				scaleSize += 0.05f;
			} else {
				ifScaled = true;
			}
		} else {
			canvas.drawBitmap(bonusBitmap, null, bonusRectangle, paint);
		}
	}

	public boolean ifBitmap() {
		return bonusBitmap != null;
	}

	public abstract void setBonusEffect(Player p, Board b);
}
