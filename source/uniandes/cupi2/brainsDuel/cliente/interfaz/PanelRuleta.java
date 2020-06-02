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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

import uniandes.cupi2.brainsDuel.cliente.mundo.Duelo;

/**
 * Representa la ruleta principal del juego
 * @author Cupi2
 *
 */
@SuppressWarnings("serial")
public class PanelRuleta extends JPanel
{

    /**
     * Panel que contiene la ruleta que girará
     */
    private RotatePanel rotatePanel;

    /**
     * Construye un nuevo panel ruleta
     * @param pPrincipal Interfaz principal de la aplicación
     */
    public PanelRuleta( InterfazBrainsDuel pPrincipal )
    {
        setLayout( new BorderLayout( ) );
        rotatePanel = new RotatePanel( pPrincipal );
        add( rotatePanel, BorderLayout.CENTER );

    }

    /**
     * Actualiza la ruleta según el estado de la aplicación
     * @param pEstado Estado de la aplicación
     */
    public void actualizar( int pEstado )
    {
        rotatePanel.actualizar( pEstado );
    }
}

/**
 * Panel que rota
 * @author Cupi2
 *
 */
@SuppressWarnings("serial")
class RotatePanel extends JPanel implements ActionListener
{

    /**
     * Tamaño de la ruleta
     */
    private static final int SIZE = 512;

    /**
     * Delta de rotación
     */
    private static double DELTA_THETA = Math.PI / 180;

    /**
     * Timer para repintar
     */
    private Timer timer;

    /**
     * Imagen de la ruleta
     */
    private Image imagenRuleta;

    /**
     * Delta de giro
     */
    private double dt = DELTA_THETA;

    /**
     * Ángulo de giro
     */
    private double theta;

    /**
     * Pasos de la ruleta
     */
    private int pasos;

    /**
     * Número aleatorio
     */
    private int numeroAleatorio;

    /**
     * Imagen del botón central
     */
    private Image imagenBoton;

    /**
     * Clase principal de la aplicación
     */
    private InterfazBrainsDuel principal;

    /**
     * Indica si ya fue presionada la ruleta
     */
    private boolean presionado;

    /**
     * Indica el historial de rotación de la ruleta
     */
    private double rotacion;

    /**
     * Construye un nuevo panel giratorio
     * @param pPrincipal Interfaz principal de la aplicación
     */
    public RotatePanel( InterfazBrainsDuel pPrincipal )
    {
        rotacion = 0;
        principal = pPrincipal;
        String ruta = "./data/img/ruleta.png";

        if( pPrincipal.darEstado( ) == Duelo.ESPERANDO_OPONENTE || pPrincipal.darEstado( ) == Duelo.ESPERANDO_RESPUESTA )
        {
            ruta = "./data/img/ruleta-gris.png";
        }
        imagenRuleta = RotatableImage.getImage( SIZE, ruta );
        numeroAleatorio = ( int ) ( 200 + Math.random( ) * 1400 );
        timer = new Timer( 0, this );
        pasos = 0;
        this.setBackground( new Color( 79, 85, 92 ) );
        this.setPreferredSize( new Dimension( imagenRuleta.getWidth( null ), imagenRuleta.getHeight( null ) ) );
        this.addMouseListener( new MouseAdapter( )
        {

            @Override
            public void mousePressed( MouseEvent e )
            {

                if( principal.darModo( ) == InterfazBrainsDuel.MODO_RULETA && presionado == false )
                {

                    presionado = true;
                    String ruta = "./data/img/ruleta.png";

                    if( principal.darEstado( ) == Duelo.ESPERANDO_OPONENTE || principal.darEstado( ) == Duelo.ESPERANDO_RESPUESTA )
                    {
                        ruta = "./data/img/ruleta-gris.png";
                    }
                    imagenRuleta = RotatableImage.getImage( SIZE, ruta );

                    if( principal.darEstado( ) == Duelo.ESPERANDO_OPONENTE )
                    {
                        JOptionPane.showMessageDialog( null, "No puedes girar la ruleta: Esperando un oponente" );
                    }
                    else if( principal.darEstado( ) == Duelo.ESPERANDO_RESPUESTA )
                    {
                        JOptionPane.showMessageDialog( null, "No puedes girar la ruleta: No es tu turno" );
                    }
                    else if( principal.darEstado( ) == Duelo.ESPERANDO_LOCAL )
                    {
                        String categoria = pPrincipal.darCategoriaAleatoria( );
                        rotar( categoria );
                    }
                }

            }
        } );

        try
        {
            imagenBoton = ImageIO.read( new File( "./data/img/girar.png" ) ).getScaledInstance( 100, 100, java.awt.Image.SCALE_SMOOTH );
        }
        catch( IOException e1 )
        {
            //No hacer nada
        }
    }

    /**
     * Actualiza la ruleta según el estado ingresado por parámetro
     * @param pEstado Estado de la aplicación
     */
    public void actualizar( int pEstado )
    {
        String ruta = "./data/img/ruleta.png";

        if( pEstado == Duelo.ESPERANDO_OPONENTE || pEstado == Duelo.ESPERANDO_RESPUESTA )
        {
            ruta = "./data/img/ruleta-gris.png";
        }

        imagenRuleta = RotatableImage.getImage( SIZE, ruta );
        rotUpdate( );
        repaint( );
        validate( );
        revalidate( );
        presionado = false;

    }

    /**
     * Actualiza las imágenes de la ruleta
     */
    public void rotUpdate( )
    {
        numeroAleatorio = 0;
        pasos = 0;
        timer = new Timer( 0, this );
        timer.stop( );
    }

    /**
     * Rota el panel según la categoría ingresada
     * @param pCategoria Categoría ingresada por parámetro
     */
    public void rotar( String pCategoria )
    {
        numeroAleatorio = ( int ) ( 200 + Math.random( ) * 1400 );
        pasos = 0;
        timer = new Timer( 0, this );
        timer.start( );

    }

    /**
     * Pinta la ruleta sobre el lienzo
     * @param g Lienzo sobre el que se pinta
     */
    public void paintComponent( Graphics g )
    {
        if( principal.darModo( ) == InterfazBrainsDuel.MODO_RULETA )
        {

            super.paintComponent( g );
            Graphics2D g2d = ( Graphics2D )g;
            g2d.drawImage( imagenBoton, 254, 200, null );
            g2d.translate( this.getWidth( ) / 2, this.getHeight( ) / 2 );
            g2d.rotate( theta );
            g2d.translate( -imagenRuleta.getWidth( this ) / 2, -imagenRuleta.getHeight( this ) / 2 );
            g2d.drawImage( imagenRuleta, 0, 0, null );

            pasos++;
            if( pasos == 0 && numeroAleatorio == 0 )
            {
                timer.stop( );
            }

            if( rotacion >= 2 * Math.PI )
            {
                rotacion = 0;
            }

            if( pasos > numeroAleatorio )
            {
                timer.setDelay( timer.getDelay( ) + 2 );

                if( timer.getDelay( ) >= 70 )
                {
                    timer.stop( );
                    pasos = 0;
                    String categoria = determinarCategoria( );
                    principal.ruletaGira( categoria );
                }
            }

        }

    }

    /**
     * Determina la categoría según la rotación
     * @return Categoría
     */
    private String determinarCategoria( )
    {
        double porcionGrados = 2 * Math.PI / ( double )7;
        String categoria = "";

        if( rotacion < porcionGrados )
        {
            categoria = Duelo.HISTORIA;
        }
        else if( rotacion >= porcionGrados && rotacion < porcionGrados * 2 )
        {
            categoria = Duelo.CIENCIA;
        }
        else if( rotacion >= porcionGrados * 2 && rotacion < porcionGrados * 3 )
        {
            categoria = Duelo.GEOGRAFIA;
        }
        else if( rotacion >= porcionGrados * 3 && rotacion < porcionGrados * 4 )
        {
            categoria = Duelo.CORONA;
        }
        else if( rotacion >= porcionGrados * 4 && rotacion < porcionGrados * 5 )
        {
            categoria = Duelo.ENTRETENIMIENTO;
        }
        else if( rotacion >= porcionGrados * 5 && rotacion < porcionGrados * 6 )
        {
            categoria = Duelo.ARTE;
        }
        else
        {
            categoria = Duelo.DEPORTES;
        }

        return categoria;

    }

    /**
     * Escucha los eventos de giro y repinta en el lienzo
     * @param e Evento
     */
    public void actionPerformed( ActionEvent e )
    {
        if( principal.darModo( ) == InterfazBrainsDuel.MODO_RULETA )
        {
            theta += dt;
            rotacion += dt;
            repaint( );
        }
    }

    /**
     * Obtiene el tamaño para el lienzo de la ruleta
     * @return Dimensión del panel
     */
    public Dimension getPreferredSize( )
    {
        return new Dimension( SIZE, SIZE );
    }

}

/**
 * Imagen que rota sobre el panel
 * @author Cupi2
 *
 */
class RotatableImage
{

    /**
     * Crea una imagen que puede rotar
     * @param size Tamaño de la imagen
     * @param ruta Ruta de la imagen
     * @return Imagen a rotar
     */
    static public Image getImage( int size, String ruta )
    {
        BufferedImage bi = null;
        try
        {
            bi = ImageIO.read( new File( ruta ) );
        }
        catch( IOException e )
        {
            //No hacer nada
        }
        ;
        Graphics2D g2d = bi.createGraphics( );
        g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        g2d.dispose( );
        return bi;
    }
}