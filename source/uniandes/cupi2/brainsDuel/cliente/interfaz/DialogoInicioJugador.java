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

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.WindowConstants;

/**
 * Diólogo que muestra las opciones iniciales de un jugador.
 */
@SuppressWarnings("serial")
public class DialogoInicioJugador extends JDialog implements ActionListener
{

    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Es el comando de la opción registrar.
     */
    private static final String REGISTRAR = "Registrar";

    /**
     * Es el comando de la opción iniciar sesión.
     */
    private static final String INICIAR_SESION = "Iniciar sesión";

    /**
     * Es el comando de la opción salir.
     */
    private static final String SALIR = "SALIR";

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Es una referencia a la clase principal de la interfaz del cliente.
     */
    private InterfazBrainsDuel principal;

    // -----------------------------------------------------------------
    // Atributos de la Interfaz
    // -----------------------------------------------------------------

    /**
     * Botón para registrarse.
     */
    private JButton btnRegistrar;

    /**
     * Botón para iniciar sesión.
     */
    private JButton btnIniciarSesion;

    /**
     * Botón para salir.
     */
    private JButton btnSalir;

    /**
     * Etiqueta de la imagen.
     */
    private JLabel lblImagenCupi2;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye un nuevo diólogo de inicio.
     * @param pInterfazCliente Referencia a la ventana principal. pInterfazCliente != null
     */
    public DialogoInicioJugador( InterfazBrainsDuel pInterfazCliente )
    {
        super( pInterfazCliente, true );
        setUndecorated( true );
        getRootPane( ).setWindowDecorationStyle( JRootPane.PLAIN_DIALOG );
        setSize( 450, 140 );
        setLocationRelativeTo( null );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setTitle( "BrainsDuel" );
        principal = pInterfazCliente;
        JPanel panelOpciones;
        lblImagenCupi2 = new JLabel( );
        panelOpciones = new JPanel( );
        btnRegistrar = new JButton( );
        btnIniciarSesion = new JButton( );
        btnSalir = new JButton( );

        setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
        setBackground( Color.WHITE );
        ImageIcon bannerCLiente = new ImageIcon( "data/img/bannerCliente.png" );
        bannerCLiente = new ImageIcon( bannerCLiente.getImage( ).getScaledInstance( 265, 155, java.awt.Image.SCALE_SMOOTH ) );

        lblImagenCupi2.setIcon( bannerCLiente );
        add( lblImagenCupi2, java.awt.BorderLayout.WEST );

        panelOpciones.setLayout( new GridLayout( 3, 0 ) );

        btnRegistrar.setText( "Registrarse" );
        btnRegistrar.addActionListener( this );
        btnRegistrar.setActionCommand( REGISTRAR );
        panelOpciones.add( btnRegistrar );

        btnIniciarSesion.setText( "Iniciar sesión" );
        btnIniciarSesion.addActionListener( this );
        btnIniciarSesion.setActionCommand( INICIAR_SESION );
        panelOpciones.add( btnIniciarSesion );

        btnSalir.setText( "Salir" );
        btnSalir.addActionListener( this );
        btnSalir.setActionCommand( SALIR );
        panelOpciones.add( btnSalir );

        panelOpciones.setBackground( new Color( 79, 85, 92 ) );
        panelOpciones.setOpaque( true );

        add( panelOpciones, java.awt.BorderLayout.CENTER );
        setBackground( new Color( 79, 85, 92 ) );
        getContentPane( ).setBackground( new Color( 79, 85, 92 ) );
        Font fuente;
        try
        {
            fuente = Font.createFont( Font.TRUETYPE_FONT, new File( InterfazBrainsDuel.FUENTE_BOLD ) ).deriveFont( 20f );
            btnRegistrar.setFont( fuente );
            btnIniciarSesion.setFont( fuente );
            btnSalir.setFont( fuente );
        }
        catch( FontFormatException | IOException e )
        {
            e.printStackTrace( );
        }
    }

    // -----------------------------------------------------------------
    // Mótodos
    // -----------------------------------------------------------------

    /**
     * Manejo de los eventos de los botones.
     * @param pEvento Acción que generó el evento. pEvento != null.
     */
    public void actionPerformed( ActionEvent pEvento )
    {
        String command = pEvento.getActionCommand( );
        if( command.equals( REGISTRAR ) )
        {
            principal.mostrarDialogoCrearCuenta( );
        }
        else if( command.equals( INICIAR_SESION ) )
        {
            principal.mostrarDialogoIniciarSesion( );
        }
        else if( command.equals( SALIR ) )
        {
            principal.dispose( );
            System.exit( 0 );
        }
    }

}
