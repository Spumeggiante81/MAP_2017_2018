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
	}

	/**Restituisce valore 0 oppure 1
	 * @param a
	 * @return 0 se la condizione è verificata 1 altrimenti
	 */
	public double distance(Object a){
		return equals(a) == true ? 0 : 1;
		/*
		invoca il metodo equals  sull'oggetto su cui è invocato distance passando a 
		 */ 
	}

	/**
	 * Controlla se l'oggetto corrente è uguale all'oggetto da confrontare
	 * @return true se l'oggetto è uguale, altrimenti false
	 */
	@Override
	public boolean equals(Object obj) {
		/*System.out.println(
    			this.getAttribute().getName() + " - "  + ((DiscreteItem) obj).getAttribute().getName()
    			+ " - valori: " + this.getValue() + " - " + ((DiscreteItem) obj).getValue()
    		);*/
		if (this == obj)//si sta conforntando due oggetti
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DiscreteItem other = (DiscreteItem) obj;
		if (this.getAttribute().getName().compareTo(other.getAttribute().getName()) != 0)
			return false;
		try{
			if (!(this.getValue().equals(other.getValue())))
				return false;
		} catch(NullPointerException e){
			if (other.getValue() != null)
				return false;
		}

		return true;
		/*if (this.getValue() == null) {
            if (other.getValue() != null)
                return false;
        } else if (!this.getValue().equals(other.getValue()))
            return false;
        return true;*/
	}
}
