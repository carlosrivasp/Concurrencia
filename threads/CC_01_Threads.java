package threads;
public class CC_01_Threads{

	private static final int N=10;
	private static final int T=3000;

	public static class Hilo extends Thread{
		private int id;
		public Hilo(int id){
			this.id=id;
		}

		public void run(){
			try {
				Thread.sleep(T);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(id + ": Aqui termino");
		}

	}
	public static void main (String[]args){

		Thread[] threads=new Thread[N];

		for(int i=0;i < N;i++){
			threads[i]= new Hilo(i);
			threads[i].start();
		}
		for(int i=0;i < N;i++){
			
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("main: Terminado");
	}
}
