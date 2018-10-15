public class Main_matrice {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Matrice mat = new Matrice(13,2);
		int righe = 0,colonne = 0;
		Matrice mat = new Matrice(righe,colonne); 
	
		
		
		
		int scelta = Keyboard.readInt();
		while(scelta != 0)
			switch (scelta){
				case 1 : {
					System.out.println("inserisci numero righe: ");
					righe = Keyboard.readInt();
					System.out.println("inserisci numero colonne: ");
					colonne = Keyboard.readInt();
					mat = new Matrice(righe,colonne); 
					System.out.println("Righe: " + righe + "  colonne "+colonne  );
					break;
					}
				case 2 : mat.scriviMatrice();//avendo già creato oggetto non ho passato la dimensione
				case 3 : mat.stampaMatrice();//avendo già creato oggetto non ho passato la dimensione
				default: System.out.println("Scelta inesistenze!");
			}
				System.out.println("Seleziona nuova operazione " + "(" + 0 + ")" + "per uscire");
				scelta = Keyboard.readInt();

		}
			
	}	


