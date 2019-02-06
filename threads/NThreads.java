package threads;

public class NThreads extends Thread{
	
	private int id=0;
	
	public NThreads(int id)  {
		this.id=id;
	}
	
	@Override
	public void run () {
		try {
			Thread.sleep(4000);//duerme 0.4 seg
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(id+1);
	}
	
	public static void main (String [] args) {
			for(int i = 0; i<10; i++) {
				NThreads t = new NThreads(i);
				t.start();
			}
			System.out.println("Comienzo");
	}
	
}
