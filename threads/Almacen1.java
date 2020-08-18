package threads;
import es.upm.babel.cclib.Producto;
import es.upm.babel.cclib.Semaphore;
import es.upm.babel.cclib.Almacen;
// TODO: importar la clase de los semaforos.
/**
 * Implementacion de la clase Almacen que permite el almacenamiento
 * de producto y el uso simultaneo del almacen por varios threads.
 */
class Almacen1 implements Almacen {
   // Producto a almacenar: null representa que no hay producto
   private Producto almacenado = null;
   // TODO: declaracion e inicializacion de los semáforos
   // necesarios
   Semaphore smp1; 
   Semaphore smp2; 
   public Almacen1() {//no puedo extraer antes de almacenar
	   smp1 = new Semaphore(0);
	   smp2 = new Semaphore(1);
   }
   public void almacenar(Producto producto) {
	  smp2.await();
      almacenado = producto;
      smp1.signal();
      // TODO: protocolo de salida de la seccion critica y codigo de
      // sincronizacion para poder extraer.
   }
   public Producto extraer() {
	  Producto result;
	  smp1.await();
      result = almacenado;
      almacenado = null;
      smp2.signal();
      // TODO: protocolo de salida de la seccion critica y codigo de
      // sincronizacion para poder almacenar.
      return result;
   }
}
