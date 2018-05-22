package data;

public abstract class Attribute {	
	protected String name; //nome simbolico dell'attributo
	protected int index;   //identificativo numerico dell'attributo
	
	/**
	 * <b> Attribute </b>
	 * 
	 * @param name
	 * @param index
	 */
	
	public Attribute(String name, int index) {
		this.name = name;
		this.index = index;
	}
	
	/**
	 * <b> getName </b>
	 * @return <b> name </b> restituisce il nome dell'attributo
	 */
	String getName()
	{
		return name;

	}
	
	/**
	 * ge
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
