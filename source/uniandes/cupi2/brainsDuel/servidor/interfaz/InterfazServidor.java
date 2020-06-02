package uniandes.cupi2.brainsDuel.servidor.interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import uniandes.cupi2.brainsDuel.servidor.BrainsDuel;
import uniandes.cupi2.brainsDuel.servidor.RegistroJugador;

/**
 * Esta es la ventana principal del servidor de Brains Duel.
 */
public class InterfazServidor extends JFrame
{

	// -----------------------------------------------------------------
	// Atributos
	// -----------------------------------------------------------------

	private BrainsDuel servidorBrainsDuel;

	// -----------------------------------------------------------------
	// Atributos de la interfaz
	// -----------------------------------------------------------------

	/**
	 * Es el panel donde se muestran la imagen del servidor.
	 */
	private PanelImagen panelImagen;

	/**
	 * Es el panel donde se muestran los registros de los jugadores
	 */
	private PanelJugadores panelJugadores;

	/**
	 * Es el panel donde se muestran los encuentros actuales
	 */
	private PanelEncuentros panelEncuentros;

	/**
	 * Es el panel donde se muestran las obciones.
	 */
	private PanelOpciones panelOpciones;

	// -----------------------------------------------------------------
	// Constructores
	// -----------------------------------------------------------------

	/**
	 * Construye la ventana principal de la aplicación<br>
	 * @param servidor Es una referencia al servidor sobre el que funciona esta interfaz
	 */
	public InterfazServidor(BrainsDuel servidor)
	{
		servidorBrainsDuel = servidor;

		// Construye la ventana
		setLayout( new BorderLayout());
		setSize( 689, 550 );
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE);
		setTitle ( "Servidor Batalla Naval" );
		setLocationRelativeTo(null);

		panelImagen = new PanelImagen();
		add( panelImagen , BorderLayout.WEST );

		JPanel centro = new JPanel();
		centro.setLayout(new BorderLayout());

		panelJugadores = new PanelJugadores();
		panelEncuentros = new PanelEncuentros();
		centro.add(panelJugadores, BorderLayout.NORTH);
		centro.add(panelEncuentros, BorderLayout.SOUTH);

		add( centro, BorderLayout.CENTER);

		panelOpciones = new PanelOpciones(this);
		add(panelOpciones, BorderLayout.EAST);


	}


	/**
	 * Actualiza la lista de encuentros mostrada en el panelEncuentros
	 */
	public void actualizarEncuentros( )
	{
		Collection encuentros = servidorBrainsDuel.darListaActualizadaEncuentros( );
		panelEncuentros.actualizarEncuentros( encuentros );
	}

	/**
	 * Actualiza la lista de jugadores mostrada en el panelJugadores
	 */
	public void actualizarJugadores( )
	{
		try
		{
			Collection jugadores = servidorBrainsDuel.darAdministradorResultados( ).consultarRegistrosJugadores( );
			panelJugadores.actualizarJugadores( jugadores );
		}
		catch( SQLException e )
		{
			JOptionPane.showMessageDialog( this, "Hubo un error consultando la lista de jugadores:\n" + e.getMessage( ), "Error", JOptionPane.ERROR_MESSAGE );
		}
	}

	/**
	 * Cierra la ventana y la aplicación
	 */
	public void dispose( )
	{
		super.dispose( );
		try
		{
			servidorBrainsDuel.darAdministradorResultados( ).desconectarBD( );
		}
		catch( SQLException e )
		{
			e.printStackTrace( );
		}
		System.exit( 0 );
	}


	// -----------------------------------------------------------------
	// Puntos de Extensión
	// -----------------------------------------------------------------

	/**
	 * Método para la extensión 1
	 */
	public void reqFuncOpcion1( )
	{
		String resultado = servidorBrainsDuel.metodo1( );
		JOptionPane.showMessageDialog( this, resultado, "Respuesta", JOptionPane.INFORMATION_MESSAGE );
	}

	/**
	 * Método para la extensión 2
	 */
	public void reqFuncOpcion2( )
	{
		String resultado = servidorBrainsDuel.metodo2( );
		JOptionPane.showMessageDialog( this, resultado, "Respuesta", JOptionPane.INFORMATION_MESSAGE );
	}

	// -----------------------------------------------------------------
	// Main
	// -----------------------------------------------------------------

	/**
	 * Este método ejecuta la aplicación, creando una nueva interfaz
	 * @param args Parámetros de ejecución que no son usados
	 */
	public static void main(String[] args)
	{
		try
		{
			String archivoPropiedades = "./data/servidor.properties";	
			BrainsDuel servidorBrainsDuel = new BrainsDuel(archivoPropiedades);

			InterfazServidor interfaz = new InterfazServidor(servidorBrainsDuel);
			interfaz.setVisible( true );

			servidorBrainsDuel.recibirConexiones();
		}
		catch( Exception e)
		{
			e.printStackTrace();
		}

	}

}
