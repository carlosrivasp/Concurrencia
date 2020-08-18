package threads;
import es.upm.babel.cclib.Producto;
import es.upm.babel.cclib.MultiAlmacen;
import es.upm.babel.cclib.Monitor;
import es.upm.babel.cclib.MultiAlmacen;

// importar la librería de monitores

public class MultiAlmacenMon {
	private int capacidad = 0;
	private Producto almacenado[] = null;
	private int aExtraer = 0;
	private int aInsertar = 0;
	private int nDatos = 0;

	// TODO: declaración de atributos extras necesarios
	// para exclusión mutua y sincronización por condición
	boolean hayDato = false;
	Monitor mutex;
	Monitor.Cond cAlmacenar;
	Monitor.Cond cExtraer;

	// Para evitar la construcción de almacenes sin inicializar la
	// capacidad 
	private MultiAlmacenMon() {
		mutex = new Monitor();
		cAlmacenar = mutex.newCond();
		cExtraer = mutex.newCond();
	}

	public MultiAlmacenMon(int n) {
		almacenado = new Producto[n];
		aExtraer = 0;
		aInsertar = 0;
		capacidad = n;
		nDatos = 0;

		// TODO: inicialización de otros atributos
		mutex = new Monitor();
		cAlmacenar = mutex.newCond();
		cExtraer = mutex.newCond();

	}

	private int nDatos() {
		return nDatos;
	}

	private int nHuecos() {
		return capacidad - nDatos;
	}

	public void almacenar(Producto[] productos) {
		mutex.enter();
		// TODO: implementación de código de bloqueo para 
		// exclusión muytua y sincronización condicional 
		if(hayDato) {
			cAlmacenar.await();
			// Sección crítica
			for (int i = 0; i < productos.length; i++) {
				almacenado[aInsertar] = productos[i];
				nDatos++;
				aInsertar++;
				aInsertar %= capacidad;
			}
		}
		hayDato = true;
		cExtraer.signal();
		// TODO: implementación de código de desbloqueo para
		// sincronización condicional y liberación de la exclusión mutua  
		mutex.leave();
	}

	public Producto[] extraer(int n) {
		Producto[] result = new Producto[n];
		mutex.enter();
		// TODO: implementación de código de bloqueo para exclusión
		// mutua y sincronización condicional 
		if(!hayDato) {
			cExtraer.await();
			// Sección crítica
			for (int i = 0; i < result.length; i++) {
				result[i] = almacenado[aExtraer];
				almacenado[aExtraer] = null;
				nDatos--;
				aExtraer++;
				aExtraer %= capacidad;
			}
		}
		hayDato = false;
		cAlmacenar.signal();
		// TODO: implementación de código de desbloqueo para
		// sincronización condicional y liberación de la exclusión mutua  
		mutex.leave();
		return result;
	}
}
