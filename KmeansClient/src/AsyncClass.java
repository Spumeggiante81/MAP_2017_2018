abstract class AsyncClass extends Thread {
    private IAsyncResponsive asyncResponse = null;

    AsyncClass(IAsyncResponsive response){
        asyncResponse = response;
    }
    @Override public final void  run(){
        asyncResponse.asyncStart(this);
        asyncResponse.asyncEnd(this, runasync());
    }
    
    /**
     * In questa funzione deve essere riportata la porzione di codice con il quale si intende effettuare
     * una comunicazione con il server. Il risultato di tale funzione sarà riportato nella funzione "asyncEnd"
     * @return 
     */
    protected abstract Object runasync();
}
