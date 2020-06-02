/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad de los Andes (Bogot� - Colombia)
 * Departamento de Ingenier�a de Sistemas y Computaci�n 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n12_brainsDuel
 * Autor: Equipo Cupi2 2017-2
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package uniandes.cupi2.brainsDuel.cliente.mundo;

/**
 * Hilo que verifica el cambio de estado de la aplicaci�n
 */
public class ThreadEnviarReto extends Thread
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Duelo actual
     */
    private Duelo duelo;
    

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye el nuevo hilo y lo deja listo para conectarse al servidor.
     * @param pDuelo Batalla actual. pBatalla != null.
     */
    public ThreadEnviarReto( Duelo pDuelo )
    {
        duelo = pDuelo;
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Inicia la ejecuci�n del hilo que verifica los cambios en el estado de juego.<br>
     */
    public void run( )
    {
        duelo.enviarMensajeReto();

    }
}
