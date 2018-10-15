
public class Matrice {
	double [][] matrice;
	double elemento;
	int i=0,j=0;
	
	Matrice(int x, int y){
		matrice = new double [x][y];
	}
	
	
	public void scriviMatrice(){
		for( i=0;i<=matrice.length-1;i++){
			for( j=0;j<=matrice[i].length-1;j++){
				System.out.print("riga "+i + " colonna "+ j+ " : ");
				matrice[i][j] = Keyboard.readInt();
			}
		}
	}
	
	
	public int stampaNumRighe(){
		System.out.println("righe "+ matrice.length);
		System.out.println();
		return 0;
	}
	
	public int stampaNumColonne(){
		System.out.println("colonne "+ matrice[0].length);
		System.out.println();
		return 0;
	}
	
	public double maxRighe(){
		double numMaxX = 0;
		
		System.out.println("matrice righe "+ matrice.length);
		for (int i=0; i<matrice.length; i++){
			System.out.println("matrice "+ i + " " + matrice[i][0]);
			if (matrice[i][0]>numMaxX)
				numMaxX =   matrice[i][0];
		}
		return numMaxX;
	}
	
	public double maxColonne(){
		double numMaxY = 0;
		
		for(int i=0;i<=matrice[i].length-1;i++)
			if (matrice[i][1]>numMaxY)
				numMaxY =   matrice[i][1];
			return numMaxY;
	}
	
	public void stampaMatrice(){
		for(int i=0;i<=matrice.length-1;i++){
			for(int j=0;j<=matrice[i].length-1;j++)
				System.out.print( matrice[i][j] + " ");
			System.out.println();
		}
	}
/*	
	//TODO : add(int x, int y, double value), e le altre funzioni
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
	/*	for (int i=0; i<=a.length-1; i++)
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
	}*/
	

}



