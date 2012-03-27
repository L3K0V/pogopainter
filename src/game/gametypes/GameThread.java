package game.gametypes;

import game.graphics.Panel;


public class GameThread extends Thread {
	private Panel _panel;
	private boolean _run = false;
	
	public GameThread(Panel panel) {
		this._panel = panel;
	}
	
	public void setRunning(boolean run) {
		this._run = run;
	}
	
	@Override
	public void run() {
		while(_run) {
			try {
				Thread.sleep(900);
			} catch (InterruptedException e) {
			}
			
			synchronized (_panel) {
				try {
					_panel.wait(500);
				} catch (InterruptedException e) {
				}
				_panel.getGame().update();	
				_panel.notify();
			}
		}
	}
}
