package threads;

import es.upm.babel.cclib.Semaphore;

class CC_04_MutexSem {

   private static int N_PASOS = 1000;					//tantos pasos como queramos
   private static int N_THREADS = 2;						//tantos threads como queramos
   
   static class Contador {
	  private static Semaphore s = new Semaphore(1);	//un semaforo por contador
      private volatile int n;							//contador
      
      public Contador() {								//construtor clase contador
         this.n = 0;
      }
      
      public int valorContador() {						//valor del contador
         return this.n;
      }
      
      public void inc () {								//incrementa el contador
    	  s.await();									//retrasa el contador interno y lo decrementa
         this.n++;
         s.signal();									//incrementa el contador interno
      }
      
      public void dec () {								//decrementa contador
    	  s.await();									//retrasa el contador interno y lo decrementa
         this.n--;
         s.signal();									//incrementa el contador interno
      }
   }	

   static class Incrementador extends Thread {
      private Contador cont;
      public Incrementador (Contador c) {
         this.cont = c;
      }
      public void run() {								//threads que incrementan la var n
         for (int i = 0; i < N_PASOS; i++) {
            this.cont.inc();
         }
      }
   }
   
   static class Decrementador extends Thread {
      private Contador cont;
      public Decrementador (Contador c) {
         this.cont = c;
      }
      public void run() {								//threads que decrementan la var n
         for (int i = 0; i < N_PASOS; i++) {
            this.cont.dec();
         }
      }
   }
   
   public static void main(String args[])
   {
      // Creacion del objeto compartido
      Contador cont = new Contador();
      
      // Creacion de los arrays que contendran los threads
      Incrementador[] tInc =
         new Incrementador[N_THREADS];
      Decrementador[] tDec =
         new Decrementador[N_THREADS];
      
      // Creacion de los objetos threads
      for (int i = 0; i < N_THREADS; i++) {
         tInc[i] = new Incrementador(cont);
         tDec[i] = new Decrementador(cont);
      }
      
      // Lanzamiento de los threads
      for (int i = 0; i < N_THREADS; i++) {
         tInc[i].start();
         tDec[i].start();
      }
      
      // Espera hasta la terminacion de los threads
      try {
         for (int i = 0; i < N_THREADS; i++) {
            tInc[i].join();
            tDec[i].join();
         }
      } catch (Exception ex) {
         ex.printStackTrace();
         System.exit (-1);
      }
      
      // Simplemente se muestra el valor final de la variable:
      System.out.println(cont.valorContador());
      System.exit (0);
   }
}