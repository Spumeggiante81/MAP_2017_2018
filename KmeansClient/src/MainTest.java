import javax.swing.JApplet;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

<<<<<<< HEAD
<<<<<<< HEAD
import javax.swing.JPanel;
import javax.swing.JLabel;
<<<<<<< HEAD
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.Color;

=======
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
>>>>>>> 2af9d8fcc1cd302668fb3a5fe7f73f04da467ebe
=======
>>>>>>> parent of 2af9d8f... JApplet (panelDB funzionante)
=======
>>>>>>> parent of 2af9d8f... JApplet (panelDB funzionante)

import keyboardinput.Keyboard;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.Color;
import KmeansServer*.*;

public class MainTest extends JApplet {
<<<<<<< HEAD
<<<<<<< HEAD
	
	private static String DEFAULT_HOST = "localhost";
	private static int DEFAULT_PORT = 8080;
<<<<<<< HEAD
	Socket socket;
	private JTextField tableNameTxt;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	
	public MainTest(String ip, int port) throws IOException{
		InetAddress addr = InetAddress.getByName(ip); //ip
		System.out.println("addr = " + addr);
		socket = new Socket(addr, port); //Port
		System.out.println(socket);
	}
	
    /**
     * Si occupa della ricezione sicura di un oggetto via socket
     * @param socket la quale ricevere l'oggetto
     * @return oggetto ricevuto dal socket specificato
     * @throws ClassNotFoundException nel caso la classe ricevuta non vi è riconosciuta
	 * @throws IOException nel caso si verifichi un errore in fase di lettura
     */
	private static Object readObject(Socket socket) throws ClassNotFoundException, IOException {
=======
	private static IAsyncResponsive ResponsiveInterface;
=======
>>>>>>> parent of 2af9d8f... JApplet (panelDB funzionante)


<<<<<<< HEAD
	protected static Object readObject(Socket socket) throws ClassNotFoundException, IOException
	{
>>>>>>> 2af9d8fcc1cd302668fb3a5fe7f73f04da467ebe
=======
=======


>>>>>>> parent of 2af9d8f... JApplet (panelDB funzionante)
	/**
	 * @param args
	 */
	private static String DEFAULT_HOST = "localhost";
	private static int DEFAULT_PORT = 8080;
	Socket socket;
	private JTextField tableNameTxt;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	double [][] matrice;
	int righe,colonne = 0;
	ClusterSet numeroCluster = new ClusterSet();
	
	
	public MainTest(String ip, int port) throws IOException{
		InetAddress addr = InetAddress.getByName(ip); //ip
		System.out.println("addr = " + addr);
		socket = new Socket(addr, port); //Port
		System.out.println(socket);
	}
	
    /**
     * Si occupa della ricezione sicura di un oggetto via socket
     * @param socket la quale ricevere l'oggetto
     * @return oggetto ricevuto dal socket specificato
     * @throws ClassNotFoundException nel caso la classe ricevuta non vi è riconosciuta
	 * @throws IOException nel caso si verifichi un errore in fase di lettura
     */
	private static Object readObject(Socket socket) throws ClassNotFoundException, IOException {
<<<<<<< HEAD
>>>>>>> parent of 2af9d8f... JApplet (panelDB funzionante)
=======
>>>>>>> parent of 2af9d8f... JApplet (panelDB funzionante)
		Object o;
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		o = in.readObject();
		return o;
	}
	/**
     * Si occupa dell'invio sicuro di un oggetto via socket
     * @param socket la quale riceverà l'oggetto
	 * @param o oggetto da inviare
	 * @throws IOException nel caso si verifichi un errore in fase di scrittura
	 */
	private static void writeObject(Socket socket, Object o) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		out.writeObject(o);
		out.flush();
	}
	
	private int menu(){
		int answer;
		System.out.println("Scegli una opzione");
		do{
			System.out.println("(1) Carica Cluster da File");
			System.out.println("(2) Carica Dati");
			System.out.print("Risposta:");
			answer=Keyboard.readInt();
		}
		while(answer<=0 || answer>2);
		return answer;
	}
	
	/**
	 * Ricava i cluster all'interno di uno specifico file, definito dall'utente, indicando inoltre
	 * la tabella da cui reperire la collezione di dati a cui è riferita.
	 * @return stampa dei cluster presenti nel file
	 * @throws SocketException
	 * @throws ServerException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private String learningFromFile() throws SocketException,ServerException,IOException,ClassNotFoundException{
		writeObject(socket,3);
		System.out.print("Nome tabella:");
		String tabName=Keyboard.readString();
		writeObject(socket,tabName);
		System.out.print("Nome file:");
		String fileName=Keyboard.readString();
		writeObject(socket,fileName);
		String result = (String)readObject(socket);
		if(result.equals("OK"))
			return (String)readObject(socket);
		else throw new ServerException(result);
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
		System.out.print("Nome tabella:");
		String tabName=Keyboard.readString();
		writeObject(socket,tabName);
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
		System.out.print("Numero di cluster:");
		int k=Keyboard.readInt();
		writeObject(socket,k);
		String result = (String)readObject(socket);
		if(result.equals("OK")){
			System.out.println("Clustering output:"+readObject(socket));
			return (String)readObject(socket);
		}
		else throw new ServerException(result);
	}
	
	/**
	 * Deposita il cluster all'interno di un file. Tale file sarà depositato su lato Server
	 * @throws SocketException
	 * @throws ServerException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void storeClusterInFile() throws SocketException,ServerException,IOException,ClassNotFoundException{
		writeObject(socket,2);
		System.out.println("Nome File in cui salvare i cluster ricavati:");
		String fileName = Keyboard.readString();
		writeObject(socket,fileName);
		String result = (String)readObject(socket);
		if(!result.equals("OK"))
			 throw new ServerException(result);
	}
	
	public static void main(String[] args){
		String ip;
		int port;
		System.out.println("Impostare manualmente indirizzo / porta comunicazione?(y/n)");
		char answer = Keyboard.readChar();
		if (answer == 'y'){
			System.out.println("Inserire indirizzo:");
			ip = Keyboard.readString();
			System.out.println("Inserire porta:");
			port = Keyboard.readInt();
<<<<<<< HEAD
		}
		else{
			ip = DEFAULT_HOST;
			port = DEFAULT_PORT;
		}
		MainTest main=null;
		try{
			main=new MainTest(ip,port);
		}
		catch (IOException e){
			System.err.println(e);
			return;
		}
=======
		}
		else{
			ip = DEFAULT_HOST;
			port = DEFAULT_PORT;
		}
		MainTest main=null;
		try{
			main=new MainTest(ip,port);
		}
		catch (IOException e){
			System.err.println(e);
			return;
		}
>>>>>>> parent of 2af9d8f... JApplet (panelDB funzionante)
		do{
			int menuAnswer=main.menu();
			switch(menuAnswer){
				case 1:
					try {
						String kmeans=main.learningFromFile();
						System.out.println(kmeans);
					}
					catch (SocketException e) {
						System.err.println(e);
						return;
					}
					catch (FileNotFoundException e) {
						System.err.println(e);
						return ;
					} catch (IOException e) {
						System.err.println(e);
						return;
					} catch (ClassNotFoundException e) {
						System.err.println(e);
						return;
					}
					catch (ServerException e) {
						System.err.println(e.getMessage());
					}
					break;
				case 2: // learning from db
				
					answer='y';//itera per learning al variare di k
					while(true){
						try{
							main.storeTableFromDb();
							break; //esce fuori dal while
						}
						
						catch (SocketException e) {
							System.err.println(e);
							return;
						}
						catch (IOException e) {
							System.err.println(e);
							return;
						} catch (ClassNotFoundException e) {
							System.err.println(e);
							return;
						}
						catch (ServerException e) {
							System.err.println(e.getMessage());
							answer = 'n';
							break;
						}
					} //end while [viene fuori dal while con un db (in alternativa il programma termina)
						
					while(answer == 'y'){
						try{
							String clusterSet=main.learningFromDbTable();
							System.out.println(clusterSet);
							
							main.storeClusterInFile();
									
						}
						catch (SocketException e) {
							System.err.println(e);
							return;
						}
						catch (FileNotFoundException e) {
							System.err.println(e);
							return;
						} 
						catch (ClassNotFoundException e) {
							System.err.println(e);
							return;
						}catch (IOException e) {
							System.err.println(e);
							return;
						}
						catch (ServerException e) {
							System.err.println(e.getMessage());
						}
						System.out.print("Vuoi ripetere l'esecuzione?(y/n)");
						answer=Keyboard.readChar();
					}
					break; //fine case 2
					default:
					System.out.println("Opzione non valida!");
			}
			
			System.out.print("Vuoi scegliere una nuova operazione da menu?(y/n)");
			if(Keyboard.readChar()!='y')
				break;
			}
		while(true);
		}

	/**
	 * Create the applet.
	 */
	public MainTest() {
		getContentPane().setBackground(Color.LIGHT_GRAY);
		getContentPane().setLayout(null);
		
		JTabbedPane JTabbedPane = new JTabbedPane();
		JTabbedPane.setBackground(Color.LIGHT_GRAY);
		JTabbedPane.setBounds(10, 11, 430, 278);
		getContentPane().add(JTabbedPane);
		
		JPanel DBPanel = new JPanel();
		DBPanel.setBackground(Color.LIGHT_GRAY);
		JTabbedPane.addTab("DB", null, DBPanel, null);
		DBPanel.setLayout(null);
		
		tableNameTxt = new JTextField();
		tableNameTxt.setBounds(81, 11, 86, 20);
		DBPanel.add(tableNameTxt);
		tableNameTxt.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(254, 11, 86, 20);
		DBPanel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblTable = new JLabel("Table:");
		lblTable.setBounds(22, 14, 46, 14);
		DBPanel.add(lblTable);
		
		JLabel lblK = new JLabel("k:");
		lblK.setBounds(226, 14, 18, 14);
		DBPanel.add(lblK);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(10, 117, 405, 91);
		DBPanel.add(textPane);
		
		JButton btnMine = new JButton("MINE");
		btnMine.setBounds(155, 219, 89, 23);
		DBPanel.add(btnMine);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(125, 72, -78, 14);
		DBPanel.add(tabbedPane);
		
		JButton button = new JButton("");
		button.setBounds(10, 72, 89, 23);
		DBPanel.add(button);
		
		JPanel FilePanel = new JPanel();
		FilePanel.setBackground(Color.LIGHT_GRAY);
		FilePanel.setLayout(null);
		JTabbedPane.addTab("FILE", null, FilePanel, null);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(81, 11, 86, 20);
		FilePanel.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(254, 11, 86, 20);
		FilePanel.add(textField_3);
		
		JLabel label = new JLabel("Table:");
		label.setBounds(22, 14, 46, 14);
		FilePanel.add(label);
		
		JLabel label_1 = new JLabel("k:");
		label_1.setBounds(226, 14, 18, 14);
		FilePanel.add(label_1);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setBounds(10, 117, 405, 91);
		FilePanel.add(textPane_1);
		
		JButton btnStoreFromFile = new JButton("STORE FROM FILE");
		btnStoreFromFile.setBounds(155, 219, 121, 23);
		FilePanel.add(btnStoreFromFile);

	}
}
