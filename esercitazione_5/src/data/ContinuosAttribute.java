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
    
    /**
     * Implementazione della funzione distance per tipo continuo
     * @param a
     * @return
     */
	public double distance(Object a){
		return equals(a) == true ? 0 : 1;
	}

	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}


	public int frequency(Data data, Set<Integer> idList, double attributeValue) {
		int count = 0;
		/*
		 * ricavo la colonna dove sono definiti i valori appartenenti all'attributo in analisi
		 */
		int j = this.getIndex();
		/*
		 * Ricavo l'arraySet, dove contiene tutti gli indici (righe) di interesse
		 */
		int arraySet [] = idList.stream().mapToInt(Integer::intValue).toArray();
		/*
		 * Ciclo per la lunghezza dell'arraySet (dato l'indice delle righe di nostro interesse sono definite al suo interno)
		 */
		for (int i = 0; i < arraySet.length; i++){
			/*
			 * Ricavo l'indice della riga da cui poter poi ricavare il valore da confrontare con v
			 */
			int riga = arraySet[i];
			double value = (double)data.getAttributeValue(riga, j);
			if (value != attributeValue) count++;
		}
		return count;
	}
}
