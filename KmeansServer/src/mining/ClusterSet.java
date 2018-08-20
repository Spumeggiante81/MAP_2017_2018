package mining;

import java.io.Serializable;
import java.util.Arrays;

import data.Data;
import data.OutOfRangeSampleSize;
import data.Tuple;

public class ClusterSet implements Serializable {
	//Attributi
	private Cluster[] C;
	private int i = 0; //indica la posizione per memorizzare un nuovo cluster in C
	//private ArraySet a;

	//Metodi

	/**
	 * Costruttore della classe
	 * @throws OutOfRangeSampleSize 
	 * @param k
	 */
	ClusterSet(int k) throws OutOfRangeSampleSize{
		try{
			C = new Cluster[k];
			Arrays.fill(C, null);
		}
		catch(NegativeArraySizeException e){
			throw new OutOfRangeSampleSize ("k deve essere un intero positivo");
		}
	}

	/**
	 * Asseggna c a C[i]
	 * Incrementa i
	 * @param c
	 */
	void add(Cluster c){
		C[i]=c;
		i++;
	} 

	/**
	 * Restituisce C[i]
	 * @param i
	 * @return
	 */
	Cluster get(int i){
		return C[i];
	}

	/**
	 * Sceglie i centroidi, crea un cluster per ogni centroide e lo memorizza in C(ClusterSet)
	 * @param data
	 * @throws OutOfRangeSampleSize 
	 */
	public void initializeCentroids(Data data) throws OutOfRangeSampleSize{
		int centroidIndex[]=data.sampling(C.length); //restituisce la posizione di "0.9,1" e "2,2.2"
		for(int i=0;i<centroidIndex.length;i++){
			Tuple centroidI=data.getItemSet(centroidIndex[i]);
			this.add(new Cluster(centroidI));//memorizza il centroide
		}
	}

	/**
	 * Logica usata
	 * volevo associare a distanza un valore massimo in modo ciclando si sarebbe aggiornato con il valore minimo
	 * double distanza2=tuple.getDistance(C[i].getCentroid()); Calcolare la distanza fra l'oggetto riferito da Tuple 
	 * e quello riferito dal metodo getcentroid
	 * associare il valore al cluster che ha la distanza minima e restituirlo
	 * 
	 * Calcola la distanza tra la tupla riferita da tuple ed il centroide di ciascun cluster C
	 * @param tuple
	 * @return restituisce il Cluster più vicino sulla base della distanza calcolata da getDistance della classe Tuple
	 */
	Cluster nearestCluster(Tuple tuple)
	{
		/*
		 * Dato che la "distanza" massima ricavabile dal confronto dei centroidi con la tupla, corrisponderà al numero di elementi presenti nella tupla 
		 * 
		 * 		distanza = nElementi in tupla => nessun riscontro trovato con il centroide
		 * 		
		 * 		distanza = 0 => tutti gli elementi della tupla corrispondono con quelli presenti nel centroide
		 */
		double nearestDistance = tuple.getDistance(this.get(0).getCentroid());
		Cluster C = this.get(0);
		for(int i = 0 ; i < this.i ;i++)
		{
			//ricordando che getCentroid della classe Cluster restituisce il centroide in 
			//una tupla
			double distance = tuple.getDistance(this.get(i).getCentroid());
			if(distance < nearestDistance){
				C = this.get(i);
				nearestDistance = distance;
			}

		}
		return C;
	}

	/**
	 * Identifica e restituisce il cluster a cui la tupla rappresentante l'esempio identificato da id
	 * Restituisce null se la tupla non è inclusa in nessun cluster
	 * @param id
	 * @return
	 */
	public Cluster currentCluster(int id){
		for(int i=0;i<this.i;i++) 
		{
			if(C[i].contain(id)) 
				//C[i] è il riferimento del mio vettore di cluster
				//contain della classe Cluster verifica se la transazione è presente
				//restituisce il cluster [i] corrispondente
				return C[i];
		}
		return null;//restituisce null
	}

	/**
	 * Calcola il nuovo centroide per ciascun cluster in C
	 * @param data
	 */
	public void updateCentroids(Data data) {
		for (int i = 0; i < this.i; i++){
			this.get(i).computeCentroid(data);
		}
	}

	/**
	 * Restituisce una stringa che descrive lo stato di ciascun cluster in C
	 * @param data La collezione di dati su cui si riferiscono i Cluster da stampare 
	 * @return
	 */
	public String toString(Data data) {
		String str="";

		for(int i=0;i<C.length;i++){
			if(C[i]!=null){
				str+=i+":"+C[i].toString(data)+"\n";
			}
		}
		return str;
	}	
}


