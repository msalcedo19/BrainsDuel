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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Clase que representa una batalla del jugador. <br>
 * <b>inv: </b> <br>
 * estadoJuego pertenece a {SIN_CONECTAR, ESPERANDO_LOCAL, ESPERANDO_OPONENTE, ESPERANDO_RESPUESTA}.<br>
 * estadoJuego = SIN_CONECTAR => juegoTerminado = true.<br>
 * estadoJuego != SIN_CONECTAR => canal != null.<br>
 * estadoJuego != SIN_CONECTAR => out != null.<br>
 * estadoJuego != SIN_CONECTAR => in != null.<br>
 * estadoJuego != SIN_CONECTAR => jugador != null.<br>
 * estadoJuego != SIN_CONECTAR => oponente != null.<br>
 * estadoJuego != SIN_CONECTAR => servidor != null.<br>
 * estadoJuego != SIN_CONECTAR => mensajesSinLeer != null.<br>
 * estadoJuego != SIN_CONECTAR => puerto > 0.
 */
public class Duelo
{

    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------
    /**
     * Constante que representa el separador de un comando.
     */
    public static final String SEPARADOR_COMANDO = ";;;";

    /**
     * Constante que representa el separador de los par�metros.
     */
    public static final String SEPARADOR_PARAMETROS = ":::";

    /**
     * Mensaje para el env�o de informaci�n de un jugador.
     */
    public static final String JUGADOR = "JUGADOR";

    /**
     * Mensaje para el registro del jugador.
     */
    public static final String INFO_JUGADOR = "INFO";

    /**
     * Mensaje para indicar que un jugador tiene el primer turno.
     */
    public static final int PRIMER_TURNO = 1;

    /**
     * Mensaje para indicar que un jugador tiene el segundo turno.
     */
    public static final int SEGUNDO_TURNO = 2;

    /**
     * Indica que no se ha establecido la conexi�n con el servidor para jugar.
     */
    public static final int SIN_CONECTAR = 0;

    /**
     * Mensaje para enviar la informaci�n de un ataque.
     */
    public static final String ATAQUE = "ATAQUE";

    /**
     * Mensaje para indicar el resultado de un ataque.
     */
    public static final String DANIO = "DANIO";

    /**
     * Mensaje de login de un jugador.
     */
    public static final String LOGIN = "LOGIN";

    /**
     * Mensaje para recibir la informaci�n de un jugador.
     */
    public static final String INFO = "INFO";

    /**
     * Mensaje de registro de un jugador.
     */
    public static final String REGISTRO = "REGISTRO";

    /**
     * Mensaje para enviar un mensaje de error.
     */
    public final static String ERROR = "ERROR";

    /**
     * Mensaje para indicar quien fue el ganador del juego.
     */
    public static final String GANA_JUEGO = "GANA_JUEGO";

    /**
     * Identifica una pregunta.
     */
    public static final String PREGUNTA = "PREGUNTA";

    /**
     * Identifica una respuesta
     */
    public static final String RESPUESTA = "RESPUESTA";

    /**
     * Identifica la validaci�n de una pregunta
     */
    public static final String VALIDACION = "VALIDACION";

    /**
     * Indica que una pregunta se responde correctamente
     */
    public static final String CORRECTO = "CORRECTO";

    /**
     * Indica que una pregunta se responde de forma incorrecta
     */
    public static final String INCORRECTO = "INCORRECTO";

    /**
     * Indica que se env�an trofeos
     */
    public static final String TROFEOS = "TROFEOS";

    /**
     * Indica que se puede girar la ruleta
     */
    public static final String JUEGO = "JUEGO";

    /**
     * Respuesta afirmativa
     */
    public static final String SI = "SI";

    /**
     * Respuesta negativa
     */
    public static final String NO = "NO";

    /**
     * Categor�a de una pregunta
     */
    public static final String CATEGORIA = "CATEGORIA";

    /**
     * Identifica la categor�a "Arte"
     */
    public static final String ARTE = "ARTE";

    /**
     * Identifica la categor�a "Deporte"
     */
    public static final String DEPORTES = "DEPORTES";

    /**
     * Identifica la categor�a "Ciancia"
     */
    public static final String CIENCIA = "CIENCIA";

    /**
     * Identifica la categor�a "Historia"
     */
    public static final String HISTORIA = "HISTORIA";

    /**
     * Identifica la categor�a "Entretenimiento"
     */
    public static final String ENTRETENIMIENTO = "ENTRETENIMIENTO";

    /**
     * Identifica la categor�a "Geograf�a"
     */
    public static final String GEOGRAFIA = "GEOGRAFIA";

    /**
     * Indica que se est� esperando que el jugador realice una jugada.
     */
    public static final int ESPERANDO_LOCAL = 1;

    /**
     * Indica que se est� esperando a que el oponente realice una jugada.
     */
    public static final int ESPERANDO_OPONENTE = 2;

    /**
     * Indica que se acaba de enviar la jugada del jugador y se est� esperando la respuesta del oponente.
     */
    public static final int ESPERANDO_RESPUESTA = 3;

    /**
     * Indica que se encuentra en estado de reto.
     */
    public static final int ESTADO_RETO = 4;

    /**
     * Representa una corona.
     */
    public static final String CORONA = "CORONA";

    /**
     * Representa un reto.
     */
    public static final String RETO = "RETO";
    
    /**
     * Indica que gana el reto.
     */
    public static final String GANADOR_RETO = "GANADOR_RETO";
    
    /**
     * Indica que hay un empate.
     */
    public static final String EMPATE = "EMPATE";
    
    /**
     * Indica que se termina el juego.
     */
    public static final String JUEGO_TERMINADO = "JUEGO_TERMINADO";

    /**
     * Indica que el juego se termina
     */
    public static final int ESTADO_TERMINADO = 8;


    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Jugador de la batalla.
     */
    private Jugador jugador;

    /**
     * Oponente del jugador en la batalla.
     */
    private Jugador oponente;

    /**
     * Indica el estado actual del juego.
     */
    private int estadoJuego;

    /**
     * Indica si el juego termin� o no.
     */
    private boolean juegoTerminado;

    /**
     * Nombre del ganador del �ltimo encuentro.
     */
    private String nombreGanador;

    /**
     * Direcci�n de servidor al que se conect�.
     */
    private String servidor;

    /**
     * Puerto usado para conectarse.
     */
    private int puerto;

    /**
     * Canal usado para comunicarse con el servidor.
     */
    private Socket canal;

    /**
     * Flujo que env�a los datos al servidor a trav�s del socketServidor.
     */
    private PrintWriter outWriter;

    /**
     * Flujo de donde se leen los datos que llegan del servidor a trav�s del socketServidor.
     */
    private BufferedReader inReader;

    /**
     * Categor�a obtenida de forma aleatoria
     */
    private String categoriaAleatoria;

    /**
     * Indica si la ruleta gira
     */
    private boolean ruletaGira;

    /**
     * Pregunta actual del sistema
     */
    private Pregunta preguntaActual;

    /**
     * Respuesta del usuario
     */
    private String respuestaUsuario;

    /**
     * Mensajes para la interfaz
     */
    private ArrayList<String> mensajes;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Constructor de la batalla. <br>
     * <b> post: </b> Se inicializan el jugador y el oponente.<br>
     * @throws BrainsDuelException Si hay alg�n problema.
     */
    public Duelo( ) throws BrainsDuelException
    {

        jugador = new Jugador( );
        oponente = new Jugador( );
        juegoTerminado = false;
        estadoJuego = ESPERANDO_OPONENTE;
        preguntaActual = null;
        respuestaUsuario = null;
        mensajes = new ArrayList<String>( );
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Retorna la direcci�n del servidor.
     * @return Direcci�n del servidor.
     */
    public String darDireccionServidor( )
    {
        return servidor;
    }

    /**
     * Retorna el puerto usado para conectarse al servidor.
     * @return Puerto al cual se conecta el programa.
     */
    public int darPuertoServidor( )
    {
        return puerto;
    }

    /**
     * Retorna el nombre del jugador que gan� el juego.
     * @return Nombre del ganador.
     */
    public String darNombreGanador( )
    {
        return nombreGanador;
    }

    /**
     * Retorna el jugador de la batalla.
     * @return Jugador de la batalla.
     */
    public Jugador darJugador( )
    {
        return jugador;
    }

    /**
     * Retorna el oponente.
     * @return Jugador oponente.
     */
    public Jugador darOponente( )
    {
        return oponente;
    }

    /**
     * Retorna el estado actual del juego.
     * @return Estado del juego.
     */
    public int darEstadoJuego( )
    {
        return estadoJuego;
    }

    /**
     * Indica si la �ltima batalla jugada ya termin�.
     * @return False si el juego no ha terminado, true en caso contrario.
     */
    public boolean juegoTerminado( )
    {
        return juegoTerminado;
    }

    /**
     * Retorna la pregunta actual del juego
     * @return Pregunta actual del juego
     */
    public Pregunta darPreguntaActual( )
    {
        return preguntaActual;
    }

    /**
     * Establece una conexi�n con el servidor del juego y env�a los datos del jugador para poder empezar un juego.<br>
     * Este m�todo termina cuando se consigue un oponente y se establece la conexi�n entre los dos jugadores.
     * @param pDireccionServidor Direcci�n usada para encontrar el servidor. pDireccionServidor != null && pDireccionServidor != "".
     * @param pPuertoServidor Puerto usado para realizar la conexi�n. pPuertoServidor > 0.
     * @param pAlias Alias del jugador. pAlias != null && pAlias != "".
     * @param pPassword Contrase�a del jugador. pPassword != null && pPassword != ""..
     * @throws BrainsDuelException Si hay problemas estableciendo la comunicaci�n.
     */
    public void iniciarSesion( String pDireccionServidor, int pPuertoServidor, String pAlias, String pPassword ) throws BrainsDuelException
    {

        servidor = pDireccionServidor;
        puerto = pPuertoServidor;
        jugador.modificarAlias( pAlias );
        jugador.modificarContrasenia( pPassword );

        try
        {
            // Conectar al servidor
            canal = new Socket( pDireccionServidor, pPuertoServidor );
            outWriter = new PrintWriter( canal.getOutputStream( ), true );
            inReader = new BufferedReader( new InputStreamReader( canal.getInputStream( ) ) );

            // iniciar el encuentro
            iniciarEncuentro( LOGIN );
        }
        catch( UnknownHostException e )
        {
            e.printStackTrace( );
            throw new BrainsDuelException( "No fue posible establecer una conexi�n con el servidor. " + e.getMessage( ) );
        }
        catch( IOException e )
        {
            e.printStackTrace( );
            throw new BrainsDuelException( "No fue posible establecer una conexi�n con el servidor. " + e.getMessage( ) );
        }

    }

    /**
     * Establece una conexi�n con el servidor del juego y env�a los datos del jugador para poder empezar un juego.<br>
     * Este m�todo termina cuando se consigue un oponente y se establece la conexi�n entre los dos jugadores.
     * @param pNombre Nombre del jugador local. pNombre != null.
     * @param pDireccionServidor Direcci�n usada para encontrar el servidor. pDireccionServidor != null.
     * @param pPuertoServidor Puerto usado para realizar la conexi�n. pPuertoServidor > 0.
     * @param pAlias Alias del jugador. pAlias != null && pAlias != "".
     * @param pApellidos Apellidos del jugador. pApellidos != null && pApellidos != "".
     * @param pPassword Contrase�a del jugador. pPassword != null && pPassword != "".
     * @throws BrainsDuelException Si hay problemas estableciendo la comunicaci�n.
     */
    public void registrar( String pDireccionServidor, int pPuertoServidor, String pAlias, String pNombre, String pApellidos, String pPassword ) throws BrainsDuelException
    {
        jugador.modificarNombreJugador( pNombre );
        servidor = pDireccionServidor;
        puerto = pPuertoServidor;
        jugador.modificarAlias( pAlias );
        jugador.modificarApellidos( pApellidos );
        jugador.modificarContrasenia( pPassword );

        try
        {
            // Conectar al servidor
            canal = new Socket( pDireccionServidor, pPuertoServidor );
            outWriter = new PrintWriter( canal.getOutputStream( ), true );
            inReader = new BufferedReader( new InputStreamReader( canal.getInputStream( ) ) );

            // iniciar el encuentro
            iniciarEncuentro( REGISTRO );
        }
        catch( UnknownHostException e )
        {
            throw new BrainsDuelException( "No fue posible establecer una conexi�n con el servidor. " + e.getMessage( ) );
        }
        catch( IOException e )
        {
            throw new BrainsDuelException( "No fue posible establecer una conexi�n con el servidor. " + e.getMessage( ) );
        }
    }

    /**
     * Env�a al servidor los mensajes necesarios para iniciar una batalla y recibe la informaci�n del oponente y del turno.
     * @throws IOException Se lanza esta excepci�n si hay un problema leyendo del canal.
     * @throws BatallaPokemonException Si hay un problema leyendo el canal.
     */
    private void iniciarEncuentro( String pTipoConexion ) throws IOException, BrainsDuelException
    {

        juegoTerminado = false;
        nombreGanador = "";

        if( pTipoConexion.equals( LOGIN ) )
        {
            outWriter.println( LOGIN + SEPARADOR_COMANDO + jugador.darAlias( ) + SEPARADOR_PARAMETROS + jugador.darContrasenia( ) );
        }
        else
        {
           
        	outWriter.println( REGISTRO + SEPARADOR_COMANDO + jugador.darAlias( ) + SEPARADOR_PARAMETROS + jugador.darNombreJugador( ) + SEPARADOR_PARAMETROS + jugador.darApellidosJugador( ) + SEPARADOR_PARAMETROS + jugador.darContrasenia( ) );
        	 
        }

        // Leer la informaci�n del jugador
        String[] partes = inReader.readLine( ).split( SEPARADOR_COMANDO );
        String[] datosJugador = partes[ 1 ].split( SEPARADOR_PARAMETROS );
        if( partes[ 0 ].equals( ERROR ) )
        {
            throw new BrainsDuelException( datosJugador[ 0 ] );
        }
        else if( partes[ 0 ].equals( INFO ) )
        {
            jugador.modificarCantidadDerrotas( Integer.parseInt( datosJugador[ 1 ] ) );
            jugador.modificarCantidadVictorias( Integer.parseInt( datosJugador[ 2 ] ) );

            // Leer la informaci�n del oponente
            String[] partesOponente = inReader.readLine( ).split( SEPARADOR_COMANDO );
            if( partesOponente[ 0 ].equals( ERROR ) )
            {
                throw new BrainsDuelException( datosJugador[ 0 ] );
            }
            else if( partesOponente[ 0 ].equals( INFO ) )
            {
                String[] datosOponente = partesOponente[ 1 ].split( SEPARADOR_PARAMETROS );
                oponente.modificarAlias( datosOponente[ 0 ] );
                oponente.modificarCantidadDerrotas( Integer.parseInt( datosOponente[ 1 ] ) );
                oponente.modificarCantidadVictorias( Integer.parseInt( datosOponente[ 2 ] ) );

                int turno = Integer.parseInt( inReader.readLine( ) );
                if( PRIMER_TURNO == turno )
                {
                    estadoJuego = ESPERANDO_LOCAL;
                }
                else
                {
                    estadoJuego = ESPERANDO_RESPUESTA;
                }
            }

        }
        else
        {
            throw new BrainsDuelException( "Error al iniciar sesi�n. No se recibi� el mensaje esperado." );
        }
    }

    /**
     * Este m�todo se encarga de esperar una jugada que env�e el otro jugador, actualizar los datos y enviar la respuesta al servidor. <br>
     * Si el juego termina, este m�todo debe cambiar el valor de juegoTerminado.<br>
     * <b>pre:</b>estadoJuego = ESPERANDO_JUGADA_OPONENTE.
     * @throws BrainsDuelException Si hay problemas en la comunicaci�n.
     * @throws IOException Si hubo problemas de lectura
     * @throws InterruptedException Si hubo problemas en la espera
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void esperarJugada( ) throws BrainsDuelException, IOException, InterruptedException
    {
        ruletaGira = false;
        String mensaje = inReader.readLine( );
        if( mensaje.startsWith( JUEGO ) )
        {
  
            preguntaActual = null;
            respuestaUsuario = null;
            estadoJuego = ESPERANDO_LOCAL;
            String categoria = calcularCategoriaAleatoria( );

            if( estadoJuego != ESTADO_RETO )
            {
                outWriter.println( CATEGORIA + SEPARADOR_COMANDO + categoria );
            }
            boolean enReto = estadoJuego == ESTADO_RETO;

            if( enReto )
            {
                categoria = CORONA;
            }

            mensaje = inReader.readLine( );
            recibirPregunta( mensaje, categoria, enReto );

        }

        else if( mensaje.startsWith( TROFEOS ) )
        {
            if( mensaje.contains( SEPARADOR_COMANDO ) )
            {
                String[] trofeos = mensaje.split( SEPARADOR_COMANDO )[ 1 ].split( SEPARADOR_PARAMETROS );
                ArrayList listaTrofeos = new ArrayList<>( );
                for( String trofeo : trofeos )
                {
                    listaTrofeos.add( trofeo );
                }
                oponente.cambiarTrofeos( listaTrofeos );
            }

            esperarJugada( );

        }

        else if( mensaje.startsWith( GANA_JUEGO ) )
        {
            String[] ganador = mensaje.split( SEPARADOR_COMANDO );

            if( ganador[ 1 ].equals( jugador.darAlias( ) ) )
            {
                agregarMensaje( "�Felicitaciones! Ganaste el juego&&Juego terminado" );
                
            }

            else
            {
                agregarMensaje( "Perdiste el juego&&Juego terminado" );
            }
            
            juegoTerminado = true;
            terminarEncuentro( );
            agregarMensaje(JUEGO_TERMINADO);
            
        }

        else if( mensaje.startsWith( PREGUNTA ) )
        {
            preguntaActual = null;
            respuestaUsuario = null;
            estadoJuego = ESTADO_RETO;
            boolean enReto = true;
            recibirPregunta( mensaje, CORONA, enReto );
        }

    }

    /**
     * Retorna la respuesta del usuario a la pregunta
     * @return Respuesta del usuario
     */
    public String darRespuestaUsuario( )
    {
        return respuestaUsuario;
    }

    /**
     * Recibe una pregunta y una categor�a
     * @param pMensaje Mensaje recibido del servidor
     * @param pCategoria Categor�a de la pregunta
     * @param enReto Indica si se recibe la pregunta de un reto
     * @return Respuesta a la pregunta
     * @throws BrainsDuelException Si hay problemas recibiendo la pregunta
     */
    private String recibirPregunta( String pMensaje, String pCategoria, boolean enReto ) throws BrainsDuelException
    {

        String cadenaParametros = pMensaje.split( SEPARADOR_COMANDO )[ 1 ];

        if( !pMensaje.startsWith( PREGUNTA ) )
            throw new BrainsDuelException( "Se esperaba recibir una pregunta: " + pMensaje + "." );

        String[] parametros = cadenaParametros.split( SEPARADOR_PARAMETROS );
        String pregunta = parametros[ 0 ];
        String[] opciones = { parametros[ 1 ], parametros[ 2 ], parametros[ 3 ], parametros[ 4 ] };
        preguntaActual = new Pregunta( pCategoria, pregunta, opciones );
        String respuesta = preguntaActual.darRespuestaUsuario( );
        return respuesta;

    }

    /**
     * Permite responder una pregunta
     * @param pRespuesta Respuesta dada por el usuario
     * @return True si se responde correctamente, false de lo contrario.
     * @throws IOException Si hubo problemas leyendo del socket.
     * @throws BrainsDuelException Si hubo problemas leyendo el mensaje del servidor.
     * @throws InterruptedException SI hubo problemas esperando la jugada
     */
    public String responderPregunta( String pRespuesta ) throws IOException, BrainsDuelException, InterruptedException
    {

        ruletaGira = false;
        outWriter.println( RESPUESTA + SEPARADOR_COMANDO + pRespuesta );
        String mensaje = inReader.readLine( );
        String respuesta = "";

        if( estadoJuego != ESTADO_RETO )
        {

            if( !mensaje.startsWith( VALIDACION ) )
                throw new BrainsDuelException( "Se esperaba recibir la VALIDACI�N de la pregunta " + mensaje + "." );

            boolean correcto = mensaje.split( SEPARADOR_COMANDO )[ 1 ].equals( CORRECTO );

            if( correcto )
            {

                respuesta = "Respuesta correcta";
                if( jugador.darPreguntasCorona( ) == 3 )
                {
                    String m = jugador.agregarTrofeo( preguntaActual.darCategoria( ) );
                    if( m != null )
                    {
                        agregarMensaje( m );
                    }

                }

                jugador.aumentarPreguntasCorona( );

            }
            else
            {
                respuesta = "Respuesta incorrecta";
                estadoJuego = ESPERANDO_RESPUESTA;
            }

            // Env�a los trofeos al oponente
            imprimirTrofeos( );

        }
        else
        {
            if( !mensaje.startsWith( GANADOR_RETO ) )
                throw new BrainsDuelException( "Se esperaba recibir GANADOR_RETO de la pregunta " + mensaje + "." );
            
            String[] resp = mensaje.split( SEPARADOR_COMANDO );
            
            if(resp[1].equals( SI ))
            {
                respuesta = "Duelo ganado";
                jugador.cambiarPreguntasCorona( 3 );
            }
            else if(resp[1].equals( EMPATE ))
            {
                respuesta = "Duelo empatado";
            }
            else
            {
                respuesta = "Duelo perdido";
            }
            estadoJuego = ESPERANDO_OPONENTE;
        }
        
        return respuesta;

    }

    /**
     * Agrega un mensaje a la lista
     * @param pMensaje
     */
    private synchronized void agregarMensaje( String pMensaje )
    {
        mensajes.add( pMensaje );
    }

    /**
     * Calcula una categor�a aleatoria para la pregunta
     * @return Categor�a aleatoria
     * @throws InterruptedException Si hubo problemas en la verificaci�n del giro de la ruleta
     */
    public String calcularCategoriaAleatoria( ) throws InterruptedException
    {
        verificarRuletaGira( );

        if( estadoJuego != ESTADO_RETO )
        {
            if( categoriaAleatoria.equals( CORONA ) )
            {
                jugador.cambiarPreguntasCorona( 3 );
            }
        }

        return categoriaAleatoria;

    }

    /**
     * Retorna un mensaje del mundo
     * @return Retorna un mensaje del mundo
     */
    public synchronized String darMensaje( )
    {
        if( mensajes.size( ) > 0 )
        {
            String mensaje = mensajes.get( 0 );
            mensajes.remove( mensaje );
            return mensaje;
        }
        return null;
    }

    /**
     * Verifica que la ruleta termin� de girar
     */
    private void verificarRuletaGira( )
    {

        while( ruletaGira == false && estadoJuego != ESTADO_RETO )
        {
            try
            {
                Thread.sleep( 1000 );
            }
            catch( InterruptedException e )
            {
                // Error en el thread, no se muestra.
            }
        }

        ruletaGira = false;

    }

    /**
     * Informa que la ruleta termina de girar
     * @param pCategoria categor�a calculada
     */
    public void ruletaGira( String pCategoria )
    {
        categoriaAleatoria = pCategoria;
        ruletaGira = true;
    }

    /**
     * Retorna la categor�a aleatoria ya calculada
     * @return Categor�a aleatoria
     */
    public String darCategoriaAleatoria( )
    {
        return categoriaAleatoria;
    }

    /**
     * Imprime en el socket los trofeos del jugador.
     */
    private void imprimirTrofeos( )
    {
        String separador = SEPARADOR_COMANDO;
        String mensaje = TROFEOS;
        @SuppressWarnings("rawtypes")
        ArrayList trofeos = jugador.darTrofeos( );

        for( int i = 0; i < trofeos.size( ); i++ )
        {
            mensaje += separador;
            String actual = ( String )trofeos.get( i );
            mensaje += actual;
            separador = SEPARADOR_PARAMETROS;
        }
        outWriter.println( mensaje );

    }

    /**
     * Realiza las tareas necesarias para terminar el encuentro.<br>
     * Se averigua el nombre del ganador, la conexi�n con el servidor se cierra y el estado del juego pasa a SIN_CONECTAR.<br>
     * <b>pre:</b>juegoTerminado = true.
     * @throws BrainsDuelException Si hay problemas en la comunicaci�n.
     */
    public void terminarEncuentro( ) throws BrainsDuelException
    {
        try
        {
            estadoJuego = ESTADO_TERMINADO;

            // Cerrar la conexi�n al servidor
            outWriter.close( );
            inReader.close( );
            canal.close( );

            outWriter = null;
            inReader = null;
            canal = null;
        }
        catch( IOException e )
        {
            throw new BrainsDuelException( "Se presentaron problemas con la conexi�n al servidor. " + e.getMessage( ) );
        }
        catch( ArrayIndexOutOfBoundsException e )
        {
            throw new BrainsDuelException( "Se presentaron problemas terminando el encuentro. No se recibi� el mensaje esperado." );

        }
    }

    /**
     * Env�a un mensaje de reto al servidor
     */
    public void enviarMensajeReto( )
    {
        estadoJuego = ESTADO_RETO;
        outWriter.println( RETO );
    }

    // -----------------------------------------------------------------
    // Puntos de Extensi�n
    // -----------------------------------------------------------------

    /**
     * M�todo para la extensi�n 1.
     * @return Respuesta 1.
     */
    public String metodo1( )
    {
        return "Respuesta 1";
    }

    /**
     * M�todo para la extensi�n 2.
     * @return Respuesta 2.
     */
    public String metodo2( )
    {
        return "Respuesta 2";
    }

}
