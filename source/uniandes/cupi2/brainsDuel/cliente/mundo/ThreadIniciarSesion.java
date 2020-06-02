/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad de los Andes (Bogot� - Colombia)
 * Departamento de Ingenier�a de Sistemas y Computaci�n 
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
 * Esta clase implementa lo que se debe hacer en un hilo de ejecuci�n cuando se quiere conectar al cliente con el servidor.
 */
public class ThreadIniciarSesion extends Thread
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Referencia a la batalla.
     */
    private Duelo duelo;

    /**
     * Ventana principal de la aplicaci�n
     */
    private InterfazBrainsDuel principal;

    /**
     * Alias que utilizar� el jugador.
     */
    private String alias;

    /**
     * Password del jugador.
     */
    private String password;

    /**
     * Direcci�n para localizar al servidor.
     */
    private String servidor;

    /**
     * Puerto a trav�s del cual se realizar� la conexi�n con el servidor.
     */
    private int puerto;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye el nuevo hilo y lo deja listo para conectarse al servidor.
     * @param pBatalla Batalla actual. pBatalla != null.
     * @param pInterfaz Ventana principal de la aplicaci�n. pInterfaz != null.
     * @param pDireccionServidor Direcci�n para localizar al servidor. pDireccionServidor != null.
     * @param pPuertoServidor Puerto a trav�s del cual se realizar� la conexi�n con el servidor. pPuertoServidor != null && pPuertoServidor != "".
     * @param pAlias Alias que utilizar� el jugador. pAlias != null && pAlias != "".
     * @param pPassword Contrase�a que utilizar� el jugador. pPassword != null && pPassword != "".
     */
    public ThreadIniciarSesion( Duelo pBatalla, InterfazBrainsDuel pInterfaz, String pDireccionServidor, int pPuertoServidor, String pAlias, String pPassword )
    {
        duelo = pBatalla;
        password = pPassword;
        principal = pInterfaz;
        servidor = pDireccionServidor;
        puerto = pPuertoServidor;
        alias = pAlias;
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Inicia la ejecuci�n del hilo que realiza la conexi�n con el servidor, dispara la selecci�n de los pok�mon e incializa la batalla.<br>
     * Cuando se tiene la conexi�n y la informaci�n de la batalla entonces se actualiza la interfaz.
     */
    public void run( )
    {
        try
        {
            duelo.iniciarSesion( servidor, puerto, alias, password );
            principal.actualizarInterfaz( );
            duelo.esperarJugada( );

        }
        catch( Exception e )
        {
            principal.mostrarError( e );
        }

    }
}
