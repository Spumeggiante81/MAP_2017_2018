<<<<<<< HEAD
package
data;

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
	Attribute getValue()
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
	
	
	
	
	
	
=======
package data;

public abstract class Item {

>>>>>>> e658db28ea828e470d7643d883157b073d740c9d
}