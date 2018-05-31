package data;

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
		return (Attribute) value;
	}
	
	/**
	 * Overriding del metodo toString
	 */
	public String toString()
	{
		return (String) value;
	}
	
	/**
	 * Classe astratta che calcola la distanza per item discreto e continuo
	 * @param a
	 * @return
	 */
	public abstract double distance(Object a);
}	
	
	
	
	

