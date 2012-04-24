package tempest.game.pogopainter.activities;

import tempeset.game.pogopainter.R;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class DeathmatchHelpTab extends TabActivity {
	
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
		    intent = new Intent().setClass(this, DeathmatchHelp.class);

		    // Initialize a TabSpec for each tab and add it to the TabHost
		    spec = tabHost.newTabSpec("rules").setIndicator("Deathmatch Rules",
		                      res.getDrawable(android.R.drawable.ic_menu_help))
		                  .setContent(intent);
		    tabHost.addTab(spec);

		    // Do the same for the other tabs
		    intent = new Intent().setClass(this, ScoreSystem.class);
		    spec = tabHost.newTabSpec("score").setIndicator("Score System",
		                      res.getDrawable(android.R.drawable.ic_menu_view))
		                  .setContent(intent);
		    tabHost.addTab(spec);

		    tabHost.setCurrentTab(0);
	}
}
