package data;

//modifica 20Maggio2018
 class ContinuosAttribute extends Attribute 
{
	private double max; 
	private double min;  
	 
	
	/**
	 * Estende attribute e modella un attributo continuo numerico
	 * @param name
	 * @param index
	 * @param min
	 * @param max
	 */
	public ContinuosAttribute(String name, int index, double min, double max) {
		super(name, index);
		this.max = max;
		this.min = min;
	}

	/**
	 * rappresentano gli estremi dell'intervallo di valori che l'attributo può realmente assumere
	 * @return valore normalizzato 
	 */
    double getScaledValue(double v) {
    	double v2; 
    	v2=(v-min)/(max-min); 
    	return v2; 
    }
    
	

}
