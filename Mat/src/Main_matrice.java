

import java.awt.EventQueue;
import javax.swing.JFrame;


public class Main_matrice {

	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		//Matrice mat = new Matrice(13,2);
		
		Matrice mat = null; 
		Grafico kMeans; 
	
		
		
		System.out.print("Inserire la scelta: ");
		int scelta = Keyboard.readInt();
		while(scelta != 0)
		{
			switch (scelta)
			{
				case 1 : 
					System.out.print("inserisci numero righe: ");
					int righe = Keyboard.readInt();
					System.out.print("inserisci numero colonne: ");
					int colonne = Keyboard.readInt();
					mat= new Matrice(righe,colonne); 
					System.out.println("Righe: " + righe + "  colonne "+colonne );
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
						 kMeans = new Grafico(mat.matrice);
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


