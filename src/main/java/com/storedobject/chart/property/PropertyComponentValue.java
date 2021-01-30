package com.storedobject.chart.property;

import static com.storedobject.chart.util.ComponentPropertyUtil.beginValueNode;
import static com.storedobject.chart.util.ComponentPropertyUtil.endNode;

public class PropertyComponentValue extends AbstractComponentProperty implements PropertyValue {

	@Override
	public StringBuilder encodeValue(StringBuilder sb) {
		buildProperties();
		if (isEmpty()) {
			return sb;
		}

		beginValueNode(sb);
		encodeProperties(sb);
		endNode(sb);

		return sb;
	}

	@Override
	public String toString() {
		return encodeValue(new StringBuilder()).toString();
	}

	@Override
	public PropertyComponentValueWrapper asProperty(String name) {
		return new PropertyComponentValueWrapper(name, this);
	}

	public static BaseComponentProperty toComponentProperty(String name, PropertyComponentValue propertyValue) {
		return name != null && propertyValue != null ? propertyValue.asProperty(name) : null;
	}
}
