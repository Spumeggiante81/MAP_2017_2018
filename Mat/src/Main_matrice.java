import java.awt.EventQueue;

import javax.swing.JFrame;

public class Main_matrice {

	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		//Matrice mat = new Matrice(13,2);
		final int RIGHE = 3;
		final int COLONNE = 2;
		Matrice mat = null; 
	
		
		
		System.out.print("Inserire la scelta: ");
		int scelta = Keyboard.readInt();
		while(scelta != 0)
		{
			switch (scelta)
			{
				case 1 : 
					//System.out.print("inserisci numero righe: ");
					//righe = Keyboard.readInt();
					//System.out.print("inserisci numero colonne: ");
					//colonne = Keyboard.readInt();
					mat = new Matrice(RIGHE,COLONNE); 
					System.out.println("Righe: " + RIGHE + "  colonne "+COLONNE );
					break;
				case 2 :try
					{ 
						mat.scriviMatrice();
				 		break;
					}catch(NullPointerException e){
						System.err.println("e");
						break;
					}
				case 3 :try
					{ 
						mat.stampaMatrice();//avendo già creato oggetto non ho passato la dimensione
						break;
					}catch(NullPointerException e){
							System.err.println("eccezione");
							break;
					}
				case 4: 
					try
					{ 
						System.out.println("il numero massimo nelle righe: "+ mat.maxRighe());
						break;
					}catch(NullPointerException e){
				System.err.println("eccezione");
							break;
					}
					
				case 5: 
					try
					{ 
						System.out.println("il numero massimo nelle colonne: "+ mat.maxColonne());
						break;
					}catch(NullPointerException e){
							System.err.println("eccezione");
							break;
					}
				default: 
					System.out.println("Scelta inesistente!");
					break;
			}
				System.out.print("Seleziona nuova operazione " + "(" + 0 + ") " + "per uscire: ");
				scelta = Keyboard.readInt();
		}

	}
			
}	


