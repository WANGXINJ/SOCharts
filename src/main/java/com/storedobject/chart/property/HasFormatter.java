package com.storedobject.chart.property;

import static com.storedobject.chart.util.ComponentPropertyUtil.toObjectArray;

public interface HasFormatter<PROPERTY extends ComponentProperty> {

	public String getFormatter();

	public PROPERTY setFormatter(String formatter, Format... formats);

	public default PROPERTY setFormatter(Format... formats) {
		if (formats == null || formats.length == 0) {
			@SuppressWarnings("unchecked")
			PROPERTY _this = (PROPERTY) this;
			return _this;
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < formats.length; i++) {
			sb.append("%s");
		}

		return setFormatter(sb.toString(), formats);
	}

	public default PROPERTY setFormatter(Format format) {
		return setFormatter("%s", format);
	}

	public default String toFormatter(String formatter, Format... formats) {
		return String.format(formatter, toObjectArray(formats));
	}
}
