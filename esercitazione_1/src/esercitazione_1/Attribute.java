package esercitazione_1;

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

}
