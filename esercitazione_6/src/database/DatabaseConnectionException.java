package database;

/**
 * Lancia un'eccezione se il collegamento col database fallisce
 * @author Ciccariello Luciano, Palumbo Vito, Rosini Luigi
 */
public class DatabaseConnectionException extends Exception {
    private String message;

    /**
     * Costruttore della classe DatabaseConnectionException
     * Modella il fallimento della connessione al database
     * @param message
     */
    DatabaseConnectionException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
