package server;

import data.Attribute;
import data.Data;
import data.DiscreteAttribute;
import data.OutOfRangeSampleSize;
import database.DatabaseConnectionException;
import mining.KMeansMiner;

import java.net.*;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.io.*;


/**
 * Classe che gestisce una singola connessione da parte di un client
 */
class ServerOneClient extends Thread {
    private Socket socket;
    private KMeansMiner kmeans;
    private Data data;


    /**
     * Inizializza gli attributi socket, in ed out. Avvia il thread.
     * @param s
     * @throws IOException
     */
    ServerOneClient(Socket s) throws IOException {
        socket = s;
    }

    /**
     * Si occupa della ricezione sicura di un oggetto via socket
     * @param socket la quale ricevere l'oggetto
     * @return oggetto ricevuto dal socket specificato
     * @throws ClassNotFoundException nel caso la classe ricevuta non vi � riconosciuta
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
     * @param socket la quale ricever� l'oggetto
	 * @param o oggetto da inviare
	 * @throws IOException nel caso si verifichi un errore in fase di scrittura
	 */
	private static void writeObject(Socket socket, Object o) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		out.writeObject(o);
		out.flush();
	}

    /**
     * Riscrive il metodo run della superclasse Thread al fine di gestire le
     * richieste del client
     */
    public void run() {
    	while (socket.isConnected()) {
            try {
            	int choice = (int)readObject(socket);
                switch (choice) {
                	case 0: // STORE FROM DB
                		storeTableFromDB(socket);
                        break;
                	case 1: //LEARNING FROM TABLE
                		learningFromDbTable(socket);
                		break;
                    case 2: // STORE IN FILE
                    	storeClusterInFile(socket);
                        break;
                    case 3: //LEARNING FROM FILE
                    	learningFromFile(socket);
                    	break;
                    default:
                       	// Nel caso venga selezionata un'operazione non supportata, si esce
                        break;
                }
                
            } 
            catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }

    /**
     * Ricava la tabella, richiesta dal client, dal DB
     * @param socket del client
     * @throws IOException 
     */
    private void storeTableFromDB(Socket socket) throws IOException {
    	try{
    		String tableName = (String)readObject(socket);
    		data = new Data(tableName);
        	writeObject(socket,"OK");
    	}
    	catch(IOException e){
    		writeObject(socket,e.getMessage());
    	}
    	catch(ClassNotFoundException e){
    		writeObject(socket,e.getMessage());
    	}
    	catch(DatabaseConnectionException e){
    		writeObject(socket,e.getMessage());
    	}
    	catch(SQLException e){
    		writeObject(socket,e.getMessage());
    	}
    }
    
    /**
     * Rileva i cluster della collezione di dati
     * @param socket
     * @throws IOException
     */
    private void learningFromDbTable (Socket socket) throws IOException {
    	try{
    		if (!data.equals(null)){
    			int k = (int)readObject(socket);
    			kmeans = new KMeansMiner(k);
    			int numIter = kmeans.kmeans(data);
    			writeObject(socket,"OK");
    			writeObject(socket,numIter);
    			writeObject(socket,kmeans.getC().toString(data));
    		}
    		else
    			writeObject(socket,"Nessuna collezione di dati selezionata");
    	}
    	catch (ClassNotFoundException e){
    		writeObject(socket, e.getMessage());
    	}
    	catch (OutOfRangeSampleSize e){
    		writeObject(socket, e.getMessage());
    	}
    	catch(IOException e){
    		writeObject(socket, e.getMessage());
    	}
    }
    
    /**
     * Salva i cluster rilevati in un file .dmp
     * @param socket
     * @throws IOException
     */
    private void storeClusterInFile(Socket socket) throws IOException{
    	try{
    		String fileName = (String)readObject(socket);
    		kmeans.salva(fileName);
    		writeObject(socket,"OK");
    	}
    	catch(ClassNotFoundException e){
    		writeObject(socket, e.getMessage());
    	}
    	catch(IOException e){
    		writeObject(socket, e.getMessage());
    	}
    }
    
    /**
     * Ricava i cluster all'interno del file specificato dal client, e glieli rimanda
     * @param socket
     * @throws IOException
     */
    private void learningFromFile(Socket socket) throws IOException{
		try{
			String tableName = (String)readObject(socket);
			String fileName = (String)readObject(socket);
			data = new Data(tableName);
			kmeans = new KMeansMiner(fileName + ".dmp");
			writeObject(socket,"OK");
			writeObject(socket,kmeans.getC().toString(data));
		}
    	catch(IOException e){
    		writeObject(socket,e.getMessage());
    	}
    	catch(ClassNotFoundException e){
    		writeObject(socket,e.getMessage());
    	}
    	catch(DatabaseConnectionException e){
    		writeObject(socket,e.getMessage());
    	}
    	catch(SQLException e){
    		writeObject(socket,e.getMessage());
    	}
    }
}
