
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import keyboardinput.Keyboard;

import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JDialog;

public class MainTest extends JApplet {
	
	private static String DEFAULT_HOST = "localhost";
	private static int DEFAULT_PORT = 8080;
	private static IAsyncResponsive ResponsiveInterface;

	private Socket socket = null;

	
	
	protected static Object readObject(Socket socket) throws ClassNotFoundException, IOException
	{
		Object o;
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		o = in.readObject();
		return o;
	}
	protected static void writeObject(Socket socket, Object o) throws IOException
	{
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		out.writeObject(o);
		out.flush();
	}
	
	/**
	 * Ricava la collezione di dati, su cui eseguire il calcolo dei cluster, tramite il db
	 * @throws SocketException
	 * @throws ServerException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void storeTableFromDb(String table) throws SocketException,ServerException,IOException,ClassNotFoundException{
		writeObject(socket,0);
		writeObject(socket,table);
		String result = (String)readObject(socket);
		if(!result.equals("OK"))
			throw new ServerException(result);
	}
	
	
	/**
	 * Deposita il cluster all'interno di un file. Tale file sarà depositato su lato Server
	 * @throws SocketException
	 * @throws ServerException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private String storeClusterInFile(String fileName) throws SocketException,ServerException,IOException,ClassNotFoundException{
		writeObject(socket,2);
		writeObject(socket,fileName);
		String result = (String)readObject(socket);
		if(!result.equals("OK"))
			 throw new ServerException(result);
		return "Cluster stored into file '" + fileName + "'";
	}
	
	
	/**
	 * Effettua il calcolo dei cluster sulla collezione di dati ricavata dal server
	 * @return stampa dei cluster rilevati
	 * @throws SocketException
	 * @throws ServerException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private String learningFromDbTable(int k) throws SocketException,ServerException,IOException,ClassNotFoundException{
		writeObject(socket,1);
		writeObject(socket,k);
		String result = (String)readObject(socket);
		if(result.equals("OK")){
			return ("Clustering output:"+readObject(socket)+"\n") + (String)readObject(socket);
		}
		else throw new ServerException(result);
	}
	
	private class AsyncLearningFromDatabaseRequest extends AsyncClass {
		Socket socket;
		private String table;
		private int k;
		
		public AsyncLearningFromDatabaseRequest(IAsyncResponsive responsive, Socket socket, String tableName, int k) {
			super(responsive);
			this.socket = socket;
			this.table = tableName;
			this.k = k;
		}
		@Override public Object runasync() {
			try
			{
				storeTableFromDb(table);
				return learningFromDbTable(k);
				
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			} catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			} catch (ServerException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			return "Error";
		}
	}
	
	private class AsyncLearningFromFileRequest extends AsyncClass {
		Socket socket;
		private String table;
		private double k;
		
		public AsyncLearningFromFileRequest(IAsyncResponsive responsive, Socket socket, String tableName, double k) {
			super(responsive);
			this.socket = socket;
			this.table = tableName;
			this.k = k;
		}

		@Override
		protected Object runasync() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	private class AsyncStoreInFileRequest extends AsyncClass {
		Socket socket;
		private String fileName;
		
		public AsyncStoreInFileRequest(IAsyncResponsive responsive, Socket socket, String fileName){
			super(responsive);
			this.socket = socket;
			this.fileName = fileName;
		}
		
 		protected Object runasync() {
			try {
				return storeClusterInFile(fileName);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			} catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			} catch (ServerException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			return "Error";
		}
		
	}
	
	private class TabbedPane extends JPanel implements IAsyncResponsive {
		private JPanelCluster panelDB;
		private JPanelCluster panelFile;
		//private JPanelCluster panelEsempio;
		
		private class JPanelCluster extends JPanel {
			private JTextField tableText;
			private JTextField kText;
			private JTextArea clusterOutput;
			private JDialogFileManager windowFile;
			private JButton excecuteButton;
			private JButton fileButton;
			private JLabel plot = new JLabel("",null,JLabel.CENTER);
			
			JPanelCluster(String buttonName, ActionListener ae1, String fileButtonName, ActionListener ae2) {
				setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
				
				JPanel upPanel = new JPanel();
				upPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Input Boxes", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
				upPanel.setAlignmentY(Component.TOP_ALIGNMENT);
				add(upPanel);
				upPanel.setLayout(new GridBagLayout());
				GridBagConstraints gbc = new GridBagConstraints();
				
				tableText = new JTextField(20);
				kText = new JTextField(10);
				
				gbc.gridx = 0;
				gbc.gridy = 0;
				upPanel.add(new JLabel("Table"),gbc);
				gbc.gridx = 1;
				gbc.gridy = 0;
				upPanel.add(tableText,gbc);
				gbc.gridx = 0;
				gbc.gridy = 1;
				upPanel.add(new JLabel("k"),gbc);
				gbc.gridx = 1;
				gbc.gridy = 1;
				upPanel.add(kText,gbc);
				
				clusterOutput = new JTextArea();
				clusterOutput.setEditable(false);
				JScrollPane scrollingArea = new JScrollPane(clusterOutput);
				
				JPanel centralPanel = new JPanel();
				centralPanel.setBorder(new TitledBorder(null, "Clusters", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				add(centralPanel);
				centralPanel.setLayout(new BorderLayout(0, 0));
				centralPanel.add(plot,BorderLayout.NORTH);
				centralPanel.add(scrollingArea,BorderLayout.CENTER);
				
				JPanel downPanel = new JPanel();
				downPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
				downPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				downPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				add(downPanel);
				
				excecuteButton = new JButton(buttonName);
				excecuteButton.addActionListener(ae1);
				downPanel.add(excecuteButton);
				
				fileButton = new JButton (fileButtonName);
				fileButton.addActionListener((ae) -> {
					windowFile = new JDialogFileManager ("Save", ae2);
					windowFile.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					windowFile.setVisible(true);
				});
				downPanel.add(fileButton);
			}
		}
		
		private class JDialogFileManager extends JDialog implements WindowListener {
			private JTextField textField;
			
			JDialogFileManager (String buttonName, ActionListener ae){
				//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
				
				JPanel dPanel = new JPanel();
				dPanel.setAlignmentY(Component.TOP_ALIGNMENT);
				add(dPanel);
				dPanel.setLayout(new GridBagLayout());
				GridBagConstraints gbc = new GridBagConstraints();
				
				textField = new JTextField(10);
				JButton executeButton = new JButton(buttonName);
				executeButton.addActionListener(ae);
				
				gbc.gridx = 0;
				gbc.gridy = 0;
				dPanel.add(new JLabel("File Name"),gbc);
				gbc.gridx = 1;
				gbc.gridy = 0;
				dPanel.add(textField,gbc);
				gbc.gridx = 0;
				gbc.gridy = 1;
				dPanel.add(executeButton,gbc);
				gbc.gridx = 1;
				gbc.gridy = 1;
				dPanel.add(new JButton("Cancel"),gbc);
			}

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				//this.getParent().setEnabled(true);
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

		}
		
		TabbedPane() {
			super(new GridLayout(0, 1, 0, 0)); 
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			panelDB = new JPanelCluster("MINE", (ae1) -> {
				String table = panelDB.tableText.getText();
				int k;
				try {
					k = new Integer((panelDB.kText.getText())).intValue();
				} catch (NumberFormatException ex) {
					k = 0;
				}
				new AsyncLearningFromDatabaseRequest(ResponsiveInterface, socket, table, k).start();
			}, "Save Clusters on File", (ae2) -> {
				String fileName = panelDB.windowFile.textField.getText();
				new AsyncStoreInFileRequest(ResponsiveInterface, socket, fileName).start();
			});
			tabbedPane.addTab("DB", null, panelDB, null);
			panelFile = new JPanelCluster("STORE FROM FILE", null, "Save Clusters on File",null);
			tabbedPane.addTab("File", null, panelFile, null);
			add(tabbedPane);
			tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		}

		@Override
		public void asyncStart(AsyncClass o) {
			JTextArea textArea;
			JLabel plot;
			
			if (o instanceof AsyncLearningFromDatabaseRequest){
				textArea = panelDB.clusterOutput;
				plot = panelDB.plot;
			}
			else if (o instanceof AsyncLearningFromFileRequest) {
				textArea = panelFile.clusterOutput;
				plot = panelFile.plot;
			}
			else
				return;
			textArea.setText("Processing the server...");
			plot.setText("Plot loading...");
			plot.setIcon(null);
		}

		@Override
		public void asyncEnd(AsyncClass o, Object result) {
			JTextArea textArea;
			JLabel plot;
			
			if (o instanceof AsyncLearningFromDatabaseRequest){
				textArea = panelDB.clusterOutput;
				plot = panelDB.plot;
			}
			else if (o instanceof AsyncLearningFromFileRequest) {
				textArea = panelFile.clusterOutput;
				plot = panelFile.plot;
			}
			else if (o instanceof AsyncStoreInFileRequest) {
				JOptionPane.showMessageDialog(null, result, "Information", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			else
				return;
			textArea.setText((String)result);
			plot.setText("");
			try {
				String is_img = (String) readObject (socket);
				if (is_img.compareTo("IMG")==0){
					byte[] buffer = (byte[])readObject(socket);
					ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
					BufferedImage img = ImageIO.read(bais);
					bais.close();
					ImageIcon icon = new ImageIcon(img);
					plot.setIcon(icon);
				}
				
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private TabbedPane tab;
	
	public void init() {
		String strHost = getParameter("ServerIP");
		String strPort = getParameter("Port");
		int port;

		this.getSize(getMaximumSize());
		if (strHost == null) { // se non ï¿½ specificato alcun indirizzo IP
			strHost = DEFAULT_HOST; // allora ne imposta uno di default
		}

		if (strPort == null) { // se non ï¿½ specificata la porta
			port = DEFAULT_PORT; // allora imposta la porta di default
		}
		else{
			// altrimenti la processa dal parametro
			try {
				port = Integer.parseInt(strPort);
			} catch (NumberFormatException e) {
				// se il parametro ï¿½ un formato differente da un numero
				// allora imposta la porta come da default
				port = DEFAULT_PORT;
			}
		}

		try {
			InetAddress addr = InetAddress.getByName(strHost); // ottiene l'indirizzo dell'host specificato
			System.out.println("Connecting to " + addr + "...");
			socket = new Socket(addr, port); // prova a connetters
			System.out.println("Success! Connected to " + socket);

			tab = new TabbedPane();
			getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
			getContentPane().add(tab);
			ResponsiveInterface = tab;

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Unable to connect to " + strHost + ":" + port + ".\n" + e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
			this.destroy();
			System.exit(0);
		}
		

	}
}