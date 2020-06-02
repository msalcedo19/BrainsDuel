package uniandes.cupi2.brainsDuel.servidor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import uniandes.cupi2.brainsDuel.cliente.mundo.BrainsDuelException;
import uniandes.cupi2.brainsDuel.cliente.mundo.Duelo;


/**
 * El Servidor Brains Duel es el que se encarga de recibir conexiones de los clientes y organizar los encuentros. <br>
 * <b>inv:</b><br>
 * receptor!= null <br>
 * config!=null <br>
 * adminResultados!=null <br>
 * encuentros!=null <br>
 */
public class BrainsDuel 
{

	// -----------------------------------------------------------------
	// Atributos
	// -----------------------------------------------------------------

	/**
	 * El canal usado para comunicarse con el jugador 1
	 */
	private Socket socketJugador1;

	/**
	 * El flujo de escritura conectado con el jugador 1
	 */
	private PrintWriter out1;

	/**
	 * El flujo de lectura conectado con el jugador 1
	 */
	private BufferedReader in1;

	/**
	 * Es el punto por el cual los clientes solicitan conexiones
	 */
	private ServerSocket receptor;

	/**
	 * Es el conjunto de propiedades que contienen la configuración de la aplicación
	 */
	private Properties config;

	/**
	 * Es el administrador que permite registrar los resultados sobre la base de datos
	 */
	private AdministradorJugadores adminJugadores;

	/**
	 * Este es el canal utilizado para establecer una comunicación con un jugador que está en espera de un oponente. <br>
	 * Si no hay jugadores en espera, este canal debe ser null.
	 */
	private Socket socketJugadorEnEspera;

	/**
	 * Es una colección con los encuentros que se están llevando a cabo en este momento
	 */
	protected Collection encuentros;

	private BancoPreguntas preguntas;

	private RegistroJugador registroActual;

	private RegistroJugador registroJugadorEnEspera;

	// -----------------------------------------------------------------
	// Constructores
	// -----------------------------------------------------------------

	/**
	 * Inicia el servidor y deja listo el administrador de resultados
	 * @param archivo El archivo de propiedades que tiene la configuración del servidor - archivo != null
	 * @throws Exception Se lanza esta excepción si hay problemas con el archivo de propiedades o hay problemas en la conexión a la base de datos
	 */
	public BrainsDuel( String archivo ) throws Exception
	{
		encuentros = new Vector( );

		cargarConfiguracion( archivo );

		adminJugadores = new AdministradorJugadores( config );
		adminJugadores.conectarABD( );
		adminJugadores.inicializarTabla( );
		preguntas = new BancoPreguntas("./data/preguntas.txt");
		verificarInvariante(); 
	}

	// -----------------------------------------------------------------
	// Métodos
	// -----------------------------------------------------------------

	/**
	 * Carga la configuración a partir de un archivo de propiedades
	 * @param archivo El archivo de propiedades que contiene la configuración que requiere el servidor - archivo != null y el archivo debe contener la propiedad
	 *        "servidor.puerto" y las propiedades que requiere el administrador de resultados
	 * @throws Exception Se lanza esta excepción si hay problemas cargando el archivo de propiedades
	 */
	private void cargarConfiguracion( String archivo ) throws Exception
	{
		FileInputStream fis = new FileInputStream( archivo );
		config = new Properties( );
		config.load( fis );
		fis.close( );
	}


	/**
	 * Retorna al administrador de resultados
	 * @return adminResultados;
	 */
	public AdministradorJugadores darAdministradorResultados( )
	{
		return adminJugadores;
	}

	/**
	 * Retorna una colección actualizada con los encuentros que se están desarrollando actualmente y no han terminado.<br>
	 * Si había encuentros en la lista que ya habían terminado deben ser eliminados.
	 * @return colección de encuentros
	 */
	public Collection darListaActualizadaEncuentros( )
	{
		Collection listaActualizada = new Vector( );

		// Armar la lista actualizada con los encuentros que no han terminado
		Iterator iter = encuentros.iterator( );
		while( iter.hasNext( ) )
		{
			Encuentro e = ( Encuentro )iter.next( );
			if( !e.encuentroTerminado( ) )
				listaActualizada.add( e );
		}

		// Reemplazar la lista antigua con la lista actualizada
		encuentros = listaActualizada;

		return encuentros;
	}

	/**
	 * Este método se encarga de recibir todas las conexiones entrantes y crear los encuentros cuando fuera necesario.
	 * @throws BrainsDuelException 
	 */
	public void recibirConexiones( ) throws BrainsDuelException
	{

		String aux = config.getProperty( "servidor.puerto" );
		int puerto = Integer.parseInt( aux );

		try
		{
			receptor = new ServerSocket( puerto );

			while( true )
			{
				// Esperar una nueva conexión
				Socket socketNuevoCliente = receptor.accept( );
			
				// Intentar iniciar un encuentro con el nuevo cliente
				crearEncuentro( socketNuevoCliente );
			}
		}
		catch( IOException e )
		{
			e.printStackTrace( );
		}
		finally
		{
			try
			{
				receptor.close( );
			}
			catch( IOException e )
			{
				e.printStackTrace( );
			}
		}
	}


	/**
	 * Intenta crear e iniciar un nueva batalla con el jugador que se acaba de conectar. <br>
	 * Si no se tiene ya un oponente, entonces el jugador queda en espera de que otra persona se conecte.
	 * @param pSocketNuevoCliente El canal que permite la comunicación con el nuevo cliente. pSocketNuevoCliente != null.
	 * @throws IOException Se lanza esta excepción si se presentan problemas de comunicación.
	 */
	synchronized private void crearEncuentro( Socket pSocketNuevoCliente )
	{

		try
		{

			out1 = new PrintWriter( pSocketNuevoCliente.getOutputStream( ), true );
			in1 = new BufferedReader( new InputStreamReader( pSocketNuevoCliente.getInputStream( ) ) );

			// Leer la información sobre los jugadores.
			String informacion = in1.readLine( );
			try
			{
				registroActual = ConsultaInformacionJugador(informacion);
				

				if( socketJugadorEnEspera == null )
				{
					// No hay un oponente aún, así que hay que dejarlo en espera.
					socketJugadorEnEspera = pSocketNuevoCliente;
					registroJugadorEnEspera = registroActual;
				}
				else
				{
					// Ya se tiene un oponente así que se puede empezar una partida.
					Encuentro nuevo = new Encuentro( socketJugadorEnEspera, in1, out1, adminJugadores, registroJugadorEnEspera, registroActual, preguntas );
					iniciarEncuentro( nuevo );
					socketJugadorEnEspera = null;

				}
			}
			catch( BrainsDuelException e )
			{
				out1.println( Duelo.ERROR + Duelo.SEPARADOR_COMANDO + e.getMessage( ) );
			}
		}
		catch( IOException e )
		{
			try
			{
				socketJugadorEnEspera.close( );
				pSocketNuevoCliente.close( );
			}
			catch( IOException e1 )
			{
				// Hubo un error cerrando el canal
			}

		}

		verificarInvariante( );
	}

	/**
	 * Obtiene la información de un jugador a partir del mensaje que envió cuando entró al encuentro
	 * @param info El mensaje que fue enviado - info es de la forma "JUGADOR:<nombre>"
	 * @return Retorna la información del jugador
	 * @throws Exception 
	 * @throws ErrorLoginException 
	 * @throws BatallaNavalException Se lanza esta excepción si hay problemas consultando a la base de datos o se recibe un mensaje con un formato inesperado
	 */
	private RegistroJugador ConsultaInformacionJugador( String info ) throws BrainsDuelException
	{
		
		RegistroJugador reg1= null;
		if( info.startsWith( Duelo.LOGIN ) )
		{

			String separador = info.split( Duelo.SEPARADOR_COMANDO )[ 1 ];
			String alias = separador.split( Duelo.SEPARADOR_PARAMETROS )[ 0 ];
			String contrasenia = separador.split( Duelo.SEPARADOR_PARAMETROS )[ 1 ];
			try
			{
				reg1 = adminJugadores.loginJugador( alias, contrasenia);
			}
			catch( SQLException e ) 
			{
				throw new BrainsDuelException( "Hubo un problema leyendo la información del jugador: " + e.getMessage( ) );
			}
			catch(Exception e)
			{
				out1.println(Duelo.ERROR + Duelo.SEPARADOR_COMANDO + e.getMessage());
			}

		}
		else if(info.startsWith(Duelo.REGISTRO))
		{
			String separador = info.split( Duelo.SEPARADOR_COMANDO )[ 1 ];
			String alias = separador.split( Duelo.SEPARADOR_PARAMETROS )[ 0 ];
			String nombre = separador.split( Duelo.SEPARADOR_PARAMETROS )[ 1 ];
			String apellidos = separador.split( Duelo.SEPARADOR_PARAMETROS )[ 2 ];
			String contrasenia = separador.split( Duelo.SEPARADOR_PARAMETROS )[ 3 ];
			
			try
			{
				reg1 = adminJugadores.registroJugador(alias, nombre, apellidos, contrasenia);
			}
			catch( SQLException e ) 
			{
				throw new BrainsDuelException( "Hubo un problema leyendo la información del jugador: " + e.getMessage( ) );
			}
			catch(Exception e)
			{
				out1.println(Duelo.ERROR + Duelo.SEPARADOR_COMANDO + e.getMessage());
			}
		}
		else
		{
			throw new BrainsDuelException( "El mensaje no tiene el formato esperado" );
		}
		
		return reg1;

	}

	/**
	 * Agrega el encuentro a la lista de encuentros en curso y lo inicia
	 * @param nuevoEncuentro Un encuentro que no ha sido inicializado ni agregado a la lista de encuentros - nuevoEncuentro != null
	 */
	protected void iniciarEncuentro( Encuentro nuevoEncuentro )
	{
		encuentros.add( nuevoEncuentro );
		nuevoEncuentro.start( );
	}


	// -----------------------------------------------------------------
	// Invariante
	// -----------------------------------------------------------------

	/**
	 * Verifica el invariante de la clase <br>
	 * <b>inv:</b><br>
	 * receptor!= null <br>
	 * config!=null <br>
	 * adminResultados!=null <br>
	 * encuentros!=null <br>
	 */
	private void verificarInvariante( )
	{        
		assert receptor != null : "Canal inválido";
		assert config != null : "Conjunto de propiedades inválidas";
		assert adminJugadores != null : "El administrador de resultados no debería ser null";
		assert encuentros != null : "La lista de encuentros no debería ser null";     
		assert preguntas !=null : "Las preguntas no pueden ser nulas";
	}
	// -----------------------------------------------------------------
	// Puntos de Extensión
	// -----------------------------------------------------------------

	/**
	 * Método para la extensión 1
	 * @return respuesta1
	 */
	public String metodo1( )
	{
		return "Respuesta 1";
	}

	/**
	 * Método para la extensión2
	 * @return respuesta2
	 */
	public String metodo2( )
	{
		return "Respuesta 2";
	}



}
