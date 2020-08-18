package threads;
import es.upm.babel.cclib.ConcIO;
import es.upm.babel.cclib.Monitor;

public class ControlAccesoPuente
{

	private int cruza;
	private Puente.Entrada entra;
	private Monitor mutexPuente = new Monitor();
	private Monitor.Cond podemos [] = new Monitor.Cond[2];

	private static final int MAP_N = 0;
	private static final int MAP_S = 1;
	
	private int translate(Puente.Entrada e)
	{
		return e == Puente.Entrada.N?
			MAP_N:
			MAP_S;
	}

	private int translate(Puente.Salida e)
	{
		return e == Puente.Salida.N?
			MAP_N:
			MAP_S;
	}

	public ControlAccesoPuente()
	{
		this.cruza = 0;
		this.entra = Puente.Entrada.N;
		podemos[MAP_N] = mutexPuente.newCond();
		podemos[MAP_S] = mutexPuente.newCond();
	}

	public void solicitarEntrada(Puente.Entrada e)
	{
		mutexPuente.enter();
		ConcIO.printfnl("Coche quiere entrar por ENTRADA " + e);
		if(this.cruza != 0 && this.entra != e) 
			podemos[translate(e)].await();

		this.entra = e;
		this.cruza = this.cruza + 1;

		ConcIO.printfnl("Coche puede entrar por ENTRADA " + e
			+ "\nEstado: (" + this.cruza + ", " + this.entra + ")");

		podemos[translate(e)].signal();

		mutexPuente.leave();
	}

	public void avisarSalida(Puente.Salida s) throws Exception
	{
		if(this.cruza <= 0 || this.entra.equals(Puente.convertS2E(s)))
			throw new Exception();
		mutexPuente.enter();
		this.cruza = this.cruza - 1;
		ConcIO.printfnl("Coche sale por SALIDA " + s
			+ "\nEstado: (" + this.cruza + ", " + this.entra + ")");
		if(this.cruza == 0)
			podemos[translate(s)].signal();
		mutexPuente.leave();
	}

}

