package tempest.game.pogopainter.graphics;

import tempest.game.pogopainter.gametypes.ClassicGame;
import tempest.game.pogopainter.system.TimeManager;
import android.app.Activity;
import android.content.Context;

public class ClassicPanel extends Panel {

	public ClassicPanel(Context context, Activity owner) {
		super(context, owner);
	}
	
	@Override
	protected void initFields() {
		manager = new TimeManager();
		game = new ClassicGame(this);
		manager.setGame(game);
		super.initFields();
	}
}
