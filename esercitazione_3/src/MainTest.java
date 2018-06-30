

import data.Data;
import keyboardinput.Keyboard;
import mining.KMeansMiner;

public class MainTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {	
	
		char c;
		Data data =new Data();
		System.out.println(data);
		do{
			int k;
			do{
				System.out.println("Inserisci k (>0) :");
				k = Keyboard.readInt();
			} while (k <= 0);
			KMeansMiner kmeans=new KMeansMiner(k); // KmeansMiner kmeans=new KmeansMiner(k); 
			int numIter=kmeans.kmeans(data);
			System.out.println("Numero di Iterazione:"+numIter);
			System.out.println(kmeans.getC().toString(data));
			char [] answers = new char [] {'y', 'n'};
			do{
				System.out.println("Vuoi ripetere l'esecuzione? (y/n)");
				c = Keyboard.readChar();
			}while (new String(answers).indexOf(c) == -1);
		} while(c == 'y');
	}
}