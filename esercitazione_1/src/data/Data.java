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
		
		String outLookValues[]=new String[3]; 
		outLookValues[0]="overcast"; 
		outLookValues[1]="Rain"; 
		outLookValues[2]="sunny"; 
		attributeSet[0]= new DiscreteAttribute("Outlook",0,outLookValues); 
		
		// SIMILMENTE PER GLI ALTRI
		
		
	}
       int getNumberOfExamples() {
    	   return numberOfExamples; 
    	   
       }
       /**
        * restutuisce la dimensione di NumberOfAttribute
        * @return
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
    	   return null;
       }
    	   
}   	   
       

