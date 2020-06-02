package uniandes.cupi2.brainsDuel.servidor.interfaz;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.xml.internal.ws.assembler.dev.ServerTubelineAssemblyContext;

public class PanelImagen extends JPanel
{

	public PanelImagen() {
		
		setSize(134,546);
		JLabel panel = new JLabel();
		ImageIcon imagen = new ImageIcon("./data/img/bannerServidor.png");
		panel.setIcon(imagen);
		add(panel);
	}

}
