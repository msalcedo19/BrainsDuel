package uniandes.cupi2.brainsDuel.servidor;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;

import uniandes.cupi2.brainsDuel.cliente.mundo.BrainsDuelException;

/**
 * Esta es la clase que se encarga de registrar los resultados de los encuentros sobre la base de datos. <br>
 * <b>inv:</b><br>
 * config!=null <br>
 */
public class AdministradorJugadores
{

	// -----------------------------------------------------------------
	// Atributos
	// -----------------------------------------------------------------

	/**
	 * Conexión a la base de datos
	 */
	private Connection conexion;

	/**
	 * Conjunto de propiedades que contienen la configuración de la aplicación
	 */
	private Properties config;

	// -----------------------------------------------------------------
	// Constructores
	// -----------------------------------------------------------------

	/**
	 * Construye el administrador de resultados y lo deja listo para conectarse a la base de datos
	 * @param propiedades Las propiedades para la configuración del administrador - Debe tener las propiedades "admin.db.path", "admin.db.driver", "admin.db.url" y
	 *        "admin.db.shutdown"
	 */
	public AdministradorJugadores( Properties propiedades )
	{
		config = propiedades;

		// Establecer la ruta donde va a estar la base de datos.
		// Derby utiliza la propiedad del sistema derby.system.home para saber donde están los datos
		File data = new File( config.getProperty( "admin.db.path" ) );
		System.setProperty( "derby.system.home", data.getAbsolutePath( ) );

	}



	// -----------------------------------------------------------------
	// Métodos
	// -----------------------------------------------------------------

    /**
     * Conecta el administrador a la base de datos
     * @throws SQLException Se lanza esta excepción si hay problemas realizando la operación
     * @throws Exception Se lanza esta excepción si hay problemas con los controladores
     */
    public void conectarABD( ) throws SQLException, Exception
    {
        String driver = config.getProperty( "admin.db.driver" );
        Class.forName( driver ).newInstance( );

        String url = config.getProperty( "admin.db.url" );
        try{
        conexion = DriverManager.getConnection( url );
        }catch(Exception e)
        {
        	e.printStackTrace();
        }
        verificarInvariante();
    }	
	

	/**
	 * Desconecta el administrador de la base de datos y la detiene
	 * @throws SQLException Se lanza esta excepción si hay problemas realizando la operación
	 */
	public void desconectarBD( ) throws SQLException
	{ 
		conexion.close( );
		String down = config.getProperty( "admin.db.shutdown" );
		try
		{
			DriverManager.getConnection( down );
		}
		catch( SQLException e )
		{
			// Al bajar la base de datos se produce siempre una excepción
		}
		verificarInvariante();
	}

	/**
	 * Crea la tabla necesaria para guardar los resultados. Si la tabla ya estaba creada entonces no hace nada. <br>
	 * @throws SQLException Se lanza esta excepción si hay problemas creando la tabla
	 */
	public void inicializarTabla( ) throws SQLException
	{
		Statement s = conexion.createStatement( );

		boolean crearTabla = false;
		try
		{
			// Verificar si ya existe la tabla resultados
			s.executeQuery( "SELECT * FROM jugadores WHERE 1=2" );
		}
		catch( SQLException se )
		{
			// La excepción se lanza si la tabla resultados no existe
			crearTabla = true;
		}

		// Se crea una nueva tabla vacía
		if( crearTabla )
		{
			s.execute( "CREATE TABLE jugadores (alias varchar(32), nombre varchar(32), apellidos varchar(32),contrasenia varchar(32), victorias int, derrotas int, PRIMARY KEY (alias))" );
		}

		s.close( );
		verificarInvariante();
	}

	/**
	 * Este método se encarga de consultar la información de un jugador (nombre, apellidos, contraseña, encuentros ganados y encuentros perdidos). <br>
	 * Si no se encuentra un registro del jugador en la base de datos, entonces se crea uno nuevo.
	 * @param pNombre El nombre del jugador del cual se está buscando información - pNombre != null
	 * @Param pApellidos El appellido del jugador del cual se esta buscando información - pApellidos !=null 
	 * @param pContrasenia La contraseña del jugador del cual se está buscando información - pContrasenia != null
	 * @return Retorna el registro de victorias y derrotas del jugador
	 * @throws Exception 
	 */
	public RegistroJugador loginJugador( String pAlias, String pContrasenia ) throws Exception
	{
		RegistroJugador registro = null;
		String sql = "SELECT alias, nombre, apellidos, contrasenia, victorias, derrotas FROM jugadores WHERE alias ='" + pAlias + "'";
		Statement st = conexion.createStatement( );
		ResultSet jugador = st.executeQuery( sql );
	
		
		if( jugador.next( ) ) // Se encontró el jugador
		{

			String nombre = jugador.getString( 2 );
			String apellidos = jugador.getString( 3 );
			String contrasenia = jugador.getString( 4 );
			int ganados = jugador.getInt( 5 );
			int perdidos = jugador.getInt( 6 );

			if(pContrasenia.equals(contrasenia))
			{
				registro = new RegistroJugador( pAlias, nombre, apellidos, contrasenia, ganados, perdidos );
			}

			else
			{
				st.close( );
				jugador.close( );
				throw new Exception("La contraseña es incorrecta");
			}

			jugador.close( );
		}
		else
		{
			jugador.close( );
			st.close( );
			throw new Exception("EL jugador no esta registrado");

		}
		st.close( );
		verificarInvariante();
		return registro;
	}

	public RegistroJugador registroJugador(String pAlias, String pNombre, String pApellidos, String pContrasenia) throws BrainsDuelException, SQLException 
	{
		RegistroJugador registro = null;

		String sql = "SELECT * FROM jugadores WHERE alias ='" + pAlias + "'";

		Statement st = conexion.createStatement( );
		ResultSet jugador = st.executeQuery( sql );

		if( jugador.next( ) ) // Se encontró el jugador
		{
			jugador.close();
			st.close();
			throw new BrainsDuelException(" Ya existe un jugador con ese alias");
		}
		else
		{

			String insert = "INSERT INTO jugadores ( alias, nombre, apellidos, contrasenia, victorias, derrotas) VALUES ('" + pAlias + "', '" + pNombre + "', '" + pApellidos + "', '" + pContrasenia + "', 0,0 )";
			st.executeUpdate(insert);
			registro = new RegistroJugador(pAlias, pNombre, pApellidos, pContrasenia, 0, 0);
		}

		jugador.close();
		st.close();
		return registro;

	}

		/**
		 * Este método se encarga de consultar la información de todos los jugadores (encuentros ganados y encuentros perdidos).
		 * @return Retorna una colección de los registros (RegistroJugador) de victorias y derrotas
		 * @throws SQLException Se lanza esta excepción si hay problemas en la comunicación con la base de datos
		 */
		public ArrayList consultarRegistrosJugadores( ) throws SQLException
		{
			ArrayList registros = new ArrayList( );
	
			String sql = "SELECT alias, nombre, apellidos, contrasenia, victorias, derrotas FROM jugadores";
	
			Statement st = conexion.createStatement( );
			ResultSet jugadores = st.executeQuery( sql );
	
			while( jugadores.next( ) )
			{
				String alias = jugadores.getString( 1 );
				String nombre = jugadores.getString( 2 );
				String apellidos = jugadores.getString( 3 );
				String contrasenia = jugadores.getString( 4 );
				int ganados = jugadores.getInt( 5 );
				int perdidos = jugadores.getInt( 6 );
	
				RegistroJugador registro = new RegistroJugador( alias, nombre, apellidos, contrasenia, ganados, perdidos );
				registros.add( registro );
			}
	
			jugadores.close( );
			st.close( );
	
			return registros;
		}


	/**
	 * Este método se encarga de registrar una victoria más a un jugador
	 * @param pAlias El alias del jugador al cual se le va a registrar la victoria - pAlias != null && corresponde a un registro en la base de datos
	 * @throws SQLException Se lanza esta excepción si hay problemas en la comunicación con la base de datos
	 */
	public void registrarVictoria( String pAlias ) throws SQLException
	{
		String sql = "UPDATE jugadores SET victorias = victorias+1 WHERE alias ='" + pAlias + "'";

		Statement st = conexion.createStatement( );
		st.executeUpdate( sql );
		st.close( );
		verificarInvariante();
	}

	/**
	 * Este método se encarga de registrar una derrota más a un jugador
	 * @param pAlias El alias del jugador al cual se le va a registrar la derrota - alias != null && corresponde a un registro en la base de datos
	 * @throws SQLException Se lanza esta excepción si hay problemas en la comunicación con la base de datos
	 */
	public void registrarDerrota( String pAlias ) throws SQLException
	{
		String sql = "UPDATE jugadores SET derrotas = derrotas+1 WHERE alias ='" + pAlias + "'";

		Statement st = conexion.createStatement( );
		st.executeUpdate( sql );
		st.close( );
		verificarInvariante();
	}

	public void eliminarTabla() throws SQLException
	{
		String sql = "DROP TABLE jugadores";
		Statement st = conexion.createStatement();
		st.executeUpdate(sql);
		st.close();
	}




	// -----------------------------------------------------------------
	// Invariante
	// -----------------------------------------------------------------
	/**
	 * Verifica el invariante de la clase <br>
	 * <b>inv:</b><br>    
	 * config!=null <br>
	 */
	private void verificarInvariante( )
	{                
		assert config != null : "Conjunto de propiedades inválidas";                           
	}

}

