interface IAsyncResponsive {
	/**
	 * Esegue la porzione di codice presente all'interno di questa funzione (laddove sarà richiamata l'interfaccia)
	 * prima che possa svolgere il suo effettivo compito
	 * @param o Istanza di una comunicazione asincrona
	 */
    public void asyncStart(AsyncClass o);
	/**
	 * Esegue la porzione di codice presente all'interno di questa funzione (laddove sarà richiamata l'interfaccia)
	 * dopo aver svolt il suo effettivo compito. Come secondo parametro ha il risultato ricavato dal compito svolto
	 * @param o Istanza di una comunicazione asincrona
	 * @param result oggetto ricavato dopo aver effettuato la comunicazione
	 */
    public void asyncEnd(AsyncClass o, Object result);
}
