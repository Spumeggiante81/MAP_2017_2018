package data;

public abstract class Attribute {	
	protected String name; //nome simbolico dell'attributo
	protected int index;   //identificativo numerico dell'attributo
	
	/**
<<<<<<< HEAD
	 * <b> Attribute </b>
	 * 
	 * @param name
	 * @param index
=======
	 * Costruttore della classe
	 * @param name nome che descrive l'attributo
	 * @param index indice associato all'attributo
>>>>>>> 03be0ed49ef4611ccec241c5f15e846377551dbf
	 */
	
	public Attribute(String name, int index) {
		this.name = name;
		this.index = index;
	}
	
	/**
<<<<<<< HEAD
	 * <b> getName </b>
	 * @return <b> name </b> restituisce il nome dell'attributo
=======
	 * Ritorna il nome dell'attributo in analisi
	 * @return nome attributo
>>>>>>> 03be0ed49ef4611ccec241c5f15e846377551dbf
	 */
	String getName()
	{
		return name;
	}
	
	/**
<<<<<<< HEAD
	 * ge
=======
	 * Ritorna l'indice dell'attributo in analisi
>>>>>>> 03be0ed49ef4611ccec241c5f15e846377551dbf
	 * @return restituisce identificativo numerico dell'attributo
	 */
	int getIndex()
	{
		return index;
	}
	
	/**
	 * Ritorna in formato String i valori contenuti nell'attributo in analisi
	 */
	public String toString()
	{
		return name;
	}
	
	
	
	
	
}
