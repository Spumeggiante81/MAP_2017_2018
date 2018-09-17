import javax.swing.JApplet;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTextField;

public class prova extends JApplet {
	private JTextField txtZorro;

	/**
	 * Create the applet.
	 */
	public prova() {
		getContentPane().setBackground(Color.LIGHT_GRAY);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(10, 11, 430, 278);
		getContentPane().add(panel);
		
		txtZorro = new JTextField();
		txtZorro.setText("zorro");
		panel.add(txtZorro);
		txtZorro.setColumns(10);

	}
}
