package data;

import java.io.Serializable;

/**
 * Rappresenta una tupla come una sequenza di coppie attributo-valore
 * @author Dany
 *
 */
public class Tuple implements Serializable{
	private Item[] tuple;
	
	/**
	 * Costruttore della classe tuple che ne definisce la dimensione
	 * @param size numero di elementi che definiscono la tupla
	 */
	Tuple(int size){
		tuple=new Item[size];
		
	}
	
	/**
	 * 
	 * @return restituisce il numero di tuple
	 */
	public int getLength(){
		return tuple.length;
		
	}
	
	/**
	 * Restituisce l'elemento Item nella i-esima posizione
	 * @param i indice dell'elemento Item
	 * @return Item
	 */
	public Item get(int i){
		return  tuple[i];
	}
	
	
	/**
	 * Determina la distanza fra la tupla riferita da obj e quella riferita da this
	 * @param obj Tupla da confrontare con quello in analisi
	 * @return 0, se le tuple corrispondono per tutti i valori, =! 0 altrimenti
	 */
	public double getDistance(Tuple obj){
		double distance = 0;
		/*
		 * Si parte dal presupposto che:
		 * 	
		 * 		-1 : entrambe le tuple in analisi abbiano la stessa dimensione;
		 * 		-2 : le posizioni degli attributi all'interno delle tuple corrispondano.
		 */
		//System.out.println(obj + " - " + getLength());
		for (int i = 0; i < this.getLength(); i++){
			//distance += this.get(i).distance(obj.get(i));			
			Item item = this.get(i);
			if (item != null){
				if (item instanceof ContinuosItem)
					distance += ((ContinuosItem)item).distance(obj.get(i));
				else
					distance += ((DiscreteItem)item).distance(obj.get(i));
			}
		}
		return distance;
	}
	
	/** 
	 * Calcola la media delle distanze di tutte le tuple passati nel parametro
	 * @param data collezione di dati da cui ricavare le tuple
	 * @param clusteredData lista di indici delle tuple da usare per ricavare la media
	 * @return
	 */

	public double avgDistance(Data data,int clusteredData[]){
		double p=0.0, sumD=0.0;
		
		for(int i=0;i<clusteredData.length;i++)
		{
			double d=getDistance(data.getItemSet(clusteredData[i]));
			sumD+=d;
		}
		p=sumD/clusteredData.length;
		return p;
	}

	/**
	 * Aggiunge un elemento Item nella posizione i-esima della tupla in analisi
	 * @param c Item da aggiungere
	 * @param i posizione su cui memorizzare l'item nella tupla
	 */
	public void add(Item c, int i) {
		tuple[i]=c;
		
	}
	

}
