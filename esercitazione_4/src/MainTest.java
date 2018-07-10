

import data.Data;
import keyboardinput.Keyboard;
import mining.KMeansMiner;

public class MainTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {	
	
		char c = 'y';
		String answer;
		Data data =new Data();
		System.out.println(data);
		do{
			try {
				
				int k;
				System.out.println("Inserisci il numero di Cluster (k) da calcolare :");
				k = Keyboard.readInt();
				KMeansMiner kmeans=new KMeansMiner(k); // KmeansMiner kmeans=new KmeansMiner(k); 
				int numIter=kmeans.kmeans(data);
				System.out.println("Numero di Iterazione:"+numIter);
				System.out.println(kmeans.getC().toString(data));
				char [] answers = new char [] {'y', 'n'};
				do{
					System.out.println("Vuoi ripetere l'esecuzione? (y/n)");
					answer = Keyboard.readString();
					c = answer.charAt(0);
				}while ((new String(answers).indexOf(c) == -1)||(answer.length() > 1));
			}
			catch (Exception e){
				System.err.println(e.getMessage());
			}
		} while(c == 'y');
	}
}