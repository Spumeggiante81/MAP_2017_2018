package data;



import java.util.Random;

public class Data {
	private Object data[][]; //è una matrice nxm di tipo Object dove ogni riga modella una transazione 
	private int numberOfExamples; //numero di righe in data 
	private Attribute attributeSet[]; //vettore di attributi
	private Attribute explanatorySet[];
	
	Data(){
		data=new Object[14][5]; 
		
		numberOfExamples=14; 
		
		
		attributeSet = new Attribute[5]; 
		
		String outLookValues[] = {"Sunny", "Overcast", "Rain"};
		attributeSet[0] = new DiscreteAttribute("Outlook", 0, outLookValues);
		
		String temperatureValues[] = {"Hot", "Mid", "Cold"};
		attributeSet[1] = new DiscreteAttribute("Temperature", 1, temperatureValues);
		
		String humidityValues[] = {"Hight", "Normal"};
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
        data[9][2] = "High";
        data[9][3] = "Weak";
        data[9][4] = "No";
        
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
		
	}
	/**
	 * Indica il numero di transazioni (tuple) presenti nella collezione in analisi
	 * @return numero di transazioni (tuple)
	 */
       int getNumberOfExamples() {
    	   return this.numberOfExamples; 
       }
       
       /**
        * restutuisce il numero di attributi quali definiscono le transazioni (tuple) presenti nella collezione in analisi
        * @return numero di attributi 
        */
       
       int getNumberOfExplanatoryAttributes() {
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
   			for (int i = 0; i < this.numberOfExamples; i++){
   				//riporto la posizione della tupla nella collezione
   				string += (i+1) +":";
   				//inizio a ciclare per ogni attributo quali descrivono la tupla
   				for (int j = 0; j < this.attributeSet.length; j++){
   					//per ogni attributo con indice "j", ricavo il valore assocciato presente nella tupla in posizione "i-esima", 
   					//e lo concateno a tutti gli altri presenti nella stessa tupla
   					string += data[i][j].toString() + ",";
   				}
   				//terminati gli attributi, termino la riga quale definisce la tupla in posizione i-esima e passo alla tupla successiva, qualora ce ne siano altre,
   				//altrimenti la funzione restituisce la stringa ricavata
   				string += "\n";
   			}
   			return string;
       }
       
       
       public static void main(String args[])
       { 
    	 Data trainingSet=new Data();
    	 DiscreteAttribute attr = (DiscreteAttribute)trainingSet.getAttribute(4);
    	 ArraySet as = new ArraySet();
    	 as.add(0);
    	 as.add(5);
    	 as.add(7);
    	 as.add(2);
    	 as.add(10);
    	 int k = attr.frequency(trainingSet, as, "Yes");
    	 System.out.println(trainingSet);
    	 System.out.println(k);
       }
       
   	/**
   	 * Crea e restituisce un oggetto di tuple che modella come sequenza di coppie Attributo-valore 
   	 * la iesima riga in data
   	 * @param index indice di riga
   	 * @return
   	 */
       
	public Tuple getItemSet(int index) {
		Tuple tuple=new Tuple(explanatorySet.length);
		for(int i=0;i<explanatorySet.length;i++)
			tuple.add(new DiscreteItem((DiscreteAttribute) explanatorySet[i],(String) data[index][i]), i);
		
		return tuple;
	}
	
	
	/**
	 * 
	 * @param k numero di cluster da generare
	 * @return array di k interi rappresentanti gli indici di riga in data 
	 * per le tuple inizialmente scelte come centroidi
	 */
	int[] sampling(int k)
	{
		int centroidIndexes[]=new int[k];//??
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
						break;
					}
				}
			}
			while(found);
			centroidIndexes[i]=c;
		}
		return centroidIndexes;
	}
	
	private boolean compare(int i, int j) {
		// TODO Auto-generated method stub
		return false;
	}
	private int String(Object[] objects) {
		// TODO Auto-generated method stub
		return 0;
	}
	
<<<<<<< HEAD
	private Object computePrototype(ArraySet idList, Attribute attribute) {
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
			return computePrototype(idList, (DiscreteAttribute)attribute); 
		}
		}
}
	
					 
	
		 

			 
	
=======

>>>>>>> abcdddb7e8f42bc900cb79e259b9b4d013023844
	
	
    	   
   	   
       

