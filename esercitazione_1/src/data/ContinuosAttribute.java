package data;

 class ContinuosAttribute extends Attribute 
{
	private double max; 
	private double min;  
	 
	public ContinuosAttribute(String name, int index, double min, double max) {
		super(name, index);
		this.max = max;
		this.min = min;
	}

	/**
	 * rappresentano gli estremi dell'intervallo di valori che l'attributo può realmente assumere 
	 */
    double getScaledValue(double v) {
    	double v2; 
    	v2=(v-min)/(max-min); 
    	return v2; 
    }
	

}
