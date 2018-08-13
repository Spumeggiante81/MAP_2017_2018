package mining;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import data.Data;
import data.OutOfRangeSampleSize;

public class KMeansMiner {

	ClusterSet C;

	/**
	 * Costruttore della classe
	 * @param k numero di Cluster da ricavare
	 * @throws OutOfRangeSampleSize 
	 */
	public KMeansMiner (int k) throws OutOfRangeSampleSize{ 
		this.C = new ClusterSet (k);
	}
	
    /**
     * Carica il ClusterSet depositato sul file indicato come parametro
     * @param fileName nome del file riferito al dato ClusterSet
     * @throws IOException
     * @throws ClassNotFoundException
     */
	public KMeansMiner (String fileName) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
        C = (ClusterSet) in.readObject();
        in.close();
    }
	
	/**
	 * Salva il contenuto del ClusterSet in un file .dmp
	 * @param fileName nome del file riferito al dato Cluster
	 * @throws IOException
	 */
    public void salva(String fileName) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
        out.writeObject(C);
        out.close();
    }
	
	/**
	 * Ritorna il set di Cluster
	 * @return
	 */
	public ClusterSet getC(){
		return C;
	}
	
	/**
	 * esegue il calcolo dei Cluster, tramite l'algoritmo k-means
	 * @param data Collezione di dati da cui ricavare le tuple
	 * @return numero di transazioni necessarie per ricavare i Cluster
	 * @throws OutOfRangeSampleSize qualora il numero di Cluster (k) passato come parametro non sia valido
	 */
	public int kmeans(Data data) throws OutOfRangeSampleSize{
		int numberOfIterations=0;
		//STEP 1
		C.initializeCentroids(data);
		boolean changedCluster=false;
		do{
			numberOfIterations++;
			//STEP 2
			changedCluster=false;
			for(int i=0;i<data.getNumberOfExamples();i++){
				Cluster nearestCluster = C.nearestCluster( data.getItemSet(i));
				Cluster oldCluster=C.currentCluster(i);
				boolean currentChange=nearestCluster.addData(i);
				if(currentChange)
					changedCluster=true;
				//rimuovo la tupla dal vecchio cluster
				if(currentChange && oldCluster!=null)
					//il nodo va rimosso dal suo vecchio cluster
					oldCluster.removeTuple(i);
			}
			//STEP 3
			C.updateCentroids(data);
				
		}
		while(changedCluster);
		return numberOfIterations;
	}
}