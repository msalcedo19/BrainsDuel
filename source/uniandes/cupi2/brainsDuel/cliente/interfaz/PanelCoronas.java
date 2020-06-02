/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n12_brainsDuel
 * Autor: Equipo Cupi2 2017-2
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package uniandes.cupi2.brainsDuel.cliente.interfaz;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel con el encabezado.
 */
public class PanelCoronas extends JPanel implements ActionListener
{
    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Constante para la serializaciï¿½n.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constante con la ruta de la imagen.
     */
    public final static String RUTA = "./data/img/0bien.png";

    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Permite salir de la aplicación
     */
    public static final String SALIR = "SALIR";

    /**
     * Permite enviar un reto
     */
    public static final String ENVIAR_RETO = "ENVIAR_RETO";

    // -----------------------------------------------------------------
    // Atributos de interfaz
    // -----------------------------------------------------------------

    /**
     * Etiqueta con la imagen.
     */
    private JLabel etiquetaImagen;

    /**
     * Botón para enviar un reto
     */
    private JButton botonReto;

    /**
     * Botón para salir
     */
    private JButton botonSalir;

    /**
     * Interfaz principal
     */
    private InterfazBrainsDuel principal;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Constructor del panel.
     * @param interfazBrainsDuel Interfaz principal de la aplicación
     */
    public PanelCoronas( InterfazBrainsDuel interfazBrainsDuel )
    {
        // setBackground( new Color( 79, 85, 92 ) );
        // setOpaque(true);
        principal = interfazBrainsDuel;
        setLayout( new BorderLayout( ) );
        etiquetaImagen = new JLabel( );
        etiquetaImagen.setHorizontalAlignment( JLabel.CENTER );
        etiquetaImagen.setVerticalAlignment( JLabel.CENTER );
        ImageIcon imagen = new ImageIcon( RUTA );
        imagen = new ImageIcon( imagen.getImage( ).getScaledInstance( 140, 55, java.awt.Image.SCALE_SMOOTH ) );
        etiquetaImagen.setIcon( imagen );
        add( etiquetaImagen, BorderLayout.CENTER );

        botonReto = new JButton( "Enviar reto" );
        botonReto.addActionListener( this );
        botonReto.setActionCommand( ENVIAR_RETO );

        botonSalir = new JButton( "      Salir     " );
        botonSalir.addActionListener( this );
        botonSalir.setActionCommand( SALIR );

        add( botonReto, BorderLayout.EAST );
        add( botonSalir, BorderLayout.WEST );

        Font fuente;
        try
        {
            fuente = Font.createFont( Font.TRUETYPE_FONT, new File( InterfazBrainsDuel.FUENTE_BOLD ) ).deriveFont( 22f );
            botonSalir.setFont( fuente );
            botonReto.setFont( fuente );
        }
        catch( FontFormatException | IOException e )
        {
            e.printStackTrace( );
        }

        deshabilitarReto( );

    }

    /**
     * Actualiza el número de coronas
     * @param pNumero Número recibido por parámetro
     */
    public void actualizar( int pNumero )
    {

        String ruta = "";

        if( pNumero == 0 )
        {
            ruta = "data/img/0bien.png";
        }
        else if( pNumero == 1 )
        {
            ruta = "data/img/1bien.png";
        }
        else if( pNumero == 2 )
        {
            ruta = "data/img/2bien.png";
        }
        else if( pNumero == 3 )
        {
            ruta = "data/img/3bien.png";
        }

        ImageIcon imagen = new ImageIcon( ruta );
        imagen = new ImageIcon( imagen.getImage( ).getScaledInstance( 140, 55, java.awt.Image.SCALE_SMOOTH ) );
        etiquetaImagen.setIcon( imagen );

    }

    /**
     * Deshabilita el botón de reto
     */
    public void deshabilitarReto( )
    {
        botonReto.setEnabled( false );
    }

    /**
     * Habilita el botón de reto
     */
    public void habilitarReto( )
    {
        botonReto.setEnabled( true );
    }

    /**
     * Recibe los eventos del botón
     * @param e Evento
     */
    public void actionPerformed( ActionEvent e )
    {
        if( e.getActionCommand( ).equals( ENVIAR_RETO ) )
        {
            principal.enviarReto( );
        }
        else
        {
            principal.windowClosing( null );
        }
    }

}
