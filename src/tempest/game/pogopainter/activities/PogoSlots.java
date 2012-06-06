package tempest.game.pogopainter.activities;

import tempest.game.pogopainter.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class PogoSlots extends Activity implements OnClickListener {
	private ToggleButton redSlot;
	private ToggleButton blueSlot;
	private ToggleButton greenSlot;
	private ToggleButton yellowSlot;
	private Button go;
	private SharedPreferences settings;
	
	private TextView gameType;
	private TextView gamePurpose;
	private TextView gamePlayersNumber;
	private TextView gameTeams;
	
	private ImageView redTeams;
	private ImageView blueTeams;
	private ImageView greenTeams;
	private ImageView yellowTeams;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.slots);

		initSlots();

		go = (Button) this.findViewById(R.id.startGame);
		go.setOnClickListener(this);

		gameType          = (TextView) findViewById(R.id.gameType_text);
		gamePurpose       = (TextView) findViewById(R.id.purpose_text);
		gamePlayersNumber = (TextView) findViewById(R.id.players_num_text);
		gameTeams         = (TextView) findViewById(R.id.teams_text);
		
		redTeams    = (ImageView) findViewById(R.id.red_player_teams);
		blueTeams   = (ImageView) findViewById(R.id.blue_player_teams);
		greenTeams  = (ImageView) findViewById(R.id.green_player_teams);
		yellowTeams = (ImageView) findViewById(R.id.yellow_player_teams);
		
		setDefaultTexts();
		settings = PreferenceManager.getDefaultSharedPreferences(this);
		showHint("Choose your starting position", Toast.LENGTH_LONG);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		System.gc();
	}

	private void initSlots() {
		redSlot      = (ToggleButton) this.findViewById(R.id.redSlot);
		blueSlot     = (ToggleButton) this.findViewById(R.id.blueSlot);
		greenSlot    = (ToggleButton) this.findViewById(R.id.greenSlot);
		yellowSlot   = (ToggleButton) this.findViewById(R.id.yellowSlot);
		
		redSlot.setOnClickListener(this);
		blueSlot.setOnClickListener(this);
		greenSlot.setOnClickListener(this);
		yellowSlot.setOnClickListener(this);
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.redSlot:
			redSlotClick();
			break;
		case R.id.blueSlot:
			blueSlotClick();
			break;
		case R.id.greenSlot:
			greenSlotClick();
			break;
		case R.id.yellowSlot:
			yellowSlotClick();
			break;
		case R.id.startGame:
			if (go.isEnabled()) {
				showHint("You are playing with " + settings.getString("PLAYER_COLOR", ""), Toast.LENGTH_SHORT);
				startActivity(new Intent(this, CanvasActivity.class));
				this.finish();
			}
		}
	}

	private void yellowSlotClick() {
		settings.edit().putString("PLAYER_COLOR", "yellow").commit();
		
		blueSlot.setChecked(false);
		greenSlot.setChecked(false);
		redSlot.setChecked(false);
		if (yellowSlot.isChecked()) {
			setSinglePlayer();
			enableStartGameButton(true);
		} else {
			setDefaultTexts();
			enableStartGameButton(false);
		}
	}

	private void greenSlotClick() {
		settings.edit().putString("PLAYER_COLOR", "green").commit();
		
		blueSlot.setChecked(false);
		redSlot.setChecked(false);
		yellowSlot.setChecked(false);
		if (greenSlot.isChecked()) {
			setSinglePlayer();
			enableStartGameButton(true);
		} else {
			setDefaultTexts();
			enableStartGameButton(false);
		}
	}

	private void blueSlotClick() {
		settings.edit().putString("PLAYER_COLOR", "blue").commit();
		
		redSlot.setChecked(false);
		greenSlot.setChecked(false);
		yellowSlot.setChecked(false);
		if (blueSlot.isChecked()) {
			setSinglePlayer();
			enableStartGameButton(true);
		} else {
			setDefaultTexts();
			enableStartGameButton(false);
		}
	}

	private void redSlotClick() {
		settings.edit().putString("PLAYER_COLOR", "red").commit();
		
		blueSlot.setChecked(false);
		greenSlot.setChecked(false);
		yellowSlot.setChecked(false);
		if (redSlot.isChecked()) {
			setSinglePlayer();
			enableStartGameButton(true);
		} else {
			setDefaultTexts();
			enableStartGameButton(false);
		}
	}
	
	private void enableStartGameButton(boolean state) {
		go.setEnabled(state);
	}
	
	private void showHint(String message, int length) {
		Toast toast = Toast.makeText(this, message, length);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	private void setDefaultTexts() {
		gameType.setText("Not defined yet...");
		gamePurpose.setText("");
		gamePlayersNumber.setText("");
		gameTeams.setText("");
		
		redTeams.setVisibility(View.GONE);
		blueTeams.setVisibility(View.GONE);
		greenTeams.setVisibility(View.GONE);
		yellowTeams.setVisibility(View.GONE);
	}
	
	private void setSinglePlayer() {
		gameType.setText("Classic single player ");
		gamePurpose.setText("Get most points over limited time ");
		gamePlayersNumber.setText("You VS. 3 AI bots ");
		gameTeams.setText("Free for all ");
		
		redTeams.setVisibility(View.GONE);
		blueTeams.setVisibility(View.GONE);
		greenTeams.setVisibility(View.GONE);
		yellowTeams.setVisibility(View.GONE);
	}
}
