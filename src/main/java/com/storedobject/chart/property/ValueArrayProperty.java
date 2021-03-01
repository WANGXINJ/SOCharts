package com.storedobject.chart.property;

import java.util.Arrays;

public class ValueArrayProperty extends AbstractArrayProperty implements NamedProperty {

	public ValueArrayProperty(String name) {
		super(name);
	}

	@Override
	public void encodeJSON(StringBuilder sb) {
		encodeArray(sb);
	}

	public static <VALUE extends PropertyValue> ValueArrayProperty toValueArrayProperty(String name, VALUE[] values) {
		if (name == null || values == null) {
			return null;
		}

		return toValueArrayProperty(name, Arrays.asList(values));
	}

	public static <VALUE extends PropertyValue> ValueArrayProperty toValueArrayProperty(String name,
			Iterable<VALUE> values) {
		if (name == null || values == null) {
			return null;
		}

		ValueArrayProperty arrayProperty = new ValueArrayProperty(name);
		for (VALUE value : values) {
			arrayProperty.addPropertyValue(value);
		}
		return arrayProperty;
	}
}
