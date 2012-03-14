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

public class AboutPogo extends Activity implements OnClickListener {

	private String tag = "About";
	private ExtrasActivity extras = new ExtrasActivity();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);

		View version = findViewById(R.id.version_button);
		version.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.version_button:
			extras.checkAppVersion(this);
			break;
		}
	}
};