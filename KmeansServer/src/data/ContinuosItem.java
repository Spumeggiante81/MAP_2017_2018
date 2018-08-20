package data;


public class ContinuosItem extends Item {

	ContinuosItem(Attribute attribute, double value) {
		super(attribute, value);
	}

	/**
	 * Ricava la distanza tra il valore scalare dell'item corrente con quello passato in parametro
	 */
	public double distance(Object a) {		
		//return Math.abs(((ContinuosAttribute)((ContinuosItem)a).getAttribute()).getScaledValue((double)this.getValue()));
		double currentScaledValue = ((ContinuosAttribute)this.getAttribute()).getScaledValue((double)this.getValue());
		ContinuosItem parameterItem = (ContinuosItem) a;
		double parameterScaledValue = ((ContinuosAttribute)parameterItem.getAttribute()).getScaledValue((double)parameterItem.getValue());
		return currentScaledValue - parameterScaledValue;
	}

	/**
	 * Controlla se l'oggetto corrente è uguale all'oggetto da confrontare
	 * @return true se l'oggetto è uguale, altrimenti false
	 */
	public boolean equals(Object obj) {
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
	}
}

