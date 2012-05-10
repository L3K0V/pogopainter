package tempest.game.pogopainter.activities;


import tempeset.game.pogopainter.R;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;

public class AboutTabActivity extends TabActivity {
	private ExtrasActivity extras = new ExtrasActivity();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.tab);

		    Resources res = getResources(); // Resource object to get Drawables
		    TabHost tabHost = getTabHost();  // The activity TabHost
		    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
		    Intent intent;  // Reusable Intent for each tab

		    // Create an Intent to launch an Activity for the tab (to be reused)
		    intent = new Intent().setClass(this, AboutPogo.class);

		    // Initialize a TabSpec for each tab and add it to the TabHost
		    spec = tabHost.newTabSpec("game").setIndicator("About Pogo",
		                      res.getDrawable(android.R.drawable.ic_menu_help))
		                  .setContent(intent);
		    tabHost.addTab(spec);

		    // Do the same for the other tabs
		    intent = new Intent().setClass(this, AboutUs.class);
		    spec = tabHost.newTabSpec("us").setIndicator(getString(R.string.about_us_title),
		                      res.getDrawable(android.R.drawable.ic_menu_view))
		                  .setContent(intent);
		    tabHost.addTab(spec);

		    tabHost.setCurrentTab(0);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.about, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.contact:
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
		case R.id.version:
			extras.checkAppVersion(this);
			return true;
		}
		return false;
	}
}
