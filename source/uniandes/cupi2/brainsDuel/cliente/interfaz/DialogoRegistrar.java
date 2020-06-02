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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Dialogo para crear una cuenta
 * 
 */
public class DialogoRegistrar extends JDialog implements ActionListener
{

    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Constante para la serialización.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Representa el comando de la opción registrar.
     */
    private static final String REGISTRAR = "REGISTRAR";

    /**
     * Representa el comando de la opción cancelar.
     */
    private static final String CANCELAR = "CANCELAR";

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Es una referencia a la clase principal de la interfaz del cliente.
     */
    private InterfazBrainsDuel principal;

    /**
     * Texto alias de usuario.
     */
    private JTextField txtServidor;

    /**
     * Texto alias de usuario.
     */
    private JTextField txtPuerto;

    /**
     * Texto alias de usuario.
     */
    private JTextField txtAlias;

    /**
     * Texto nombre del usuario.
     */
    private JTextField txtNombres;

    /**
     * Texto apellidos.
     */
    private JTextField txtApellidos;

    /**
     * Texto contraseña.
     */
    private JPasswordField txtPwd;

    /**
     * Texto confirmación de contraseña.
     */
    private JPasswordField txtPwdConfirmacion;

    // -----------------------------------------------------------------
    // Atributos de la Interfaz
    // -----------------------------------------------------------------

    /**
     * Botón para registrarse.
     */
    private JButton btnRegistrar;

    /**
     * Botón cancelar.
     */
    private JButton btnCancelar;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye un nuevo diálogo crear cuenta.
     * @param pPrincipal Referencia a la ventana principal. pPrincipal != null
     */
    public DialogoRegistrar( InterfazBrainsDuel pPrincipal )
    {
        super( pPrincipal, true );
        setSize( 340, 370 );
        setLocationRelativeTo( null );
        setTitle( "Crear cuenta" );
        principal = pPrincipal;

        JPanel panelDatos = new JPanel( );
        JPanel panelOpciones = new JPanel( );

        setLayout( new BorderLayout( ) );

        panelDatos.setBorder( BorderFactory.createTitledBorder( "Información de registro" ) );
        panelDatos.setLayout( new GridLayout( 8, 2, 5, 5 ) );

        JLabel lblServidor = new JLabel( "Servidor:" );
        panelDatos.add( lblServidor );

        txtServidor = new JTextField( "Localhost" );
        panelDatos.add( txtServidor );

        JLabel lblPuerto = new JLabel( "Puerto:" );
        panelDatos.add( lblPuerto );

        txtPuerto = new JTextField( "9999" );
        panelDatos.add( txtPuerto );

        JLabel lblAlias = new JLabel( "Alias:" );
        panelDatos.add( lblAlias );

        txtAlias = new JTextField( );
        panelDatos.add( txtAlias );

        JLabel lblNombres = new JLabel( "Nombres:" );
        panelDatos.add( lblNombres );

        txtNombres = new JTextField( );
        panelDatos.add( txtNombres );

        JLabel lblApellidos = new JLabel( "Apellidos:" );
        panelDatos.add( lblApellidos );

        txtApellidos = new JTextField( );
        panelDatos.add( txtApellidos );

        JLabel lblPwd = new JLabel( "Contraseña:" );
        panelDatos.add( lblPwd );

        txtPwd = new JPasswordField( );
        panelDatos.add( txtPwd );

        JLabel lblPwdConfirmacion = new JLabel( "Confirmación contraseña:" );
        panelDatos.add( lblPwdConfirmacion );

        txtPwdConfirmacion = new JPasswordField( );
        panelDatos.add( txtPwdConfirmacion );

        panelOpciones.setLayout( new GridLayout( 1, 2 ) );

        btnRegistrar = new JButton( "Crear cuenta" );
        btnRegistrar.addActionListener( this );
        btnRegistrar.setActionCommand( REGISTRAR );
        panelDatos.add( btnRegistrar );

        btnCancelar = new JButton( "Cancelar" );
        btnCancelar.addActionListener( this );
        btnCancelar.setActionCommand( CANCELAR );
        panelDatos.add( btnCancelar );

        add( panelDatos );
    }

    /**
     * Manejo de los eventos de los botones.
     * @param pEvento Acción que generó el evento. pEvento!= null
     */
    public void actionPerformed( ActionEvent pEvento )
    {
        String command = pEvento.getActionCommand( );
        if( command.equals( REGISTRAR ) )
        {
            @SuppressWarnings("deprecation")
            String pass1 = txtPwd.getText( );
            @SuppressWarnings("deprecation")
            String pass2 = txtPwdConfirmacion.getText( );
            String alias = txtAlias.getText( );
            String nombre = txtNombres.getText( );
            String apellido = txtApellidos.getText( );
            String servidor = txtServidor.getText( );
            int puerto = Integer.valueOf( txtPuerto.getText( ) );

            if( nombre != null && !nombre.isEmpty( ) && apellido != null && !apellido.isEmpty( ) )
            {
                if( alias != null && !alias.isEmpty( ) )
                {
                    if( pass1 != null && !pass1.isEmpty( ) && pass2 != null && !pass2.isEmpty( ) )
                    {
                        if( !pass1.equals( pass2 ) )
                        {
                            JOptionPane.showMessageDialog( this, "Las contraseñas no coinciden.", "Crear cuenta", JOptionPane.ERROR_MESSAGE );
                        }
                        else
                        {
                            principal.configurarDatosConexion( servidor, puerto );
                            principal.crearRegistro( alias, nombre, apellido, pass1 );
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog( this, "Debe ingresar la contraseña y su confirmación.", "Crear cuenta", JOptionPane.ERROR_MESSAGE );
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog( this, "Debe ingresar el nombre de usuario.", "Crear cuenta", JOptionPane.ERROR_MESSAGE );
                }
            }
            else
            {
                JOptionPane.showMessageDialog( this, "Debe ingresar el nombre y apellido.", "Crear cuenta", JOptionPane.ERROR_MESSAGE );
            }
        }
        else if( command.equals( CANCELAR ) )
        {
            dispose( );
        }

    }

}
