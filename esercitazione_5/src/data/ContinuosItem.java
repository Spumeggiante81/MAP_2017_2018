package data;


public class ContinuosItem extends Item {

	ContinuosItem(Attribute attribute, double value) {
		super(attribute, value);
	}

	@Override
	public double distance(Object a) {
		// TODO Auto-generated method stub
		double valore = 0;
		ContinuosAttribute valore2 = null;
		
		valore = Math.abs((double) this.getValue()-valore2.getScaledValue((double) a));
	
		return valore;
	}
}
		
