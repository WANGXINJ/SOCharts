package com.storedobject.chart.property;

public class DateFormat extends Format {

	final private String pattern;

	public DateFormat(String dataKey, String pattern) {
		super("d", dataKey);

		this.pattern = pattern;
	}

	@Override
	protected String pattern() {
		return "'" + pattern + "'";
	}

	@Override
	public DateFormat clone(String dataKey) {
		return new DateFormat(dataKey, pattern);
	}
}
