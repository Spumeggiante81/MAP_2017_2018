package data;

import utility.ArraySet;

public abstract class Item {
	Attribute attribute;//attributo coinvolto nell'item
	Object value;//valore assegnato all'attributo
	
	/**
	 *Inizializza i valori dei membri attributo
	 * 
	 * @param attribute
	 * @param value
	 *
	 */
	Item(Attribute attribute, Object value){
		this.attribute=attribute;
		this.value=value;
	}
	
	/**
	 * 
	 * @return Restituisce attribute
	 */
	Attribute getAttribute()
	{
		return attribute;
	}
	
	/**
	 * 
	 * @return Restituisce value
	 */
	Object getValue()
	{
		return value;
	}
	
	/**
	 * Overriding del metodo toString
	 */
	public String toString()
	{
		return (String) value;
	}
	
	public abstract double distance(Object a);

	/**
	 * Modifica il valore dell'item, in base ai valori dello stesso tipo, presenti nelle tuple definite nella lista (clusteredData) 
	 * @param data Collezione di dati da cui ricavare le tuple
	 * @param clusteredData lista di indici su cui sono definite le tuple da usare
	 */
	public void update(Data data, ArraySet clusteredData) {
		value = data.computePrototype(clusteredData, attribute);
	}
}	
	
	
	
	

