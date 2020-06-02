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

import java.io.IOException;

import uniandes.cupi2.brainsDuel.cliente.interfaz.*;

/**
 * Esta clase implementa lo que se debe hacer en un hilo de ejecuci�n cuando se quiere conectar al cliente con el servidor.
 */
public class ThreadRegistrar extends Thread
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
     * Nombre que utilizar� el jugador.
     */
    private String nombre;

    /**
     * Apellidos que utilizar� el jugador.
     */
    private String apellidos;

    /**
     * Alias que utilizar� el jugador.
     */
    private String alias;

    /**
     * Direcci�n para localizar al servidor.
     */
    private String servidor;

    /**
     * Puerto a trav�s del cual se realizar� la conexi�n con el servidor.
     */
    private int puerto;

    /**
     * Password del jugador.
     */
    private String password;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye el nuevo hilo y lo deja listo para conectarse al servidor.
     * @param pBatalla Batalla actual. pBatalla != null.
     * @param pInterfaz Ventana principal de la aplicaci�n. pInterfaz != null.
     * @param pNombreJugador Nombre que utilizar� el jugador. pNombreJugador != null.
     * @param pApellidosJugador Apellidos que utilizar� el jugador. pApellidosJugador != null.
     * @param pDireccionServidor Direcci�n para localizar al servidor. pDireccionServidor != null.
     * @param pPuertoServidor Puerto a trav�s del cual se realizar� la conexi�n con el servidor. pPuertoServidor != null.
     * @param pAlias Alias que utilizar� el jugador. pAlias != null.
     * @param pPassword Contrase�a que utilizar� el jugador. pPassword != null && pPassword != "".
     */
    public ThreadRegistrar( Duelo pBatalla, InterfazBrainsDuel pInterfaz, String pDireccionServidor, int pPuertoServidor, String pAlias, String pNombreJugador, String pApellidosJugador, String pPassword )
    {
        duelo = pBatalla;
        principal = pInterfaz;
        servidor = pDireccionServidor;
        puerto = pPuertoServidor;
        alias = pAlias;
        nombre = pNombreJugador;
        apellidos = pApellidosJugador;
        password = pPassword;
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
            duelo.registrar( servidor, puerto, alias, nombre, apellidos, password );
            principal.actualizarInterfaz( );
            duelo.esperarJugada( );

        }
        catch( BrainsDuelException | IOException | InterruptedException e )
        {
            principal.mostrarError( e );
        }

    }
}
