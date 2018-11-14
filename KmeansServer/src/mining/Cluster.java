package mining;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import data.Data;
import data.Tuple;

//Classe definita dalla prof insieme ai suoi metodi
public class Cluster implements Serializable{
	private Tuple centroid;

	private Set<Integer> clusteredData = new HashSet<Integer>(); 

	/**
	 * Costruttore di classe
	 * @param centroid
	 */
	Cluster(Tuple centroid){
		this.centroid=centroid;
	}

	/**
	 * Ricava il centroide del Cluster in analisi
	 * @return
	 */
	public Tuple getCentroid(){
		return centroid;
	}

	/**
	 * Modifica il centroide in base alle tuple ad esso associate
	 * @param data Collezione di dati da cui ricavare le tuple
	 */
	void computeCentroid(Data data){
		for(int i=0;i<centroid.getLength();i++){
			centroid.get(i).update(data,clusteredData);
		}
	}

	/**
	 * Aggiunge una tupla associata al cluster
	 * @param id indice su cui � riferito la tupla
	 * @return true se l'operazione ha "modificato" la struttura dati, false altrimenti (ovvero, qualora fosse gi� presente e quindi non serve aggiungerlo nuovamente)
	 */
	boolean addData(int id){
		return clusteredData.add(id);	
	}

	/**
	 * Verifica se una tupla � associata o meno al cluster in analisi
	 * @param id indice su cui � riferito la tupla
	 * @return true se la tupla � associata al cluster, false altrimenti
	 */
	boolean contain(int id){
		return clusteredData.contains(id);
	}

	/**
	 * Rimuove l'associazione della data tupla con il cluster
	 * @param id indice su cui � riferito la tupla
	 */
	void removeTuple(int id){
		clusteredData.remove(id);
	}

	public String toString(){
		String str="Centroid=(";
		for(int i=0;i<centroid.getLength();i++)
			str+=" "+centroid.get(i);
		str+=")";
		return str;

	}

	public Object [] toString(Data data){
		Object [] result = new Object [2];
		double distance = 0;
		String str="Centroid=(";
		for(int i=0;i<centroid.getLength();i++)
			str+=centroid.get(i)+ " ";
		str+=")\nExamples:\n";
		int array [] = clusteredData.stream().mapToInt(Integer::intValue).toArray();
		double [] distances = new double [array.length];
		for(int i=0;i<array.length;i++){
			str+="[";
			for(int j=0;j<data.getNumberOfExplanatoryAttributes();j++)
				str+=data.getAttributeValue(array[i], j)+" ";
			//str+="] \n";
			distance = Math.abs(getCentroid().getDistance(data.getItemSet(array[i])));
			str+="] dist="+ distance +"\n";
			distances[i] = distance;
		}
		str+="\nAvgDistance="+getCentroid().avgDistance(data, array);
		//return str;
		result[0] = str;
		result[1] = distances;
		return result;

	}
}