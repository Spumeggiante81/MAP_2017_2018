package data;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import utility.ArraySet;

public class DiscreteAttribute extends Attribute implements Iterable<String> {
	private Set<String> values;
	
	/**
	 * Costruttore della classe; Oltre a definirne il nome e l'indice, esso conterrà una serie di valori "discreti" quali definiscono
	 * il dominio di tale attributo discreto (ergo, i valori accettabili)
	 * @param name nome dell'attributo
	 * @param index identificativo numerico
	 * @param values array di stringhe rappresentanti il dominio dell'attributo
	 */
	public DiscreteAttribute(String name, int index, String values[]) {
		super(name, index);
		List <String> list = (List<String>) Arrays.asList(values);
		this.values = new TreeSet<String>(list);
	}

	/**
	 * Indica il numero di valori discreti quali definiscono il dominio dell'attributo in analisi
	 * @return numero di valori discreti nel dominio
	 */
	int getNumberOfDistinctValues() {
		return values.size();
	}
	
	/**
	 * Calcola la frequenza di un dato valore (v) con quelli presenti nelle tuple definite nel set di id (idList). 
	 * In particolare, viene confrontato con i valori associati allo stesso attributo in analisi.
	 * @param data collezione di dati
	 * @param idList Set di id da confrontare con la stringa
	 * @param v valore da confrontare
	 * @return  frequenza di v nelle tuple 
	 */
	int frequency(Data data,ArraySet idList,String v)
	{
		int count = 0;
		/*
		 * ricavo la colonna dove sono definiti i valori appartenenti all'attributo in analisi
		 */
		int j = this.getIndex();
		/*
		 * Ricavo l'arraySet, dove contiene tutti gli indici (righe) di interesse
		 */
		int arraySet [] = idList.toArray();
		/*
		 * Ciclo per la lunghezza dell'arraySet (dato l'indice delle righe di nostro interesse sono definite al suo interno)
		 */
		for (int i = 0; i < arraySet.length; i++){
			/*
			 * Ricavo l'indice della riga da cui poter poi ricavare il valore da confrontare con v
			 */
			int riga = arraySet[i];
			String value = (String)data.getAttributeValue(riga, j);
			if (value.compareTo(v)==0) count++;
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<String> iterator() {
		return values.iterator();
	}
	
}
