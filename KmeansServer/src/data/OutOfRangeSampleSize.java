package data;

/**
 * Gestisce un eccezione controllata restituendo un messaggio qualora il 
 * numero di k di cluster inserito sia maggiore rispetto al numero di centroidi 
 * generabili
 *
 */
public class OutOfRangeSampleSize extends Exception{

	private String message;

	public OutOfRangeSampleSize (String message){
		this.message = message;
	}

	public String getMessage (){
		return message;
	}
}
