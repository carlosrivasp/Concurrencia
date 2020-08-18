package threads;


import es. upm. babel . cclib . Producto ;
import es. upm. babel . cclib . Almacen ;
import es.upm.babel.cclib.Semaphore;
// TODO : importar la clase de los sema foros .

/**
 * Implementación de la clase Almacen que permite el almacenamiento
 * FIFO de hasta un determinado número de productos y el uso
 * simultáneo del almacén por varios threads.
 */
class AlmacenN implements Almacen {
   private int capacidad = 0;
   private Producto[] almacenado = null;
   private int nDatos = 0;
   private int aExtraer = 0;
   private int aInsertar = 0;
   private Semaphore ex = null;
   private Semaphore alfull = null;
   private Semaphore alemp = null;
   // TODO: declaración de los semáforos necesarios

   public AlmacenN(int n) {
      capacidad = n;
      almacenado = new Producto[capacidad];
      nDatos = 0;
      aExtraer = 0;
      aInsertar = 0;
      ex = new Semaphore(1);
      alfull = new Semaphore(0);
      alemp = new Semaphore(1);
      // TODO: inicialización de los semáforos
   }

   public void almacenar(Producto producto) {
      // TODO: protocolo de acceso a la sección crítica y código de
      // sincronización para poder almacenar.
	  ex.await();
	  alemp.await();
      // Sección crítica
      almacenado[aInsertar] = producto;
      nDatos++;
      aInsertar++;
      aInsertar %= capacidad;

      // TODO: protocolo de salida de la sección crítica y código de
      // sincronización para poder extraer.
      alfull.signal();
      
   }

   public Producto extraer() {
      Producto result;

      // TODO: protocolo de acceso a la sección crítica y código de
      // sincronización para poder extraer.
      alfull.await();
      // Sección crítica
      result = almacenado[aExtraer];
      almacenado[aExtraer] = null;
      nDatos--;
      aExtraer++;
      aExtraer %= capacidad;

      // TODO: protocolo de salida de la sección crítica y código de
      // sincronización para poder almacenar.
      ex.signal();
      alemp.signal();
      return result;
   }
}
