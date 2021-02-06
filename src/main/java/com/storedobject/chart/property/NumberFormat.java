package com.storedobject.chart.property;

public class NumberFormat extends AbstractNumberFormat {

	public NumberFormat(String dataKey, int fraction, boolean kSeparate) {
		super("n", dataKey, fraction, kSeparate);
	}

	@Override
	public NumberFormat clone(String dataKey) {
		return new NumberFormat(dataKey, getFraction(), isKSeparate());
	}
}
