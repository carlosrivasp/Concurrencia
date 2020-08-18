package threads;

//Exclusion mutua con espera activa.
//
//Intentar garantizar la exclusion mutua en sc_inc y sc_dec sin
//utilizar mas mecanismo de concurrencia que el de la espera activa
//(nuevas +variables y bucles).
//
//Las propiedades que deberin cumplirse:
//- Garantia mutual exclusion (exclusion mutua): nunca hay dos
//procesos ejecutando secciones criticas de forma simultanea.
//- Ausencia de deadlock (interbloqueo): los procesos no quedan
//"atrapados" para siempre.
//- Ausencia de starvation (inanicion): si un proceso quiere acceder
//a su seccion critica entonces es seguro que alguna vez lo hace.
//- Ausencia de esperas innecesarias: si un proceso quiere acceder a
//su seccion critica y ningun otro proceso esta accediendo ni
//quiere acceder entonces el primero puede acceder.
//
//Ideas:
//- Una variable booleana en_sc que indica que algun proceso esta
//ejecutando en la seccion critica?
//- Una variable booleana turno?
//- Dos variables booleanas en_sc_inc y en_sc_dec que indican que un
//determinado proceso (el incrementador o el decrementador) esta
//ejecutando su seccion critica?
//- Combinaciones?

public class CC_03_MutexEA_todo {

	static final int N_PASOS = 10000;

	   // Generador de numeros aleatorios para simular tiempos de
	   // ejecucion
	    static final java.util.Random RNG = new java.util.Random(0);

	   // Variable compartida
	   volatile static int n = 0;

	   // Variables para asegurar exclusion mutua
	   volatile static boolean en_sc = false;

	   // Seccion no critica
	   static void no_sc() {
	       System.out.println("No SC");
	       try {
	          // No mas de 2ms
	          Thread.sleep(RNG.nextInt(3));
	       }
	       catch (Exception e) {
	          e.printStackTrace();
	       }
	   }

	   // Secciones criticas
	   static void sc_inc() {
	       System.out.println("Incrementando");
	      n++;
	   }

	   static void sc_dec() {
	       System.out.println("Decrementando");
	      n--;
	   }

	   // La labor del proceso incrementador es ejecutar no_sc() y luego
	   // sc_inc() durante N_PASOS asegurando exclusion mutua sobre
	   // sc_inc().
	   static class Incrementador extends Thread {
	      public void run () {
	         for (int i = 0; i < N_PASOS; i++) {
	            // Seccion no critica
	            no_sc();

	            // Protocolo de acceso a la seccion critica
	            while (en_sc) {}
	            en_sc = true;

	            // Seccion critica
	            sc_inc();

	            // Protocolo de salida de la seccion critica
	            en_sc = false;
	         }
	      }
	   }

	   // La labor del proceso incrementador es ejecutar no_sc() y luego
	   // sc_dec() durante N_PASOS asegurando exclusion mutua sobre
	   // sc_dec().
	   static class Decrementador extends Thread {
	      public void run () {
	         for (int i = 0; i < N_PASOS; i++) {
	            // Seccion no critica
	            no_sc();

	            // Protocolo de acceso a la seccion critica
	            while (en_sc) {}
	            en_sc = true;

	            // Seccion critica
	            sc_dec();

	            // Protocolo de salida de la seccion critica
	            en_sc = false;
	         }
	      }
	   }

	   public static final void main(final String[] args)
	      throws InterruptedException
	   {
	      // Creamos las tareas
	      Thread t1 = new Incrementador();
	      Thread t2 = new Decrementador();

	      // Las ponemos en marcha
	      t1.start();
	      t2.start();

	      // Esperamos a que terminen
	      t1.join();
	      t2.join();

	      // Simplemente se muestra el valor final de la variable:
	      System.out.println(n);
	   }
}
