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
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import uniandes.cupi2.brainsDuel.cliente.mundo.Duelo;

/**
 * Clase que presenta el panel de personajes
 * @author Cupi2
 *
 */
@SuppressWarnings("serial")
public class PanelPersonajes extends JPanel
{
    /**
     * Label para el nombre del jugador
     */
    private JLabel lblNombreJugador;

    /**
     * Label para el nombre del oponente
     */
    private JLabel lblNombreOponente;

    /**
     * Ruta base de las imágenes
     */
    private static String RUTA_BASE = "data/img/";

    /**
     * Arreglo con labels de trofeos del jugador.
     */
    private ArrayList<JLabel> trofeosJugador;

    /**
     * Arreglo con labels de trofeos del oponente.
     */
    private ArrayList<JLabel> trofeosOponente;

    /**
     * Construye un nuevo panel de personajes
     */
    public PanelPersonajes( )
    {

        ArrayList<String> rutas = new ArrayList<String>( );
        trofeosJugador = new ArrayList<JLabel>( );
        trofeosOponente = new ArrayList<JLabel>( );
        rutas.add( RUTA_BASE + "geografia-gris.png" );
        rutas.add( RUTA_BASE + "historia-gris.png" );
        rutas.add( RUTA_BASE + "ciencia-gris.png" );
        rutas.add( RUTA_BASE + "entretenimiento-gris.png" );
        rutas.add( RUTA_BASE + "arte-gris.png" );
        rutas.add( RUTA_BASE + "deportes-gris.png" );

        setLayout( new BorderLayout( ) );
        setBackground( new Color( 79, 85, 92 ) );
        setOpaque( true );
        JPanel panelNombres = new JPanel( new GridLayout( 1, 2 ) );
        JPanel panelImagenes = new JPanel( new BorderLayout( ) );
        JPanel panelMisPersonajes = new JPanel( new GridLayout( 1, 6 ) );
        JPanel panelPersonajesOponente = new JPanel( new GridLayout( 1, 6 ) );

        lblNombreJugador = new JLabel( "", SwingConstants.CENTER );
        lblNombreOponente = new JLabel( "", SwingConstants.CENTER );
        panelNombres.add( lblNombreJugador );
        panelNombres.add( lblNombreOponente );

        Font fuente;
        try
        {
            fuente = Font.createFont( Font.TRUETYPE_FONT, new File( InterfazBrainsDuel.FUENTE_BOLD ) ).deriveFont( 30f );
            lblNombreJugador.setFont( fuente );
            lblNombreOponente.setFont( fuente );
        }
        catch( FontFormatException | IOException e )
        {
            e.printStackTrace( );
        }

        for( String ruta : rutas )
        {

            JLabel labelImagen = new JLabel( "", SwingConstants.CENTER );
            trofeosJugador.add( labelImagen );
            ImageIcon imagen = new ImageIcon( ruta );
            labelImagen.setToolTipText( ruta.split( "img/" )[ 1 ].split( "-" )[ 0 ].toUpperCase( ) );
            imagen = new ImageIcon( imagen.getImage( ).getScaledInstance( 45, 45, java.awt.Image.SCALE_SMOOTH ) );
            labelImagen.setIcon( imagen );
            panelMisPersonajes.add( labelImagen );
            labelImagen.setOpaque( true );

        }

        Collections.reverse( rutas );

        for( String ruta : rutas )
        {

            JLabel labelImagen = new JLabel( "", SwingConstants.CENTER );
            trofeosOponente.add( labelImagen );
            ImageIcon imagen = new ImageIcon( ruta );
            imagen = new ImageIcon( imagen.getImage( ).getScaledInstance( 45, 45, java.awt.Image.SCALE_SMOOTH ) );
            labelImagen.setToolTipText( ruta.split( "img/" )[ 1 ].split( "-" )[ 0 ].toUpperCase( ) );
            labelImagen.setIcon( imagen );
            panelPersonajesOponente.add( labelImagen );
            labelImagen.setOpaque( true );

        }

        panelImagenes.add( panelMisPersonajes, BorderLayout.WEST );
        ImageIcon vs = new ImageIcon( "data/img/vs.png" );
        vs = new ImageIcon( vs.getImage( ).getScaledInstance( 50, 50, java.awt.Image.SCALE_SMOOTH ) );
        JLabel centro = new JLabel( "", SwingConstants.CENTER );
        centro.setIcon( vs );
        panelImagenes.add( centro, BorderLayout.CENTER );
        panelImagenes.add( panelPersonajesOponente, BorderLayout.EAST );
        add( panelNombres, BorderLayout.NORTH );
        add( panelImagenes, BorderLayout.CENTER );

    }

    /**
     * Actualiza los nombres de los jugadores
     * @param pNombreJugador Nombre del jugador
     * @param pNombreOponente Nombre del oponente
     * @param victoriasDerrotas1 Victorias y derrotas del jugador 1
     * @param victoriasDerrotas2 Victorias y derrotas del jugador 2
     */
    public void actualizarNombres( String pNombreJugador, String pNombreOponente, String victoriasDerrotas1, String victoriasDerrotas2 )
    {
        lblNombreJugador.setText( pNombreJugador );
        lblNombreOponente.setText( pNombreOponente );
        lblNombreJugador.setToolTipText( victoriasDerrotas1 );
        lblNombreOponente.setToolTipText( victoriasDerrotas2 );
    }

    /**
     * Actualiza los trofeos en la interfaz
     * @param pJugador Arreglo de trofeos del jugador
     * @param pOponente Arreglo de trofeos del oponente
     */
    public void actualizarTrofeos( ArrayList<String> pJugador, ArrayList<String> pOponente )
    {
        String ruta = "";

        for( String trofeo : pJugador )
        {
            if( trofeo.equals( Duelo.GEOGRAFIA ) )
            {
                ruta = "data/img/geografia.png";
                ImageIcon imagen = new ImageIcon( ruta );
                imagen = new ImageIcon( imagen.getImage( ).getScaledInstance( 45, 45, java.awt.Image.SCALE_SMOOTH ) );
                trofeosJugador.get( 0 ).setIcon( imagen );
            }

            if( trofeo.equals( Duelo.HISTORIA ) )
            {
                ruta = "data/img/historia.png";
                ImageIcon imagen = new ImageIcon( ruta );
                imagen = new ImageIcon( imagen.getImage( ).getScaledInstance( 45, 45, java.awt.Image.SCALE_SMOOTH ) );
                trofeosJugador.get( 1 ).setIcon( imagen );
            }

            if( trofeo.equals( Duelo.CIENCIA ) )
            {
                ruta = "data/img/ciencia.png";
                ImageIcon imagen = new ImageIcon( ruta );
                imagen = new ImageIcon( imagen.getImage( ).getScaledInstance( 45, 45, java.awt.Image.SCALE_SMOOTH ) );
                trofeosJugador.get( 2 ).setIcon( imagen );
            }

            if( trofeo.equals( Duelo.ENTRETENIMIENTO ) )
            {
                ruta = "data/img/entretenimiento.png";
                ImageIcon imagen = new ImageIcon( ruta );
                imagen = new ImageIcon( imagen.getImage( ).getScaledInstance( 45, 45, java.awt.Image.SCALE_SMOOTH ) );
                trofeosJugador.get( 3 ).setIcon( imagen );
            }

            if( trofeo.equals( Duelo.ARTE ) )
            {
                ruta = "data/img/arte.png";
                ImageIcon imagen = new ImageIcon( ruta );
                imagen = new ImageIcon( imagen.getImage( ).getScaledInstance( 45, 45, java.awt.Image.SCALE_SMOOTH ) );
                trofeosJugador.get( 4 ).setIcon( imagen );
            }

            if( trofeo.equals( Duelo.DEPORTES ) )
            {
                ruta = "data/img/deportes.png";
                ImageIcon imagen = new ImageIcon( ruta );
                imagen = new ImageIcon( imagen.getImage( ).getScaledInstance( 45, 45, java.awt.Image.SCALE_SMOOTH ) );
                trofeosJugador.get( 5 ).setIcon( imagen );
            }
        }

        for( String trofeo : pOponente )
        {
            if( trofeo.equals( Duelo.GEOGRAFIA ) )
            {
                ruta = "data/img/geografia.png";
                ImageIcon imagen = new ImageIcon( ruta );
                imagen = new ImageIcon( imagen.getImage( ).getScaledInstance( 45, 45, java.awt.Image.SCALE_SMOOTH ) );
                trofeosOponente.get( 5 ).setIcon( imagen );
            }

            if( trofeo.equals( Duelo.HISTORIA ) )
            {
                ruta = "data/img/historia.png";
                ImageIcon imagen = new ImageIcon( ruta );
                imagen = new ImageIcon( imagen.getImage( ).getScaledInstance( 45, 45, java.awt.Image.SCALE_SMOOTH ) );
                trofeosOponente.get( 4 ).setIcon( imagen );
            }

            if( trofeo.equals( Duelo.CIENCIA ) )
            {
                ruta = "data/img/ciencia.png";
                ImageIcon imagen = new ImageIcon( ruta );
                imagen = new ImageIcon( imagen.getImage( ).getScaledInstance( 45, 45, java.awt.Image.SCALE_SMOOTH ) );
                trofeosOponente.get( 3 ).setIcon( imagen );
            }

            if( trofeo.equals( Duelo.ENTRETENIMIENTO ) )
            {
                ruta = "data/img/entretenimiento.png";
                ImageIcon imagen = new ImageIcon( ruta );
                imagen = new ImageIcon( imagen.getImage( ).getScaledInstance( 45, 45, java.awt.Image.SCALE_SMOOTH ) );
                trofeosOponente.get( 2 ).setIcon( imagen );
            }

            if( trofeo.equals( Duelo.ARTE ) )
            {
                ruta = "data/img/arte.png";
                ImageIcon imagen = new ImageIcon( ruta );
                imagen = new ImageIcon( imagen.getImage( ).getScaledInstance( 45, 45, java.awt.Image.SCALE_SMOOTH ) );
                trofeosOponente.get( 1 ).setIcon( imagen );
            }

            if( trofeo.equals( Duelo.DEPORTES ) )
            {
                ruta = "data/img/deportes.png";
                ImageIcon imagen = new ImageIcon( ruta );
                imagen = new ImageIcon( imagen.getImage( ).getScaledInstance( 45, 45, java.awt.Image.SCALE_SMOOTH ) );
                trofeosOponente.get( 0 ).setIcon( imagen );
            }
        }
    }

}
