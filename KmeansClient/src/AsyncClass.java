abstract class AsyncClass extends Thread {
    private IAsyncResponsive asyncResponse = null;

    AsyncClass(IAsyncResponsive response){
        asyncResponse = response;
    }
    @Override public final void  run(){
        asyncResponse.asyncStart(this);
        asyncResponse.asyncEnd(this, runasync());
    }
    protected abstract Object runasync();
}
