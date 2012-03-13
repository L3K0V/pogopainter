package game.graphics;

import game.pogopainter.R;
import android.app.Activity;
import android.os.Bundle;

public class CanvasActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.canvas);
    }
}