package database;

/**
 * Lanciato quando non viene trovato nessun valore
 */
class NoValueException extends Exception {

	private String message;

	NoValueException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
}
