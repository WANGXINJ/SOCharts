package com.storedobject.chart.property;

public class StringFormat extends Format {

	public StringFormat(String dataKey) {
		super(null, dataKey);
	}

	@Override
	protected String pattern() {
		return "";
	}

	@Override
	public StringFormat clone(String dataKey) {
		return new StringFormat(dataKey);
	}
}
