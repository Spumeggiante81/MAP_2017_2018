package data;

import java.io.Serializable;

public abstract class Attribute implements Serializable{	
	protected String name; //nome simbolico dell'attributo
	protected int index;   //identificativo numerico dell'attributo
	
	/**
	 * Costruttore della classe
	 * @param name nome che descrive l'attributo
	 * @param index indice associato all'attributo
	 */
	
	public Attribute(String name, int index) {
		this.name = name;
		this.index = index;
	}
	
	/**
	 * Ritorna il nome dell'attributo in analisi
	 * @return nome attributo
	 */
	String getName()
	{
		return name;
	}
	
	/**
	 * Ritorna l'indice dell'attributo in analisi
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