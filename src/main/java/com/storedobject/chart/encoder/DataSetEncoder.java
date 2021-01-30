package com.storedobject.chart.encoder;

import com.storedobject.chart.component.ComponentParts;
import com.storedobject.chart.data.AbstractDataProvider;

public class DataSetEncoder extends ComponentEncoder {

	public DataSetEncoder() {
		super("dataset", AbstractDataProvider.class);
	}

	@Override
	public void encode(StringBuilder sb, ComponentParts parts) {
		if (parts.isSkippingData() || !parts.isDataSetEncoding())
			return;

		super.encode(sb, parts);
	}

	@Override
	protected void begin(StringBuilder sb, int partCount) {
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
	protected void end(StringBuilder sb, int partCount) {
		sb.append("}}");
	}
}
