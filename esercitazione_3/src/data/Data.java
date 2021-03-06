package data;



import java.util.Random;

import utility.ArraySet;

public class Data {
	private Object data[][]; //� una matrice nxm di tipo Object dove ogni riga modella una transazione 
	private int numberOfExamples; //numero di righe in data 
	private Attribute attributeSet[]; //vettore di attributi
	private int distinctTuples;
	public Data(){
		
		
		numberOfExamples=14; 
		
		data=new Object[numberOfExamples][5]; 
		
		
		attributeSet = new Attribute[5]; 
		
		String outLookValues[] = {"Sunny", "Overcast", "Rain"};
		attributeSet[0] = new DiscreteAttribute("Outlook", 0, outLookValues);
		
		String temperatureValues[] = {"Hot", "Mild", "Cold"};
		attributeSet[1] = new DiscreteAttribute("Temperature", 1, temperatureValues);
		
		String humidityValues[] = {"High", "Normal"};
		attributeSet[2] = new DiscreteAttribute("Humidity", 2, humidityValues);
		
		String windValues[] = {"Weak", "Strong"};
		attributeSet[3] = new DiscreteAttribute("Wind", 3, windValues);
		
		String playTennisValues[] = {"No", "Yes"};
		attributeSet[4] = new DiscreteAttribute("PlayTennis", 4, playTennisValues);
		
        data[0][0] = "Sunny";
        data[0][1] = "Hot";
        data[0][2] = "High";
        data[0][3] = "Weak";
        data[0][4] = "No";
        
        data[1][0] = "Sunny";
        data[1][1] = "Hot";
        data[1][2] = "High";
        data[1][3] = "Strong";
        data[1][4] = "No";
        
        data[2][0] = "Overcast";
        data[2][1] = "Hot";
        data[2][2] = "High";
        data[2][3] = "Weak";
        data[2][4] = "Yes";
        
        data[3][0] = "Rain";
        data[3][1] = "Mild";
        data[3][2] = "High";
        data[3][3] = "Weak";
        data[3][4] = "Yes";
        
        data[4][0] = "Rain";
        data[4][1] = "Cool";
        data[4][2] = "Normal";
        data[4][3] = "Weak";
        data[4][4] = "Yes";
        
        data[5][0] = "Rain";
        data[5][1] = "Cool";
        data[5][2] = "Normal";
        data[5][3] = "Strong";
        data[5][4] = "No";
        
        data[6][0] = "Overcast";
        data[6][1] = "Cool";
        data[6][2] = "Normal";
        data[6][3] = "Strong";
        data[6][4] = "Yes";
        
        data[7][0] = "Sunny";
        data[7][1] = "Mild";
        data[7][2] = "High";
        data[7][3] = "Weak";
        data[7][4] = "No";
        
        data[8][0] = "Sunny";
        data[8][1] = "Cool";
        data[8][2] = "Normal";
        data[8][3] = "Weak";
        data[8][4] = "Yes";
        
        data[9][0] = "Rain";
        data[9][1] = "Mild";
        data[9][2] = "Normal";
        data[9][3] = "Weak";
        data[9][4] = "Yes";
        
        data[10][0] = "Sunny";
        data[10][1] = "Mild";
        data[10][2] = "Normal";
        data[10][3] = "Strong";
        data[10][4] = "Yes";
        
        data[11][0] = "Overcast";
        data[11][1] = "Mild";
        data[11][2] = "High";
        data[11][3] = "Strong";
        data[11][4] = "Yes";
        
        data[12][0] = "Overcast";
        data[12][1] = "Hot";
        data[12][2] = "Normal";
        data[12][3] = "Weak";
        data[12][4] = "Yes";
        
        data[13][0] = "Rain";
        data[13][1] = "Mild";
        data[13][2] = "High";
        data[13][3] = "Strong";
        data[13][4] = "No";

        
        
        distinctTuples = countDistinctTuples();
        
        System.out.println("distinctTuples = " + distinctTuples);
	}
	/**
	 * Indica il numero di transazioni (tuple) presenti nella collezione in analisi
	 * @return numero di transazioni (tuple)
	 */
      public int getNumberOfExamples() {
    	   return this.numberOfExamples; 
       }
       
       /**
        * restutuisce il numero di attributi quali definiscono le transazioni (tuple) presenti nella collezione in analisi
        * @return numero di attributi 
        */
      public int getNumberOfExplanatoryAttributes() {
		return this.attributeSet.length;
    	   
       }
       
       Attribute getAttribute(int index){
    	   return attributeSet[index];
       }

       /**
        * Restituisce lo schema degli attributi quali definiscono le transazioni (tuple) presenti nella collezione in analisi
        * @return schema degli attributi
        */
       Attribute[] getAttributeSchema(){
		return attributeSet;
       }
       /**
        * Ritorna il valore riportato nella transazione in posizione "exampleIndex" e riportato all'interno dell'attributo con indice "attributeIndex" presente
        * nella collezione in analisi
        * @param exampleIndex posizione della transazione (tupla) all'interno di cui si vuole ricavare il valore desiderato
        * @param attributeIndex indice dell'attributo quale definisce il valore desiderato
        * @return
        */
       Object getAttributeValue(int exampleIndex, int attributeIndex) {
		return this.data[exampleIndex][attributeIndex];
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
   				for (int j = 0; j < attributeSet.length; j++){
   					//per ogni attributo con indice "j", ricavo il valore assocciato presente nella tupla in posizione "i-esima", 
   					//e lo concateno a tutti gli altri presenti nella stessa tupla
   					//CONCATENAZIONE SEPARATA PER NON STAMPARE LA VIRGOLA A FINE RIGA
   					string += data[i][j].toString();
   					if(j<attributeSet.length-1)
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
		Tuple tuple=new Tuple(attributeSet.length);
		for(int i=0;i<attributeSet.length;i++)
			tuple.add(new DiscreteItem((DiscreteAttribute) attributeSet[i],(String) data[index][i]), i);
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
		if ((k <= 0)||(k > distinctTuples))
			throw new OutOfRangeSampleSize("Errore: il numero di Cluster da calcolare (k) deve essere compreso tra 1 e " + distinctTuples +".");
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
		 //Tuple tupla_i = this.getItemSet(i);
		 return (this.getItemSet(i).getDistance(this.getItemSet(j)) == 0); 
	}  
	
	public Object computePrototype(ArraySet idList, Attribute attribute) {
		return computePrototype(idList, (DiscreteAttribute) attribute);
	}

	public String computePrototype(ArraySet idList, DiscreteAttribute attribute){
		String moreFrequentCentroid = attribute.getValues(0);
		int countFrequency, countFrequencyCentroid = attribute.frequency(this, idList, attribute.getValues(0));;
		for (int i = 1; i < attribute.getNumberOfDistinctValues(); i++){
			countFrequency = attribute.frequency(this, idList, attribute.getValues(i));
			if (countFrequency > countFrequencyCentroid){
				countFrequencyCentroid = countFrequency;
				moreFrequentCentroid = attribute.getValues(i);
			}
		}
		return moreFrequentCentroid;
	}

	public String getValue(int i, int j) {
		return (String)this.getAttributeValue(i, j);
	}

	
	/**
	 * @return distinctTuples : Conta il numero di transazioni distinte memorizzate in Data
	 */
	private int countDistinctTuples(){
		int num = 0;
		
		boolean[] escluse = new boolean[numberOfExamples];
		for(int i=0;i<numberOfExamples-1;i++){
			if (!escluse[i]){
				for(int j=i+1;j<numberOfExamples;j++){
					if (compare(i,j)){
						num++;
						escluse[j] = true;
					}
				}
			}
		
		}
		
		return  numberOfExamples-num;
	}	
}

