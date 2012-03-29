package game.pogopainter;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class MultiplayerActivity extends Activity implements OnClickListener {

	private String tag = "Multiplayer";
	boolean choiceMenu = false;
	private static BluetoothAdapter myBluetoothAdapter = null;
	private ExtrasActivity extras = new ExtrasActivity();

	public static BluetoothAdapter getPogoBluetoothAdapter() {
		return myBluetoothAdapter;
	}

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
			Log.d(tag, "Options");
			startActivity(new Intent(this, PreferencesActivity.class));
			return true;
		case R.id.create:
			Log.d(tag, "Create");
			if (extras.checkForBluetooth(this) == true) {
				Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
				discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
				startActivity(discoverableIntent);
				myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
			} else {
				extras.checkForBluetooth(this);
			}
			return true;
		case R.id.join:
			Log.d(tag, "Join");
			if (extras.checkForBluetooth(this) == true) {
				Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				int REQUEST_ENABLE_BT = 0;
				startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT );
				myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
			} else {
				extras.checkForBluetooth(this);
			}
			return true;

		case R.id.version:
			extras.checkAppVersion(this);
			return true;
		}
		return false;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear(); //Clear view of previous menu
		MenuInflater inflater = getMenuInflater();
		if(choiceMenu) {
			Log.d(tag, "Choice");
			inflater.inflate(R.menu.multiplayer, menu);
		} else {
			Log.d(tag, "Options");
			inflater.inflate(R.menu.menu, menu);
		}
		return super.onPrepareOptionsMenu(menu);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.classic_help_button:
			Log.d(tag, "Classic help");
			startActivity(new Intent( this, ClassicHelpTab.class));
			break;
		case R.id.coop_help_button:
			Log.d(tag, "Coop help");
			startActivity(new Intent( this, CoopHelpTab.class));
			break;
		case R.id.deathmatch_help_button:
			Log.d(tag, "Deathmatch help");
			startActivity(new Intent( this, DeathmatchHelpTab.class));
			break;
		case R.id.classic_button:
			Log.d(tag, "Classic");
			choiceMenu = true;
			openOptionsMenu();
			choiceMenu = false;
			break;
		case R.id.coop_button:
			Log.d(tag, "Coop");
			choiceMenu = true;
			openOptionsMenu();
			choiceMenu = false;
			break;
		case R.id.deathmatch_button:
			Log.d(tag, "Deathmatch");
			choiceMenu = true;
			openOptionsMenu();
			choiceMenu = false;
			break;
		}
	}
}
