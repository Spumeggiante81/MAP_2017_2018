package data;

import java.util.Iterator;
import java.util.Set;

class ContinuosAttribute extends Attribute 
{
	private double max; 
	private double min;  
	 
	
	/**
	 * Costruttore della classe; Oltre a definirne il nome e l'indice, esso conterr� un intervallo [min,max] quale definisce
	 * il dominio dei valori quali rappresentano tale attributo continuo (ergo, i valori accettabili)
	 * @param name nome che descrive l'attributo continuo
	 * @param index indice associato all'attributo continuo
	 * @param min parte sinistra dell'intervallo all'interno del cui � definitio il dominio dell'attributo continuo
	 * @param max parte destra dell'intervallo all'interno del cui � definitio il dominio dell'attributo continuo
	 */
	public ContinuosAttribute(String name, int index, double min, double max) {
		super(name, index);
		this.max = max;
		this.min = min;
	}


	/**
	 * rappresentano gli estremi dell'intervallo di valori che l'attributo pu� realmente assumere
	 * @return valore normalizzato 
	 */
    double getScaledValue(double v) {
    	return(v-min)/(max-min);  
    }
    
    /**
     * Implementazione della funzione distance per tipo continuo
     * @param a
     * @return
     */
	public double distance(Object a){
		return equals(a) == true ? 0 : 1;
	}

	public double getScaledValue(ContinuosAttribute valore) {
		// TODO Auto-generated method stub
		return 0;
	}


	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}


	public int frequency(Data data, Set<Integer> idList, String attributeValue) {
		// TODO Auto-generated method stub
		return 0;
	}
}
