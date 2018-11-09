package server;

import data.Attribute;
import data.Data;
import data.DiscreteAttribute;
import data.OutOfRangeSampleSize;
import data.Tuple;
import database.DatabaseConnectionException;
import mining.Cluster;
import mining.ClusterSet;
import mining.KMeansMiner;

import java.net.*;
import java.sql.SQLException;
import java.text.DecimalFormat;
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
	private Grafico grafico;

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
	 * @throws ClassNotFoundException nel caso la classe ricevuta non vi è riconosciuta
	 * @throws IOException nel caso si verifichi un errore in fase di lettura
	 */
	private static Object readObject(Socket socket) throws ClassNotFoundException, IOException{
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

	/**
	 * Riscrive il metodo run della superclasse Thread al fine di gestire le
	 * richieste del client
	 */
	public void run(){//non poteamo mettere in un throws per gestire le eccezioni?
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
				case 4:
					socket.close();
					break;
				default:
					// Nel caso venga selezionata un'operazione non supportata, si esce
					System.out.println("Scelta errata " + choice + " scelta corretta da 1 a 3");
					break;
				}

			} 
			catch (IOException | ClassNotFoundException e) {
				System.out.println(e.getMessage());
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
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
	 * @param matTuple 
	 * @throws IOException
	 */
	private void learningFromDbTable (Socket socket) throws IOException {
		
		int numeroRighe,k,numIter;
		double [][] matTuple = null;
		ClusterSet clusterSet;
		Tuple t, centroid;
		Cluster cluster, clusterVet;
		double distanza,idCluster;
		DecimalFormat distanza1 = new DecimalFormat("0.00");
		
		
		try{
			if (!data.equals(null)){
				k = (int)readObject(socket);
				kmeans = new KMeansMiner(k);
				numIter = kmeans.kmeans(data);
				
				if /*(false)*/ (true){
					//TODO : ricavare la matrice di coppie "idCluster - distanza", per ciascuna tupla
					//recuperarli da ciò che abbiamo
					//ricordo che tale matrice deve essere di tipo double, vedi riga 171

					//Ricava il numero di tuple in data tramite il metodo getNumberOfExample e 
					//lo conserva nella variabile numeroRighe 
					numeroRighe = data.getNumberOfExamples();
					//Matrice di supporto alla stampa del grafico
					//il numero di righe rappresenta le tuple in data
					matTuple = new double[numeroRighe][2];
					System.out.println("Righe "+ numeroRighe);

					for(int  i=0;i<numeroRighe;i++){
						//Restituisce un oggetto di tuple che modella come sequenza di coppie Attributo-valore
						t = data.getItemSet(i);
						clusterSet = this.kmeans.getC();
						//clusterSet da dove lo ricavi?!
						cluster= clusterSet.nearestCluster(t); //modificata la visibilità a public di nearestCluster
						System.out.println("CLUSTER "+cluster.toString());
						centroid = cluster.getCentroid(); //modificata la visibilità a public di getcentroid
						//Se alla matrice non passi dei valori di tipo Double, non sarà in grado di creare il grafico.
						//e dato che cluster è di tipo, guardacaso, Cluster, non riuscirà nell'intento ora come ora.
						//Pertanto conviene definire un identificatore di tipo double per ciascuno di questi cluster
						distanza = t.getDistance(centroid);
						idCluster = 0;

						for (int j=0; j<k;j++){
							//Restituisce il Cluster in posizione i
							clusterVet  = clusterSet.get(j);
							//Confronta il clustervet, cluster del vettore di Cluster, 
							//con il cluster più vicino alla tupla sulla base della distanza calcolata
							if(clusterVet.equals(cluster)){
								idCluster = j;
								break;
							}
						}
						//Assegna alla matrice la distanza ed il Cluster associato a tale distanza
						matTuple[i][1] = distanza;
						matTuple[i][0] = idCluster;

						grafico = new Grafico("Tuple",matTuple);
					}
					grafico = new Grafico("Tuple",matTuple);
					//Aggiunto per verifica stampa matrice

					for(int i=0;i<numeroRighe;i++){
						for(int j=0;j<2;j++)
							System.out.print( matTuple[i][j] + " ");
						System.out.println();
					}
					grafico = new Grafico("Tuple",matTuple);
					grafico.setSize(400,400);
					grafico.setVisible(true);
				}//<<<
				
				// inserire la porzione di codice per la generazione del grafico fino all'interno della parentesi graffa riportato qui sopra
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