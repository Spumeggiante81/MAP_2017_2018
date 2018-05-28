package data;


/**
 * Estende la classe Item
 * Rappresenta una coppia attributo - valore discreto
 * @author Dany
 *
 */
public class DiscreteItem extends Item{

	/**
	 * Invoca il costruttore della classe madre
	 * @param attribute 
	 * @param value
	 */
	DiscreteItem(DiscreteAttribute attribute, String value) {
		super(attribute, value);
		// TODO Auto-generated constructor stub
	}


	//public DiscreteItem(Attribute attribute) {
	//}


	/**Restituisce valore 0 oppure 1
	 * @param a
	 * @return 0 se la condizione è verificata 1 altrimenti
	 */
	public double distance(Object a){
		if(getValue().equals(a))
			return 0;
		else
			return 1;
		
	
}
	
	
	
	

}
