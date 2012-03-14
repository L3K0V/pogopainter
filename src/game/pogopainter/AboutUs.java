package game.pogopainter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class AboutUs extends Activity implements OnClickListener {
	private String tag = "About";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_us);

		View contact = findViewById(R.id.contact_button);
		contact.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.contact_button:
			/* Create the Intent */
			final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

			/* Fill it with Data */
			emailIntent.setType("plain/text");
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"asenlekoff@gmail.com, alekssasho@mail.bg"});
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");

			/* Send it off to the Activity-Chooser */
			startActivity(Intent.createChooser(emailIntent, "Send mail..."));
			break;
		}
	}
};