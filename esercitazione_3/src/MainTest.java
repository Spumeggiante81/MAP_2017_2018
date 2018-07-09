

import data.Data;
import data.OutOfRangeSampleSize;
import keyboardinput.Keyboard;
import mining.KMeansMiner;

public class MainTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {	
	
		char c = 'y';
		Data data =new Data();
		System.out.println(data);
		do{
			try {
				
				int k;
				System.out.println("Inserisci il numero di Cluster (k) da calcolare :");
				//gestire con eccezione
				k = Keyboard.readInt();//gestire con eccezione
				KMeansMiner kmeans=new KMeansMiner(k); // KmeansMiner kmeans=new KmeansMiner(k); 
				int numIter=kmeans.kmeans(data);
				System.out.println("Numero di Iterazione:"+numIter);
				System.out.println(kmeans.getC().toString(data));
				
				char [] answers = new char [] {'y', 'n'};
				do{
					System.out.println("Vuoi ripetere l'esecuzione? (y/n)");
					//gestire con un if
					c = Keyboard.readChar();//gestire con un if
				}while (new String(answers).indexOf(c) == -1);
				
				
			}catch (OutOfRangeSampleSize e){
				System.out.println(e.getMessage());
			}
			catch (Exception e){
				System.err.println(e.getMessage());
			}
		} while(c == 'y');
	}
}