package game.pogopainter;

import java.util.Locale;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class PreferencesActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	private SharedPreferences settings;
	private String tag = "Preferences";
	private Intent data = new Intent();
	private ExtrasActivity extras = new ExtrasActivity();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);

		settings = PreferenceManager.getDefaultSharedPreferences(this);
		settings.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onBackPressed() {
		Log.d(tag, "Close");
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.version:
			extras.checkAppVersion(this);
			return true;
		case R.id.resetSettings:
			new AlertDialog.Builder(this)
			.setTitle(getString(R.string.pogo_reset))
			.setMessage(getString(R.string.pogo_reset_question))
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					Log.d( tag, "Reset negative" );
				}
			})
			.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					Log.d(tag, "Reset positive");
					settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
					SharedPreferences.Editor editor = settings.edit();
					editor.clear();
					//PreferenceManager.setDefaultValues(getBaseContext(), R.xml.settings, true);
					PreferenceManager.setDefaultValues(getBaseContext(), R.xml.settings, false);
					editor.commit();
					setResult(3, data);
					finish();
				}
			})
			.show();
			return true;
		}
		return false;
	}

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals("GAME_LANGUEGE")) {
			String lang = sharedPreferences.getString("GAME_LANGUEGE", "");
			if ("English".equals(lang)) {
				lang = "en";
			} else if ("Bulgarian".equals(lang)) {
				lang = "bg";
			}

			Locale local = new Locale(lang);
			Locale.setDefault(local);
			Configuration config = new Configuration();
			config.locale = local;
			getBaseContext().getResources().updateConfiguration(config, null);
			setResult(4, data);
		} else {
			setResult(RESULT_OK, data);
		}
		Map<String, ?> prefs = sharedPreferences.getAll();
		Log.d(tag, "Change " + key + " to " + prefs.get(key));
	}
}
