
public class Matrice {
	double [][] matrice;

	
	Matrice(int x, int y){
		matrice = new double [x][y];
	}
	
	
	public void scriviMatrice(){
		for(int  i=0;i<=matrice.length-1;i++){
			for( int j=0;j<=matrice[i].length-1;j++){
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
}



