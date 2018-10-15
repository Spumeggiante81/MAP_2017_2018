public class Main_matrice {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Matrice mat = new Matrice(13,2);
		Matrice mat  = null;
		
		
		System.out.println("Scegli operazione: ");
		System.out.println("1 Crea matrice");
		System.out.println("2 Stampa matrice ");
		System.out.println("Seleziona operazione " + "(" + 0 + ")" + "per uscire");
		
		int scelta = Keyboard.readInt();
		while(scelta != 0)
			switch (scelta){
				case 1 : new Matrice(3,4); 
				case 2 : mat.inserisciMatrice();//avendo già creato oggetto non ho passato la dimensione
				case 3 : mat.stampaMatrice();//avendo già creato oggetto non ho passato la dimensione
				default:{
					System.out.println("Scelta inesistenze!");
					
				}
		}
			
	}	

}
