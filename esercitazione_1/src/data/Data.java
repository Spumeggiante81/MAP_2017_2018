package data;

public class Data {
	private Object data[][]; //è una matrice nxm di tipo Object dove ogni riga modella una transazione 
	private int numberOfExamples; //numero di righe in data 
	private Attribute attributeSet[]; //vettore di attributi
	private Attribute explanatorySet[];
	
	Data(){
		data=new Object[14][5]; 
		
		numberOfExamples=14; 
		
		
		attributeSet = new Attribute[5]; 
		
		String OutLookValues[]=new String[3]; 
		OutLookValues[0]="Overcast"; 
		OutLookValues[1]="Rain"; 
		OutLookValues[2]="Sunny"; 
		attributeSet[0]= new DiscreteAttribute("Outlook",0,OutLookValues); 
		
		String TemperatureValues[]=new String[3]; 
		TemperatureValues[0]="Hot"; 
		TemperatureValues[1]="Mild"; 
		TemperatureValues[2]="Cool"; 
		attributeSet[1]= new DiscreteAttribute("Temperature",1,TemperatureValues); 
		
		String HumidityValues[]=new String[2]; 
		HumidityValues[0]="Hight"; 
		HumidityValues[1]="Normal";  
		attributeSet[2]= new DiscreteAttribute("Humidity",0,HumidityValues); 
		
		String WindValues[]=new String[3]; 
		WindValues[0]="Weak"; 
		WindValues[1]="Strong"; 
		attributeSet[3]= new DiscreteAttribute("Wind",0,WindValues); 
		
		String PlayTennisValues[]=new String[3]; 
		PlayTennisValues[0]="overcast"; 
		PlayTennisValues[1]="Rain"; 
		PlayTennisValues[2]="sunny"; 
		attributeSet[4]= new DiscreteAttribute("PlayTennis",0,PlayTennisValues); 
		
	}
       int getNumberOfExamples() {
    	   return numberOfExamples; 
    	   
       }
       /**
        * restutuisce la dimensione di NumberOfAttribute
        * @return numerOfExaples dimensione di NumberOfAttribute
        */
       
       int getNumberOfExplanatoryAttributes() { // da finire
		return numberOfExamples;
    	   
       }
       
       Attribute[]getAttributeSchema(){ //da finire
		return attributeSet;
    	   
       }
       
       Object getAttributeValue(int exampleIndex, int attributeIndex) { //da finire
		return attributeIndex;
		
		
    	   
       }
       public String toString() { //da finire
    	   return "a";
       }
       
       
       public static void main(String args[])
       { 
    	 Data trainingSet=new Data();
    	 System.out.println(trainingSet);
       }
    	   
}   	   
       

