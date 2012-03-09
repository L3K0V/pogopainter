package game.pogopainter;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class PogoPainterActivity extends Activity implements OnClickListener {

	private int reques_code = 1;
	private String tag = "Pogo";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        View aboutButton = findViewById(R.id.about_button);
        aboutButton.setOnClickListener(this);
        
        View multiPlayerButton = findViewById(R.id.multiPlayer_button);
        multiPlayerButton.setOnClickListener(this);
        
        View singlePlayerButton = findViewById(R.id.singlePlayer_button);
        singlePlayerButton.setOnClickListener(this);
        
        View optionsButton = findViewById(R.id.options_button);
        optionsButton.setOnClickListener(this);
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
		}
		return false;
	}
    
	@Override
	public void onBackPressed() {
		new AlertDialog.Builder( this )
		.setTitle( "Exit?" )
		.setMessage( "Are you sure you want to exit?" )
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setNegativeButton( "No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Log.d( tag, "Exit - Negative" );
			}
		})
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Log.d( tag, "Exit - Positive");
				finish();
				
			}
		})
		.show();
	}
    public void onClick(View v) {
    	
        switch (v.getId()) {
        case R.id.about_button:
        	Log.d( tag, "About");
        	Intent About = new Intent(this, AboutActivity.class);
            startActivity(About);
            break;
        case R.id.singlePlayer_button:
        	Log.d( tag, "Singleplayer");
        	Intent Single = new Intent(this, MultiplayerActivity.class);
            startActivity(Single);
            break;
        case R.id.multiPlayer_button:
        	BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
			if (mBluetoothAdapter == null) {
				Log.d(tag, "BluetoothAlert");
				new AlertDialog.Builder( this )
				.setTitle( "Ops..." )
				.setMessage( "Bluetooth unavailable on your device. You cannot use multiplayer option!" )
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setNegativeButton( "Okay", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Log.d(tag, "BluetoothAlert - Positive" );
					}
				} )
				.show();
			} else {
				Log.d(tag, "Multiplayer");
				Intent Multiplayer = new Intent(this, MultiplayerActivity.class);
	            startActivity(Multiplayer);
			}
            break;
        case R.id.options_button:
        	Log.d(tag, "Preferences");
        	Intent Options = new Intent(this, PreferencesActivity.class);
            startActivityForResult(Options, reques_code);
            break;
        }
    }
    
    @Override
    protected void onDestroy() {
    	Log.d(tag, "Exiting");
    	this.finish();
    	super.onDestroy();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode == reques_code) {
			if (resultCode == RESULT_OK) {
				Log.d(tag, "Preferences changed");
				Toast.makeText(getBaseContext(), "Changes saved", Toast.LENGTH_SHORT).show();
			} else if (resultCode == 3) {
				Log.d(tag, "Preferences reset");
				Toast.makeText(getBaseContext(), "Preferences reset", Toast.LENGTH_SHORT).show();
			}
		}
    }
    
    public void getApplicationVersion() {
		
	}
}