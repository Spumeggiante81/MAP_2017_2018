package data;

/**
 * Rappresenta una tupla come una sequenza di coppie attributo-valore
 * @author Dany
 *
 */
public class Tuple {
	private Item[] tuple;
	
	/**
	 * Costruttore della classe tuple che ne definisce la dimensione
	 * @param size
	 */
	Tuple(int size){
		tuple=new Item[size];
		
	}
	
	/**
	 * 
	 * @return restituisce il numero di tuple
	 */
	int getLength(){
		return tuple.length;
		
	}
	
	
	Item get(int i){
		return  tuple[i];
	}
	
	
	/**
	 * Determina la distanza fra la tupla riferita da obj e quella riferita da this
	 * @param obj 
	 * @return 
	 */
	double getDistance(Tuple obj){
		double distance = 0;
		/*
		 * Si parte dal presupposto che:
		 * 	
		 * 		-1 : entrambe le tuple in analisi abbiano la stessa dimensione;
		 * 		-2 : le posizioni degli attributi all'interno delle tuple corrispondano.
		 */
		for (int i = 0; i < this.getLength(); i++){
			distance += this.get(i).distance(obj.get(i));
		}
		return distance;
	}
	
	/** 
	 * 
	 * @param data
	 * @param clusteredData
	 * @return
	 */
	double avgDistance(Data data,int clusteredData[]){
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
	 * Memorizza c in tuple[i]
	 * @param c
	 * @param i
	 */
	public void add(Item c, int i) {
		tuple[i]=c;
		
	}
	

}
