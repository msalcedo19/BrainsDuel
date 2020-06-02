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

package uniandes.cupi2.brainsDuel.cliente.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import uniandes.cupi2.brainsDuel.cliente.mundo.BrainsDuelException;
import uniandes.cupi2.brainsDuel.cliente.mundo.Duelo;
import uniandes.cupi2.brainsDuel.cliente.mundo.ThreadEnviarReto;
import uniandes.cupi2.brainsDuel.cliente.mundo.ThreadEsperarJugada;
import uniandes.cupi2.brainsDuel.cliente.mundo.ThreadIniciarSesion;
import uniandes.cupi2.brainsDuel.cliente.mundo.ThreadRegistrar;
import uniandes.cupi2.brainsDuel.cliente.mundo.ThreadVerificarCambioEstado;

/**
 * Ventana principal de la aplicación.
 */
@SuppressWarnings("serial")
public class InterfazBrainsDuel extends JFrame implements WindowListener
{

    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Ruta de la fuente REGULAR
     */
    public static final String FUENTE_REGULAR = "data/fuentes/regular.ttf";

    /**
     * Ruta de la fuente en negrilla
     */
    public static final String FUENTE_BOLD = "data/fuentes/bold.ttf";

    /**
     * Modo Ruleta dentro de la interfaz
     */
    public static final int MODO_RULETA = 1;

    /**
     * Modo Pregunta dentro de la interfaz
     */
    public static final int MODO_PREGUNTA = 2;

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Dirección del servidor.
     */
    private String servidor;

    /**
     * Puerto para conectarse al servidor.
     */
    private int puerto;

    /**
     * Clase principal del mundo.
     */
    private Duelo duelo;

    // -----------------------------------------------------------------
    // Atributos de la interfaz
    // -----------------------------------------------------------------

    /**
     * Panel la imagen de banner.
     */
    private PanelImagen panelImagen;

    /**
     * Panel con los personajes
     */
    private PanelPersonajes panelPersonajes;

    /**
     * Panel con la ruleta
     */
    private PanelRuleta panelRuleta;

    /**
     * Panel con la pregunta y las opciones
     */
    private PanelPregunta panelPregunta;

    /**
     * Panel con las preguntas respondidas para ganar la corona.
     */
    private PanelCoronas panelCoronas;

    /**
     * Diálogo de inicio
     */
    private DialogoInicioJugador dialogoInicio;

    /**
     * Diálogo para registrar un nuevo jugador
     */
    private DialogoRegistrar dialogoRegistrar;

    /**
     * Diálogo para iniciar sesión
     */
    private DialogoIniciarSesion dialogoIniciarSesion;

    /**
     * Panel ubicado en la parte sur de la interfaz
     */
    private JPanel panelSur;

    /**
     * Modo actual de la interfaz
     */
    private int modo;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye la interfaz para un cliente del juego.
     */
    public InterfazBrainsDuel( )
    {

        try
        {

            duelo = new Duelo( );
            dialogoInicio = new DialogoInicioJugador( this );
            dialogoRegistrar = new DialogoRegistrar( this );
            dialogoIniciarSesion = new DialogoIniciarSesion( this );

            setTitle( "Brains Duel" );
            getContentPane( ).setLayout( new BorderLayout( ) );
            setSize( 615, 750 );
            setResizable( false );
            setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
            getContentPane( ).setBackground( new Color( 79, 85, 92 ) );

            panelImagen = new PanelImagen( );
            add( panelImagen, BorderLayout.NORTH );
            panelPersonajes = new PanelPersonajes( );
            panelSur = new JPanel( new BorderLayout( ) );
            panelPregunta = new PanelPregunta( this );
            panelRuleta = new PanelRuleta( this );
            panelCoronas = new PanelCoronas( this );
            panelSur.add( panelCoronas, BorderLayout.SOUTH );
            add( panelSur, BorderLayout.SOUTH );
            setLocationRelativeTo( null );
            modoRuleta( );

            ThreadVerificarCambioEstado t = new ThreadVerificarCambioEstado( duelo, this );
            t.start( );

            dialogoInicio.addWindowListener( this );
            dialogoInicio.setVisible( true );

        }
        catch( Exception e )
        {
            JOptionPane.showMessageDialog( this, "Error al ejecutar la aplicación:" + e.getMessage( ) );
            e.printStackTrace( );
        }

    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Actualiza la interfaz al modo ruleta.
     */
    public void modoRuleta( )
    {
        modo = MODO_RULETA;
        remove( panelPregunta );
        add( panelPersonajes, BorderLayout.CENTER );
        panelSur.add( panelRuleta, BorderLayout.CENTER );
        repaint( );
        validate( );
    }

    /**
     * Actualiza la interfaz al modo pregunta.
     */

    public void modoPregunta( )
    {
        modo = MODO_PREGUNTA;
        remove( panelPersonajes );
        panelSur.remove( panelRuleta );

        while( duelo.darPreguntaActual( ) == null )
        {
            try
            {
                Thread.sleep( 600 );
            }
            catch( InterruptedException e )
            {
                e.printStackTrace( );
            }
        }
        panelPregunta.actualizar( duelo.darPreguntaActual( ) );
        add( panelPregunta, BorderLayout.CENTER );
        repaint( );
        validate( );

    }

    /**
     * Método para iniciar sesión con los parámetros dados.
     * @param pAlias Alias del jugador. pAlias != null.
     * @param pPassword Contraseña del jugador. pPassword != null.
     */
    public void iniciarSesion( String pAlias, String pPassword )
    {
        dialogoInicio.setVisible( false );
        dialogoIniciarSesion.setVisible( false );
        dialogoRegistrar.setVisible( false );

        ThreadIniciarSesion t = new ThreadIniciarSesion( duelo, this, servidor, puerto, pAlias, pPassword );
        t.start( );

        // Vuelve visible la ventana principal de la interfaz
        this.setVisible( true );

    }

    /**
     * Crea un nuevo registro de jugador
     * @param alias Alias del jugador
     * @param nombre Nombre del jugador
     * @param apellido Apellido del jugador
     * @param pass1 Contraseña del jugador
     */
    public void crearRegistro( String alias, String nombre, String apellido, String pass1 )
    {
        dialogoInicio.setVisible( false );
        dialogoIniciarSesion.setVisible( false );
        dialogoRegistrar.setVisible( false );

        ThreadRegistrar t = new ThreadRegistrar( duelo, this, servidor, puerto, alias, nombre, apellido, pass1 );
        t.start( );

        // Vuelve visible la ventana principal de la interfaz
        this.setVisible( true );
    }

    /**
     * Espera una jugada del oponente. <br>
     * El proceso para esperar una jugada se hace en un hilo aparte usando la clase ThreadEsperarJugada.
     */
    public void esperarJugada( )
    {

        Thread t = new ThreadEsperarJugada( duelo, this );
        t.start( );

    }

    /**
     * Configura los datos de la conexión.
     * @param pServidor Nombre del servidor. pServidor != null.
     * @param pPuerto puerto para conectarse al servidor. pPuerto>=0.
     */
    public void configurarDatosConexion( String pServidor, int pPuerto )
    {
        servidor = pServidor;
        puerto = pPuerto;

    }

    /**
     * Actualiza la interfaz gráfica.
     */
    @SuppressWarnings("unchecked")
    public void actualizarInterfaz( )
    {
        String nombreJugador = duelo.darJugador( ).darAlias( );
        String nombreOponente = duelo.darOponente( ).darAlias( );
        String victoriasJugador = duelo.darJugador( ).darCantidadVictorias( ) + " victorias - " + duelo.darJugador( ).darCantidadDerrotas( ) + " derrotas";
        String victoriasOponente = duelo.darOponente( ).darCantidadVictorias( ) + " victorias - " + duelo.darOponente( ).darCantidadDerrotas( ) + " derrotas";
        panelPersonajes.actualizarNombres( nombreJugador, nombreOponente, victoriasJugador, victoriasOponente );

        if( duelo.darEstadoJuego( ) == Duelo.ESPERANDO_OPONENTE )
        {
            panelImagen.actualizarImagen( Duelo.ESPERANDO_OPONENTE );
        }
        else if( duelo.darEstadoJuego( ) == Duelo.ESPERANDO_LOCAL )
        {
            panelImagen.actualizarImagen( Duelo.ESPERANDO_LOCAL );
        }
        else if( duelo.darEstadoJuego( ) == Duelo.ESPERANDO_RESPUESTA )
        {
            panelImagen.actualizarImagen( Duelo.ESPERANDO_RESPUESTA );
        }
        else if(duelo.darEstadoJuego( ) == Duelo.ESTADO_RETO)
        {
            panelImagen.actualizarImagen( Duelo.ESTADO_RETO );
        }
        else if(duelo.darEstadoJuego( ) == Duelo.ESTADO_TERMINADO)
        {
            panelImagen.actualizarImagen( Duelo.ESTADO_TERMINADO );
        }

        int pEstado = duelo.darEstadoJuego( );
        panelRuleta.actualizar( pEstado );
        
        if( pEstado == Duelo.ESPERANDO_OPONENTE || pEstado == Duelo.ESPERANDO_RESPUESTA ||  pEstado == Duelo.ESTADO_RETO )
        {
            panelCoronas.deshabilitarReto( );
        }
        else
        {
            panelCoronas.habilitarReto( );
        }

        
        panelCoronas.actualizar( duelo.darJugador( ).darPreguntasCorona( ) );
        panelPersonajes.actualizarTrofeos( duelo.darJugador( ).darTrofeos( ), duelo.darOponente( ).darTrofeos( ) );
    }

    /**
     * Consulta cuál fue el jugador que ganó el encuentro y le avisa al usuario.
     */
    public void mostrarGanador( )
    {
        JOptionPane.showMessageDialog( this, "El ganador del encuentro fue " + duelo.darNombreGanador( ) + ".", "Fin del juego", JOptionPane.INFORMATION_MESSAGE );
        validate( );
    }

    /**
     * Muestra un mensaje con el error enviado.
     * @param pExcepcion Excepción con el error. pExcepcion != null.
     */
    public void mostrarError( Exception pExcepcion )
    {
        JOptionPane.showMessageDialog( this, pExcepcion.getMessage( ), "BrainsDuel", JOptionPane.ERROR_MESSAGE );
        windowClosing( null );

    }

    /**
     * Retorna la categoría calculada por el mundo
     * @return Categoría aleatoria calculada
     */
    public String darCategoriaAleatoria( )
    {

        return duelo.darCategoriaAleatoria( );

    }

    /**
     * Muestra el diálogo para crear una nueva cuenta
     */
    public void mostrarDialogoCrearCuenta( )
    {

        dialogoRegistrar.setVisible( true );

    }

    /**
     * Muestra el diálogo para iniciar sesión
     */
    public void mostrarDialogoIniciarSesion( )
    {

        dialogoIniciarSesion.setVisible( true );

    }

    /**
     * Informa que la ruleta termina de girar
     * @param pCategoria Categoría en la que cae la ruleta
     */
    public void ruletaGira( String pCategoria )
    {
        panelCoronas.deshabilitarReto( );
        duelo.ruletaGira( pCategoria );
        modoPregunta( );

    }

    /**
     * Retorna el estado actual del juego
     * @return Estado actual del juego
     */
    public int darEstado( )
    {
        return duelo.darEstadoJuego( );
    }
    
    /**
     * Envía un reto al servidor
     */
    public void enviarReto( )
    {
        ThreadEnviarReto t = new ThreadEnviarReto( duelo );
        t.start( );
    }

    // -----------------------------------------------------------------
    // Puntos de Extensión
    // -----------------------------------------------------------------

    /**
     * Responde la pregunta recibida
     * @param respuesta Respuesta a la pregunta
     * @return True si se responde correctamente, false de lo contrario
     * @throws Exception Si ocurre un error
     * @throws BrainsDuelException Si hubo problemas respondiendo la pregunta
     */
    public String responderPregunta( String respuesta ) throws Exception
    {
        String r = duelo.responderPregunta( respuesta );
        return r;
    }

    /**
     * Retorna el modo de juego
     * @return Modo de juego
     */
    public int darModo( )
    {
        return modo;
    }
    
    /**
     * Muestra el mensaje que llega por parámetro
     * @param parte1 Parte 1 del mensaje
     * @param parte2 Parte 2 del mensaje
     */
    public void mostarMensaje( String parte1, String parte2 )
    {
        JOptionPane.showMessageDialog( this, parte1, parte2, JOptionPane.INFORMATION_MESSAGE );
        
    }

    // -----------------------------------------------------------------
    // Mótodos de WindowListener
    // -----------------------------------------------------------------



    /**
     * Mótodo para manejar los eventos de una ventana al momento de cerrarse.
     * @param pEvento Evento de la ventana.
     */
    public void windowClosing( WindowEvent pEvento )
    {
        this.dispose( );
        System.exit( 0 );
    }

    /**
     * Mótodo para manejar los eventos de una ventana al momento de activarse.
     * @param pEvento Evento de la ventana.
     */
    public void windowActivated( WindowEvent pEvento )
    {
        // No se implementa.
    }

    /**
     * Mótodo para manejar los eventos de una ventana cuando ha sido cerrada.
     * @param pEvento Evento de la ventana.
     */
    public void windowClosed( WindowEvent pEvento )
    {
        // No se implementa.
    }

    /**
     * Mótodo para manejar los eventos de una ventana cuando no estó activa.
     * @param pEvento Evento de la ventana.
     */
    public void windowDeactivated( WindowEvent pEvento )
    {
        // No se implementa.
    }

    /**
     * Mótodo para manejar los eventos de una ventana.
     * @param pEvento Evento de la ventana.
     */
    public void windowDeiconified( WindowEvent pEvento )
    {
        // No se implementa.
    }

    /**
     * Mótodo para manejar los eventos de una ventana.
     * @param pEvento Evento de la ventana.
     */
    public void windowIconified( WindowEvent pEvento )
    {
        // No se implementa.
    }

    /**
     * Mótodo para manejar los eventos de una ventana al momento abrirse.
     * @param pEvento Evento de la ventana.
     */
    public void windowOpened( WindowEvent pEvento )
    {
        // No se implementa.
    }

    // -----------------------------------------------------------------
    // Main
    // -----------------------------------------------------------------

    /**
     * Ejecuta la aplicación, creando una nueva interfaz.
     * @param args Argumentos para la ejecución de la aplicación. En este caso no son necesarios.
     */
    public static void main( String[] args )
    {
        try
        {
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
        }
        catch( ClassNotFoundException e )
        {
            e.printStackTrace( );
        }
        catch( InstantiationException e )
        {
            e.printStackTrace( );
        }
        catch( IllegalAccessException e )
        {
            e.printStackTrace( );
        }
        catch( UnsupportedLookAndFeelException e )
        {
            e.printStackTrace( );
        }
        InterfazBrainsDuel interfaz = new InterfazBrainsDuel( );
        interfaz.setVisible( true );

    }

}
