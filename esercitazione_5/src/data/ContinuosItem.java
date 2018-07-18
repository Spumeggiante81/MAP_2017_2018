package data;


public class ContinuosItem extends Item {

	 ContinuosItem(Attribute attribute, double value) {
		super(attribute, value);
	}

	@Override
	public double distance(Object a) {
		// TODO Auto-generated method stub
		double valore;
		ContinuosAttribute valore2 = new ContinuosAttribute(null, 0, 3.2, 38.7);
		
		valore = Math.abs((double) this.getValue()-valore2.getScaledValue((double) a));
	
		return valore;
	}
}
		
