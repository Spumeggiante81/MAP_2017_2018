package database;

/**
 * Lancia un'eccezione se il collegamento col database fallisce
 */
public class DatabaseConnectionException extends Exception {
    private String message;

    DatabaseConnectionException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
