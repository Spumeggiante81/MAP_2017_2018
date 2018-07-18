package data;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class Data {
	
	class Example implements Comparable<Example>{
		private List<Object> example;
		
		Example() {
			example = new ArrayList<Object>();
		}
		
		void add(Object o){
			example.add(o);
		}
		
		Object get(int i){
			return example.get(i);
		}

		@Override
		public int compareTo(Example ex) {
			return (example.equals(ex) == true) ? 0 : -1;
		}
		
		public String toString(){
			String result = "";
			/*
			example.forEach((e) -> {
				result += e.toString();
			});
			*/
			
			result += example.stream()
					.findAny();
			return result;
		}
	}
	
	private List<Example> data = new ArrayList<Example>();
	private int numberOfExamples; //numero di righe in data 
	private List<Attribute> explanatorySet = new LinkedList<Attribute>();
	
	public Data(){
		
		String outLookValues[] = {"Sunny", "Overcast", "Rain"};
		explanatorySet.add(new DiscreteAttribute("Outlook", 0, outLookValues));
		
		String temperatureValues[] = {"Hot", "Mild", "Cool"};
		explanatorySet.add(new DiscreteAttribute("Temperature", 1, temperatureValues));
		
		String humidityValues[] = {"High", "Normal"};
		explanatorySet.add(new DiscreteAttribute("Humidity", 2, humidityValues));
		
		String windValues[] = {"Weak", "Strong"};
		explanatorySet.add(new DiscreteAttribute("Wind", 3, windValues));
		
		String playTennisValues[] = {"No", "Yes"};
		explanatorySet.add(new DiscreteAttribute("PlayTennis", 4, playTennisValues));
		
		TreeSet<Example> tempData = new TreeSet<Example>();
		
		Example ex0 = new Example();
		Example ex1 = new Example();
		Example ex2 = new Example();
		Example ex3 = new Example();
		Example ex4 = new Example();
		Example ex5 = new Example();
		Example ex6 = new Example();
		Example ex7 = new Example();
		Example ex8 = new Example();
		Example ex9 = new Example();
		Example ex10 = new Example();
		Example ex11 = new Example();
		Example ex12 = new Example();
		Example ex13 = new Example(); 
		
        ex0.add( "Sunny");
        ex0.add( "Hot");
        ex0.add( "High");
        ex0.add( "Weak");
        ex0.add( "No");
        
        ex1.add( "Sunny");
        ex1.add( "Hot");
        ex1.add( "High");
        ex1.add( "Strong");
        ex1.add( "No");
        
        ex2.add( "Overcast");
        ex2.add( "Hot");
        ex2.add( "High");
        ex2.add( "Weak");
        ex2.add( "Yes");
        
        ex3.add( "Rain");
        ex3.add( "Mild");
        ex3.add( "High");
        ex3.add( "Weak");
        ex3.add( "Yes");
        
        ex4.add( "Rain");
        ex4.add( "Cool");
        ex4.add( "Normal");
        ex4.add( "Weak");
        ex4.add( "Yes");
        
        ex5.add( "Rain");
        ex5.add( "Cool");
        ex5.add( "Normal");
        ex5.add( "Strong");
        ex5.add( "No");
        
        ex6.add( "Overcast");
        ex6.add( "Cool");
        ex6.add( "Normal");
        ex6.add( "Strong");
        ex6.add( "Yes");
        
        ex7.add( "Sunny");
        ex7.add( "Mild");
        ex7.add( "High");
        ex7.add( "Weak");
        ex7.add( "No");
        
        ex8.add( "Sunny");
        ex8.add( "Cool");
        ex8.add( "Normal");
        ex8.add( "Weak");
        ex8.add( "Yes");
        
        ex9.add( "Rain");
        ex9.add( "Mild");
        ex9.add( "Normal");
        ex9.add( "Weak");
        ex9.add( "Yes");
        
        ex10.add( "Sunny");
        ex10.add( "Mild");
        ex10.add( "Normal");
        ex10.add( "Strong");
        ex10.add( "Yes");
        
        ex11.add( "Overcast");
        ex11.add( "Mild");
        ex11.add( "High");
        ex11.add( "Strong");
        ex11.add( "Yes");
        
        ex12.add( "Overcast");
        ex12.add( "Hot");
        ex12.add( "Normal");
        ex12.add( "Weak");
        ex12.add( "Yes");
        
        ex13.add( "Rain");
        ex13.add( "Mild");
        ex13.add( "High");
        ex13.add( "Strong");
        ex13.add( "No");

        tempData.add(ex0);
        tempData.add(ex1);
        tempData.add(ex2);
        tempData.add(ex3);
        tempData.add(ex4);
        tempData.add(ex5);
        tempData.add(ex6);
        tempData.add(ex7);
        tempData.add(ex8);
        tempData.add(ex9);
        tempData.add(ex10);
        tempData.add(ex11);
        tempData.add(ex12);
        tempData.add(ex13);
        
        data = new ArrayList<Example>(tempData);
        
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
       Object getAttributeValue(int exampleIndex, int attributeIndex) {
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
   	 * Crea e restituisce un oggetto di tuple che modella come sequenza di coppie Attributo-valore 
   	 * la iesima riga in data
   	 * @param index indice di riga
   	 * @return
   	 */
       
	public Tuple getItemSet(int index) {
		Tuple tuple=new Tuple(getNumberOfExplanatoryAttributes());
		for(int i=0;i<getNumberOfExplanatoryAttributes();i++)
			tuple.add(new DiscreteItem((DiscreteAttribute) getAttribute(i),(String) getAttributeValue(index, i)), i);
		return tuple;
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
		return computePrototype(idList, (DiscreteAttribute) attribute);
	}

	public String computePrototype(Set<Integer> idList, DiscreteAttribute attribute){
		Iterator iter = attribute.iterator();
		String attributeValue = (String)iter.next();
		String moreFrequentCentroid = attributeValue;
		int countFrequency, countFrequencyCentroid = attribute.frequency(this, idList, attributeValue);;
		while (iter.hasNext()){
			attributeValue = (String)iter.next();
			countFrequency = attribute.frequency(this, idList, attributeValue);
			if (countFrequency > countFrequencyCentroid){
				countFrequencyCentroid = countFrequency;
				moreFrequentCentroid = attributeValue;
			}
		}
		return moreFrequentCentroid;
	}

	public String getValue(int i, int j) {
		return (String)getAttributeValue(i, j);
	}	
}

