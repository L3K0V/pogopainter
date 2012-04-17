package game.pogopainter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ToggleButton;

public class MultiplayerSlotActivity extends Activity implements OnClickListener {
	private ToggleButton redSlot;
	private ToggleButton blueSlot;
	private ToggleButton greenSlot;
	private ToggleButton yellowSlot;
	private Button lockIn;
	private Button go;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.multiplayer_ver2);
		
		redSlot      = (ToggleButton) this.findViewById(R.id.redSlot);
		blueSlot     = (ToggleButton) this.findViewById(R.id.blueSlot);
		greenSlot    = (ToggleButton) this.findViewById(R.id.greenSlot);
		yellowSlot   = (ToggleButton) this.findViewById(R.id.yellowSlot);
		
		lockIn = (Button) this.findViewById(R.id.lockIn);
		go = (Button) this.findViewById(R.id.startGame);
		
		redSlot.setOnClickListener(this);
		blueSlot.setOnClickListener(this);
		greenSlot.setOnClickListener(this);
		yellowSlot.setOnClickListener(this);
	}
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.redSlot:
			if (redSlot.isChecked()) {
				blueSlot.setChecked(false);
				greenSlot.setChecked(false);
				yellowSlot.setChecked(false);
				
				lockIn.setEnabled(true);
			} else {
				lockIn.setEnabled(false);
			}
			break;
		case R.id.blueSlot:
			if (blueSlot.isChecked()) {
				redSlot.setChecked(false);
				greenSlot.setChecked(false);
				yellowSlot.setChecked(false);
				
				lockIn.setEnabled(true);
			} else {
				lockIn.setEnabled(false);
			}
			break;
		case R.id.greenSlot:
			if (greenSlot.isChecked()) {
				blueSlot.setChecked(false);
				redSlot.setChecked(false);
				yellowSlot.setChecked(false);
				
				lockIn.setEnabled(true);
			} else {
				lockIn.setEnabled(false);
			}
			break;
		case R.id.yellowSlot:
			if (yellowSlot.isChecked()) {
				blueSlot.setChecked(false);
				greenSlot.setChecked(false);
				redSlot.setChecked(false);
				
				lockIn.setEnabled(true);
			} else {
				lockIn.setEnabled(false);
			}
			break;
		case R.id.lockIn:
			break;
		}
	}

}
