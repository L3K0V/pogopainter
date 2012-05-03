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
	private Map<Integer, Integer> _soundCache;
	private SoundPool _pool;
	private MediaPlayer backgroundMusic;
	
	public Music(boolean background, boolean otherSounds) {
		Context context = PogoPainterActivity.getAppContext();
		if (otherSounds)
			fillSoundCache(context);
		if (background)
			backgroundMusic = MediaPlayer.create(context, R.raw.background);
	}
	
	private void fillSoundCache(Context context) {
		_pool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
		_soundCache = new HashMap<Integer, Integer>();
		_soundCache.put(R.raw.arrow, _pool.load(context, R.raw.arrow, 0));
		_soundCache.put(R.raw.checkpoint, _pool.load(context, R.raw.arrow, 0));
		_soundCache.put(R.raw.teleport, _pool.load(context, R.raw.teleport, 0));
	}
	
	public void playSound(int id) {
		if (_pool != null) {
			AudioManager mgr = (AudioManager)PogoPainterActivity.getAppContext().getSystemService(Context.AUDIO_SERVICE);
			float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
			float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);    
			float volume = streamVolumeCurrent / streamVolumeMax;
			
			_pool.play(_soundCache.get(id), volume, volume, 1, 0, 1f);
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
		if (_pool != null) {
			_pool.release();
			_soundCache.clear();
		}
		if (backgroundMusic != null) {
			backgroundMusic.stop();
			backgroundMusic.release();
		}
	}
}
