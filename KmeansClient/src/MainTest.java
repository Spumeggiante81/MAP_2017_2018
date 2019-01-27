
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
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

public class MainTest extends JApplet {
	//definisco delle "tipi" di campi, quali potranno essere usati per definire dei campi d'intestazione nei pannelli
	private enum FieldType {table, k, FileName};
	private static String DEFAULT_HOST = "localhost";
	private static int DEFAULT_PORT = 8080;
	private static IAsyncResponsive ResponsiveInterface;

	private Socket socket = null;

	
	/**
	 * Permette di leggere il contenuto presente in una socket
	 * @param socket
	 * @return oggetto contenuto nella socket
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	protected static Object readObject(Socket socket) throws ClassNotFoundException, IOException
	{
		Object o;
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		o = in.readObject();
		return o;
	}
	
	/**
	 * Permette di riportare un oggetto all'interno di una socket, tale da rendere l'oggetto
	 * condivisibile per coloro che comunicano utilizzando la stessa socket
	 * @param socket
	 * @param o Oggetto da passare alla socket
	 * @throws IOException
	 */
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
	private void storeTableFromDb(Socket socket, String table) throws SocketException,ServerException,IOException,ClassNotFoundException{
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
	private String storeClusterInFile(Socket socket, String fileName) throws SocketException,ServerException,IOException,ClassNotFoundException{
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
	private String learningFromDbTable(Socket socket, int k) throws SocketException,ServerException,IOException,ClassNotFoundException{
		writeObject(socket,1);
		writeObject(socket,k);
		String result = (String)readObject(socket);
		if(result.equals("OK")){
			return ("Clustering output:"+readObject(socket)+"\n") + (String)readObject(socket);
		}
		else throw new ServerException(result);
	}
	
	/**
	 * Permette di ricavare i cluster che sono stati serializzati all'interno di un dato file
	 * @param table nome della tabella da cui ricavare le tuple
	 * @param fileName nome del file da cui si desidera estrapolare il cluster desiderato
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws ServerException
	 */
	private String learningFromFile (Socket socket, String table, String fileName) throws IOException, ClassNotFoundException, ServerException{
		writeObject(socket,3);
		writeObject(socket, table);
		writeObject(socket, fileName);
		String result = (String)readObject(socket);
		if(result.equals("OK")){
			return ("Clustering output in file : " + fileName + "\n") + (String)readObject(socket);
		}
		else throw new ServerException(result);
	}
	
	//Lettura e calcolo dei cluster dal database
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
			try{
				storeTableFromDb(socket, table);
				return learningFromDbTable(socket,k);
				
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
	
	//Lettura dei cluster presenti in un file
	private class AsyncLearningFromFileRequest extends AsyncClass {
		Socket socket;
		private String table;
		private String fileName;
		
		public AsyncLearningFromFileRequest(IAsyncResponsive responsive, Socket socket,String table, String fileName) {
			super(responsive);
			this.socket = socket;
			this.fileName = fileName;
			this.table = table;
		}

		@Override
		protected Object runasync() {
			try{
				return learningFromFile(socket, table, fileName);
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
	
	//Salvataggio dei cluster in un file
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
				return storeClusterInFile(socket, fileName);
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
		
		private class JPanelCluster extends JPanel {

			/*
			 * upPanelFields conterrà tutti i campi compilabili presenti nella parte superiore di ciascun pannello
			 * (upPanel). Ad ogni campo è associato un "tipo", quale servirà sia per identificarlo e sia per 
			 * descrivere l'utilità di tale campo, indicando il tipo di valore richiesto da inserire
			 */
			private Map<String, JTextField> upPanelFields;
			private JPanel upPanel, centralPanel, downPanel;
			private JTextArea clusterOutput;
			private JLabel plot = new JLabel("",null,JLabel.CENTER);
			
			/**
			 * Istanzia un generico Pannello, suddiviso in tre sotto-pannelli : upPanel, centralPanel, downPanel.
			 * 
			 * Nel primo (upPanel) vi saranno indicati i campi che dovranno essere compilati dall'utente. Per definire tali
			 * campi ususfruire delle funzioni "addUpPanelField" e "getUpPanelField". 
			 * 
			 * Nel secondo (centralPanel) vi sarà un'area di testo su cui si potrà riportare qualsiasi genere di messaggio
			 * da far visualizzare all'utente.
			 * 
			 * Nel terzo (downPanel) vi saranno riportati i pulsanti relativi alle azioni quali si vorranno far eseguire.
			 *
			 * @param upPanelName Stringa che descrive il pannello da usare (e verrà impostato ai margini del bordo di tale pannello).
			 * Inserire null se si vuole omettere l'utilizzo di tale pannello.
			 * @param centralPanelName Stringa che descrive il pannello da usare (e verrà impostato ai margini del bordo di tale pannello).
			 * Inserire null se si vuole omettere l'utilizzo di tale pannello.
			 * @param downPanelName Stringa che descrive il pannello da usare (e verrà impostato ai margini del bordo di tale pannello).
			 * Inserire null se si vuole omettere l'utilizzo di tale pannello.
			 */
			JPanelCluster (String upPanelName, String centralPanelName, String downPanelName){
				setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
				
				upPanelFields = new HashMap <>();
				
				upPanel = new JPanel();
				upPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Input Boxes", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
				upPanel.setAlignmentY(Component.TOP_ALIGNMENT);
				upPanel.setLayout(new GridBagLayout());
				
				if (upPanelName != null)
					add(upPanel);

				clusterOutput = new JTextArea();
				clusterOutput.setEditable(false);
				JScrollPane scrollingArea = new JScrollPane(clusterOutput);
				
				centralPanel = new JPanel();
				centralPanel.setBorder(new TitledBorder(null, "Clusters", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				centralPanel.setLayout(new BorderLayout(0, 0));
				centralPanel.add(plot,BorderLayout.NORTH);
				centralPanel.add(scrollingArea,BorderLayout.CENTER);
				
				if (centralPanelName != null)
					add(centralPanel);
				
				downPanel = new JPanel();
				downPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
				downPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				downPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				if (downPanelName != null)
					add(downPanel);
			}
			
			/**
			 * Permette di definire un nuovo campo compilabile, da inserire nel pannello "upPanel"
			 * @param field nome del campo 
			 */
			public void addUpPanelField (FieldType field){
				JTextField textField = new JTextField(10);
				upPanelFields.put(field.name(), textField);
				GridBagConstraints gbc = new GridBagConstraints();
				/*
				 * gridy = indica la posizione della "riga" nella griglia
				 * gridx = indica la posizione della "colonna" nella riga
				 * 
				 * non a caso tutti i campi risulteranno "incolonnati", dato che tra di loro varierà solo il valore di gridy in base all'ordine di inserimento.
				 */
				gbc.gridx = 0;
				gbc.gridy = upPanelFields.size() - 1;
				upPanel.add(new JLabel (field.name()), gbc);
				gbc.gridx = 1;
				upPanel.add(textField, gbc);
			}
			
			/**
			 * Permette di recuperare l'oggetto associato al tipo di campo compilabile ricercato presente nel pannello upPanel(sempre
			 * qualora sia aggiunto nel Pannello. In caso contrario, guardare "addUpPanelField").
			 * @param field nome del campo
			 * @return l'oggetto riferito al campo compilabile
			 */
			public JTextField getUpPanelField (FieldType field){
				return upPanelFields.get(field.name());
			}
			
			/**
			 * Permette di aggiungere un bottone, associandoci un ActionListener quale sarà attivato quando tale bottone sarà premuto
			 * @param buttonName Stringa da inserire sul bottone
			 * @param ae evento associato all'attivazione di tale bottone
			 */
			public void addDownPanelButton (String buttonName, ActionListener ae){
				JButton button = new JButton (buttonName);
				button.addActionListener(ae);
				downPanel.add(button);
			}
		}
				
		TabbedPane() {
			super(new GridLayout(0, 1, 0, 0)); 
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			//----------------PANEL DB-----------------------------
			panelDB = new JPanelCluster("Input Boxes", "Clusters", "Comands");
			//----------------PANEL DB (upPanel)-------------------
			panelDB.addUpPanelField(FieldType.table);
			panelDB.addUpPanelField(FieldType.k);
			//----------------PANEL DB(downPanel)------------------
			panelDB.addDownPanelButton("MINE", (ae1) -> {
				String table = panelDB.getUpPanelField(FieldType.table).getText();
				int k;
				try {
					k = new Integer((panelDB.getUpPanelField(FieldType.k).getText())).intValue();
				} catch (NumberFormatException ex) {
					k = 0;
				}
				new AsyncLearningFromDatabaseRequest(ResponsiveInterface, socket, table, k).start();
			});
			panelDB.addDownPanelButton("Save", (ae) -> {
				/*
				 * In questo caso non mi interessa che "windowFile" mostri la parte centrale,
				 * perchè non ho interesse di mostrare nulla all'interno di questa finestra.
				 * Pertanto, basta passare "null" nell'apposito campo
				 */
				if ((!panelDB.clusterOutput.getText().isEmpty())&&(!panelDB.clusterOutput.getText().equals("Error"))){
					JPanelCluster windowFile = new JPanelCluster ("Input Boxes", null, "Comands");
					JDialog dialogWindow = new JDialog();
					JPanel contentPane = new JPanel();
					contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
					windowFile.addDownPanelButton("Ok", (ae1) -> {
						String fileName = windowFile.getUpPanelField(FieldType.FileName).getText();
						new AsyncStoreInFileRequest(ResponsiveInterface, socket, fileName).start();
						dialogWindow.setVisible(false);
					});
					windowFile.addUpPanelField(FieldType.FileName);
					windowFile.addDownPanelButton("Cancel", (ae1) -> {
						dialogWindow.setVisible(false);
					});
					contentPane.add(windowFile);
					dialogWindow.add(contentPane);
					dialogWindow.setSize(300, 150);
					dialogWindow.setVisible(true);
				}
				else
					JOptionPane.showMessageDialog(null, "Ricavare il calcolo dei cluster prima di effettuare questa opzione", "Error", JOptionPane.ERROR_MESSAGE);
			});
			panelDB.addDownPanelButton("Save as PDF", (ae) -> {
				if ((!panelDB.clusterOutput.getText().isEmpty())&&(!panelDB.clusterOutput.getText().equals("Error"))) {
					Image img = ((ImageIcon)panelDB.plot.getIcon()).getImage();
					BufferedImage image = (BufferedImage)img;

					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setMultiSelectionEnabled(false);
					fileChooser.setFileFilter(new FileNameExtensionFilter("PDF file", "pdf"));

					if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						String fileName = file.getName();
						int i = fileName.indexOf('.');
						try {
							if (i >= 0 && fileName.substring(i + 1).equalsIgnoreCase("pdf"))
								PDFcreator(file.getAbsolutePath(), panelDB.clusterOutput.getText(), image);
							else
								PDFcreator(file.getAbsolutePath() + ".pdf", panelDB.clusterOutput.getText(), image);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
				else
					JOptionPane.showMessageDialog(null, "Ricavare il calcolo dei cluster prima di effettuare questa opzione", "Error", JOptionPane.ERROR_MESSAGE);
			});
			panelDB.addDownPanelButton("Clear Text", (ae) -> {
				panelDB.getUpPanelField(FieldType.table).setText("");
				panelDB.getUpPanelField(FieldType.k).setText("");
				panelDB.clusterOutput.setText("");
				panelDB.plot.setIcon(null);
			});
			tabbedPane.addTab("DB", null, panelDB, null);
			//-------------------PANEL FILE-----------------------------
			panelFile = new JPanelCluster("Input Boxes", "Clusters", "Comands");
			//-------------------PANEL FILE (upPanel)-------------------
			panelFile.addUpPanelField(FieldType.table);
			panelFile.addUpPanelField(FieldType.FileName);
			//-------------------PANEL FILE (downPanel)-----------------
			panelFile.addDownPanelButton("MINE", (ae) -> {
				String table = panelFile.getUpPanelField(FieldType.table).getText();
				String fileName = panelFile.getUpPanelField(FieldType.FileName).getText();
				new AsyncLearningFromFileRequest(ResponsiveInterface, socket, table, fileName).start();;
			});
			panelFile.addDownPanelButton("Save as PDF", (ae) -> {
				if ((!panelFile.clusterOutput.getText().isEmpty())&&(!panelFile.clusterOutput.getText().equals("Error"))) {
					Image img = ((ImageIcon)panelFile.plot.getIcon()).getImage();
					BufferedImage image = (BufferedImage)img;

					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setMultiSelectionEnabled(false);
					fileChooser.setFileFilter(new FileNameExtensionFilter("PDF file", "pdf"));

					if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						String fileName = file.getName();
						int i = fileName.indexOf('.');
						try {
							if (i >= 0 && fileName.substring(i + 1).equalsIgnoreCase("pdf"))
								PDFcreator(file.getAbsolutePath(), panelFile.clusterOutput.getText(), image);
							else
								PDFcreator(file.getAbsolutePath() + ".pdf", panelFile.clusterOutput.getText(), image);
							JOptionPane.showMessageDialog(null, "Cluster salvato nel percorso : \n" + file.getAbsolutePath(), "Information", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}		
				}
				else
					JOptionPane.showMessageDialog(null, "Ricavare il calcolo dei cluster prima di effettuare questa opzione", "Error", JOptionPane.ERROR_MESSAGE);
			});
			panelFile.addDownPanelButton("Clear Text Area", (ae) -> {
				panelFile.getUpPanelField(FieldType.table).setText("");
				panelFile.getUpPanelField(FieldType.FileName).setText("");
				panelFile.clusterOutput.setText("");
				panelFile.plot.setIcon(null);
			});
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
			if (!result.equals("Error")){
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
			tab.panelDB.addDownPanelButton("Exit", (ae) -> {
				this.destroy();
				System.exit(0);
			});
			tab.panelFile.addDownPanelButton("Exit", (ae) -> {
				this.destroy();
				System.exit(0);
			});
			getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
			setSize(550,650);
			getContentPane().add(tab);
			ResponsiveInterface = tab;

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Unable to connect to " + strHost + ":" + port + ".\n" + e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
			this.destroy();
			System.exit(0);
		}
	}
	
	/**
	 * Ricava un file PDF
	 * @param title percorso / nome del file PDF da creare
	 * @param text testo da inserire all'interno del file PDF
	 * @param image immagine da inserire all'interno del file PDF 
	 * @throws Exception
	 */
	private void PDFcreator (String title, String text, BufferedImage image ) throws Exception {

        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);

        PDFont pdfFont = PDType1Font.HELVETICA;
        float fontSize = 12;
        float leading = 1.5f * fontSize;

        PDRectangle mediabox = page.findMediaBox();
        float margin = 72;
        float startX = mediabox.getLowerLeftX() + margin/2;
        float startY = mediabox.getUpperRightY() - margin;
		float center = mediabox.getWidth() /2.0f;

        List<String> lines = new ArrayList<String>();
        int lastSpace = -1;
        while (text.length() > 0){
            int spaceIndex = text.indexOf('\n');
            if (spaceIndex < 0){
                lines.add(text);
                text = "";
            }
            else{
                String subString = text.substring(0, spaceIndex);
				if (lastSpace < 0)
					lastSpace = spaceIndex;
				else
					lastSpace = spaceIndex;
				lines.add(subString);
				text = text.substring(lastSpace).trim();
				lastSpace = -1;
            }
        }

        contentStream.beginText();
        contentStream.setFont(pdfFont, fontSize);
        contentStream.moveTextPositionByAmount(startX, startY + margin - center);
        for (String line: lines){
            contentStream.drawString(line);
            contentStream.moveTextPositionByAmount(0, -leading);
        }
        contentStream.endText(); 

        
        try {
            PDXObjectImage ximage = new PDPixelMap(doc, image);
            float scale = 0.75f;
            contentStream.drawXObject(ximage, startX + center - ximage.getWidth()/2, ximage.getHeight()+ startY - center - margin,
					                  ximage.getWidth()*scale, ximage.getHeight()*scale);
        } 
        
        catch (FileNotFoundException fnfex) {
            System.out.println("No image for you");
        }
        contentStream.close();
        doc.save(title);
        doc.close();
    }
}