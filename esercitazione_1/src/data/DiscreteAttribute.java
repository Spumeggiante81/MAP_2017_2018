package data;

public class DiscreteAttribute extends Attribute {
	String values[]; //array di oggetti String uno per ciascun valore del dominio discreto
	
	/**
	 * nome dell'attributo, identificativo numerico dell'attributo, array di stringhe rappresentanti il dominio dell'attributo
	 * @param name2
	 * @param index2
	 * @param values
	 */
	public DiscreteAttribute(String name2, int index2, String values[]) {
		super(name2, index2);
		this.values = values;
	}

	int getNumberOfDistinctValues() {
		int contavalues=0; //dimensione del vettore 
		     while(values[contavalues]!=null) 
		    	 contavalues+=1;
		    return contavalues; 
		
	}
	/**
	 * //restituisce un valore in posizione i di values
	 * @param i
	 * @return
	 */
	String getValues(int i) {  //restituisce un valore in posizione i di values
		return values[i]; 
		
	}
	
	

	
	
}
