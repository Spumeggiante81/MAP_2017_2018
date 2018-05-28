package data;

/**
 * Rappresenta una tupla come una sequenza di coppie attributo-valore
 * @author Dany
 *
 */
public class Tuple {
	private Tuple[] tuple;
	private Item[] item;
	
	/**
	 * Costruttore della classe tuple che ne definisce la dimensione
	 * @param size
	 */
	Tuple(int size){
		tuple=new Tuple[size];
		
	}
	
	/**
	 * 
	 * @return restituisce il numero di tuple
	 */
	int getLength(){
		return tuple.length;
		
	}
	
	
	Item get(int i){
		return item[i];
	}
	

	/**
	 * Memorizza c nel vettore tuple
	 * @param c
	 * @param i
	 */
	void add(Item c, int i){
	 tuple[i]=(Tuple) c.value;
	}
	
	/**
	 * Determina la distanza fra la tupla riferita da obj e quella riferita da this
	 * @param obj 
	 * @return 
	 */
	double getDistance(Tuple obj){
		double distance;
		
		//Non lo inserisco in un ciclo in quanto viene richiamato in un ciclo
		distance=obj.getDistance(obj)-this.getDistance(obj);
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

	public void add(DiscreteItem discreteItem, int string, int i) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	

}
