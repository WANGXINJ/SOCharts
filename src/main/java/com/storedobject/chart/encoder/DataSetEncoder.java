package com.storedobject.chart.encoder;

import com.storedobject.chart.data.AbstractDataProvider;

public class DataSetEncoder extends ComponentEncoder {

	public DataSetEncoder() {
		super("dataset", AbstractDataProvider.class);
	}

	@Override
	protected void begin(StringBuilder sb) {
		sb.append("{\"source\":{");
	}

	@Override
	protected void partBegin(StringBuilder sb) {
		// NOOP
	}

	@Override
	protected void partEnd(StringBuilder sb) {
		// NOOP
	}

	@Override
	protected void end(StringBuilder sb) {
		sb.append("}}");
	}
}
