package threads;

public class MiPrimerThread implements Runnable{
	@Override
	public void run () {
		System.out.println("1");
	}
	
	public static void main (String [] args) {
		for(int i = 0; i<10; i++) {
			NThreads t = new NThreads(i);
			t.start();
		}
		System.out.println("2");
}
}
