package game.graphics;

import game.gametypes.ClassicGame;
import android.app.Activity;
import android.content.Context;

public class ClassicPanel extends Panel {

	public ClassicPanel(Context context, Activity owner) {
		super(context, owner);
	}
	
	@Override
	protected void initFields() {
		game = new ClassicGame(this);
		super.initFields();
	}
}
