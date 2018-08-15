import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;


import keyboardinput.Keyboard;



public class MainTest {

	/**
	 * @param args
	 */
	private static String DEFAULT_HOST = "localhost";
	private static int DEFAULT_PORT = 8080;
	/*
	private ObjectOutputStream out;
	private ObjectInputStream in ;
	*/
	Socket socket;
	
	public MainTest(String ip, int port) throws IOException{
		InetAddress addr = InetAddress.getByName(ip); //ip
		System.out.println("addr = " + addr);
		socket = new Socket(addr, port); //Port
		System.out.println(socket);
		
		/*
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		*/
	}
	
    /**
     * Si occupa della ricezione sicura di un oggetto via socket
     * @param socket la quale ricevere l'oggetto
     * @return oggetto ricevuto dal socket specificato
     * @throws ClassNotFoundException nel caso la classe ricevuta non vi è riconosciuta
	 * @throws IOException nel caso si verifichi un errore in fase di lettura
     */
	private static Object readObject(Socket socket) throws ClassNotFoundException, IOException {
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
	private void storeTableFromDb() throws SocketException,ServerException,IOException,ClassNotFoundException{
		writeObject(socket,0);
		System.out.print("Nome tabella:");
		String tabName=Keyboard.readString();
		writeObject(socket,tabName);
		String result = (String)readObject(socket);
		if(!result.equals("OK"))
			throw new ServerException(result);
		
	}
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
	
	private void storeClusterInFile() throws SocketException,ServerException,IOException,ClassNotFoundException{
		writeObject(socket,2);
		System.out.println("Nome File in cui salvare i cluster ricavati:");
		String fileName = Keyboard.readString();
		writeObject(socket,fileName);
		String result = (String)readObject(socket);
		if(!result.equals("OK"))
			 throw new ServerException(result);
		
	}
	public static void main(String[] args) {
		/*
		String ip=args[0];
		int port=new Integer(args[1]).intValue();
		*/
		String ip = DEFAULT_HOST;
		int port = DEFAULT_PORT;
		MainTest main=null;
		try{
			main=new MainTest(ip,port);
		}
		catch (IOException e){
			System.err.println(e);
			return;
		}
		do{
			int menuAnswer=main.menu();
			switch(menuAnswer)
			{
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
				
					while(true){
						try{
							main.storeTableFromDb();
							break; //esce fuori dal while
						}
						
						catch (SocketException e) {
							System.err.println(e);
							return;
						}
						catch (FileNotFoundException e) {
							System.err.println(e);
							return;
							
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
					} //end while [viene fuori dal while con un db (in alternativa il programma termina)
						
					char answer='y';//itera per learning al variare di k
					do{
						try
						{
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
					while(answer=='y');
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
	}



