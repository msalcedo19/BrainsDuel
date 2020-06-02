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
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uniandes.cupi2.brainsDuel.cliente.mundo.Duelo;

/**
 * Panel con el encabezado.
 */
public class PanelImagen extends JPanel
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
    public final static String RUTA = "data/img/bannerEsperandoOponente.png";

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    // -----------------------------------------------------------------
    // Atributos de interfaz
    // -----------------------------------------------------------------

    /**
     * Etiqueta con la imagen.
     */
    private JLabel etiquetaImagen;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Constructor del panel.
     */
    public PanelImagen( )
    {
        setLayout( new BorderLayout( ) );
        setBackground( Color.WHITE );
        setPreferredSize( new Dimension( 500, 40 ) );
        etiquetaImagen = new JLabel( "" );
        etiquetaImagen.setIcon( new ImageIcon( RUTA ) );
        etiquetaImagen.setHorizontalAlignment( JLabel.CENTER );
        etiquetaImagen.setVerticalAlignment( JLabel.CENTER );
        add( etiquetaImagen, BorderLayout.CENTER );
    }

    /**
     * Actualiza el banner de la ventana según el estado de la aplicación.
     * @param pEstado Estado de la aplicación.
     */
    public void actualizarImagen( int pEstado )
    {

        String ruta = "";

        if( pEstado == Duelo.ESPERANDO_LOCAL )
        {
            ruta = "data/img/bannerGiraRuleta.png";
        }
        else if( pEstado == Duelo.ESPERANDO_OPONENTE )
        {
            ruta = "data/img/bannerEsperandoOponente.png";
        }
        else if( pEstado == Duelo.ESPERANDO_RESPUESTA )
        {
            ruta = "data/img/bannerEsperandoTurno.png";
        }
        else if(pEstado == Duelo.ESTADO_RETO)
        {
            ruta = "data/img/bannerReto.png";
        }
        else if(pEstado == Duelo.ESTADO_TERMINADO)
        {
            ruta = "data/img/bannerJuegoTerminado.png";
        }

        ImageIcon icono = new ImageIcon( ruta );
        etiquetaImagen.setIcon( icono );
    }

}
