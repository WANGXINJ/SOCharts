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
	public BaseArrayProperty asProperty(String name) {
		BaseArrayProperty componentProperty = new BaseArrayProperty(name);
		componentProperty.copyProperties(this);
		return componentProperty;
	}

	public static BaseArrayProperty toArrayProperty(String name, PropertyValueArray propertyValue) {
		return name != null && propertyValue != null ? propertyValue.asProperty(name) : null;
	}

	@Override
	public String toString() {
		return encodeValue(new StringBuilder()).toString();
	}
}
