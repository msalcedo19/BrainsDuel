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

package uniandes.cupi2.brainsDuel.cliente.interfaz;

import java.awt.BorderLayout;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * Di�logo para iniciar sesi�n.
 */
public class DialogoIniciarSesion extends JDialog implements ActionListener
{

    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Constante para la serializaci�n.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Representa el comando para iniciar sesi�n.
     */
    private static final String INICIAR_SESION = "CREAR_CUENTA";

    /**
     * Representa el comando para salir.
     */
    private static final String CANCELAR = "SALIR";

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
     * Bot�n para iniciar sesi�n.
     */
    private JButton btnIniciarSesion;

    /**
     * Bot�n para cancelar.
     */
    private JButton btnCancelar;

    /**
     * Texto alias de usuario.
     */
    private JTextField txtServidor;

    /**
     * Texto alias de usuario.
     */
    private JTextField txtPuerto;

    /**
     * Campo de texto para el alias.
     */
    private JTextField txtAlias;

    /**
     * Campo de texto del password.
     */
    private JPasswordField txtPwd;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye un nuevo di�logo para iniciar sesi�n.
     * @param pPrincipal Referencia a la ventana principal. pPrincipal != null.
     */
    public DialogoIniciarSesion( InterfazBrainsDuel pPrincipal )
    {
        super( pPrincipal, true );

        setLayout( new BorderLayout( ) );
        principal = pPrincipal;
        setSize( 325, 420 );
        setLocationRelativeTo( null );
        setTitle( "Iniciar sesi�n" );

        JPanel panelDatos = new JPanel( );
        TitledBorder borde = new TitledBorder( "Iniciar sesi�n" );
        panelDatos.setBorder( borde );

        JLabel lblImagen = new JLabel( );
        lblImagen.setHorizontalAlignment( JLabel.CENTER );
        ImageIcon bannerCLiente = new ImageIcon( "data/img/bannerCliente.png" );
        bannerCLiente = new ImageIcon( bannerCLiente.getImage( ).getScaledInstance( 265, 155, java.awt.Image.SCALE_SMOOTH ) );

        lblImagen.setIcon( bannerCLiente );
        add( lblImagen, BorderLayout.NORTH );

        panelDatos.setLayout( new GridLayout( 5, 2, 5, 5 ) );

        JLabel lblServidor = new JLabel( "Servidor:" );
        panelDatos.add( lblServidor );

        txtServidor = new JTextField( "Localhost" );
        panelDatos.add( txtServidor );

        JLabel lblPuerto = new JLabel( "Puerto:" );
        panelDatos.add( lblPuerto );

        txtPuerto = new JTextField( "9999" );
        panelDatos.add( txtPuerto );

        JLabel lblUsuario = new JLabel( "Alias:" );
        panelDatos.add( lblUsuario );

        txtAlias = new JTextField( );
        panelDatos.add( txtAlias );

        JLabel lblPwd = new JLabel( "Contrase�a:" );
        panelDatos.add( lblPwd );

        txtPwd = new JPasswordField( );
        panelDatos.add( txtPwd );

        btnIniciarSesion = new JButton( );
        btnIniciarSesion.setText( "Iniciar sesi�n" );
        btnIniciarSesion.setActionCommand( INICIAR_SESION );
        btnIniciarSesion.addActionListener( this );
        panelDatos.add( btnIniciarSesion );

        btnCancelar = new JButton( );
        btnCancelar.setText( "Cancelar" );
        btnCancelar.setActionCommand( CANCELAR );
        btnCancelar.addActionListener( this );
        panelDatos.add( btnCancelar );

        add( panelDatos, BorderLayout.CENTER );

        Font fuente;
        try
        {
            fuente = Font.createFont( Font.TRUETYPE_FONT, new File( InterfazBrainsDuel.FUENTE_BOLD ) ).deriveFont( 16f );
            btnCancelar.setFont( fuente );
            btnIniciarSesion.setFont( fuente );
            lblPwd.setFont( fuente );
            txtAlias.setFont( fuente );
            lblUsuario.setFont( fuente );
            txtPuerto.setFont( fuente );
            lblPuerto.setFont( fuente );
            txtServidor.setFont( fuente );
            lblServidor.setFont( fuente );
            borde.setTitleFont( fuente );
        }
        catch( FontFormatException | IOException e )
        {
            e.printStackTrace( );
        }
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Manejo de los eventos de los botones.
     * @param pEvento Acci�n que gener� el evento. pEvento != null
     */
    public void actionPerformed( ActionEvent pEvento )
    {
        String command = pEvento.getActionCommand( );
        if( command.equals( INICIAR_SESION ) )
        {
            @SuppressWarnings("deprecation")
            String pass1 = txtPwd.getText( );
            String alias = txtAlias.getText( );
            String servidor = txtServidor.getText( );
            int puerto = Integer.valueOf( txtPuerto.getText( ) );

            if( alias != null && !alias.isEmpty( ) )
            {
                if( pass1 == null || pass1.isEmpty( ) )
                {
                    JOptionPane.showMessageDialog( this, "Por favor, ingrese su contrase�a", "Error contrase�a", JOptionPane.ERROR_MESSAGE );
                }
                else
                {
                    principal.configurarDatosConexion( servidor, puerto );
                    principal.iniciarSesion( alias, pass1 );
                }
            }
            else
            {
                JOptionPane.showMessageDialog( this, "Por favor, ingrese el nombre de usuario", "Error usuario", JOptionPane.ERROR_MESSAGE );
            }
        }
        else if( command.equals( CANCELAR ) )
        {
            dispose( );
        }
    }

}
