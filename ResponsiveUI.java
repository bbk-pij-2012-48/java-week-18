public class ResponsiveUI implements Runnable {
	private int length;
	private int number;
	private static boolean[] complete = new boolean[10];
	private static Object obj = new Object();
	
	public ResponsiveUI(int length, int number) {
		this.length = length;
		this.number = number;
	}
	
	public void run() {
		try {
			Thread.sleep(length);
		} catch (InterruptedException ex) {
			System.err.println("Thread terminated early");
		}
		complete[number - 1] = true;
		synchronized(obj) {
			checkComplete();	
		}
	}
	
	public static void main(String[] args) {
		for(int i=0 ; i<10 ; i++) {
			int duration;
			synchronized(obj) {
				System.out.print("Enter the duration (in ms) of task " + (i+1) + ": ");
				duration = Integer.parseInt(System.console().readLine());
			}
			Runnable task = new ResponsiveUI(duration, i+1);
			Thread t = new Thread(task);
			t.start();
		}
	}
	
	public static void checkComplete() {
		String completed = "";
		boolean somethingComplete = false;
		for(int i=0 ; i<10 ; i++) {
			if(complete[i]) {
				if(somethingComplete) {
					completed += ", ";	
				}
				completed += (i + 1);
				complete[i] = false;
				somethingComplete = true;
			}
		}
		if (somethingComplete) {
			System.out.println("Finished tasks: " + completed);
		}
	}
}
