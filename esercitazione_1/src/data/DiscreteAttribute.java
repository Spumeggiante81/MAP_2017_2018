package data;

public class DiscreteAttribute extends Attribute {
	String values[]; //array di oggetti String uno per ciascun valore del dominio discreto
	
	/**
	 * @param name2 nome dell'attributo
	 * @param index2 identificativo numerico
	 * @param values array di stringhe rappresentanti il dominio dell'attributo
	 */
	public DiscreteAttribute(String name2, int index2, String values[]) {
		super(name2, index2);
		this.values = values;
	}

	/**
	 * @return dim dimensione del vettore
	 */
	int getNumberOfDistinctValues() {
		int contavalues=0; //dimensione del vettore 
		     while(values[contavalues]!=null) 
		    	 contavalues+=1;
		    return contavalues; 
		
	}
	/**
	 * //restituisce un valore in posizione i di values
	 * @param i idice della posizione del vettore vales[]
	 * @return val[i] restituisce un valore in posizione i di values
	 */
	String getValues(int i) {  
		return values[i]; 
		
	}
	
	

	
	
}
