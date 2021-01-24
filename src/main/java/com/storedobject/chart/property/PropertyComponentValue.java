package com.storedobject.chart.property;

import static com.storedobject.chart.util.ComponentPropertyUtil.beginValueNode;
import static com.storedobject.chart.util.ComponentPropertyUtil.endNode;

public class PropertyComponentValue extends AbstractComponentProperty implements PropertyValue {

	@Override
	public void encodeValue(StringBuilder sb) {
		buildProperties();
		if (isEmpty())
			return;

		beginValueNode(sb);
		encodeProperties(sb);
		endNode(sb);
	}

	@Override
	public BaseComponentProperty asProperty(String name) {
		BaseComponentProperty componentProperty = new BaseComponentProperty(name);
		componentProperty.copyProperties(this);
		return componentProperty;
	}

	public static BaseComponentProperty toComponentProperty(String name, PropertyComponentValue propertyValue) {
		return name != null && propertyValue != null ? propertyValue.asProperty(name) : null;
	}
}
