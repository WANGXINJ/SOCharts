package com.storedobject.chart.property;

public class PercentFormat extends AbstractNumberFormat {

	public PercentFormat(String dataKey, int fraction, boolean kSeparate) {
		super("p", dataKey, fraction, kSeparate);
	}

	@Override
	public PercentFormat clone(String dataKey) {
		return new PercentFormat(dataKey, getFraction(), isKSeparate());
	}
}
