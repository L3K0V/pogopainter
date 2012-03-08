package game.pogopainter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
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
		//Toast.makeText(getBaseContext(), "Changes saved", Toast.LENGTH_SHORT).show();
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
				PackageInfo pinfo = null;
				try {
					pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				}
				int versionNumber = pinfo.versionCode;
				String versionName = pinfo.versionName;

				new AlertDialog.Builder( this )
				.setTitle( "Pogo-Version" )
				.setMessage("Application version: " + versionNumber + "." + versionName)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setNegativeButton( "Okay", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Log.d( tag, "Version exit" );
					}
				} )
				.show();
				return true;
			case R.id.resetSettings:
				new AlertDialog.Builder( this )
				.setTitle( "Pogo-Reset" )
				.setMessage("Do you want to reset game preferences?")
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setNegativeButton( "No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Log.d( tag, "Reset negative" );
					}
				})
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
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
		Log.d(tag, "Change " + key);
		setResult(RESULT_OK, data);
	}
}
