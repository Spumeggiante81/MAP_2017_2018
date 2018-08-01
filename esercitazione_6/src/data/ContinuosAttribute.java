package data;

import java.util.Iterator;
import java.util.Set;

class ContinuosAttribute extends Attribute 
{
	private double max; 
	private double min;  
	 
	
	/**
	 * Costruttore della classe; Oltre a definirne il nome e l'indice, esso conterrà un intervallo [min,max] quale definisce
	 * il dominio dei valori quali rappresentano tale attributo continuo (ergo, i valori accettabili)
	 * @param name nome che descrive l'attributo continuo
	 * @param index indice associato all'attributo continuo
	 * @param min parte sinistra dell'intervallo all'interno del cui è definitio il dominio dell'attributo continuo
	 * @param max parte destra dell'intervallo all'interno del cui è definitio il dominio dell'attributo continuo
	 */
	public ContinuosAttribute(String name, int index, double min, double max) {
		super(name, index);
		this.max = max;
		this.min = min;
	}


	/**
	 * rappresentano gli estremi dell'intervallo di valori che l'attributo può realmente assumere
	 * @return valore normalizzato 
	 */
    double getScaledValue(double v) {
    	return(v-min)/(max-min);  
    }
}
