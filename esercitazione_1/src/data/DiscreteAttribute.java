package data;

public class DiscreteAttribute extends Attribute {
	String values[]; //array di oggetti String uno per ciascun valore del dominio discreto

	/**
	 * Costruttore della classe; Oltre a definirne il nome e l'indice, esso conterrà una serie di valori "discreti" quali definiscono
	 * il dominio di tale attributo discreto (ergo, i valori accettabili)
	 * @param name nome dell'attributo
	 * @param index identificativo numerico
	 * @param values array di stringhe rappresentanti il dominio dell'attributo
	 */
	public DiscreteAttribute(String name, int index, String values[]) {
		super(name, index);
		this.values = values;
	}

	/**
	 * Indica il numero di valori discreti quali definiscono il dominio dell'attributo in analisi
	 * @return numero di valori discreti nel dominio
	 */
	int getNumberOfDistinctValues() {
		return values.length;
	}

	/**
	 * restituisce un valore nella i-esima posizione
	 * @param i posizione del valore discreto contenuto all'interno dell'attributo in analisi
	 * @return restituisce il valore discreto trovato
	 */
	String getValues(int i) {  
		return values[i]; 
	}	
}
