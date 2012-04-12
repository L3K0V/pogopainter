package game.pogopainter;

import android.os.Bundle;
import android.view.View.OnClickListener;

public class GameOverTeams extends GameOver implements OnClickListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_over_teams);
		
		gameIntent = getIntent();
		//TODO: implement different gametypes
	}

}
