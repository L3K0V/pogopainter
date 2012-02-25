package game.pogopainter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class MultiplayerActivity extends Activity implements OnClickListener {
	boolean choiceMenu = false;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.multiplayer);

		View classicHelp = findViewById(R.id.classic_help_button);
		classicHelp.setOnClickListener(this);

		View coopHelp = findViewById(R.id.coop_help_button);
		coopHelp.setOnClickListener(this);

		View deathmatchHelp = findViewById(R.id.deathmatch_help_button);
		deathmatchHelp.setOnClickListener(this);
		
		View classic = findViewById(R.id.classic_button);
		classic.setOnClickListener(this);

		View coop = findViewById(R.id.coop_button);
		coop.setOnClickListener(this);

		View deathmatch = findViewById(R.id.deathmatch_button);
		deathmatch.setOnClickListener(this);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.settings:
			startActivity(new Intent(this, PreferencesActivity.class));
			return true;
		}
		return false;
	}
	
	 @Override
	    public boolean onPrepareOptionsMenu(Menu menu) {
	        menu.clear(); //Clear view of previous menu
	        MenuInflater inflater = getMenuInflater();
	        if(choiceMenu)
	            inflater.inflate(R.menu.multiplayer, menu);
	        else
	            inflater.inflate(R.menu.menu, menu);
	        return super.onPrepareOptionsMenu(menu);
	    }

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.classic_help_button:
			startActivity(new Intent( this, ClassicHelpActivity.class));
			break;
		case R.id.coop_help_button: 
			startActivity(new Intent( this, CoopHelpActivity.class));
			break;
		case R.id.deathmatch_help_button: 
			startActivity(new Intent( this, DeathmatchHelpActivity.class));
			break;
		case R.id.classic_button:
			choiceMenu = true;
			openOptionsMenu();
			break;
		case R.id.coop_button: 
			choiceMenu = true;
			openOptionsMenu();
			break;
		case R.id.deathmatch_button:
			choiceMenu = true;
			openOptionsMenu();
			break;
		}
	}
}
