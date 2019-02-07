package threads;

public class CC_02_Carrera{

	static int contador = 0;
	static int N = 1000;

	static class Incremento extends Thread{
		@Override
		public void run() {
			for(int i = 0; i < N; i++) {
				CC_02_Carrera.contador++;
			}
		}
	}

	static class Decremento extends Thread{
		@Override
		public void run() {
			for(int i = 0; i < N; i++) {
				CC_02_Carrera.contador--;
			}
		}
	}

	public static void main(String[] args) {
		Thread[]incrementa = new Thread[N];
		Thread[]decrementa = new Thread[N];

		for(int i = 0; i < N; i++) {
			incrementa[i] = new Incremento();
			decrementa[i] = new Decremento();
			incrementa[i].start();
			decrementa[i].start();
		}
		for(int i = 0; i < N; i++) {
			try {
				incrementa[i].join();
				decrementa[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(contador);
	}
}




