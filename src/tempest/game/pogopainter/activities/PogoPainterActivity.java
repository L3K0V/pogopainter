package tempest.game.pogopainter.activities;

import tempeset.game.pogopainter.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.bugsense.trace.BugSenseHandler;

public class PogoPainterActivity extends Activity implements OnClickListener {

	private int reques_code = 1;
	private String tag = "Pogo";
	private static Context context;
	private ExtrasActivity extras = new ExtrasActivity();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.main_slots);

		BugSenseHandler.setup(this, "318415b5");

		context = getBaseContext();

		View aboutButton = findViewById(R.id.about_button);
		aboutButton.setOnClickListener(this);

		View singlePlayerButton = findViewById(R.id.play_button);
		singlePlayerButton.setOnClickListener(this);

		View optionsButton = findViewById(R.id.options_button);
		optionsButton.setOnClickListener(this);

	}

	@Override
	protected void onDestroy() {
		Log.d(tag, "Exiting");
		this.finish();
		super.onDestroy();
	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.about_button:
			Log.d( tag, "About");
			Intent About = new Intent(this, AboutTabActivity.class);
			startActivity(About);
			break;
		case R.id.play_button:
			Log.d( tag, "Play");
			Intent Single = new Intent(this, PogoSlots.class);
			startActivity(Single);
			break;
		case R.id.options_button:
			Log.d(tag, "Preferences");
			Intent Options = new Intent(this, PreferencesActivity.class);
			startActivityForResult(Options, reques_code);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == reques_code) {
			if (resultCode == RESULT_OK) {
				Log.d(tag, getString(R.string.pref_changed));
				Toast.makeText(getBaseContext(), getString(R.string.change_saved), Toast.LENGTH_SHORT).show();
			} else if (resultCode == 3) {
				Log.d(tag, "Preferences reset");
				Toast.makeText(getBaseContext(), getString(R.string.pref_reset), Toast.LENGTH_SHORT).show();
			} else if (resultCode == 4) {
				Log.d(tag, "Restarting main activity");
				Intent intent = getIntent();
				finish();
				startActivity(intent);
				Toast.makeText(getBaseContext(), getString(R.string.lang_change), Toast.LENGTH_SHORT).show();
			}
		}
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
		case R.id.version:
			extras.checkAppVersion(this);
			return true;
		}
		return false;
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder( this )
		.setTitle(getString(R.string.exit))
		.setMessage(getString(R.string.exit_question))
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Log.d( tag, "Exit - Negative" );
			}
		})
		.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Log.d( tag, "Exit - Positive");
				finish();

			}
		})
		.show();
	}

	public static Context getAppContext() {
		return PogoPainterActivity.context;
	}
}