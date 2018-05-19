package esercitazione_1;

 class ContinuosAttribute extends Attribute 
{
	 private double max; 
	private double min;  
	 
	public ContinuosAttribute(String name2, int index2, double min, double max) {
		super(name2, index2);
		// TODO Auto-generated constructor stub
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
