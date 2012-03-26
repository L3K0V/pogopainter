package game.gametypes;

public class GameThread extends Thread {
	private ClassicGameType game;
	private boolean _run = false;
	
	public GameThread(ClassicGameType classic) {
		this.game = classic;
	}
	
	public void setRunning(boolean run) {
		this._run = run;
	}
	
	@Override
	public void run() {
		while(_run) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			synchronized (game) {
				game.update();	
			}
		}
	}
}
