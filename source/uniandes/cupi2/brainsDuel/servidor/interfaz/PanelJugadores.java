package uniandes.cupi2.brainsDuel.servidor.interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import uniandes.cupi2.brainsDuel.servidor.RegistroJugador;


/**
 * Es el panel donde se muestran los jugadores registrados en la base de datos
 */
public class PanelJugadores extends JPanel 
{

    // -----------------------------------------------------------------
    // Atributos de la Interfaz
    // -----------------------------------------------------------------

    /**
     * Es la lista donde se muestran los jugadores
     */
    private JList listaJugadores;


    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Inicializa el panel
     * @param ventanaPrincipal Es una referencia a la ventana principal del servidor
     */
    public PanelJugadores( )
    {
        inicializarPanel( );
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Inicializa los elementos dentro del panel
     */
    private void inicializarPanel( )
    {
        setLayout( new BorderLayout( ) );

        JScrollPane scroll = new JScrollPane( );
        scroll.setPreferredSize(new Dimension(500, 225));
        listaJugadores = new JList( );
        scroll.getViewport( ).add( listaJugadores );
        add(scroll, BorderLayout.CENTER);
//        
//        JPanel panelRefrescar= new JPanel();
//        panelRefrescar.setLayout(new GridBagLayout());
//        GridBagConstraints gbc= new GridBagConstraints(); 
//        gbc.gridx=0;
//        gbc.gridy=0;
//        gbc.fill= GridBagConstraints.BOTH;
//
//        add(panelRefrescar, BorderLayout.SOUTH); 
        setBorder( new TitledBorder( "Registro Jugadores" ) );
    }

    
    /**
     * Actualiza la lista mostrada de jugadores
     * @param jugadores Es una colección con la información de los jugadores que hay actualmente en la bd
     */
    public void actualizarJugadores( Collection jugadores )
    {
        listaJugadores.setListData( jugadores.toArray());

    }

}

