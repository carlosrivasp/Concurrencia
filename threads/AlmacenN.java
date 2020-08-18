package threads;


import es. upm. babel . cclib . Producto ;
import es. upm. babel . cclib . Almacen ;
import es.upm.babel.cclib.Semaphore;
// TODO : importar la clase de los sema foros .

/**
 * Implementaci�n de la clase Almacen que permite el almacenamiento
 * FIFO de hasta un determinado n�mero de productos y el uso
 * simult�neo del almac�n por varios threads.
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
   // TODO: declaraci�n de los sem�foros necesarios

   public AlmacenN(int n) {
      capacidad = n;
      almacenado = new Producto[capacidad];
      nDatos = 0;
      aExtraer = 0;
      aInsertar = 0;
      ex = new Semaphore(1);
      alfull = new Semaphore(0);
      alemp = new Semaphore(1);
      // TODO: inicializaci�n de los sem�foros
   }

   public void almacenar(Producto producto) {
      // TODO: protocolo de acceso a la secci�n cr�tica y c�digo de
      // sincronizaci�n para poder almacenar.
	  ex.await();
	  alemp.await();
      // Secci�n cr�tica
      almacenado[aInsertar] = producto;
      nDatos++;
      aInsertar++;
      aInsertar %= capacidad;

      // TODO: protocolo de salida de la secci�n cr�tica y c�digo de
      // sincronizaci�n para poder extraer.
      alfull.signal();
      
   }

   public Producto extraer() {
      Producto result;

      // TODO: protocolo de acceso a la secci�n cr�tica y c�digo de
      // sincronizaci�n para poder extraer.
      alfull.await();
      // Secci�n cr�tica
      result = almacenado[aExtraer];
      almacenado[aExtraer] = null;
      nDatos--;
      aExtraer++;
      aExtraer %= capacidad;

      // TODO: protocolo de salida de la secci�n cr�tica y c�digo de
      // sincronizaci�n para poder almacenar.
      ex.signal();
      alemp.signal();
      return result;
   }
}
