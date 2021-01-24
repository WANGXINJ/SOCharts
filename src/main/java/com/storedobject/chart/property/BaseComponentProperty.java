package com.storedobject.chart.property;

import static com.storedobject.chart.util.ComponentPropertyUtil.beginNode;
import static com.storedobject.chart.util.ComponentPropertyUtil.endNode;

public class BaseComponentProperty extends AbstractComponentProperty {

	final String name;

	public BaseComponentProperty(String name) {
		this.name = name;
	}

	@Override
	public void encodeJSON(StringBuilder sb) {
		buildProperties();
		if (isEmpty())
			return;

		beginNode(name, sb);
		encodeProperties(sb);
		endNode(sb);
	}
}
