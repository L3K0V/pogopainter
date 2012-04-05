package game.graphics;

import game.gametypes.ClassicGame;
import android.content.Context;

public class ClassicPanel extends Panel {

	public ClassicPanel(Context context) {
		super(context);
	}
	
	@Override
	protected void initFields() {
		game = new ClassicGame(this);
		super.initFields();
	}
}
