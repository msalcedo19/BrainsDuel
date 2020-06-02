package uniandes.cupi2.brainsDuel.servidor.interfaz;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel de manejo de Opciones.
 */
public class PanelOpciones extends JPanel implements ActionListener
{
	
    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Comando Actualizar.
     */
	public static final String ACTUALIZAR = "ACTUALIZAR";
	
    /**
     * Comando Opción 1
     */
	public static final String OPCION_1 = "OPCION_1";
	
    /**
     * Comando Opción 2
     */
	public static final String OPCION_2 = "OPCION_2";
	
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Ventana principal de la aplicación
     */
    private InterfazServidor principal;
    

    // -----------------------------------------------------------------
    // Atributos de interfaz
    // -----------------------------------------------------------------
    
    /**
     * Botón Actualizar.
     */
	private JButton actualizar;
	
    /**
     * Botón Opción 1
     */
	private JButton opcion1;
	
    /**
     * Botón Opción 2
     */
	private JButton opcion2;
	
    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Constructor del panel
     * @param ventanaPrincipal Ventana principal
     */
	public PanelOpciones(InterfazServidor p) {

		principal = p;
		setBorder ( BorderFactory.createTitledBorder("Opciones"));
		setSize(166,534);
		setLayout(new GridLayout(6, 1, 166, 10));
		
		JLabel imagen = new JLabel();
		ImageIcon icono = new ImageIcon("./data/img/actualizar.png");
		imagen.setIcon(icono);
		actualizar = new JButton("Actualizar");
		actualizar.addActionListener(this);
		actualizar.setActionCommand(ACTUALIZAR);
		actualizar.setPreferredSize(new Dimension(166, 78));
		actualizar.setIcon(icono);

		
		opcion1 = new JButton("Opcion 1");
		opcion1.addActionListener(this);
		opcion1.setActionCommand(OPCION_1);
		opcion1.setPreferredSize(new Dimension(166, 78));
		
		opcion2 = new JButton("Opcion 2");
		opcion2.addActionListener(this);
		opcion2.setActionCommand(OPCION_2);
		opcion2.setPreferredSize(new Dimension(166, 78));
		
		add( actualizar );
		add( opcion1 );
		add( opcion2 );

		
	}

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Manejo de los eventos de los botones
     * @param e Acción que generó el evento.
     */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		 if( OPCION_1.equals( e.getActionCommand( ) ) )
	        {
	            principal.reqFuncOpcion1( );
	        }
	        else if( OPCION_2.equals( e.getActionCommand( ) ) )
	        {
	            principal.reqFuncOpcion2( );
	        }
	        else if( ACTUALIZAR.equals( e.getActionCommand( ) ) )
	        {
	            principal.actualizarEncuentros();
	            principal.actualizarJugadores();
	        }
		
		
	}

}
