package threads;
/**
 * Interfaz de control de acceso al puente de un solo sentido.
 *
 * Hay dos accesos de entrada al puente, uno al norte y otro al sur.
 *
 * Hay dos salidas del puente, una al norte y otra al sur.
 *
 * En las entradas y en las salidas hay detectores. Para detectar que
 * un vehículo ha llegado a un detector se usan los métodos detectar.
 *
 * Las entradas están controladas con barreras. Para abrir una barrera
 * se utiliza el método abrir.
 */

import java.util.Random;

public class Puente {
   // TODO: introducir atributos para la simulación (generadores
   // aleatorios, etc.)
   
   /**
    * Enumerado con los identificadores de las entradas.
    */
   static public enum Entrada { N, S }

   /**
    * Enumerado con los identificadores de las salidas.
    */
   static public enum Salida { N, S }

   static public Entrada convertS2E(Salida s)
   {
      return s == Salida.N? Entrada.N:Entrada.S;
   }
   static public Salida convertE2S(Entrada e)
   {
      return e == Entrada.N? Salida.N:Salida.S;
   }

   private static final int MAX_WAITING_TIME_N = 200;
   private static final int MAX_WAITING_TIME_S = 200;


   /**
    * El thread que invoque la operación detectar queda bloqueado
    * hasta que un coche llegue el detector indicado, en ese momento
    * el thread termina de ejecutar el método.
    */
   static public void detectar(Entrada e) {
      int tope = e == Entrada.N? MAX_WAITING_TIME_N : MAX_WAITING_TIME_S;
      try
      {
        Thread.sleep((new Random()).nextInt(tope));
      }
      catch (InterruptedException exc){}
   }

   /**
    * El thread que invoque la operación detectar queda bloqueado
    * hasta que un coche llegue el detector indicado, en ese momento
    * el thread termina de ejecutar el método.
    */
   static public void detectar(Salida s) {

      try
      {
        Thread.sleep(200);
      }
      catch (InterruptedException exc){}
   }

   /**
    * Al invocar la operación se abrirá la barrera indicada, se
    * permite la entrada de un coche al puente y se cierra la barrera
    * indicada. El tiempo que tarda en ejecutarse el método abrir
    * coincide con el tiempo que tarda en realizarse toda la actividad
    * (abrir-pasar-cerrar).
    */
   static public void abrir(Entrada e) {
      try
      {
        Thread.sleep(50);
      }
      catch (InterruptedException exc){}
   }
}
