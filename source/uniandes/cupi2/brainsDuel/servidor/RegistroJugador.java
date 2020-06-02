package uniandes.cupi2.brainsDuel.servidor;

import java.text.DecimalFormat;

import uniandes.cupi2.brainsDuel.cliente.mundo.Duelo;

/**
 * Esta clase mantiene la informaci�n del n�mero de de victorias y derrotas de un jugador<br>
 * <b>inv:</b><br>
 * aliasJugador !=null<br>
 * nombreJugador != null<br>
 * apellidoJugador != null<br>
 * contrase�aJugador !=null <br>
 * encuentrosGanados >= 0<br>
 * encuentrosPerdidos >= 0<br>
 */
public class RegistroJugador
{
	// -----------------------------------------------------------------
	// Atributos
	// -----------------------------------------------------------------

	/**
	 * El alias del jugador
	 */
	private String alias;

	/**
	 * El nombre del jugador
	 */
	private String nombre;

	/**
	 * El nombre del jugador
	 */
	private String apellidos;

	/**
	 * El nombre del jugador
	 */
	private String contrasenia;

	/**
	 * El n�mero de encuentros ganados hasta el momento
	 */
	private int victorias;

	/**
	 * El n�mero de encuentros perdidos hasta el momento
	 */
	private int derrotas;

	// -----------------------------------------------------------------
	// Constructores
	// -----------------------------------------------------------------

	/**
	 * Crea un nuevo registro
	 * @param aliasP El alias del jugador - alias!=null
	 * @param nombreP El nombre del jugador - nombre != null
	 * @Param apellidosP Los apellidos del jugador - apellidos != null
	 * @Param contraseniaP La contrase�a del jugador - contrasenia != null
	 * @param ganadosP El n�mero de encuentros ganados - ganados >= 0
	 * @param perdidosP El n�mero de encuentros perdidos - perdidos >= 0
	 */
	public RegistroJugador( String aliasP, String nombreP, String apellidosP, String contraseniaP, int ganadosP, int perdidosP )
	{
		alias = aliasP;
		nombre = nombreP;
		apellidos = apellidosP;
		contrasenia = contraseniaP;
		victorias = ganadosP;
		derrotas = perdidosP;
		verificarInvariante( );
	}

	// -----------------------------------------------------------------
	// M�todos
	// -----------------------------------------------------------------


	/**
	 * Retorna el alias del jugador
	 * @return aliasJugador
	 */
	public String darAliasJugador( )
	{
		return alias;
	}

	/**
	 * Retorna el nombre del jugador
	 * @return nombreJugador
	 */
	public String darNombreJugador( )
	{
		return nombre;
	}

	/**
	 * Retorna el apellido del jugador
	 * @return apellidoJugador
	 */
	public String darApellidosJugador( )
	{
		return apellidos;
	}


	/**
	 * Retorna la contrase�a del jugador
	 * @return contrase�a
	 */
	public String darContrase�aJugador( )
	{
		return contrasenia;
	}


	/**
	 * Retorna el n�mero de encuentros ganados por el jugador
	 * @return encuentrosGanados
	 */
	public int darEncuentrosGanados( )
	{
		return victorias;
	}

	/**
	 * Retorna el n�mero de encuentros perdidos por el jugador
	 * @return encuentrosPerdidos
	 */
	public int darEncuentrosPerdidos( )
	{
		return derrotas;
	}

	/**
	 * Retorna el porcentaje de efectividad del jugador. <br>
	 * Si el jugador no ha terminado ning�n encuentro, la efectividad es 0.
	 * @return encuentrosGanados * 100 / encuentrosTotales
	 */
	public double darEfectividad( )
	{
		if( victorias + derrotas > 0 )
			return ( ( double )victorias * 100.0 / ( double ) ( victorias + derrotas ) );
		else
			return 0.0;
	}

	/**
	 * Retorna una cadena con la informaci�n del registro
	 * @return Retorna una cadena de la forma <alias>: <nombre>: <apellidos>: <contrasenia>: <ganados> ganados / <perdidos> perdidos ( <efectividad>% )
	 */
	public String toString( )
	{
		DecimalFormat df = new DecimalFormat( "0.00" );
		String efectividad = df.format( darEfectividad( ) );
		return alias + ": " + victorias + " ganadas / " + derrotas + " perdidas " +"("+ efectividad +"%)";
	}




	// -----------------------------------------------------------------
	// Invariante
	// -----------------------------------------------------------------

	/**
	 * Verifica el invariante de la clase<br>
	 * <b>inv:</b><br>
	 * nombreJugador != null<br>
	 * encuentrosGanados >= 0<br>
	 * encuentrosPerdidos >= 0<br>
	 */
	private void verificarInvariante( )
	{
		assert ( alias != null ) : "El alias no puede ser null";
		assert ( nombre != null ) : "El nombre no puede ser null";
		assert ( apellidos != null ) : "El apellido no puede ser null";
		assert ( contrasenia != null ) : "La contrase�a no puede ser null";
		assert ( victorias >= 0 ) : "El n�mero de encuentros ganados debe ser mayor o igual a 0";
		assert ( derrotas >= 0 ) : "El n�mero de encuentros perdidos debe ser mayor o igual a 0";
	}
}

