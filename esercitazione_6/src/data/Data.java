package data;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import database.DatabaseConnectionException;
import database.DbAccess;
import database.Example;
import database.TableData;


public class Data {	
	private List<Example> data = new ArrayList<Example>();
	private int numberOfExamples; //numero di righe in data 
	private List<Attribute> explanatorySet = new LinkedList<Attribute>();
	
	public Data(String table) throws DatabaseConnectionException, SQLException{
		
		String outLookValues[] = {"sunny", "overcast", "rain"};
		explanatorySet.add(new DiscreteAttribute("Outlook", 0, outLookValues));
		
		explanatorySet.add(new ContinuosAttribute("Temperature", 1, 3.2, 38.7)); 
		
		String humidityValues[] = {"high", "normal"};
		explanatorySet.add(new DiscreteAttribute("Humidity", 2, humidityValues));
		
		String windValues[] = {"weak", "strong"};
		explanatorySet.add(new DiscreteAttribute("Wind", 3, windValues));
		
		String playTennisValues[] = {"no", "yes"};
		explanatorySet.add(new DiscreteAttribute("PlayTennis", 4, playTennisValues));
        
		//definisco db per poter eseguire l'accesso al database, e la variabile td, quale consente di gestire una data tabella nel database selezionato
        DbAccess db = new DbAccess();
        TableData td = new TableData(db);

        //effettuo la connessione col database
        db.initConnection();
        //ricavo la lista delle tuple presenti nella tabella "table" all'interno del database
        data = new ArrayList<Example>(td.getTransazioni(table));
        numberOfExamples = data.size();
	}
	/**
	 * Indica il numero di transazioni (tuple) presenti nella collezione in analisi
	 * @return numero di transazioni (tuple)
	 */
      public int getNumberOfExamples() {
    	  return numberOfExamples; 
       }
       
       /**
        * restutuisce il numero di attributi quali definiscono le transazioni (tuple) presenti nella collezione in analisi
        * @return numero di attributi 
        */
      public int getNumberOfExplanatoryAttributes() {
    	  return explanatorySet.size();
    	   
       }
       
       Attribute getAttribute(int index){
    	   return explanatorySet.get(index);
       }

       /**
        * Restituisce lo schema degli attributi quali definiscono le transazioni (tuple) presenti nella collezione in analisi
        * @return schema degli attributi
        */
       Attribute[] getAttributeSchema(){
    	   return (Attribute[]) explanatorySet.toArray();
       }
       /**
        * Ritorna il valore riportato nella transazione in posizione "exampleIndex" e riportato all'interno dell'attributo con indice "attributeIndex" presente
        * nella collezione in analisi
        * @param exampleIndex posizione della transazione (tupla) all'interno di cui si vuole ricavare il valore desiderato
        * @param attributeIndex indice dell'attributo quale definisce il valore desiderato
        * @return
        */
       public Object getAttributeValue(int exampleIndex, int attributeIndex) {
		Example example = data.get(exampleIndex);
		return example.get(attributeIndex);
       }
       
       /**
        * Ritorna in formato String tutti dati presenti all'interno della collezione in analisi
        */
       public String toString() {
    	   //incomincio definendo una stringa "vuota" ( qualora la collezione sia vuota)
    	   String string = "";
   			//inizio a ciclare per ogni tupla presente all'interno della collezione
   			for (int i = 0; i < numberOfExamples; i++){
   				//riporto la posizione della tupla nella collezione
   				string += (i+1) +":";
   				//inizio a ciclare per ogni attributo quali descrivono la tupla
   				//for (int j = 0; j < this.getNumberOfExplanatoryAttributes(); j++){ this inutile
   				for (int j = 0; j < getNumberOfExplanatoryAttributes(); j++){
   					//per ogni attributo con indice "j", ricavo il valore assocciato presente nella tupla in posizione "i-esima", 
   					//e lo concateno a tutti gli altri presenti nella stessa tupla
   					//CONCATENAZIONE SEPARATA PER NON STAMPARE LA VIRGOLA A FINE RIGA
   					//string += this.getAttributeValue(i,j).toString(); this inutile
   					string += getAttributeValue(i,j).toString();
   					//if(j<this.getNumberOfExplanatoryAttributes()-1) this inutile
   					if(j<getNumberOfExplanatoryAttributes()-1)
   					  string +=  ",";
   				}
   				//terminati gli attributi, termino la riga quale definisce la tupla in posizione i-esima e passo alla tupla successiva, qualora ce ne siano altre,
   				//altrimenti la funzione restituisce la stringa ricavata
   				string += "\n";
   			}
   			return string;
       }
       
   	
	
	/**
	 * 
	 * @param k numero di cluster da generare
	 * @return array di k interi rappresentanti gli indici di riga in data 
	 * per le tuple inizialmente scelte come centroidi
	 */
	public int[] sampling(int k) throws OutOfRangeSampleSize
	{
		if ((k <= 0)||(k > this.getNumberOfExamples()))
			throw new OutOfRangeSampleSize("Errore: il numero di Cluster da calcolare (k) deve essere compreso tra 1 e " + this.getNumberOfExamples() +".");
		int centroidIndexes[]=new int[k];
		Random rand=new Random();
		rand.setSeed(System.currentTimeMillis());
		for(int i=0;i<k;i++)
		{
			boolean found=false;
			int c;
			do
			{
				found=false;
				c=rand.nextInt(getNumberOfExamples());
				for(int j=0;j<i;j++)
				{
					if(compare(centroidIndexes[j],c))
					{
						found=true;
						break; //forza uscita da questo ciclo for(int j=0;j<i;j++)
					}
				}
			}
			while(found);
			centroidIndexes[i]=c;
		}
		return centroidIndexes;
	}
	
	/**
	 * 
	 * @param i indice di riga dell'insieme Data
	 * @param j indice di riga dell'insieme Data
	 * @return restituisce vero se le due righe di data contengono gli stessi valori, falso altrimenti
	 */
	private boolean compare(int i, int j)  {  
		 return (this.getItemSet(i).getDistance(this.getItemSet(j)) == 0); 
	}  
	
	public Object computePrototype(Set<Integer> idList, Attribute attribute) {
		if (attribute instanceof DiscreteAttribute)
			return computePrototype(idList, (DiscreteAttribute) attribute);
		else
			return computePrototype(idList, (ContinuosAttribute) attribute);
	}

	/**
	 * Ricava il valore prototipo come quello più frequente, presente tra i valori rappresentati dall'attributo passato in paramentro,
	 * presenti nelle tuple indicate nel Set
	 * @param idList
	 * @param attribute
	 * @return
	 */
	private String computePrototype(Set<Integer> idList, DiscreteAttribute attribute){
		/*
		 * Ricavo il primo valore di "attribute" e la indico temporaneamente come "centroide"
		 * Riporto la frequenza di tuple, indicate nel set di id, che contengono tae valore in "countFrequencyCentroid"
		 */
		Iterator<String> iter = attribute.iterator();
		String attributeValue = (String)iter.next();
		String moreFrequentCentroid = attributeValue;
		int countFrequency, countFrequencyCentroid = attribute.frequency(this, idList, attributeValue);
		/*
		 * ciclo finchè ci sono tuple della collezione
		 */
		while (iter.hasNext()){
			/*
			 * ricavo il successivo valore dell'attributo e ne ricavo la frequenza in funzione delle tuple definite nel Set
			 */
			attributeValue = (String)iter.next();
			countFrequency = attribute.frequency(this, idList, attributeValue);
			/*
			 * Se il nuovo valore è più frequente di quello del centroide precedentemente rilevato
			 * allora vuol dire che abbiamo trovato un nuovo centroide
			 */
			if (countFrequency > countFrequencyCentroid){
				countFrequencyCentroid = countFrequency;
				moreFrequentCentroid = attributeValue;
			}
		}
		return moreFrequentCentroid;
	}

	/**
	 * Determina il valore prototipo come media dei valori appartenenti all'attributo in parametro, presenti nelle tuple indicate nel Set
	 * @param idList
	 * @param attribute
	 * @return
	 */
	private double computePrototype(Set<Integer> idList, ContinuosAttribute attribute){		
		double tot = 0;
		int j = attribute.index;
		int arraySet [] = idList.stream().mapToInt(Integer::intValue).toArray();
		for(int i=0;i<arraySet.length;i++){
			int riga = arraySet[i];
			double value = (double)this.getAttributeValue(riga, j);
			tot += value;
		}
		return tot/arraySet.length;
	}
	
	
	
	/**
   	 * Crea e restituisce un oggetto di tuple che modella come sequenza di coppie Attributo-valore 
   	 * la iesima riga in data
   	 * @param index indice di riga
   	 * @return
   	 */
       
	public Tuple getItemSet(int index) {
		Tuple tuple=new Tuple(getNumberOfExplanatoryAttributes());
		for(int i=0;i<getNumberOfExplanatoryAttributes();i++)
			if (getAttribute(i) instanceof DiscreteAttribute)
				tuple.add(new DiscreteItem((DiscreteAttribute) getAttribute(i),(String) getAttributeValue(index, i)), i);
			else
				tuple.add(new ContinuosItem((ContinuosAttribute) getAttribute(i),(double) getAttributeValue(index, i)), i);
		return tuple;
	}
	
	
}

