
import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JPanel;
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
			private TextArea clusterOutput;
			private JDialogFileManager windowFile;
			private JButton excecuteButton;
			private JButton fileButton;
			
			JPanelCluster(String buttonName, ActionListener ae1, String fileButtonName, ActionListener ae2) {
				setLayout(null);
				
				JPanel upPanel = new JPanel();
				upPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Input Boxes", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
				upPanel.setBounds(10, 11, 405, 50);
				add(upPanel);
				upPanel.setLayout(null);
				
				JLabel tableLabel = new JLabel("Table");
				tableLabel.setBounds(10, 25, 32, 14);
				upPanel.add(tableLabel);
				
				tableText = new JTextField();
				tableText.setBounds(52, 22, 86, 20);
				upPanel.add(tableText);
				tableText.setColumns(10);
				
				JLabel kLabel = new JLabel("k");
				kLabel.setBounds(203, 25, 24, 14);
				upPanel.add(kLabel);
				
				kText = new JTextField();
				kText.setBounds(213, 22, 86, 20);
				upPanel.add(kText);
				kText.setColumns(10);
				
				JPanel centralPanel = new JPanel();
				centralPanel.setBorder(new TitledBorder(null, "Clusters", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				centralPanel.setBounds(10, 65, 405, 130);
				add(centralPanel);
				centralPanel.setLayout(null);
				
				clusterOutput = new TextArea();
				clusterOutput.setEditable(false);
				clusterOutput.setBounds(10, 21, 380, 99);
				centralPanel.add(clusterOutput);
				
				JPanel downPanel = new JPanel();
				downPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				downPanel.setBounds(10, 200, 405, 39);
				add(downPanel);
				downPanel.setLayout(null);
				
				excecuteButton = new JButton(buttonName);
				excecuteButton.addActionListener(ae1);
				excecuteButton.setBounds(10, 11, 89, 23);
				downPanel.add(excecuteButton);
				
				fileButton = new JButton (fileButtonName);
				fileButton.addActionListener((ae) -> {
					windowFile = new JDialogFileManager ("Save", ae2);
					windowFile.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					windowFile.setVisible(true);
				});
				fileButton.setBounds(306, 11, 89, 23);
				downPanel.add(fileButton);
				
				/*
				setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
				
				//upPanel
				JPanel upPanel = new JPanel();
				add(upPanel);
				JLabel tableLabel = new JLabel("table");
				upPanel.add(tableLabel);
				upPanel.add(tableText);
				JLabel kLabel = new JLabel("k");
				upPanel.add(kLabel);
				upPanel.add(kText);
				
				//centralPanel
				JPanel centralPanel = new JPanel();
				add(centralPanel);
				centralPanel.add(clusterOutput);
				
				//downPanel
				JPanel downPanel = new JPanel();
				add(downPanel);
				excecuteButton = new JButton(buttonName);
				excecuteButton.addActionListener(ae1);
				fileButton = new JButton (fileButtonName);
				fileButton.addActionListener((ae) -> {
					windowFile = new JDialogFileManager ("Save", ae2);
					windowFile.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					windowFile.setVisible(true);
				});
				downPanel.add(excecuteButton);
				downPanel.add(fileButton);
				*/
			}
		}
		
		private class JDialogFileManager extends JDialog implements WindowListener {
			private JPanel contentPanel = new JPanel();
			private JTextField fileNameText;
			
			JDialogFileManager (String buttonName, ActionListener ae){
				setBounds(100, 100, 220, 120);
				getContentPane().setLayout(new BorderLayout());
				contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
				getContentPane().add(contentPanel, BorderLayout.CENTER);
				contentPanel.setLayout(null);
				{
					JLabel fileNaleLabel = new JLabel("File name");
					fileNaleLabel.setBounds(10, 11, 45, 14);
					contentPanel.add(fileNaleLabel);
				}
				
				fileNameText = new JTextField();
				fileNameText.setBounds(65, 8, 129, 20);
				contentPanel.add(fileNameText);
				fileNameText.setColumns(10);
				{
					JPanel buttonPane = new JPanel();
					buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
					getContentPane().add(buttonPane, BorderLayout.SOUTH);
					{
						JButton okButton = new JButton("OK");
						okButton.setActionCommand("OK");
						okButton.addActionListener(ae);
						buttonPane.add(okButton);
						getRootPane().setDefaultButton(okButton);
					}
					{
						JButton cancelButton = new JButton("Cancel");
						cancelButton.setActionCommand("Cancel");
						buttonPane.add(cancelButton);
					}
				}
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
			super(new GridLayout(1, 1)); 
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setBounds(10, 11, 430, 278);
			panelDB = new JPanelCluster("MINE", (ae1) -> {
				String table = panelDB.tableText.getText();
				int k;
				try {
					//k = new Double(panelDB.kText.getText()).doubleValue();
					k = new Integer((panelDB.kText.getText())).intValue();
				} catch (NumberFormatException ex) {
					k = 0;
				}
				String result = (String) new AsyncLearningFromDatabaseRequest(ResponsiveInterface, socket, table, k).runasync();
				panelDB.clusterOutput.setText(result);
			}, "Save Clusters on File", (ae2) -> {
				String fileName = panelDB.windowFile.fileNameText.getText();
				String result = (String) new AsyncStoreInFileRequest(ResponsiveInterface, socket, fileName).runasync();
				JOptionPane.showMessageDialog(null, result, "Information", JOptionPane.INFORMATION_MESSAGE);
				panelDB.windowFile.setEnabled(false);
			});
			tabbedPane.addTab("DB", null, panelDB, null);
			panelFile = new JPanelCluster("STORE FROM FILE", null, "Save Clusters on File",null);
			tabbedPane.addTab("File", null, panelFile, null);
			//panelEsempio = new JPanelCluster();
			//tabbedPane.addTab("Esempio", null, panelEsempio, null);
			add(tabbedPane);
			tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		}

		@Override
		public void asyncStart(AsyncClass o) {
			TextArea textArea;
			if (o instanceof AsyncLearningFromDatabaseRequest)
				textArea = panelDB.clusterOutput;
			else if (o instanceof AsyncLearningFromFileRequest)
				textArea = panelFile.clusterOutput;
			else
				return;
			textArea.setText("Processing the server...");
		}

		@Override
		public void asyncEnd(AsyncClass o, Object result) {
			TextArea textArea;

			if (o instanceof AsyncLearningFromDatabaseRequest){
				textArea = panelDB.clusterOutput;
			}
			else if (o instanceof AsyncLearningFromFileRequest) {
				textArea = panelFile.clusterOutput;
			}
			else
				return;
			textArea.setText((String)result);
			
		}
	}
	
	private TabbedPane tab;
	
	public void init() {
		String strHost = getParameter("ServerIP");
		String strPort = getParameter("Port");
		int port;

		if (strHost == null) { // se non ï¿½ specificato alcun indirizzo IP
			strHost = DEFAULT_HOST; // allora ne imposta uno di default
		}

		if (strPort == null) { // se non ï¿½ specificata la porta
			port = DEFAULT_PORT; // allora imposta la porta di default
		}
		else
		{
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
			tab.setBounds(0, 0, 450, 300);
			getContentPane().add(tab);
			tab.setLayout(null);
			
			/*
			tab = new TabbedPane();
			getContentPane().setLayout(new GridLayout(1, 1));
			getContentPane().add(tab);
			ResponsiveInterface = tab;
			*/

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Unable to connect to " + strHost + ":" + port + ".\n" + e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
			this.destroy();
			System.exit(0);
		}
		

	}
}