package database;

import java.util.ArrayList;
import java.util.List;


/**Modella la transazione letta dalla base di dati**/

public class Example implements Comparable<Example>{
	private List<Object> example=new ArrayList<Object>();

	/**
	 * Aggiunge un valore di tipo Object
	 * @param o
	 */
	public void add(Object o){
		example.add(o);
	}

	/**
	 * 
	 * @param i
	 * @return Restituisce un Object in corrispondenza di un parametro di tipo intero in input
	 */
	public Object get(int i){
		return example.get(i);
	}

	public int compareTo(Example ex) {

		int i=0;
		for(Object o:ex.example){
			if(!o.equals(this.example.get(i)))
				return ((Comparable)o).compareTo(example.get(i));
			i++;
		}
		return 0;
	}

	/**
	 * Restituisce una stringa
	 */
	public String toString(){
		String str="";
		for(Object o:example)
			str+=o.toString()+ " ";
		return str;
	}

}