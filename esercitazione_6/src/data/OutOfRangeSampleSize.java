package data;

public class OutOfRangeSampleSize extends Exception{
	
	private String message;
	
	public OutOfRangeSampleSize (String message){
		this.message = message;
	}

	public String getMessage (){
		return message;
	}
}
