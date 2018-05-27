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
	
	
	Item get(int i){//NON MI CONVINCE
		return item[i];
	}
	

	/**
	 * Memorizza c in nel vettore tuple
	 * @param c
	 * @param i
	 */
	void add(Item c, int i){
	 tuple[i]=(Tuple) c.value;
	}
	}

	
