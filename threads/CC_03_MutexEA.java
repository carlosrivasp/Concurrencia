package threads;

public class CC_03_MutexEA {


	static int contador = 0;						// contador = resultado final del programa
	static int N = 1000;							// N = numero de threads que se van a crear

	static class Decremento extends Thread{			// clase Decremento 
		@Override
		public void run() {							//run () Decremento = resta 1 a contador
			for(int i = 0; i < N; i++) {
				contador -= 1;
			}
		}
	}

	static class Incremento extends Thread{			// clase Incremento 
		@Override
		public void run() {							// run () Incremento = suma 1 a contador
			for(int i = 0; i < N; i++) {
				contador += 1;
			}
		}
	}

	public static void main(String[] args) {		//main

		Thread [] hiloDecremento = new Thread[N];	// hiloDecremento[] espacio para n threads
		Thread [] hiloIncremento = new Thread[N];	// hiloIncremento[] espacio para n threads

		for(int i = 0; i < N; i++) {				
			hiloDecremento[i] = new Decremento();	// hiloDecremento[i] nuevo thread tipo Decremento
			hiloDecremento[i].start();				// ejecuta en i el thread del array
			hiloIncremento[i] = new Incremento();	// hiloIncremento[i] nuevo thread tipo Incremento
			hiloIncremento[i].start();				// ejecuta en i el thread del array

			try {
				hiloDecremento[i].join();			//esperamos a que termine
				hiloIncremento[i].join();			//esperamos a que termine
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		System.out.println(contador);				// se imprime por pantalla el contador
	}
}
