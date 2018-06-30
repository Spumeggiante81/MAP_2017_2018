package data;

import java.util.Arrays;

public class ClusterSet {
	//Attributi
	private Cluster[] C;
	private int i = 0; //indica la posizione per memorizzare un nuovo cluster in C
	//private ArraySet a;
	
	//Metodi
	
	/**
	 * Costruttore della classe
	 * @param k
	 */
	ClusterSet(int k){
		C = new Cluster[k];
		Arrays.fill(C, null);
	}
	
	/**
	 * Inserisce un nuovo Cluster nel Set
	 * @param c Cluster da aggiungere
	 */
	void add(Cluster c){
			C[i]=c;
			i++;
		} 
	
	/**
	 * Ritorna il Cluster presente nella i-esima posizione nel Set
	 * @return Cluster
	 */
	Cluster get(int i){
		return C[i];
	}
	
	/**
	 * Sceglie i centroidi, crea un cluster per ogni centroide e lo memorizza in C(ClusterSet)
	 * @param data
	 */
	public void initializeCentroids(Data data){
		int centroidIndex[]=data.sampling(C.length); //restituisce la posizione di "0.9,1" e "2,2.2"
		for(int i=0;i<centroidIndex.length;i++){
			Tuple centroidI=data.getItemSet(centroidIndex[i]);
			this.add(new Cluster(centroidI));//memorizza il centroide
		}
	}
		
	
	/**
	 * Tra i Cluster presenti nel Set, individua e restituisce quello più "vicino" alla tupla passata per paramentro
	 * @param tuple Tupla da cui ricercare il Cluster associabile
	 * @return Cluster
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
		double nearestDistance = tuple.getLength();
		Cluster C = null;
		for(int i = 0 ; i < this.i ;i++)
		{
			//ricordando che getCentroid della classe Cluster restituisce il centroide in 
			//una tupla
			double distance = tuple.getDistance(this.C[i].getCentroid());
			if(distance < nearestDistance){
				C = this.C[i];
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


