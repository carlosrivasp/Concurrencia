package threads;

public class CC_01_Threads extends Thread{
	private static int N = 10;
	private static int T = 3000;
	private int id;

	public CC_01_Threads(int id) {
		this.id = id;
	}

	public void run () {
		System.out.println("Thread id:" + id);
	}

	public static void main (String[]args) {
		for(int i=0; i<N ;i++) {
			CC_01_Threads t = new CC_01_Threads(i);
			t.start();
		}
		try {
			Thread.sleep(T);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Terminado");
	}

}
