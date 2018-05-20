package data;

public abstract class Attribute {	
	protected String name; //nome simbolico dell'attributo
	protected int index;   //identificativo numerico dell'attributo
	
	/**
	 * Costruttore della classe
	 * inizializza i valori name, index
	 */
	
	public Attribute(String name, int index) {
		this.name = name;
		this.index = index;
	}
	
	/**
	 * 
	 * @return restituisce il nome dell'attributo
	 */
	String getname()
	{
		return name;

	}
	
	/**
	 * 
	 * @return restituisce identificativo numerico dell'attributo
	 */
	int getIndex()
	{
		return index;

	}
	
	/**
	 * Overriding metodo toString
	 */
	public String toString()
	{
	
		return name;

	}
	
	
	
	
	
}
