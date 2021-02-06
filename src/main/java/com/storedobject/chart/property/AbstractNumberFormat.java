package com.storedobject.chart.property;

public abstract class AbstractNumberFormat extends Format {

	final private int fraction;
	final private boolean kSeparate;

	protected AbstractNumberFormat(String key, String dataKey, int fraction, boolean kSeparate) {
		super(key, dataKey);

		this.fraction = fraction;
		this.kSeparate = kSeparate;
	}

	public int getFraction() {
		return fraction;
	}

	public boolean isKSeparate() {
		return kSeparate;
	}

	@Override
	protected String pattern() {
		return (kSeparate ? "," : "") + "." + fraction;
	}
}
