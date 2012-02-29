package game.pogopainter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class AboutActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);

		View coop = findViewById(R.id.version_button);
		coop.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.version_button:

			PackageInfo pinfo = null;
			try {
				pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int versionNumber = pinfo.versionCode;
			String versionName = pinfo.versionName;

			new AlertDialog.Builder( this )
			.setTitle( "Pogo-Version" )
			.setMessage("Application version: " + versionName +"("+ versionNumber + ")")
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setNegativeButton( "Okay", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					Log.d( "AlertDialog", "Negative" );
				}
			} )
			.show();
			break;
		}
	}
};