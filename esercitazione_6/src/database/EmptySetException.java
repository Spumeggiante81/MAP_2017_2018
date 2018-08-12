package database;

/**
 * 
 * Modella la restituzione di un resultset vuoto
 *
 */
public class EmptySetException extends Exception {
	
	private String message;
	
	public EmptySetException(String message){
		this.message = message;
	}

    public String getMessage() {
        return this.message;
    }
}
