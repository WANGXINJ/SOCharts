package com.storedobject.chart.property;

public class PropertyValueArray extends AbstractArrayProperty implements PropertyValue {

	public PropertyValueArray() {
		super(null);
	}

	@Override
	public StringBuilder encodeValue(StringBuilder sb) {
		encodeArray(sb);
		return sb;
	}

	@Override
	public ValueArrayProperty asProperty(String name) {
		ValueArrayProperty arrayProperty = new ValueArrayProperty(name);
		arrayProperty.copyProperties(this);
		return arrayProperty;
	}

	@Override
	public String toString() {
		return encodeValue(new StringBuilder()).toString();
	}

	public static ValueArrayProperty toValueArrayProperty(String name, PropertyValueArray propertyValue) {
		return name != null && propertyValue != null ? propertyValue.asProperty(name) : null;
	}
}
