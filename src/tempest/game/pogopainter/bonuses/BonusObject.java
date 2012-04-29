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

	private float scaleX = 0.1f;
	private float scaleY = 0.1f;

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
		Matrix m = new Matrix();
		Bitmap bitmap = null;
		Rect scaledRect = null;
		
		m.postScale(scaleX, scaleY);

		if (scaleX <= 1.0f) {
			scaleX += 0.05f;
			scaleY += 0.05f;
		}
		bitmap = Bitmap.createBitmap(bonusBitmap, 0, 0, bonusBitmap.getWidth(), bonusBitmap.getHeight(), m, true);
		
		int top    = bonusRectangle.centerY()-(bitmap.getHeight()/2);
		int left   = bonusRectangle.centerX()-(bitmap.getWidth()/2);
		int bottom = bonusRectangle.centerY()+(bitmap.getHeight()/2);
		int right  = bonusRectangle.centerX()+(bitmap.getWidth()/2);
		
		if(left < bonusRectangle.left) {
			left = bonusRectangle.left;
			top = bonusRectangle.top;
			right = bonusRectangle.right;
			bottom = bonusRectangle.bottom;
		}
		scaledRect = new Rect(left, top, right, bottom);
		canvas.drawBitmap(bitmap, null, scaledRect, paint);
	}

	public boolean ifBitmap() {
		return bonusBitmap != null;
	}

	public abstract void setBonusEffect(Player p, Board b);
}
