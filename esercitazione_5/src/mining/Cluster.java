package mining;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import data.Data;
import data.Tuple;

//Classe definita dalla prof insieme ai suoi metodi
public class Cluster {
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
	Tuple getCentroid(){
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
	 * @param id indice su cui è riferito la tupla
	 * @return true se l'operazione ha "modificato" la struttura dati, false altrimenti (ovvero, qualora fosse già presente e quindi non serve aggiungerlo nuovamente)
	 */
	boolean addData(int id){
		return clusteredData.add(id);	
	}
	
	/**
	 * Verifica se una tupla è associata o meno al cluster in analisi
	 * @param id indice su cui è riferito la tupla
	 * @return true se la tupla è associata al cluster, false altrimenti
	 */
	boolean contain(int id){
		return clusteredData.contains(id);
	}
	
	/**
	 * Rimuove l'associazione della data tupla con il cluster
	 * @param id indice su cui è riferito la tupla
	 */
	void removeTuple(int id){
		clusteredData.remove(id);
	}
	
	public String toString(){
		String str="Centroid=(";
		for(int i=0;i<centroid.getLength();i++)
			str+=centroid.get(i);
		str+=")";
		return str;
		
	}
	
	public String toString(Data data){
		String str="Centroid=(";
		for(int i=0;i<centroid.getLength();i++)
			str+=centroid.get(i)+ " ";
		str+=")\nExamples:\n";
		int array [] = clusteredData.stream().mapToInt(Integer::intValue).toArray();
		for(int i=0;i<array.length;i++){
			str+="[";
			for(int j=0;j<data.getNumberOfExplanatoryAttributes();j++)
				str+=data.getValue(array[i], j)+" ";
			str+="] dist="+getCentroid().getDistance(data.getItemSet(array[i]))+"\n";
			
		}
		str+="\nAvgDistance="+getCentroid().avgDistance(data, array);
		return str;
		
	}
}