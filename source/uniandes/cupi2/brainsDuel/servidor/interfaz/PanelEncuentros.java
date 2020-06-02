package uniandes.cupi2.brainsDuel.servidor.interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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


/**
 * Es el panel donde se muestran los encuentros que hay actualmente en el servidor
 */
public class PanelEncuentros extends JPanel
{

    // -----------------------------------------------------------------
    // Atributos de la Interfaz
    // -----------------------------------------------------------------

    /**
     * Es la lista donde se muestran los encuentros
     */
    private JList listaEncuentros;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Inicializa el panel
     * @param ventanaPrincipal Es una referencia a la ventana principal del servidor
     */
    public PanelEncuentros( )
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
        listaEncuentros = new JList( );
        scroll.getViewport( ).add( listaEncuentros );
        add(scroll, BorderLayout.CENTER);  
        
//        JPanel panelRefrescar= new JPanel(); 
//        panelRefrescar.setLayout(new GridBagLayout());
//                
//        GridBagConstraints gbc= new GridBagConstraints(); 
//        gbc.gridx=0;
//        gbc.gridy=0;      
//        gbc.fill= GridBagConstraints.BOTH;
//        gbc.insets= new Insets(5,0,0,0);
//        add(panelRefrescar, BorderLayout.SOUTH);
        
        setBorder( new TitledBorder( "Encuentros" ) );
    }

    /**
     * Actualiza la lista mostrada de encuentros
     * @param encuentros Es una colección con los encuentros que hay actualmente en curso
     */
    public void actualizarEncuentros( Collection encuentros )
    {
        listaEncuentros.setListData( encuentros.toArray( ) );
    }

}
