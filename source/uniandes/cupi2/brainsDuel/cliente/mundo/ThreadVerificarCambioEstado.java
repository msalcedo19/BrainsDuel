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
 * Hilo que verifica el cambio de estado de la aplicación
 */
public class ThreadVerificarCambioEstado extends Thread
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Duelo actual
     */
    private Duelo duelo;

    /**
     * Clase principal de la interfaz
     */
    private InterfazBrainsDuel principal;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye el nuevo hilo y lo deja listo para conectarse al servidor.
     * @param pDuelo Batalla actual. pBatalla != null.
     * @param pPrincipal Ventana principal de la aplicación. pPrincipal != null.
     */
    public ThreadVerificarCambioEstado( Duelo pDuelo, InterfazBrainsDuel pPrincipal )
    {
        duelo = pDuelo;
        principal = pPrincipal;
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Inicia la ejecución del hilo que verifica los cambios en el estado de juego.<br>
     */
    public void run( )
    {
        while( duelo.darEstadoJuego( ) != 0 )
        {
            int estadoActual = duelo.darEstadoJuego( );
            int trofeosActual = duelo.darOponente( ).darTrofeos( ).size( );

            try
            {
                Thread.sleep( 1000 );
            }
            catch( InterruptedException e )
            {
                e.printStackTrace( );
            }

            int estadoNuevo = duelo.darEstadoJuego( );
            int trofeosNuevo = duelo.darOponente( ).darTrofeos( ).size( );

            if( estadoNuevo != estadoActual || trofeosNuevo != trofeosActual )
            {
                principal.actualizarInterfaz( );
            }

            if( duelo.darEstadoJuego( ) == Duelo.ESTADO_RETO )
            {
                principal.modoPregunta( );
            }

            String mensaje = duelo.darMensaje( );
            while( mensaje != null )
            {
                if( !mensaje.contains( Duelo.JUEGO_TERMINADO ) )
                {
                    String[] n = mensaje.split( "&&" );
                    ThreadMostrarMensaje t= new ThreadMostrarMensaje( duelo, principal, n[0], n[1] );
                    t.start( );
                    mensaje = duelo.darMensaje( );
                }
                else
                {
                    //Se termina el juego.
                }
            }

        }

    }
}
