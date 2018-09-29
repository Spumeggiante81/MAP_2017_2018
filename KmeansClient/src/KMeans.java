

import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
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

import keyboardinput.Keyboard;

import javax.swing.JTextArea;
import javax.swing.JButton;

public class KMeans extends JApplet {
	
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
				storeTableFromDb();
				return learningFromDbTable();
				
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			} catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			} catch (ServerException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			return "Error";
		}
		

		/**
		 * Ricava la collezione di dati, su cui eseguire il calcolo dei cluster, tramite il db
		 * @throws SocketException
		 * @throws ServerException
		 * @throws IOException
		 * @throws ClassNotFoundException
		 */
		private void storeTableFromDb() throws SocketException,ServerException,IOException,ClassNotFoundException{
			writeObject(socket,0);
			writeObject(socket,table);
			String result = (String)readObject(socket);
			if(!result.equals("OK"))
				throw new ServerException(result);
		}
		
		
		/**
		 * Effettua il calcolo dei cluster sulla collezione di dati ricavata dal server
		 * @return stampa dei cluster rilevati
		 * @throws SocketException
		 * @throws ServerException
		 * @throws IOException
		 * @throws ClassNotFoundException
		 */
		private String learningFromDbTable() throws SocketException,ServerException,IOException,ClassNotFoundException{
			writeObject(socket,1);
			writeObject(socket,k);
			String result = (String)readObject(socket);
			if(result.equals("OK")){
				return ("Clustering output:"+readObject(socket)+"\n") + (String)readObject(socket);
			}
			else throw new ServerException(result);
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
	
	
	public KMeans() {
	}

	private class TabbedPane extends JPanel implements IAsyncResponsive {
		private JPanelCluster panelDB;
		private JPanelCluster panelFile;
		//private JPanelCluster panelEsempio;
		
		private class JPanelCluster extends JPanel {
			private JTextField tableText = new JTextField (10);
			private JTextField kText = new JTextField (5);
			private JTextArea clusterOutput = new JTextArea(20,12);
			private JButton excecuteButton;
			
			JPanelCluster(String buttonName) {
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
				downPanel.add(excecuteButton);
			}
			
			private void addActionListener(ActionListener ae){
				excecuteButton.addActionListener(ae);
			}
		}
		
		TabbedPane() {
			super(new GridLayout(1, 1)); 
			JTabbedPane tabbedPane = new JTabbedPane();
			panelDB = new JPanelCluster("MINE");
			panelDB.addActionListener((ae) -> {
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
			});
			tabbedPane.addTab("DB", null, panelDB, null);
			panelFile = new JPanelCluster("STORE FROM FILE");
			tabbedPane.addTab("File", null, panelFile, null);
			//panelEsempio = new JPanelCluster();
			//tabbedPane.addTab("Esempio", null, panelEsempio, null);
			add(tabbedPane);
			tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		}

		@Override
		public void asyncStart(AsyncClass o) {
			JTextArea textArea;
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
			JTextArea textArea;

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

		if (strHost == null) { // se non � specificato alcun indirizzo IP
			strHost = DEFAULT_HOST; // allora ne imposta uno di default
		}

		if (strPort == null) { // se non � specificata la porta
			port = DEFAULT_PORT; // allora imposta la porta di default
		}
		else
		{
			// altrimenti la processa dal parametro
			try {
				port = Integer.parseInt(strPort);
			} catch (NumberFormatException e) {
				// se il parametro � un formato differente da un numero
				// allora imposta la porta come da default
				port = DEFAULT_PORT;
			}
		}

		try {
			InetAddress addr = InetAddress.getByName(strHost); // ottiene l'indirizzo dell'host specificato
			System.out.println("Connecting to " + addr + "...");
			socket = new Socket(addr, port); // prova a connetters
			System.out.println("Success! Connected to " + socket);

			TabbedPane tab = new TabbedPane();
			getContentPane().setLayout(new GridLayout(1, 1));
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

