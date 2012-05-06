package tempest.game.pogopainter.system;

import java.util.HashMap;
import java.util.Map;
import tempeset.game.pogopainter.R;
import tempest.game.pogopainter.activities.PogoPainterActivity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class Music {
	private Map<Integer, Integer> soundCache;
	private SoundPool pool;
	private MediaPlayer backgroundMusic;
	
	public Music(boolean background, boolean otherSounds) {
		Context context = PogoPainterActivity.getAppContext();
		if (otherSounds)
			fillSoundCache(context);
		if (background)
			backgroundMusic = MediaPlayer.create(context, R.raw.background);
	}
	
	private void fillSoundCache(Context context) {
		pool = new SoundPool(5, AudioManager.STREAM_MUSIC, 100);
		soundCache = new HashMap<Integer, Integer>();
		soundCache.put(R.raw.arrow, 	  pool.load(context, R.raw.arrow, 0));
		soundCache.put(R.raw.checkpoint, pool.load(context, R.raw.arrow, 0));
		soundCache.put(R.raw.teleport,   pool.load(context, R.raw.teleport, 0));
		soundCache.put(R.raw.cleaner,    pool.load(context, R.raw.cleaner, 0));
		soundCache.put(R.raw.stun,       pool.load(context, R.raw.stun, 0));
	}
	
	public void playSound(int id) {
		if (pool != null) {
			AudioManager mgr = (AudioManager)PogoPainterActivity.getAppContext().getSystemService(Context.AUDIO_SERVICE);
			float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
			float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);    
			float volume = streamVolumeCurrent / streamVolumeMax;
			
			pool.play(soundCache.get(id), volume, volume, 1, 0, 1f);
		}
	}

	public void playMusic() {
		if (backgroundMusic != null) {
			if (!backgroundMusic.isPlaying()) {
				backgroundMusic.seekTo(0);
				backgroundMusic.start();
			}
		}
	}

	public void pauseMusic() {
		if (backgroundMusic != null) {
			if (backgroundMusic.isPlaying()) {
				backgroundMusic.pause();
			}
		}
	}

	public void releaseSounds() {
		if (pool != null) {
			pool.release();
			soundCache.clear();
		}
		if (backgroundMusic != null) {
			backgroundMusic.stop();
			backgroundMusic.release();
		}
	}
}
