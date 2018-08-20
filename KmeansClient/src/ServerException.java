
public class ServerException extends Exception{

	private String message;

	public ServerException(){

	}

	public ServerException(String message){
		this.message = message;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}
