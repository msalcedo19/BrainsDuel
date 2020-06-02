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

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import uniandes.cupi2.brainsDuel.cliente.mundo.Duelo;
import uniandes.cupi2.brainsDuel.cliente.mundo.Pregunta;

/**
 * Clase que representa un panel de pregunta
 * @author Cupi2
 *
 */
@SuppressWarnings("serial")
public class PanelPregunta extends JPanel implements ActionListener
{
    /**
     * Label para la pregunta
     */
    private JLabel lblPregunta;

    /**
     * Botón para la respuesta 1
     */
    private JButton btnRespuesta1;

    /**
     * Botón para la respuesta 2
     */
    private JButton btnRespuesta2;

    /**
     * Botón para la respuesta 3
     */
    private JButton btnRespuesta3;

    /**
     * Botón para la respuesta 4
     */
    private JButton btnRespuesta4;

    /**
     * Interfaz principal de la aplicación
     */
    private InterfazBrainsDuel principal;

    /**
     * Método constructor del panel
     * @param pPrincipal Ventana principal de la aplicación
     */
    public PanelPregunta( InterfazBrainsDuel pPrincipal )
    {
        principal = pPrincipal;
        setLayout( new BorderLayout( ) );

        lblPregunta = new JLabel( );
        btnRespuesta1 = new JButton( );
        btnRespuesta2 = new JButton( );
        btnRespuesta3 = new JButton( );
        btnRespuesta4 = new JButton( );

        Font fuente;
        try
        {
            fuente = Font.createFont( Font.TRUETYPE_FONT, new File( InterfazBrainsDuel.FUENTE_BOLD ) ).deriveFont( 28f );
            lblPregunta.setFont( fuente );
            fuente = Font.createFont( Font.TRUETYPE_FONT, new File( InterfazBrainsDuel.FUENTE_REGULAR ) ).deriveFont( 24f );
            btnRespuesta1.setFont( fuente );
            btnRespuesta2.setFont( fuente );
            btnRespuesta3.setFont( fuente );
            btnRespuesta4.setFont( fuente );

        }
        catch( FontFormatException | IOException e )
        {
            e.printStackTrace( );
        }

        btnRespuesta1.addActionListener( this );
        btnRespuesta2.addActionListener( this );
        btnRespuesta3.addActionListener( this );
        btnRespuesta4.addActionListener( this );

        JPanel panelBotones = new JPanel( new GridLayout( 4, 1 ) );
        panelBotones.add( btnRespuesta1 );
        panelBotones.add( btnRespuesta2 );
        panelBotones.add( btnRespuesta3 );
        panelBotones.add( btnRespuesta4 );

        add( lblPregunta, BorderLayout.CENTER );
        add( panelBotones, BorderLayout.SOUTH );
    }

    /**
     * Actualiza el panel con la pregunta recibida por parámetro
     * @param pPregunta Pregunta para actualizar el panel
     */
    public void actualizar( Pregunta pPregunta )
    {
        btnRespuesta1.setEnabled( true );
        btnRespuesta2.setEnabled( true );
        btnRespuesta3.setEnabled( true );
        btnRespuesta4.setEnabled( true );

        lblPregunta.setText( "<html>" + pPregunta.darPregunta( ) + "</html>" );
        btnRespuesta1.setText( pPregunta.darOpciones( )[ 0 ] );
        btnRespuesta2.setText( pPregunta.darOpciones( )[ 1 ] );
        btnRespuesta3.setText( pPregunta.darOpciones( )[ 2 ] );
        btnRespuesta4.setText( pPregunta.darOpciones( )[ 3 ] );

        btnRespuesta1.setActionCommand( pPregunta.darOpciones( )[ 0 ] );
        btnRespuesta2.setActionCommand( pPregunta.darOpciones( )[ 1 ] );
        btnRespuesta3.setActionCommand( pPregunta.darOpciones( )[ 2 ] );
        btnRespuesta4.setActionCommand( pPregunta.darOpciones( )[ 3 ] );

        String rutaImagen = "";

        if( pPregunta.darCategoria( ).equals( Duelo.ARTE ) )
        {
            rutaImagen = "data/img/arte.png";
        }
        else if( ( pPregunta.darCategoria( ).equals( Duelo.HISTORIA ) ) )
        {
            rutaImagen = "data/img/historia.png";
        }
        else if( ( pPregunta.darCategoria( ).equals( Duelo.DEPORTES ) ) )
        {
            rutaImagen = "data/img/deportes.png";
        }
        else if( ( pPregunta.darCategoria( ).equals( Duelo.ENTRETENIMIENTO ) ) )
        {
            rutaImagen = "data/img/entretenimiento.png";
        }
        else if( ( pPregunta.darCategoria( ).equals( Duelo.GEOGRAFIA ) ) )
        {
            rutaImagen = "data/img/geografia.png";
        }
        else if( ( pPregunta.darCategoria( ).equals( Duelo.CIENCIA ) ) )
        {
            rutaImagen = "data/img/ciencia.png";
        }
        else if( ( pPregunta.darCategoria( ).equals( Duelo.CORONA ) ) )
        {
            rutaImagen = "data/img/corona.png";
        }
        ImageIcon personaje = new ImageIcon( rutaImagen );
        lblPregunta.setIcon( personaje );

    }

    /**
     * Recibe los evento0s de los botones
     * @param e Evento lanzado
     * 
     */
    public void actionPerformed( ActionEvent e )
    {

        String respuesta = e.getActionCommand( );

        btnRespuesta1.setEnabled( false );
        btnRespuesta2.setEnabled( false );
        btnRespuesta3.setEnabled( false );
        btnRespuesta4.setEnabled( false );
        

        try
        {
            String r = principal.responderPregunta( respuesta );
            if( r.equals( "Respuesta correcta" ) )
            {
                JOptionPane.showMessageDialog( principal,"Respuesta correcta", "Respuesta correcta", JOptionPane.INFORMATION_MESSAGE );
            }
            else if ( r.equals( "Respuesta incorrecta" ) )
            {
                JOptionPane.showMessageDialog( principal,"Respuesta incorrecta", "Respuesta incorrecta", JOptionPane.ERROR_MESSAGE );
            }
            else
            {
                JOptionPane.showMessageDialog( principal, r, "Reto", JOptionPane.INFORMATION_MESSAGE );
            }

        }
        catch( Exception e1 )
        {
            principal.mostrarError( e1 );
        }

        principal.esperarJugada( );
        principal.modoRuleta( );
        principal.actualizarInterfaz( );

    }
}