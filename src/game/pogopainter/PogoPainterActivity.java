package game.pogopainter;

import android.R.drawable;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class PogoPainterActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        View aboutButton = findViewById(R.id.about_button);
        aboutButton.setOnClickListener(this);
        
        View multiPlayerButton = findViewById(R.id.multiPlayer_button);
        multiPlayerButton.setOnClickListener(this);
        
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
		}
		return false;
	}
    
    public void onClick(View v) {
    	
        switch (v.getId()) {
        case R.id.about_button:
        	Intent About = new Intent(this, AboutActivity.class);
            startActivity(About);
            break;
        case R.id.multiPlayer_button:
        	BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
			if (mBluetoothAdapter == null) {
				new AlertDialog.Builder( this )
				.setTitle( "Ops..." )
				.setMessage( "Bluetooth unavailable on your device. You cannot use multiplayer option!" )
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setNegativeButton( "Okay", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Log.d( "AlertDialog", "Negative" );
					}
				} )
				.show();
			} else {
				Intent Multiplayer = new Intent(this, MultiplayerActivity.class);
	            startActivity(Multiplayer);
			}
            break;
        case R.id.options_button:
        	Intent Options = new Intent(this, PreferencesActivity.class);
            startActivity(Options);
            break;
        }
    }
}