package com.storedobject.chart.property;

public interface PropertyValue {

	public StringBuilder encodeValue(StringBuilder sb);

	public ComponentProperty asProperty(String name);

	public static ComponentProperty toProperty(String name, PropertyValue propertyValue) {
		return name != null && propertyValue != null ? propertyValue.asProperty(name) : null;
	}
}
