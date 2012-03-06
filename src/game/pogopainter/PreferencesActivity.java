package game.pogopainter;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.widget.Toast;

public class PreferencesActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}

	@Override
	public void onBackPressed() {
		Log.d("PreferencesDialog", "Closing");
		Toast.makeText(getBaseContext(), "Changes saved", Toast.LENGTH_SHORT).show();
		finish();
	}
}
