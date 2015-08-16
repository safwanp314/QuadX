package quadx.utils;

public abstract class RepetiveJob implements Runnable {

	protected long repeatInterval = 2;
	
	public abstract void execute();
	
	public void run() {
		while(true) {
			execute();
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void start() {
		new Thread(this).start();
	}
}
