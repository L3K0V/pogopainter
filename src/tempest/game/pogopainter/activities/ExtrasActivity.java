package tempest.game.pogopainter.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

public class ExtrasActivity extends Activity {
	private String tag = "Extras";

	public void checkAppVersion(Context context) {
		try {
			Log.d(tag, "Version check");
			PackageInfo pinfo = null;
			pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			String versionName = pinfo.versionName;

			new AlertDialog.Builder(context)
			.setTitle( "Pogo Version" )
			.setMessage("Application version: " + versionName)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setNegativeButton( "Okay", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					Log.d( tag, "Version exit" );
				}
			} )
			.show();
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean checkForBluetooth(Context context) {
		BluetoothAdapter jBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		boolean bt = false;
		if (jBluetoothAdapter == null) {
			new AlertDialog.Builder(context)
			.setTitle( "Ops..." )
			.setMessage( "Bluetooth unavailable on your device. You cannot use multiplayer option!" )
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setNegativeButton( "Okay", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					Log.d(tag, "Bluetooth alert" );
				}
			} )
			.show();
			bt = false;
		} else 
			bt = true;
		return bt;
	}
}
