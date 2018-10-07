
public class Matrice {
	public static void main (String [] args) {

		double [][] a = {
				{1,0.17}, {2,0.24},{5,2.5}};
		//ho inserito solo tre righe per comodità
		
		double somma = 0;
		double numMaxX = 0,numMaxY = 0;

		System.out.println("righe "+ a.length + " colonne "+ a[0].length);
		System.out.println();
		
		/*
		for (int i=0; i<=a.length-1; i++){
			for ( int j=0; j<=a[0].length-1; j++){
				somma = somma + a[i][j]; 
				System.out.print(a[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("Somma "+somma);
*/
		for (int i=0; i<=a.length-1; i++)
			if (a[i][0]>numMaxX)
				numMaxX =  (int) a[i][0];
		

		for(int i=0;i<=a.length-1;i++)
			if (a[i][1]>numMaxY)
				numMaxY =   a[i][1];
			System.out.println();
		
		//risultato utile per rappresentare il numero max nelle Y
		System.out.println("NumMaxY: "+ numMaxY);
		
		//risultato utile per rappresentare il numero max nelle X
		System.out.println("NumMaxX: "+ numMaxX);	
		System.out.println();
		
		//stampa la matrice
		for(int i=0;i<=a.length-1;i++){
			for(int j=0;j<=a[i].length-1;j++)
				System.out.print( a[i][j] + " ");
			System.out.println();
		}
	}

}



