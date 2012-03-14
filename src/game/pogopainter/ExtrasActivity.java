package game.pogopainter;

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
	private int width;
	private int height;
	private int cell;

	public void checkAppVersion(Context context) {

		try {
			Log.d(tag, "Version check");
			PackageInfo pinfo = null;
			pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			int versionNumber = pinfo.versionCode;
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

	public void calculateScreen() {

		//this.width = metrics.widthPixels;
		//this.height = metrics.heightPixels;
		this.cell = width / 8;
		//width = shirochina
		//height = visochina
		// TODO: return cell numbers too 
	}
	
	public int getCellSize() {
		calculateScreen();
		return this.cell;
	}
	
	public int getScreenWidth() {
		calculateScreen();
		return this.width;
	}
	
	public int getScreenHeight() {
		calculateScreen();
		return this.height;
	}
	
	public void setScreenWidth(int w) {
		//calculateScreen();
		this.width = w;
	}
	
	public void setScreenHeight(int h) {
		//calculateScreen();
		this.height = h;
	}
}
