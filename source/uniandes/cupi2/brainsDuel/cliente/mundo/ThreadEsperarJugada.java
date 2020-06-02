/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n12_brainsDuel
 * Autor: Equipo Cupi2 2016
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package uniandes.cupi2.brainsDuel.cliente.mundo;

import uniandes.cupi2.brainsDuel.cliente.interfaz.*;

/**
 * Esta clase implementa lo que se debe hacer en un hilo de ejecución cuando se quiere esperar la jugada del oponente.
 */
public class ThreadEsperarJugada extends Thread
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Referencia a la batalla.
     */
    private Duelo duelo;

    /**
     * Ventana principal de la aplicación.
     */
    private InterfazBrainsDuel principal;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye el nuevo hilo y lo deja listo para esperar la jugada.
     * @param pBatalla Referencia de la batalla. pBatalla != null.
     * @param pInterfaz Ventana principal de la aplicación. pInterfaz != null
     */
    public ThreadEsperarJugada( Duelo pBatalla, InterfazBrainsDuel pInterfaz )
    {
        super( );
        duelo = pBatalla;
        principal = pInterfaz;
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Inicia la ejecución del hilo que espera la jugada del oponente. <br>
     * Cuando se tiene información sobre la jugada del oponente entonces se actualiza la interfaz.<br>
     * Si el juego debe terminar entonces muestra quién fue el ganador y termina el encuentro y la conexión al servidor.
     */
    public void run( )
    {
        try
        {
            duelo.esperarJugada( );
        }

        catch( Exception e )
        {
            principal.mostrarError( e );
        }
    }
}
