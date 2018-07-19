package data;


public class ContinuosItem extends Item {

	 ContinuosItem(Attribute attribute, double value) {
		super(attribute, value);
	}

	@Override
	public double distance(Object a) {		
		return Math.abs(((ContinuosAttribute)((ContinuosItem)a).getAttribute()).getScaledValue((double)this.getValue()));
	}
}
		
